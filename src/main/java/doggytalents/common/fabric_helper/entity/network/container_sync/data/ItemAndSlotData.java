package doggytalents.common.fabric_helper.entity.network.container_sync.data;

// import doggytalents.common.util.NetworkUtil;
// import net.minecraft.network.RegistryFriendlyByteBuf;
// import net.minecraft.network.codec.StreamCodec;
// import net.minecraft.world.item.ItemStack;

public class ItemAndSlotData {
    
    // public final ItemStack val;
    // public final int slot;

    // public ItemAndSlotData(ItemStack val, int slot) {
    //     this.val = val == null ? ItemStack.EMPTY : val;
    //     this.slot = slot;
    // }

    // public static StreamCodec<RegistryFriendlyByteBuf, ItemAndSlotData>
    //     CODEC = StreamCodec.of(
    //         (buf, val) -> {
    //             buf.writeByte((byte) val.slot);
    //             NetworkUtil.writeItemToBuf(buf, val.val);   
    //         }, 
    //         buf -> {
    //             int slot = (int) buf.readByte();
    //             var item = NetworkUtil.readItemFromBuf(buf);
    //             return new ItemAndSlotData(item, slot);
    //         }
    //     );


}
