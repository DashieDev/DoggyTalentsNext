package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SausageItem extends Item implements IDogEddible {

    public static FoodProperties FOOD_PROPS = 
        (new FoodProperties.Builder())
            .nutrition(3)
            .saturationMod(0.6F)
            .meat()
            .build();

    public SausageItem() {
        super(
            (new Properties()).food(
                FOOD_PROPS
            )
        );
    }

    @Override
    public float getAddedHunger(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getNutrition() * 5;
    }
    
}
