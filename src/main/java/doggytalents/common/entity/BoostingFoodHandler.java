package doggytalents.common.entity;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.network.packet.ParticlePackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BoostingFoodHandler implements IDogFoodHandler  {

    @Override
    public boolean isFood(ItemStack stackIn) {
        return isBoostingFood(stackIn);
    }

    @Override
    public boolean canConsume(AbstractDog dogIn, ItemStack stackIn, @Nullable Entity entityIn) {
        return this.isFood(stackIn);
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
        if (!dog.level().isClientSide()) {
            
            var item = stack.getItem();

            var props = stack.get(net.minecraft.core.component.DataComponents.FOOD);
            
            if (props == null) return InteractionResult.FAIL;

            int heal = props.nutrition() * 5;

            dog.addHunger(heal);
            dog.consumeItemFromStack(entityIn, stack);

            var consumable = stack.get(net.minecraft.core.component.DataComponents.CONSUMABLE);
            if (consumable != null) {
                for (var effect : consumable.onConsumeEffects()) {
                    effect.apply(dog.level(), stack, (net.minecraft.world.entity.LivingEntity) dog);
                }
            }

            if (dog.level() instanceof ServerLevel) {
                ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                    dog, new ItemStack(item));
            }
            dog.playSound(
                SoundEvents.GENERIC_EAT.value(), 
                dog.getSoundVolume(), 
                (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
            );
        }

        return InteractionResult.SUCCESS;
    }

    private boolean isBoostingFood(ItemStack stack) {
        var item = stack.getItem();
        return item == Items.GOLDEN_APPLE
            || item == Items.APPLE
            || item == Items.ENCHANTED_GOLDEN_APPLE
            || item == Items.GOLDEN_CARROT;
    }
    
}
