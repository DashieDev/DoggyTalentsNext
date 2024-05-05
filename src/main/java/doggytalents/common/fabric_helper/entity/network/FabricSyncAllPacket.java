package doggytalents.common.fabric_helper.entity.network;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public class FabricSyncAllPacket implements IPacket<FabricSyncAllData> {

    @Override
    public void encode(FabricSyncAllData data, FriendlyByteBuf buf) {
        data.writeToPacket(buf);
    }

    @Override
    public FabricSyncAllData decode(FriendlyByteBuf buf) {
        return FabricSyncAllData.readFromPacket(buf);
    }

    @Override
    public void handle(FabricSyncAllData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (!ctx.get().isClientRecipent())
                return;
            var mc = Minecraft.getInstance(); 
            var level = mc.level;
            var e = level.getEntity(data.dogId);
            if (!(e instanceof Dog dog))
                return;
            data.updateDog(dog);
        });
    }
    


}
