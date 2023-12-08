package doggytalents.common.item;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import doggytalents.DoggyItemGroups;
import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EggSandwichItem extends DogEddibleItem {

    public EggSandwichItem() {
        super(
            b -> b
                .nutrition(6)
                .saturationMod(0.6F)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 1)
        );
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
    
}
