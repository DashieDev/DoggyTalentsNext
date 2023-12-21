package doggytalents.common.item;

import javax.annotation.Nullable;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class OnsenTamagoItem extends DogEddibleItem{

    public static FoodProperties FOOD_PROPS = 
        (new FoodProperties.Builder())
            
            .build();

    public OnsenTamagoItem() {
        super(
            b -> b
                .nutrition(4)
                .saturationMod(0.6F)
                .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 1)
        );
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    @Override
    public float getAddedHungerWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getNutrition() * 5;
    }

    @Override
    public List<Pair<MobEffectInstance, Float>> getAdditionalEffectsWhenDogConsume(ItemStack useStack,
            AbstractDog dog) {
        return FOOD_PROPS.getEffects();
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
    
}
