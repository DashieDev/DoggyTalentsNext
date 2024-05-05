package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.event.EventHandler;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.TrainWolfToDogData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class TrainWolfToDogPacket implements IPacket<TrainWolfToDogData> {

    @Override
    public void encode(TrainWolfToDogData data, FriendlyByteBuf buf) {
        buf.writeInt(data.wolfId);
        buf.writeFloat(data.wolfYBodyRot);
        buf.writeFloat(data.wolfYHeadRot);
    }

    @Override
    public TrainWolfToDogData decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        float body = buf.readFloat();
        float head = buf.readFloat();
        return new TrainWolfToDogData(id, body, head);
    }

    @Override
    public void handle(TrainWolfToDogData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (!ctx.get().isServerRecipent())
                return;
            
            var sender = ctx.get().getSender();
            var level = sender.level();
            var e = level.getEntity(data.wolfId);
            if (!(e instanceof Wolf wolf))
                return;
            EventHandler.checkAndTrainWolf(sender, wolf, data.wolfYBodyRot, data.wolfYHeadRot);
        });

        ctx.get().setPacketHandled(true);
    }
}
