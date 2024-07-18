package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogPettingManager.DogPettingType;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.packet.data.DogPettingData;
import net.minecraft.network.FriendlyByteBuf;

public class DogPettingPacket extends DogPacket<DogPettingData> {

    @Override
    public void encode(DogPettingData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
        if (data.val)
            buf.writeInt(data.type.getId());
    }

    @Override
    public DogPettingData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        var petting_type = val ? DogPettingType.fromId(buf.readInt())
            : null;
        return new DogPettingData(entityId, val, petting_type);
    }

    @Override
    public void handleDog(Dog dogIn, DogPettingData data, Supplier<Context> ctx) {
        if (data.val) {  
            dogIn.pettingManager.setPetting(ctx.get().getSender(), data.type);
        } else
            dogIn.pettingManager.stopPetting();
    }
    
}
