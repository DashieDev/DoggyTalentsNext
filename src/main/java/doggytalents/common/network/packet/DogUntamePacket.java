package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.AmnesiaBoneItem;
import doggytalents.common.lib.Constants;
import doggytalents.common.network.packet.data.DogUntameData;
import doggytalents.common.storage.DogLocationStorage;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.chat.Component;
import net.minecraft.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent.Context;

public class DogUntamePacket extends DogPacket<DogUntameData> {

    @Override
    public DogUntameData decode(FriendlyByteBuf buf) {
        return new DogUntameData(buf.readInt());
    }

    @Override
    public void handleDog(Dog dog, DogUntameData data, Supplier<Context> ctx) {
        var sender = ctx.get().getSender();
        var stack = sender.getMainHandItem();
        if (stack.getItem() != DoggyItems.AMNESIA_BONE.get()) return;
        if (sender.getCooldowns().isOnCooldown(DoggyItems.AMNESIA_BONE.get())) return;
        var ownerUUID = dog.getOwnerUUID();
        if (ownerUUID == null) return;
        if (!ownerUUID.equals(sender.getUUID())) return;
        if (sender.experienceLevel < AmnesiaBoneItem.getUntameXPCost()) return;
        
        dog.untame();
        sender.giveExperienceLevels(-AmnesiaBoneItem.getUntameXPCost());
        
        dog.level.broadcastEntityEvent(dog, Constants.EntityState.WOLF_SMOKE);
        
        var tag = stack.getOrCreateTag();
        int usedTime = tag.getInt("amnesia_bone_used_time");
        
        ++usedTime;
        if (usedTime >= AmnesiaBoneItem.getUseCap()) {
            stack.shrink(1);
            sender.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }

        tag.putInt("amnesia_bone_used_time", usedTime);

        sender.getCooldowns().addCooldown(DoggyItems.AMNESIA_BONE.get(), 60);
    }
    
}
