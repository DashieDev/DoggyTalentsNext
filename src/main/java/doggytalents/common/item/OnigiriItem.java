package doggytalents.common.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class OnigiriItem extends Item {

    public OnigiriItem() {
        super(
            (new Properties()).food(
                (new FoodProperties.Builder())
                    .nutrition(6)
                    .saturationMod(0.6F)
                    .meat()
                    .build()
            )
        );
    }
    
}
