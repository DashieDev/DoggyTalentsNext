package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.api.registry.Accessory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class HotDogAccessoryItem extends AccessoryItem {

    public HotDogAccessoryItem(Supplier<? extends Accessory> type, Properties properties) {
        super(type, properties.food(
            (new FoodProperties.Builder())
                        .nutrition(8)
                        .saturationMod(1F)
                        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 1)
                        .build()
        ));
    }
    
}
