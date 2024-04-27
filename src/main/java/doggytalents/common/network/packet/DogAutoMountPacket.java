package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DogAutoMountData;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class DogAutoMountPacket extends DogPacket<DogAutoMountData> {
    
    @Override
    public void encode(DogAutoMountData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public DogAutoMountData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new DogAutoMountData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, DogAutoMountData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setDogAutoMount(data.val);
    }
    

}
