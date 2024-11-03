package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.api.anim.DogAnimation;
import doggytalents.common.item.DogAnimDebugItem;
import doggytalents.common.item.DogAnimDebugItem.ItemMode;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.DogAnimDebugData;
import doggytalents.common.network.packet.data.DogAnimDebugData.UpdateItemSettingsData;
import doggytalents.common.util.ItemUtil;
import net.minecraft.network.FriendlyByteBuf;

public class DogAnimDebugPackets {
    
    public static class UpdateItemSettings implements IPacket<UpdateItemSettingsData> {

        @Override
        public void encode(UpdateItemSettingsData data,
                FriendlyByteBuf buf) {
            buf.writeInt(data.selected.getId());
            buf.writeInt(data.mode.getId());
        }

        @Override
        public UpdateItemSettingsData decode(FriendlyByteBuf buf) {
            var selected = DogAnimation.byId(buf.readInt());
            var mode = ItemMode.fromId(buf.readInt());
            return new UpdateItemSettingsData(selected, mode);
        }

        @Override
        public void handle(UpdateItemSettingsData data,
            Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {
                //LogicalSide side = ctx.get().getDirection().getReceptionSide();

                if (!ctx.get().isServerRecipent())
                    return;
                var sender = ctx.get().getSender();
                var stack = sender.getMainHandItem();
                
                DogAnimDebugItem.editDebugAnimStack(stack, data.selected, data.mode);
            });
    
            ctx.get().setPacketHandled(true);
            
        }
        
    }

}
