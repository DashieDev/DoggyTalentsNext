package doggytalents.common.item;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.network.packet.ParticlePackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class DogEddibleItem extends Item implements IDogEddible {

    public DogEddibleItem(Properties itemProps) {
        super(itemProps);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == this;
    }

    @Override
    public boolean canConsume(AbstractDog dog, ItemStack stackIn, @Nullable Entity entityIn) {
        return !dog.isDefeated() && isFood(stackIn);
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
        var dogEddible = this;
        
        if (!dogEddible.alwaysEatWhenDogConsume(dog) && !dog.canStillEat()) {
            return InteractionResult.FAIL;
        }

        if (!dog.level().isClientSide) {    
            float heal = dogEddible.getAddedHungerWhenDogConsume(stack, dog);

            dog.addHunger(heal);
            dog.consumeItemFromStack(entityIn, stack);

            for(var pair : dogEddible.getAdditionalEffectsWhenDogConsume(stack, dog)) {
                if (pair.getFirst() != null && dog.getRandom().nextFloat() < pair.getSecond()) {
                   dog.addEffect(new MobEffectInstance(pair.getFirst()));
                }
             }

            if (dog.level() instanceof ServerLevel) {
                ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                    dog, new ItemStack(this));
            }
            dog.playSound(
                dogEddible.getDogEatingSound(dog), 
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
