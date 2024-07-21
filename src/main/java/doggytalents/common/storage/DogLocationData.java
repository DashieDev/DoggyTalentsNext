package doggytalents.common.storage;

import doggytalents.DoggyAccessories;
import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyItems;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.api.feature.EnumGender;
import doggytalents.api.registry.AccessoryType;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.LocatorOrbAccessory;
import doggytalents.common.util.NBTUtil;
import doggytalents.common.util.WorldUtil;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class DogLocationData implements IDogData {

    private final DogLocationStorage storage;
    private final UUID uuid;
    private @Nullable UUID ownerId;
    private @Nullable UUID sessionUUID;

    // Location data
    private @Nullable Vec3 position;
    private @Nullable ResourceKey<Level> dimension;

    // Other saved data
    private @Nullable Component name;
    private @Nullable Component ownerName;
    private @Nullable EnumGender gender;
    private boolean hasRadarCollar;
    private int locateColor;

    protected DogLocationData(DogLocationStorage storageIn, UUID uuid) {
        this.storage = storageIn;
        this.uuid = uuid;
    }

    @Override
    public UUID getDogId() {
        return this.uuid;
    }

    @Override
    @Nullable
    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Nullable
    public UUID getSessionUUID() {
        return this.sessionUUID;
    }

    public void setSessionUUID(UUID sessionUUID) {
        this.sessionUUID = sessionUUID;
        this.storage.setDirty();
    }

    @Override
    public String getDogName() {
        return this.name == null ? "" : this.name.getString();
    }

    @Override
    public String getOwnerName() {
        return this.ownerName == null ? "" : this.ownerName.getString();
    }

    public void populate(Dog dogIn) {
        this.update(dogIn);
    }

    public void update(Dog dogIn) {
        this.ownerId = dogIn.getOwnerUUID();
        this.position = dogIn.position();
        this.dimension = dogIn.level.dimension();

        this.name = dogIn.getName();
        this.ownerName = dogIn.getOwnersName().orElse(null);
        this.gender = dogIn.getGender();

        updateLocator(dogIn);

        this.storage.setDirty();
    }

    private void updateLocator(Dog dog) {
        var locatorOptional = dog.getAccessory(DoggyAccessoryTypes.BAND.get());
        this.hasRadarCollar = locatorOptional.isPresent();
        if (!this.hasRadarCollar) 
            return;
        var locator = locatorOptional.get();
        if (locator.getAccessory() instanceof LocatorOrbAccessory orb) {
            this.locateColor = orb.getOrbColor();
        }
    }


    public void read(CompoundTag compound) {
        this.ownerId = NBTUtil.getUniqueId(compound, "ownerId");
        this.position = NBTUtil.getVector3d(compound);
        this.dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, NBTUtil.getResourceLocation(compound, "dimension"));
        this.name = NBTUtil.getTextComponent(compound, "name_text_component");
        if (compound.contains("gender", Tag.TAG_STRING)) {
            this.gender = EnumGender.bySaveName(compound.getString("gender"));
        }
        this.hasRadarCollar = compound.getBoolean("collar");
        if (compound.hasUUID("sessionUUID")) {
            this.sessionUUID = compound.getUUID("sessionUUID");
        }
        this.locateColor = compound.getInt("locateOrbColor");
    }

    public CompoundTag write(CompoundTag compound) {
        NBTUtil.putUniqueId(compound, "ownerId", this.ownerId);
        NBTUtil.putVector3d(compound, this.position);
        NBTUtil.putResourceLocation(compound, "dimension", this.dimension.location());
        NBTUtil.putTextComponent(compound, "name_text_component", this.name);
        if (this.gender != null) {
            compound.putString("gender", this.gender.getSaveName());
        }
        compound.putBoolean("collar", this.hasRadarCollar);
        if (this.sessionUUID != null) {
            compound.putUUID("sessionUUID", this.sessionUUID);
        }
        compound.putInt("locateOrbColor", locateColor);
        return compound;
    }

    public static DogLocationData from(DogLocationStorage storageIn, Dog dogIn) {
        DogLocationData locationData = new DogLocationData(storageIn, dogIn.getUUID());
        locationData.populate(dogIn);
        return locationData;
    }

    public boolean shouldDisplay(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (ConfigHandler.SERVER.ALLOW_TRACK_ANY_DOG.get())
            return true;
        return this.hasRadarCollar || playerIn.isCreative() || playerIn.getItemInHand(handIn).getItem() == DoggyItems.CREATIVE_CANINE_TRACKER.get();
    }

    public Optional<Dog> getOnlineDog() {
        return this.storage.getOnlineDogsManager().getOnlineDog(this.uuid);
    }

    @Nullable
    public Component getName(@Nullable Level worldIn) {
        return this.getOnlineDog().map(Dog::getDisplayName).orElse(this.name);
    }

    @Nullable
    public Vec3 getPos(@Nullable ServerLevel worldIn) {
        return this.getOnlineDog().map(Dog::position).orElse(this.position);
    }

    @Nullable
    public Vec3 getPos() {
        return this.position;
    }

    @Nullable
    public ResourceKey<Level> getDimension() {
        return this.dimension;
    }

    public int getLocateColor() {
        return this.locateColor;
    }

    @Override
    public String toString() {
        return "DogLocationData [uuid=" + uuid + ", owner=" + ownerId + ", position=" + position + ", dimension=" + dimension + ", name=" + name + ", gender=" + gender + ", hasRadarCollar=" + hasRadarCollar + "]";
    }


}
