package doggytalents.common.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class SausageItem extends Item{

    public SausageItem() {
        super(
            (new Properties()).food(
                (new FoodProperties.Builder())
                    .nutrition(8)
                    .saturationMod(0.9F)
                    .build()
            )
        );
    }
    
}
