package doggytalents.common.network.packet;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.FriendlyFireData;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class FriendlyFirePacket extends DogPacket<FriendlyFireData> {

    @Override
    public void encode(FriendlyFireData data, PacketBuffer buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.friendlyFire);
    }

    @Override
    public FriendlyFireData decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        boolean obeyOthers = buf.readBoolean();
        return new FriendlyFireData(entityId, obeyOthers);
    }

    @Override
    public void handleDog(Dog dogIn, FriendlyFireData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setCanPlayersAttack(data.friendlyFire);
    }
}
