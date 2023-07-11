package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.AmnesiaBoneItem;
import doggytalents.common.network.packet.data.DogMigrateOwnerData;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent.Context;

public class DogMigrateOwnerPacket extends DogPacket<DogMigrateOwnerData> {

    @Override
    public void encode(DogMigrateOwnerData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
        buf.writeBoolean(data.confirmed);
    }

    @Override
    public DogMigrateOwnerData decode(FriendlyByteBuf buf) {
        var id = buf.readInt();
        var confirmed = buf.readBoolean();
        return new DogMigrateOwnerData(id, confirmed);
    }

    @Override
    public void handleDog(Dog dog, DogMigrateOwnerData data, Supplier<Context> ctx) {
        var sender = ctx.get().getSender();

        //Sender must be holding the item
        var stack = sender.getMainHandItem();
        if (stack.getItem() != DoggyItems.AMNESIA_BONE.get()) return;

        //And not in cooldown
        if (sender.getCooldowns().isOnCooldown(DoggyItems.AMNESIA_BONE.get())) return;

        //And is dog's owner.
        var ownerUUID = dog.getOwnerUUID();
        if (ownerUUID == null) return;
        if (!ownerUUID.equals(sender.getUUID())) return;
        
        var tag = stack.getTag();

        //Nothing to do if required tag doesn't exist
        if (tag == null) return;
        if (!tag.hasUUID("request_uuid")) {
            tag.remove("request_str"); return;
        };

        var uuid = tag.getUUID("request_uuid");
        
        //Consume these tags
        tag.remove("request_uuid");    
        tag.remove("request_str");

        //Migrating between same uuid is a NO-OP
        if (ownerUUID.equals(uuid)) return;

        var requester = dog.level.getPlayerByUUID(uuid);

        //the requester doesn't exist, nothing to do.
        if (requester == null) return;

        //If reject then send the requester reject msg and return;
        if (!data.confirmed) {
            requester.sendMessage(
                ComponentUtil.translatable("item.doggytalents.amnesia_bone.migrate_owner.reject",
                    dog.getName().getString()
                ).withStyle(ChatFormatting.RED), Util.NIL_UUID
            );
            return;
        }

        //Begin the check related to the migration itself

        //Not enough level
        if (sender.experienceLevel < AmnesiaBoneItem.getMigrateOwnerXPCost()) return;

        //Begin migrating.
        
        //Proccess Dog
        dog.migrateOwner(uuid);
        
        //Proccess sender
        sender.giveExperienceLevels(-AmnesiaBoneItem.getMigrateOwnerXPCost());
        sender.getCooldowns().addCooldown(DoggyItems.AMNESIA_BONE.get(), 60);
        int usedTime = tag.getInt("amnesia_bone_used_time");
        ++usedTime;
        if (usedTime >= AmnesiaBoneItem.getUseCap()) {
            stack.shrink(1);
            sender.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        }
        tag.putInt("amnesia_bone_used_time", usedTime);

        //Proccess requester
        requester.sendMessage(
            ComponentUtil.translatable("item.doggytalents.amnesia_bone.migrate_owner.confirmed",
                dog.getName().getString(), dog.getGenderPronoun()
            ), Util.NIL_UUID
        );
    }
    
}
