package doggytalents.common.storage;

import com.google.common.collect.Lists;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTalentsNext;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogIncapacitatedMananger.BandaidState;
import doggytalents.common.entity.DogIncapacitatedMananger.DefeatedType;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.ai.triggerable.DogDrownAction;
import doggytalents.common.util.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DogRespawnData implements IDogData {

    private final DogRespawnStorage storage;
    private final UUID uuid;
    private UUID ownerUUID;
    private Optional<String> dogName = Optional.empty();
    private CompoundTag data;
    private IncapacitatedSyncState killedBy = IncapacitatedSyncState.NONE;

    //TODO Make it list you can only add too
    private static final List<String> TAGS_TO_REMOVE = Lists.newArrayList(
            "Pos", "Health", "Motion", "Rotation", "FallDistance", "Fire", "Air", "OnGround",
            "Dimension", "PortalCooldown", "Passengers", "Leash", "InLove", "Leash", "HurtTime",
            "HurtByTimestamp", "DeathTime", "AbsorptionAmount", "FallFlying", "Brain", "Sitting", "ActiveEffects"); // Remove dog mode

    private static final List<String> IMPORTANT_TAGS = List.of(
        "Owner", "friendlyFire", "UUID", "talents", "level_normal", "level_kami", "dogGender",
        "dogSize", "entityKills", "accessories", "doggytalents_dog_skin"
    );

    private static final String STORAGE_AGE_TAG = "DogRespawn_Age";
    private static final String STORAGE_OWNER_TAG = "DogRespawn_ownerUUID";
    private static final String STORAGE_NAME_TAG = "DogRespawn_dogName";

    protected DogRespawnData(DogRespawnStorage storageIn, UUID uuid) {
        this.storage = storageIn;
        this.uuid = uuid;
    }

    @Override
    public UUID getDogId() {
        return this.uuid;
    }

    @Override
    public String getDogName() {
        var name = this.dogName.orElse(null);
        return name == null ? "noname" : name;
    }

    @Override
    public UUID getOwnerId() {
        return this.ownerUUID;
    }

    @Override
    public String getOwnerName() {
        Component name = NBTUtil.getTextComponent(this.data, "lastKnownOwnerName");
        return name == null ? "" : name.getString();
    }

    public void populate(Dog dogIn) {
        this.data = new CompoundTag();
        
        this.ownerUUID = dogIn.getOwnerUUID();
        var customName = dogIn.getCustomName();
        if (customName != null) {
            this.dogName = Optional.ofNullable(customName.getString());
        } 

        var deathCauseOptional = dogIn.getDogDeathCause();
        if (deathCauseOptional.isPresent()) {
            this.killedBy = dogIn.createIncapSyncState(deathCauseOptional.get());
        }

        var strategy = fetchDataStrategy();
        if (strategy == DataStrategy.KEEP_ALL_EXCEPT) {
            writeAlldataAndRemoveSpecific(dogIn, this.data);
        } else {
            writeImportantDataToKeep(dogIn, this.data);
        }
        
        this.data.remove("UUID");
        this.data.remove("LoveCause");

        //Duplication Detection
        this.data.remove("DTN_DupeDetect_UUID");
    }

    private void writeAlldataAndRemoveSpecific(Dog dog, CompoundTag target) {
        dog.saveWithoutId(target);
        // Remove tags that don't need to be saved
        for (String tag : TAGS_TO_REMOVE) {
            target.remove(tag);
        }
        target.remove(STORAGE_AGE_TAG);
        target.remove(STORAGE_OWNER_TAG);
        target.remove(STORAGE_NAME_TAG);
        try {
            var extraTagsToRemove = ConfigHandler.RESPAWN_TAGS.TAGS_TO_REMOVE.get();
            for (String tag : extraTagsToRemove) {
                if (IMPORTANT_TAGS.contains(tag))
                    continue;
                target.remove(tag);
            }
        } catch (Exception e) {
        }
    }

    private void writeImportantDataToKeep(Dog dog, CompoundTag target) {
        dog.addDTNAdditionalSavedData(target);
        target.putInt(STORAGE_AGE_TAG, dog.getAge());
        var owner_uuid = dog.getOwnerUUID();
        if (owner_uuid != null) {
            target.putUUID(STORAGE_OWNER_TAG, owner_uuid);
        }
        var custom_name = dog.getCustomName();
        if (custom_name != null) {
            target.putString(STORAGE_NAME_TAG, Component.Serializer.toJson(custom_name));
        }
        keepAdditionalTag(target, dog);
    }

    private void keepAdditionalTag(CompoundTag target, Dog dog) {

        try {
            var extraTagsToKeep = ConfigHandler.RESPAWN_TAGS.TAGS_TO_KEEP.get();
            if (extraTagsToKeep == null || extraTagsToKeep.isEmpty())
                return;
            var nonDTNTags = new CompoundTag();
            dog.addNonDTNAdditionalData(nonDTNTags);
            for (var toKeepStr : extraTagsToKeep) {
                if (!nonDTNTags.contains(toKeepStr))
                    continue;
                if (target.contains(toKeepStr))
                    continue;
                var toKeep = nonDTNTags.get(toKeepStr).copy();
                target.put(toKeepStr, toKeep);
            }
        } catch (Exception e) {

        }
    }

    private void restoreAndConsumeImportantDataIfNeeded(Dog dog, CompoundTag tag) {
        if (tag.contains(STORAGE_AGE_TAG, Tag.TAG_INT)) {
            dog.setAge(tag.getInt(STORAGE_AGE_TAG));
            tag.remove(STORAGE_AGE_TAG);
        }
        if (tag.hasUUID(STORAGE_OWNER_TAG)) {
            var correct_owner_uuid = this.ownerUUID;
            try {
                correct_owner_uuid = tag.getUUID(STORAGE_OWNER_TAG);
            } catch (Exception e) {

            }
            dog.setOwnerUUID(correct_owner_uuid);
            dog.setTame(correct_owner_uuid != null);
            tag.remove(STORAGE_OWNER_TAG);
        }
        if (tag.contains(STORAGE_NAME_TAG)) {
            try {
                var name_c1_str = tag.getString(STORAGE_NAME_TAG);
                dog.setDogCustomName(Component.Serializer.fromJson(name_c1_str));
            } catch (Exception e) {
    
            }
            tag.remove(STORAGE_NAME_TAG);
        }
        
    }

    @Nullable
    public Dog respawn(ServerLevel worldIn, Player playerIn, BlockPos pos) {
        Dog dog = DoggyEntityTypes.DOG.get().create(worldIn, null, null, null, pos, MobSpawnType.TRIGGERED, true, false);

        // Failed for some reason
        if (dog == null) {
            return null;
        }
        
        restoreAndConsumeImportantDataIfNeeded(dog, this.data);
        CompoundTag compoundnbt = dog.saveWithoutId(new CompoundTag());
        UUID uuid = dog.getUUID();
        compoundnbt.merge(this.data);
        dog.load(compoundnbt);
        boolean useOldUUID = 
            !ConfigHandler.SERVER.DISABLE_PRESERVE_UUID.get()
            && worldIn.getEntity(this.uuid) == null;
        dog.setUUID(useOldUUID ? this.uuid : uuid);

        dog.setOrderedToSit(true);
        if (killedBy != null && killedBy != IncapacitatedSyncState.NONE) {
            if (dog.getDogIncapValue() <= 0)
                dog.setDogIncapValue(dog.getDefaultInitIncapVal());
            dog.setDogHunger(0);
            dog.setMode(EnumMode.INJURED);
            dog.setHealth(1);
            dog.setIncapSyncState(killedBy);
            if (dog.isInWater() || dog.isInLava()) {
                dog.triggerAnimationAction(new DogDrownAction(dog));
            } else
            dog.setAnim(dog.incapacitatedMananger.getAnim());
        } else {
            dog.setMode(EnumMode.DOCILE);
            dog.setAnim(DogAnimation.STAND_QUICK);
            dog.maxHealth();
        }

        worldIn.addFreshEntityWithPassengers(dog);
        DogLocationStorage.setSessionUUIDFor(dog, uuid);
        

        return dog;
    }

    public void read(CompoundTag compound) {
        this.data = compound.getCompound("data");
        if (compound.contains("dog_name", Tag.TAG_STRING)) {
            try {
                var name_str = compound.getString("dog_name");
                this.dogName = Optional.ofNullable(name_str);
            } catch (Exception e) {}
        }
        if (compound.hasUUID("owner_uuid")) {
            this.ownerUUID = compound.getUUID("owner_uuid");
        }
        readKilledBy(compound);
    }

    public CompoundTag write(CompoundTag compound) {
        compound.put("data", this.data);
        if (this.dogName.isPresent()) { 
            compound.putString("dog_name", this.dogName.get());
        }
        if (this.ownerUUID != null) {
            compound.putUUID("owner_uuid", this.ownerUUID);
        }
        writeKilledBy(compound);
        return compound;
    }

    public void writeKilledBy(CompoundTag compound) {
        if (killedBy == IncapacitatedSyncState.NONE)
            return;
        if (killedBy == null)
            return;
        var killedByTag = new CompoundTag();
        killedByTag.putInt("typeId", killedBy.type.getId());
        killedByTag.putInt("poseId", killedBy.poseId);
        compound.put("dog_killed_by", killedByTag);
    }

    public void readKilledBy(CompoundTag compound) {
        if (!compound.contains("dog_killed_by", Tag.TAG_COMPOUND))
            return;
        var killedByTag = compound.getCompound("dog_killed_by");
        var typeId = killedByTag.getInt("typeId");
        var poseId = killedByTag.getInt("poseId");
        this.killedBy = 
            new IncapacitatedSyncState(DefeatedType.byId(typeId), BandaidState.NONE, poseId);
    }

    private DataStrategy fetchDataStrategy() {
        var stategy_int = ConfigHandler.RespawnTagConfig.getConfig(ConfigHandler.RESPAWN_TAGS.STRATEGY);
        if (stategy_int != 1)
            return DataStrategy.REMOVE_ALL_EXCEPT;
        return DataStrategy.KEEP_ALL_EXCEPT;

    }
    private enum DataStrategy {
        REMOVE_ALL_EXCEPT, KEEP_ALL_EXCEPT
    }
}
