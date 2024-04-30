package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SausageItem extends DogEddibleItem {

    public SausageItem() {
        super(
            b -> b
                .nutrition(8)
                .saturationModifier(0.6F)
        );
    }
    
}
