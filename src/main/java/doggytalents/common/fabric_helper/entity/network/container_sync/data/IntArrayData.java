package doggytalents.common.fabric_helper.entity.network.container_sync.data;

import java.util.ArrayList;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class IntArrayData {
    
    public final ArrayList<Integer> val;

    public IntArrayData(ArrayList<Integer> val) {
        this.val = val;
    }

    public static StreamCodec<RegistryFriendlyByteBuf, IntArrayData>
        CODEC = StreamCodec.of(
            (buf, val) -> {
                buf.writeInt(val.val.size());
                for (int i = 0; i < val.val.size(); ++i) {
                    buf.writeInt(val.val.get(i));
                }
            }, 
            buf -> {
                int size = buf.readInt();
                var list = new ArrayList<Integer>(size);
                for (int i = 0; i < size; ++i) {
                    list.add(buf.readInt());
                }
                return new IntArrayData(list);
            }
        );

}
