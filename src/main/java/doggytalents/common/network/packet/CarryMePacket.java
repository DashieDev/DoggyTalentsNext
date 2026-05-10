package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.DoggySounds;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.DogGoAndCarryPlayerAction;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.packet.data.CarryMeData;
import doggytalents.common.talent.WolfMountTalent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;

public class CarryMePacket extends DogPacket<CarryMeData> {

    @Override
    public CarryMeData decode(FriendlyByteBuf buf) {
        return new CarryMeData(buf.readInt());
    }

    @Override
    public void handleDog(Dog dog, CarryMeData data, Supplier<Context> ctx) {
        var owner = ctx.get().getSender();
        if (!dog.canInteract(owner)) 
            return;
        if (owner.getCooldowns().isOnCooldown(new net.minecraft.world.item.ItemStack(DoggyItems.WHISTLE.get())))
            return;
        if (!ConfigHandler.SERVER.CARRY_ME_WHISTLE.get())
            return;
        if (owner.distanceToSqr(dog) > 12 * 12)
            return;
        if (!WolfMountTalent.isValidCarryMeDog(dog))
            return;
        if (!WolfMountTalent.isValidCarryMeTarget(owner))
            return;
        dog.clearTriggerableAction();
        dog.triggerAction(new DogGoAndCarryPlayerAction(dog, owner));

        if (ConfigHandler.WHISTLE_SOUNDS)
            owner.level().playSound(null, owner.blockPosition(), DoggySounds.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + owner.level().getRandom().nextFloat() * 0.1F, 0.4F + owner.level().getRandom().nextFloat() * 0.2F);
        if (owner instanceof net.minecraft.world.entity.player.Player _p_sys) _p_sys.sendSystemMessage(Component.translatable("dogcommand.carry_me", dog.getName().getString()));
        owner.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(DoggyItems.WHISTLE.get()), 20);
    }

}
