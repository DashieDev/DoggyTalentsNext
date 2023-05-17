package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.Dog.LowHealthStrategy;
import doggytalents.common.network.packet.data.DogLowHealthStrategyData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class DogLowHealthStrategyPacket extends DogPacket<DogLowHealthStrategyData> {
    
    @Override
    public void encode(DogLowHealthStrategyData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeByte(data.strategy.getId());
    }

    @Override
    public DogLowHealthStrategyData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        var strategy = LowHealthStrategy.fromId(buf.readByte());
        return new DogLowHealthStrategyData(entityId, strategy);
    }

    @Override
    public void handleDog(Dog dog, DogLowHealthStrategyData data, Supplier<Context> ctx) {
        if (!dog.canInteract(ctx.get().getSender())) {
            return;
        }

        dog.setLowHealthStrategy(data.strategy);

    }

}
