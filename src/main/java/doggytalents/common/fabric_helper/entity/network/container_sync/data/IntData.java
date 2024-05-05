package doggytalents.common.fabric_helper.entity.network.container_sync.data;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class IntData {
    
    public final int val;

    public IntData(int val) {
        this.val = val;
    }

    public static StreamCodec<RegistryFriendlyByteBuf, IntData>
        CODEC = StreamCodec.of(
            (buf, val) -> {
                buf.writeInt(val.val);   
            }, 
            buf -> new IntData(buf.readInt())
        );

}
