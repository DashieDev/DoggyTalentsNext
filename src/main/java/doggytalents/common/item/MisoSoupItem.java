package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class MisoSoupItem extends BowlFoodItem implements IDogEddible {

    public static FoodProperties FOOD_PROPS = 
        (new FoodProperties.Builder())
            .nutrition(8)
            .saturationMod(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 1)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 1), 1)
            .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1), 1)
            .alwaysEat()
            .build();

    public MisoSoupItem() {
        super(
            (new Properties()).food(
                FOOD_PROPS
            ).stacksTo(1).craftRemainder(Items.BOWL)
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

    @Override
    public ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return new ItemStack(Items.BOWL);
    }
    
    @Override
    public SoundEvent getDogEatingSound(AbstractDog dog) {
        return SoundEvents.GENERIC_DRINK;
    }
    
}
