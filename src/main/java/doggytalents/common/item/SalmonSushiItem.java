package doggytalents.common.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class SalmonSushiItem extends Item {

    public SalmonSushiItem() {
        super(
            (new Properties()).food(
                (new FoodProperties.Builder())
                    .nutrition(5)
                    .saturationMod(0.5F)
                    .meat()
                    .build()
            )
        );
    }
    
}
