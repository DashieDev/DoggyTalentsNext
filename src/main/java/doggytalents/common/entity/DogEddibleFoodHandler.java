package doggytalents.common.entity;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.item.IDogEddible;
import doggytalents.common.network.packet.ParticlePackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class DogEddibleFoodHandler implements IDogFoodHandler {
    
    @Override
    public boolean isFood(ItemStack stackIn) {
        return stackIn.getItem() instanceof IDogEddible;
    }

    @Override
    public boolean canConsume(AbstractDog dogIn, ItemStack stackIn, @Nullable Entity entityIn) {
        return this.isFood(stackIn);
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
        if (!dog.level().isClientSide) {
            
            var item = stack.getItem();

            if (!(item instanceof IDogEddible dogEddible))
                return InteractionResult.SUCCESS;

            float heal = dogEddible.getAddedHunger(stack, dog);

            dog.addHunger(heal);
            dog.consumeItemFromStack(entityIn, stack);

            for(var pair : dogEddible.getAdditionalEffects(stack, dog)) {
                if (pair.getFirst() != null && dog.getRandom().nextFloat() < pair.getSecond()) {
                   dog.addEffect(new MobEffectInstance(pair.getFirst()));
                }
             }

            if (dog.level() instanceof ServerLevel) {
                ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                    dog, new ItemStack(item));
            }
            dog.playSound(
                SoundEvents.GENERIC_EAT, 
                dog.getSoundVolume(), 
                (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
            );

            var returnStack = dogEddible.getReturnStackAfterDogConsume(stack, dog);
            if (!returnStack.isEmpty()) {
                dog.spawnAtLocation(returnStack);
            }
        }

        return InteractionResult.SUCCESS;
    }

}
