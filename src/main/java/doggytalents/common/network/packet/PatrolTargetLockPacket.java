package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.PatrolTargetLockData;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class PatrolTargetLockPacket extends DogPacket<PatrolTargetLockData> {

    @Override
    public void encode(PatrolTargetLockData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public PatrolTargetLockData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new PatrolTargetLockData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, PatrolTargetLockData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setPatrolTargetLock(data.val);
    }
    
}
