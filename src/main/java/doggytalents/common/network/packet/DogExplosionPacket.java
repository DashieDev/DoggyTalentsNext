package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.DogExplosionData;
import doggytalents.common.talent.OokamiKazeTalent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class DogExplosionPacket implements IPacket<DogExplosionData> {

    @Override
    public void encode(DogExplosionData data, FriendlyByteBuf buf) {
        buf.writeInt(data.dogId);
    }

    @Override
    public DogExplosionData decode(FriendlyByteBuf buf) {
        var id = buf.readInt();
        return new DogExplosionData(id);
    }

    @Override
    public void handle(DogExplosionData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {

            if (ctx.get().getDirection().getReceptionSide().isClient()) { 
                var mc = Minecraft.getInstance();
                var e = mc.level.getEntity(data.dogId);
                if (e instanceof Dog dog) {
                    OokamiKazeTalent.explodeClient(dog);
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }
    
}
