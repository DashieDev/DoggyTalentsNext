package doggytalents.common.entity;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
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
    public boolean isFood(ItemStack stackIn) {
        return stackIn.isEdible() && stackIn.getItem().getFoodProperties().isMeat() && stackIn.getItem() != Items.ROTTEN_FLESH;
    }

    @Override
    public boolean canConsume(AbstractDog dogIn, ItemStack stackIn, Entity entityIn) {
        return this.isFood(stackIn);
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, Entity entityIn) {

        if (dog.getDogHunger() < dog.getMaxHunger()) {
            if (!dog.level.isClientSide) {
                int heal = stack.getItem().getFoodProperties().getNutrition() * 5;

                dog.addHunger(heal);
                dog.consumeItemFromStack(entityIn, stack);

                if (dog.level instanceof ServerLevel) {

                    //TODO tune and may recheck
                    var a1 = dog.getYRot();
                    var dx1 = -Mth.sin(a1*Mth.DEG_TO_RAD);
                    var dz1 = Mth.cos(a1*Mth.DEG_TO_RAD);

                    //TODO Maybe send the particle to the client through packets...
                    //Because there is seems to be a problem with the stack on the client 
                    //as it is occasionally missing texture...
                    ((ServerLevel) dog.level).sendParticles(
                        new ItemParticleOption(ParticleTypes.ITEM, stack), 
                        dog.getX() + dx1, dog.getY() + dog.getEyeHeight(), dog.getZ() + dz1, 
                        15, 
                        0.5, 0.8f, 0.5, 
                        0.1
                    );
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
