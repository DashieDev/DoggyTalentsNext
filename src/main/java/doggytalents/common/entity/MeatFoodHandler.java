package doggytalents.common.entity;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.network.packet.ParticlePackets;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MeatFoodHandler implements IDogFoodHandler {

    @Override
    public boolean isFood(ItemStack stack) {
        var props = stack.getItem().getFoodProperties();

        if (props == null) return false;
        return stack.isEdible() && props.isMeat() && stack.getItem() != Items.ROTTEN_FLESH;
    }

    @Override
    public boolean canConsume(AbstractDog dog, ItemStack stackIn, @Nullable Entity entityIn) {
        return this.isFood(stackIn) && !dog.isDefeated();
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, Entity entityIn) {

        if (dog.getDogHunger() < dog.getMaxHunger()) {
            if (!dog.level.isClientSide) {
                var item = stack.getItem();

                var props = item.getFoodProperties();

                if (props == null) return InteractionResult.FAIL;
                
                int heal = props.getNutrition() * 5;

                dog.addHunger(heal);
                dog.consumeItemFromStack(entityIn, stack);

                if (dog.level instanceof ServerLevel) {
                    ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                        dog, new ItemStack(item));
                }
                dog.playSound(
                    SoundEvents.GENERIC_EAT, 
                    dog.getSoundVolume(), 
                    (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
                );
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;

    }

}
