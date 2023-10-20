package doggytalents.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class RiceBowlItem extends BowlFoodItem {
    
    public RiceBowlItem() {
        super(
            (new Properties()).food(
                (new FoodProperties.Builder())
                    .nutrition(3)
                    .saturationMod(0.5F)
                    .build()
            ).stacksTo(1).craftRemainder(Items.BOWL)
        ); 
    }

}
