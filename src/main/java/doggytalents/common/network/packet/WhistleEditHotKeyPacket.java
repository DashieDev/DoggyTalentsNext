package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.WhisltleEditHotKeyData;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

public class WhistleEditHotKeyPacket implements IPacket<WhisltleEditHotKeyData> {

    @Override
    public void encode(WhisltleEditHotKeyData data, FriendlyByteBuf buf) {
        buf.writeInt(data.hotkey_id);
        buf.writeInt(data.new_mode_id);
    }

    @Override
    public WhisltleEditHotKeyData decode(FriendlyByteBuf buf) {
        int hotkey_id = buf.readInt();
        int new_mode_id = buf.readInt();
        return new WhisltleEditHotKeyData(hotkey_id, new_mode_id);
    }

    @Override
    public void handle(WhisltleEditHotKeyData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LogicalSide side = ctx.get().getDirection().getReceptionSide();

            if (!side.isServer()) return;
            var player = ctx.get().getSender();
            var stack = player.getMainHandItem();
            if (!(stack.getItem() == DoggyItems.WHISTLE.get())) return;
            var tag = stack.getOrCreateTag();
            if (!tag.contains("hotkey_modes", Tag.TAG_INT_ARRAY)) {
                tag.putIntArray("hotkey_modes", new int[]{-1, -1, -1, -1});
            }
            var keyarr = tag.getIntArray("hotkey_modes");
            if (keyarr == null) return;
            if (keyarr.length != 4) {
                tag.putIntArray("hotkey_modes", new int[]{-1, -1, -1, -1});
                return;
            }
            if (data.hotkey_id >= keyarr.length) return;
            if (data.hotkey_id < 0) return;

            if (data.new_mode_id >= 0)
            for (int i : keyarr) {
                if (i == data.new_mode_id) return;
            }

            keyarr[data.hotkey_id] = data.new_mode_id;
            tag.putIntArray("hotkey_modes", keyarr);
        });

        ctx.get().setPacketHandled(true);
    }
    
}
