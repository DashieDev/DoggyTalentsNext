package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class OnigiriItem extends DogEddibleItem {

    public OnigiriItem() {
        super(
            b -> b
                .nutrition(4)
                .saturationMod(0.5F)
        );
    }
    
}
