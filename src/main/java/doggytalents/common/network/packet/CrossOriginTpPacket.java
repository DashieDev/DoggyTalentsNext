package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.CrossOriginTpData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class CrossOriginTpPacket extends DogPacket<CrossOriginTpData> {

    @Override
    public void encode(CrossOriginTpData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.crossOriginTp);
    }

    @Override
    public CrossOriginTpData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean crossOriginTp = buf.readBoolean();
        return new CrossOriginTpData(entityId, crossOriginTp);
    }

    @Override
    public void handleDog(Dog dogIn, CrossOriginTpData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setCrossOriginTp(data.crossOriginTp);
    }
    
}
