package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SalmonSushiItem extends DogEddibleItem {

    public SalmonSushiItem() {
        super(
            b -> b
                .nutrition(5)
                .saturationMod(0.5F)
        );
    }
    
}
