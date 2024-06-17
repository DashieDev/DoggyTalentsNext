package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SoyMilkItem extends DogEddibleBowlFoodItem  {

    public SoyMilkItem() {
        super(
            b -> b
                .nutrition(6)
                .saturationModifier(0.5F)
        );
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }

    @Override
    public SoundEvent getDogEatingSound(AbstractDog dog) {
        return SoundEvents.GENERIC_DRINK;
    }

}
