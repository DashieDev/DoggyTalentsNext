package doggytalents.common.network.packet;

import doggytalents.common.item.WhistleItem;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.WhistleRequestModeData;
import doggytalents.common.util.ItemUtil;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

import java.util.function.Supplier;

public class WhistleRequestModePacket implements IPacket<WhistleRequestModeData> {

    @Override
    public void encode(WhistleRequestModeData data, FriendlyByteBuf buf) {
        buf.writeInt(data.id);
        buf.writeBoolean(data.dogOnDutyOnly);
    }

    @Override
    public WhistleRequestModeData decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        boolean on_duty_only = buf.readBoolean();
        return new WhistleRequestModeData(id, on_duty_only);
    }

    @Override
    public void handle(WhistleRequestModeData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            //LogicalSide side = ctx.get().getDirection().getReceptionSide();

            if (!ctx.get().isServerRecipent()) return;
            var player = ctx.get().getSender();
            var stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof WhistleItem)) return;
            var tag = ItemUtil.getTag(stack);
            tag.putByte("mode", (byte)data.id);
            tag.putBoolean("dog_on_duty_only", data.dogOnDutyOnly);
            ItemUtil.putTag(stack, tag);
        });

        ctx.get().setPacketHandled(true);
    }
}
