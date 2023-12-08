package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SalmonSushiItem extends DogEddibleItem {
    
    public static final FoodProperties FOOD_PROPS =
        (new FoodProperties.Builder())
            .nutrition(5)
            .saturationMod(0.5F)
            .meat()
            .build();

    public SalmonSushiItem() {
        super(
            (new Properties()).food(FOOD_PROPS)
        );
    }

    @Override
    public float getAddedHungerWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getNutrition() * 5;
    }
    
}
