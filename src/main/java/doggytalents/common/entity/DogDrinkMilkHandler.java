package doggytalents.common.entity;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DogDrinkMilkHandler implements IDogFoodHandler {

    @Override
    public boolean isFood(ItemStack stackIn) {
        return stackIn.is(Items.MILK_BUCKET);
    }

    @Override
    public boolean canConsume(AbstractDog dogIn, ItemStack stackIn, @Nullable Entity entityIn) {
        return isFood(stackIn) && !dogIn.getActiveEffectsMap().isEmpty();
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stackIn, @Nullable Entity entityIn) {
        if (!dog.level().isClientSide) {
            
            dog.removeAllEffects();
            if (entityIn instanceof Player player
                && !player.getAbilities().instabuild) {
                consumeMilkFromPlayer(player, stackIn);
            }

            dog.playSound(
                SoundEvents.GENERIC_DRINK, 
                dog.getSoundVolume(), 
                (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
            );
        }

        return InteractionResult.SUCCESS;
    }

    private void consumeMilkFromPlayer(Player player, ItemStack stack) {
        InteractionHand handOwner = null;
        if (player.getMainHandItem() == stack)
            handOwner = InteractionHand.MAIN_HAND;
        else if (player.getOffhandItem() == stack)
            handOwner = InteractionHand.OFF_HAND;
        if (handOwner == null)
            return;

        player.setItemInHand(handOwner, new ItemStack(Items.BUCKET));
    }
    
}
    