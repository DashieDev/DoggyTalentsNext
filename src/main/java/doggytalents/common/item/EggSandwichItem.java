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

public class EggSandwichItem extends Item implements IDogEddible {

    private static FoodProperties FOOD_PROPS = (new FoodProperties.Builder())
        .nutrition(6)
        .saturationMod(0.6F)
        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 1)
        .build();

    public EggSandwichItem() {
        super(
            (new Properties()).food(FOOD_PROPS)
        );
    }

    @Override
    public float getAddedHunger(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getNutrition() * 5;
    }

    @Override
    public List<Pair<MobEffectInstance, Float>> getAdditionalEffects(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getEffects();
    }
    
}
