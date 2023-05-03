package doggytalents.common.item;

import doggytalents.DoggyItemGroups;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class EggSandwichItem extends Item {

    public EggSandwichItem() {
        super(
            (new Properties()).food(
                (new FoodProperties.Builder())
                    .nutrition(6)
                    .saturationMod(0.6F)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 1)
                    .build()
            )
        );
    }
    
}
