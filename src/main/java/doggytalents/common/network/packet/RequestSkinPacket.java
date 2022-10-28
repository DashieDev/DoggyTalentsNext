package doggytalents.common.network.packet;

import doggytalents.DoggyTalentsNext;
import doggytalents.common.entity.texture.DogTextureServer;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.RequestSkinData;
import doggytalents.common.network.packet.data.SendSkinData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class RequestSkinPacket implements IPacket<RequestSkinData> {

    @Override
    public void encode(RequestSkinData data, FriendlyByteBuf buf) {
        buf.writeUtf(data.hash, 128);
    }

    @Override
    public RequestSkinData decode(FriendlyByteBuf buf) {
        return new RequestSkinData(buf.readUtf(128));
    }

    @Override
    public void handle(RequestSkinData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LogicalSide side = ctx.get().getDirection().getReceptionSide();

            if (side.isServer()) {
                byte[] stream = DogTextureServer.INSTANCE.getCachedBytes(DogTextureServer.INSTANCE.getServerFolder(), data.hash);
                if (stream != null) {
                    DoggyTalentsNext.HANDLER.reply(new SendSkinData(0, stream), ctx.get());

                    DoggyTalentsNext.LOGGER.debug("Client requested skin for hash  {}", data.hash);
                } else {
                    DoggyTalentsNext.LOGGER.warn("Client requested skin but no cache was available {}", data.hash);
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }
}
