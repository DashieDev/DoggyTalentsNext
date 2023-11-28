package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DogNoCuriousData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class DogNoCuriousPacket extends DogPacket<DogNoCuriousData> {

    @Override
    public void encode(DogNoCuriousData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public DogNoCuriousData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new DogNoCuriousData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, DogNoCuriousData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setNoDogCurious(data.val);
    }
}
