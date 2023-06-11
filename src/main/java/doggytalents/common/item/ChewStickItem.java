package doggytalents.common.item;

import doggytalents.DoggyItems;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.network.packet.ParticlePackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChewStickItem extends Item implements IDogFoodHandler {

    public ChewStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFood(ItemStack stackIn) {
        return stackIn.getItem() == this;
    }

    @Override
    public boolean canConsume(AbstractDog dog, ItemStack stackIn, Entity entityIn) {
        return !dog.isDefeated();
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, Entity entityIn) {
        if (!dog.level().isClientSide) {
            dog.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 1, false, true));
            dog.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 6, false, true));
            dog.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2, false, true));
            dog.consumeItemFromStack(entityIn, stack);

            if (dog.level() instanceof ServerLevel) {
                ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                    dog, new ItemStack(DoggyItems.CHEW_STICK.get()));
            }
            dog.playSound(
                SoundEvents.GENERIC_EAT, 
                dog.getSoundVolume(), 
                (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
            );
        }

        return InteractionResult.SUCCESS;
    }

}
