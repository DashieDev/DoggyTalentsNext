package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.packet.data.DogOnDutyData;
import net.minecraft.network.FriendlyByteBuf;

public class DogOnDutyPacket extends DogPacket<DogOnDutyData> {
    
    @Override
    public void encode(DogOnDutyData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public DogOnDutyData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new DogOnDutyData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, DogOnDutyData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setDogOnDuty(data.val);
    }
    

}
