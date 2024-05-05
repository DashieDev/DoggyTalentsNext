package doggytalents.common.network.packet;

import java.util.function.Supplier;

import org.apache.commons.lang3.ObjectUtils;

import doggytalents.DoggyItems;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.ForceChangeOwnerData;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class ForceChangeOwnerPacket extends DogPacket<ForceChangeOwnerData> {
    
    @Override
    public void encode(ForceChangeOwnerData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
    }

    @Override
    public ForceChangeOwnerData decode(FriendlyByteBuf buf) {
        var id = buf.readInt();
        return new ForceChangeOwnerData(id);
    }

    @Override
    public void handleDog(Dog dog, ForceChangeOwnerData data, Supplier<Context> ctx) {
        //Only change owner if sender have access to Admin actions and
        //is in creative.
        var sender = ctx.get().getSender();
        if (!sender.hasPermissions(4))
            return;
        if (!sender.getAbilities().instabuild)
            return;

        var stack = sender.getMainHandItem();
        if (!stack.is(DoggyItems.AMNESIA_BONE.get()))
            return;
        if (sender.getCooldowns().isOnCooldown(DoggyItems.AMNESIA_BONE.get()))
            return;

        var currentOwnerUUID = dog.getOwnerUUID();
        var newOwnerUUID = sender.getUUID();
        if (newOwnerUUID == null)
            return;
        if (!ObjectUtils.notEqual(newOwnerUUID, currentOwnerUUID))
            return;
        
        dog.migrateOwner(newOwnerUUID);
        
        sender.getCooldowns().addCooldown(DoggyItems.AMNESIA_BONE.get(), 40);
    }

}
