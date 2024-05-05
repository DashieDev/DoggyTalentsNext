package doggytalents.common.fabric_helper.entity.network.container_sync.data;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class BlockPosData {
    
    public final BlockPos val;

    public BlockPosData(BlockPos val) {
        this.val = val;
    }

    public static StreamCodec<RegistryFriendlyByteBuf, BlockPosData>
        CODEC = StreamCodec.of(
            (buf, val) -> {
                buf.writeBlockPos(val.val);   
            }, 
            buf -> new BlockPosData(buf.readBlockPos())
        );


}
