package doggytalents.common.network.packet;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DogForceSitData;
import doggytalents.common.network.packet.data.DogObeyData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class DogForceSitPacket extends DogPacket<DogForceSitData> {

    @Override
    public void encode(DogForceSitData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.forceSit);
    }

    @Override
    public DogForceSitData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean forceSit = buf.readBoolean();
        return new DogForceSitData(entityId, forceSit);
    }

    @Override
    public void handleDog(Dog dogIn, DogForceSitData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setForceSit(data.forceSit);
    }
}
