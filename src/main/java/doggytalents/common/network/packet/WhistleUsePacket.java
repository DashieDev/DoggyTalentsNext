package doggytalents.common.network.packet;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem.WhistleMode;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.WhistleUseData;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent.Context;

public class WhistleUsePacket implements IPacket<WhistleUseData> {

    @Override
    public void encode(WhistleUseData data, FriendlyByteBuf buf) {
        buf.writeInt(data.mode_id);
    }

    @Override
    public WhistleUseData decode(FriendlyByteBuf buf) {
        return new WhistleUseData(buf.readInt());
    }

    @Override
    public void handle(WhistleUseData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var side = ctx.get().getDirection().getReceptionSide();

            if (!side.isServer()) return;
            var player = ctx.get().getSender();
            var whistle = DoggyItems.WHISTLE.get();
            var whistle_stack = 
                InventoryUtil.findStackWithItemFromHands(player, whistle);
            if (whistle_stack == null) return;
            if (player.getCooldowns().isOnCooldown(whistle)) return;
            var whistle_modes = WhistleMode.VALUES;
            if (data.mode_id >= whistle_modes.length) return;
            if (data.mode_id < 0) return;
            var useMode = whistle_modes[data.mode_id];
            List<Dog> dogsList = player.level().getEntitiesOfClass(
                Dog.class, 
                player.getBoundingBox().inflate(100D, 50D, 100D), 
                dog -> dog.isDoingFine() && dog.isOwnedBy(player)
            );
            whistle.useMode(useMode, dogsList, 
                player.level, player, InteractionHand.MAIN_HAND);
        });

        ctx.get().setPacketHandled(true);
    }
    
}
