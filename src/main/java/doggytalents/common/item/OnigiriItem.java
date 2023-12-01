package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class OnigiriItem extends Item implements IDogEddible {

    public static final FoodProperties FOOD_PROPS = 
        (new FoodProperties.Builder())
            .nutrition(4)
            .saturationMod(0.5F)
            .meat()
            .build();

    public OnigiriItem() {
        super(
            (new Properties()).food(FOOD_PROPS)
        );
    }

    @Override
    public float getAddedHunger(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getNutrition();
    }

    
    
}
