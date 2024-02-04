package doggytalents.common.storage;

import com.google.common.collect.Lists;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTalentsNext;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogIncapacitatedMananger.BandaidState;
import doggytalents.common.entity.DogIncapacitatedMananger.DefeatedType;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.ai.triggerable.DogDrownAction;
import doggytalents.common.entity.anim.DogAnimation;
import doggytalents.common.util.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DogRespawnData implements IDogData {

    private final DogRespawnStorage storage;
    private final UUID uuid;
    private UUID ownerUUID;
    private CompoundTag data;
    private IncapacitatedSyncState killedBy = IncapacitatedSyncState.NONE;

    //TODO Make it list you can only add too
    private static final List<String> TAGS_TO_REMOVE = Lists.newArrayList(
            "Pos", "Health", "Motion", "Rotation", "FallDistance", "Fire", "Air", "OnGround",
            "Dimension", "PortalCooldown", "Passengers", "Leash", "InLove", "Leash", "HurtTime",
            "HurtByTimestamp", "DeathTime", "AbsorptionAmount", "FallFlying", "Brain", "Sitting", "ActiveEffects"); // Remove dog mode

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
        Component name = NBTUtil.getTextComponent(this.data, "CustomName");
        return name == null ? "noname" : name.getString();
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
        dogIn.saveWithoutId(this.data);
        this.ownerUUID = dogIn.getOwnerUUID();
        var deathCauseOptional = dogIn.getDogDeathCause();
        if (deathCauseOptional.isPresent()) {
            this.killedBy = dogIn.createIncapSyncState(deathCauseOptional.get());
        }

        // Remove tags that don't need to be saved
        for (String tag : TAGS_TO_REMOVE) {
            this.data.remove(tag);
        }

        var extraTagsToRemove = ConfigHandler.RESPAWN_TAGS.TAGS_TO_REMOVE.get();
        for (String tag : extraTagsToRemove) {
            this.data.remove(tag);
        }

        this.data.remove("UUID");
        this.data.remove("LoveCause");

        //Duplication Detection
        this.data.remove("DTN_DupeDetect_UUID");
    }

    @Nullable
    public Dog respawn(ServerLevel worldIn, Player playerIn, BlockPos pos) {
        Dog dog = DoggyEntityTypes.DOG.get().create(worldIn, null, null, null, pos, MobSpawnType.TRIGGERED, true, false);

        // Failed for some reason
        if (dog == null) {
            return null;
        }

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
            dog.setMode(EnumMode.INCAPACITATED);
            dog.setHealth(1);
            dog.setIncapSyncState(killedBy);
            if (dog.isInWater() || dog.isInLava()) {
                dog.triggerAnimationAction(new DogDrownAction(dog));
            } else
            dog.setAnim(dog.incapacitatedMananger.getAnim());
        } else {
            dog.setMode(EnumMode.DOCILE);
            dog.setAnim(DogAnimation.STAND_QUICK);
        }

        worldIn.addFreshEntityWithPassengers(dog);
        DogLocationStorage.setSessionUUIDFor(dog, uuid);
        

        return dog;
    }

    public void read(CompoundTag compound) {
        this.data = compound.getCompound("data");
        if (compound.hasUUID("owner_uuid")) {
            this.ownerUUID = compound.getUUID("owner_uuid");
        }
        readKilledBy(compound);
    }

    public CompoundTag write(CompoundTag compound) {
        compound.put("data", this.data);
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
}
