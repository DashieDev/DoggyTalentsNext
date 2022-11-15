package doggytalents.common.network.packet;

import doggytalents.common.item.WhistleItem;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.WhistleRequestModeData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class WhistleRequestModePacket implements IPacket<WhistleRequestModeData> {

    @Override
    public void encode(WhistleRequestModeData data, FriendlyByteBuf buf) {
        buf.writeInt(data.id);
    }

    @Override
    public WhistleRequestModeData decode(FriendlyByteBuf buf) {
        return new WhistleRequestModeData(buf.readInt());
    }

    @Override
    public void handle(WhistleRequestModeData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LogicalSide side = ctx.get().getDirection().getReceptionSide();

            if (side.isServer()) {
                var player = ctx.get().getSender();
                var stack = player.getMainHandItem();
                if (stack.getItem() instanceof WhistleItem) {
                    if (!stack.hasTag()) {
                        stack.setTag(new CompoundTag());
                        stack.getTag().putByte("mode", (byte)data.id);
                    } else {
                        stack.getTag().putByte("mode", (byte)data.id);
                    }                
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }
}
