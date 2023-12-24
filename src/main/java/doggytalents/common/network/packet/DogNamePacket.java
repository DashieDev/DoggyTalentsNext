package doggytalents.common.network.packet;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DogNameData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class DogNamePacket extends DogPacket<DogNameData> {

    @Override
    public void encode(DogNameData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeUtf(data.name, 64);
    }

    @Override
    public DogNameData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        String name = buf.readUtf(64);
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
            dogIn.setDogCustomName(ComponentUtil.literal(data.name));
        }
    }
}
