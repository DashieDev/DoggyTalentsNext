package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogIncapacitatedMananger;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogIncapMsgData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;

public class DogIncapMsgPackets {
    
    public static class Request extends DogPacket<DogIncapMsgData.Request> {

        @Override
        public DogIncapMsgData.Request decode(FriendlyByteBuf buf) {
            var id = buf.readInt();
            return new DogIncapMsgData.Request(id);
        }

        @Override
        public void handleDog(Dog dog, DogIncapMsgData.Request data,
                Supplier<Context> ctx) {
            if (dog.level().isClientSide)
                return;
            var sender = ctx.get().getSender();
            if (dog.getOwner() != sender)
                return;
            var msg = dog.incapacitatedMananger.getIncapMsg();
            PacketHandler.send(
                PacketDistributor.PLAYER.with(() -> sender), 
                new DogIncapMsgData.Response(msg, dog.getId())
            );
        }
        
    }

    public static class Response implements IPacket<DogIncapMsgData.Response> {

        @Override
        public void encode(DogIncapMsgData.Response data, FriendlyByteBuf buf) {
            buf.writeInt(data.entityId);
            buf.writeUtf(data.msg, DogIncapacitatedMananger.MAX_INCAP_MSG_LEN);
        }

        @Override
        public DogIncapMsgData.Response decode(FriendlyByteBuf buf) {
            var id = buf.readInt();
            var msg = buf.readUtf(DogIncapacitatedMananger.MAX_INCAP_MSG_LEN);
            return new DogIncapMsgData.Response(msg, id);
        }

        @Override
        public void handle(DogIncapMsgData.Response data,
                Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                if (ctx.get().getDirection().getReceptionSide().isClient()) { 
                    Minecraft mc = Minecraft.getInstance();
                    var e = mc.level.getEntity(data.entityId);
                    if (e instanceof Dog d) {
                        d.incapacitatedMananger.setIncapMsg(data.msg);
                    }
                }

            });

            ctx.get().setPacketHandled(true);
        }
        
    }

}
