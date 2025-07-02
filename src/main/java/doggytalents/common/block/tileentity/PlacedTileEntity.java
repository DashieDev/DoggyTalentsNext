package doggytalents.common.block.tileentity;

import doggytalents.api.backward_imitate.CompoundTag_1_21_5;
import doggytalents.api.backward_imitate.CompoundTag_1_21_7;
import doggytalents.api.backward_imitate.ValueOutputUtil_1_21_7;
import doggytalents.common.util.NBTUtil;
import doggytalents.common.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlacedTileEntity extends BlockEntity {

    private @Deprecated @Nullable LivingEntity placer;
    private @Nullable UUID placerUUID;

    public PlacedTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState blockState) {
        super(tileEntityTypeIn, pos, blockState);
    }

    @Override
    public void loadAdditional(ValueInput compound) {
        super.loadAdditional(compound);

        this.placerUUID = NBTUtil.getUniqueId(CompoundTag_1_21_5.wrap(compound), "placerId");
    }

    @Override
    public void saveAdditional(ValueOutput compound) {
        super.saveAdditional(compound);
        NBTUtil.putUniqueId(CompoundTag_1_21_7.wrap(compound), "placerId", this.placerUUID);
    }

    public void setPlacer(@Nullable LivingEntity placer) {
        this.placer = placer;
        this.placerUUID = placer == null ? null : placer.getUUID();
        this.setChanged();
    }

    @Nullable
    public UUID getPlacerId() {
        return this.placerUUID;
    }

    @Nullable
    public LivingEntity getPlacer() {
        return WorldUtil.getCachedEntity(this.level, LivingEntity.class, this.placer, this.placerUUID);
    }

    // Sync to client
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider prov) {
        CompoundTag compound = new CompoundTag();
        ValueOutputUtil_1_21_7.outputValueOutputTo(compound, prov, compound_1_21_7 -> this.saveAdditional(compound_1_21_7));
        return compound;
    }

    // @Override
    // public void onDataPacket(Connection net, ValueInput compound_1_21_7) {
    //     this.loadAdditional(compound_1_21_7);
    // }
}
