package doggytalents.common.block.tileentity;

import doggytalents.DoggyRegistries;
import doggytalents.DoggyTileEntityTypes;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.backward_imitate.CompoundTag_1_21_5;
import doggytalents.api.backward_imitate.CompoundTag_1_21_7;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.backward_imitate.NBTUtil_1_21_7;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.entity.Dog;
import doggytalents.common.storage.DogLocationData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.util.NBTUtil;
import doggytalents.common.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.model.data.ModelProperty;

import javax.annotation.Nullable;
import java.util.UUID;

public class DogBedTileEntity extends PlacedTileEntity {

    private ICasingMaterial casingType = DogBedMaterialManager.NaniCasing.NULL;
    private IBeddingMaterial beddingType = DogBedMaterialManager.NaniBedding.NULL;


    public static ModelProperty<ICasingMaterial> CASING = new ModelProperty<>();
    public static ModelProperty<IBeddingMaterial> BEDDING = new ModelProperty<>();
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    private @Deprecated @Nullable Dog dog;
    private @Nullable UUID dogUUID;

    private @Nullable Component name;
    private @Nullable Component ownerName;

    public DogBedTileEntity(BlockPos pos, BlockState blockState) {
        super(DoggyTileEntityTypes.DOG_BED.get(), pos, blockState);
    }

    @Override
    public void loadAdditional(ValueInput compound_1_21_5) {
        super.loadAdditional(compound_1_21_5);
        var compound = CompoundTag_1_21_5.wrap(compound_1_21_5); // 1.21.5+

        this.casingType = DogBedMaterialManager.getCasing(compound, "casingId");
        this.beddingType = DogBedMaterialManager.getBedding(compound, "beddingId");

        this.dogUUID = NBTUtil.getUniqueId(compound, "ownerId");
        this.name = NBTUtil.getTextComponent(compound, "name");
        this.ownerName = NBTUtil.getTextComponent(compound, "ownerName");
        this.requestModelDataUpdate();
    }

    @Override
    public void saveAdditional(ValueOutput compound) {
        super.saveAdditional(compound);

        NBTUtil_1_21_7.putRegistryValue(compound, "casingId", DogBedMaterialManager.getKey( this.casingType) );
        NBTUtil_1_21_7.putRegistryValue(compound, "beddingId", DogBedMaterialManager.getKey( this.beddingType) );

        NBTUtil.putUniqueId(CompoundTag_1_21_7.wrap(compound), "ownerId", this.dogUUID);
        NBTUtil_1_21_7.putTextComponent(compound, "name", this.name);
        NBTUtil_1_21_7.putTextComponent(compound, "ownerName", this.ownerName);
    }

    public void setCasing(ICasingMaterial casingType) {
        this.casingType = casingType;
        this.setChanged();
        this.requestModelDataUpdate();
    }

    public void setBedding(IBeddingMaterial beddingType) {
        this.beddingType = beddingType;
        this.setChanged();
        this.requestModelDataUpdate();
    }

    public ICasingMaterial getCasing() {
        return this.casingType;
    }

    public IBeddingMaterial getBedding() {
        return this.beddingType;
    }

    @Override
    public ModelData getModelData() {
        var state = this.getBlockState();
        var facing = Direction.NORTH;
        if (state != null && state.hasProperty(DogBedBlock.FACING)) {
            facing = state.getValue(DogBedBlock.FACING);
        }
        return ModelData.builder()
                .with(CASING, this.casingType)
                .with(BEDDING, this.beddingType)
                .with(FACING, facing)
                .build();
    }

    public void setOwner(@Nullable Dog owner) {
        this.setOwner(owner == null ? null : owner.getUUID());

        this.dog = owner;

    }

    public void setOwner(@Nullable UUID owner) {
        this.dog = null;
        this.dogUUID = owner;

        this.setChanged();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.dogUUID;
    }

    @Nullable
    public Dog getOwner() {
       return WorldUtil.getCachedEntity(this.level, Dog.class, this.dog, this.dogUUID);
    }

    @Nullable
    public Component getBedName() {
        return this.name;
    }

    @Nullable
    public Component getOwnerName() {
        if (this.dogUUID == null || this.level == null) { return null; }

        DogLocationData locData = DogLocationStorage
                .get(this.level)
                .getData(this.dogUUID);

        if (locData != null) {
            Component text = locData.getName();
            if (text != null) {
                this.ownerName = text;
            }
        }

        return this.ownerName;
    }

    public boolean shouldDisplayName(LivingEntity camera) {
        return true;
    }

    public void setBedName(@Nullable Component nameIn) {
        this.name = nameIn;
        this.setChanged();
    }
}
