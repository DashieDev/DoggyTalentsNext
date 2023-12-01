package doggytalents.common.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Items;

public class SoyMilkItem extends BowlFoodItem{

    public SoyMilkItem() {
        super(
            (new Properties()).food(
                (new FoodProperties.Builder())
                    .nutrition(4)
                    .saturationMod(0.5F)
                    .build()
            ).stacksTo(1).craftRemainder(Items.BOWL)
        );
    }
    
}
