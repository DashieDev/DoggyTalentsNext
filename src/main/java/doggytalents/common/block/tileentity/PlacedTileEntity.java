package doggytalents.common.block.tileentity;

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

import javax.annotation.Nullable;
import java.util.UUID;

public class PlacedTileEntity extends BlockEntity {

    private @Deprecated @Nullable LivingEntity placer;
    private @Nullable UUID placerUUID;

    public PlacedTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState blockState) {
        super(tileEntityTypeIn, pos, blockState);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider prov) {
        super.loadAdditional(compound, prov);

        this.placerUUID = NBTUtil.getUniqueId(compound, "placerId");
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider prov) {
        super.saveAdditional(compound, prov);
        NBTUtil.putUniqueId(compound, "placerId", this.placerUUID);
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
        this.saveAdditional(compound, prov);
        return compound;
    }

    // @Override
    // public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider prov) {
    //     this.loadAdditional(pkt.getTag(), prov);
    // }
}
