package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SoyMilkItem extends DogEddibleBowlFoodItem  {

    public static FoodProperties FOOD_PROPS = 
        (new FoodProperties.Builder())
            .nutrition(4)
            .saturationMod(0.5F)
            .build();

    public SoyMilkItem() {
        super(
            (new Properties()).food(
                FOOD_PROPS
            )
        );
    }

    @Override
    public float getAddedHungerWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getNutrition() * 5;
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
