package doggytalents.common.network.packet;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DogNameData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

import java.util.function.Supplier;

public class DogNamePacket extends DogPacket<DogNameData> {

    @Override
    public void encode(DogNameData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeUtf(data.name, Dog.MAX_NAME_LEN);
    }

    @Override
    public DogNameData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        String name = buf.readUtf(Dog.MAX_NAME_LEN);
        return new DogNameData(entityId, name);
    }

    @Override
    public void handleDog(Dog dogIn, DogNameData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        if (data.name.isEmpty()) {
            dogIn.setDogCustomName(null);
        }
        else {
            dogIn.setDogCustomName(Component.literal(data.name));
        }
    }
}
