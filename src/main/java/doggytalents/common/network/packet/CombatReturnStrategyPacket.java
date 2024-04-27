package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.Dog.CombatReturnStrategy;
import doggytalents.common.network.packet.data.CombatReturnStrategyData;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class CombatReturnStrategyPacket extends DogPacket<CombatReturnStrategyData> {
    
    @Override
    public void encode(CombatReturnStrategyData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeByte(data.val.getId());
    }

    @Override
    public CombatReturnStrategyData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        var strategy = CombatReturnStrategy.fromId(buf.readByte());
        return new CombatReturnStrategyData(entityId, strategy);
    }

    @Override
    public void handleDog(Dog dog, CombatReturnStrategyData data, Supplier<Context> ctx) {
        if (!dog.canInteract(ctx.get().getSender())) {
            return;
        }

        dog.setCombatReturnStrategy(data.val);

    }

}
