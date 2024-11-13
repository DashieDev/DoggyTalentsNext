package doggytalents.common.fabric_helper.entity.network;

import java.util.function.Supplier;

import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.util.NetworkUtil;
import doggytalents.common.entity.misc.DogPlushie;
import doggytalents.common.network.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public class FabricPlushieSpawnPacket implements IPacket<FabricPlushieSpawnData> {

    @Override
    public void encode(FabricPlushieSpawnData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
        buf.writeInt(data.collarColor);
        buf.writeBoolean(data.collarThicc);
        NetworkUtil.writeDogVariantToBuf(buf, data.variant);
    }

    @Override
    public FabricPlushieSpawnData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int collarColor = buf.readInt();
        boolean collarThicc = buf.readBoolean();
        var variant = NetworkUtil.readDogVariantFromBuf(buf);
        return new FabricPlushieSpawnData(entityId, variant, collarColor, collarThicc);
    }

    @Override
    public void handle(FabricPlushieSpawnData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (!ctx.get().isClientRecipent())
                return;
            var mc = Minecraft.getInstance();
            var entity = mc.level.getEntity(data.entityId);
            if (entity == null)
                return;
            if (!(entity instanceof DogPlushie plush))
                return;
            plush.setCollarThicc(data.collarThicc);
            plush.setCollarColor(data.collarColor);
            plush.setDogVariant(data.variant);
        });
    }
    
}
