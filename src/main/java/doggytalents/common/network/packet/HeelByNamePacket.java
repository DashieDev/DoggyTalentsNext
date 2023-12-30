package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.DoggySounds;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.DogSoftHeelAction;
import doggytalents.common.item.WhistleItem;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogData;
import doggytalents.common.network.packet.data.HeelByNameData;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;


 public class HeelByNamePacket extends DogPacket<HeelByNameData> {

    @Override
    public void encode(HeelByNameData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
        buf.writeBoolean(data.heelAndSit);
        buf.writeBoolean(data.softHeel);
    }

    @Override
    public HeelByNameData decode(FriendlyByteBuf buf) {
        return new HeelByNameData(buf.readInt(), buf.readBoolean(), buf.readBoolean());
    }


    @Override
    public void handleDog(Dog dog, HeelByNameData data, Supplier<Context> ctx) {
        var owner = ctx.get().getSender();
        if (!dog.canInteract(owner)) return;
        if (owner.getCooldowns().isOnCooldown(DoggyItems.WHISTLE.get())) return;
        if (dog.isPassenger()) dog.stopRiding();
        dog.clearTriggerableAction();

        var mode = dog.getMode();
        if (mode.canWander()) {
            dog.setMode(mode.shouldAttack() ? 
                EnumMode.AGGRESIVE : EnumMode.DOCILE);
        }
        
        if (data.softHeel) {
            if (dog.readyForNonTrivialAction())
            dog.triggerAction(new DogSoftHeelAction(dog, owner));
        } else {
            dog.setOrderedToSit(data.heelAndSit);
            DogUtil.dynamicSearchAndTeleportToOwnwer(dog, owner, 2);
        }
        
        if (ConfigHandler.WHISTLE_SOUNDS)
        owner.level().playSound(null, owner.blockPosition(), DoggySounds.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + owner.level().random.nextFloat() * 0.1F, 0.4F + owner.level().random.nextFloat() * 0.2F);
        owner.sendSystemMessage(Component.translatable("dogcommand.heel_by_name", dog.getName().getString()));
        owner.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);

        var stack = owner.getMainHandItem();
        if (stack.getItem() instanceof WhistleItem) {
            if (!stack.hasTag()) {
                stack.setTag(new CompoundTag());
                stack.getTag().putBoolean("soft_heel", data.softHeel);
            } else {
                stack.getTag().putBoolean("soft_heel", data.softHeel);
            }                
        }

    }
    

}
