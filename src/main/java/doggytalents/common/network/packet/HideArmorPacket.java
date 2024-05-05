package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.HideArmorData;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class HideArmorPacket extends DogPacket<HideArmorData> {

    @Override
    public void encode(HideArmorData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public HideArmorData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new HideArmorData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, HideArmorData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setHideArmor(data.val);
    }
    
}
