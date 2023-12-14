package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class RiceBowlItem extends DogEddibleBowlFoodItem {
    
    public RiceBowlItem() {
        super(
            b -> b
                .nutrition(5)
                .saturationMod(0.5F)
        ); 
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }

}
