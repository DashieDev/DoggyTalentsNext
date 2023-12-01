package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import doggytalents.DoggyItemGroups;
import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import doggytalents.api.enu.forward_imitate.ComponentUtil;

public class GoldenAFiveWagyuItem extends Item implements IDogEddible {

    public static final FoodProperties FOOD_PROPS = 
        (new FoodProperties.Builder())
            .nutrition(8)
            .saturationMod(10F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 1), 1)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1), 1)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 1200, 1), 1)
            .build();

    public GoldenAFiveWagyuItem() {
        super(
            (new Properties()).food(FOOD_PROPS)
        );
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(ComponentUtil.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }
    @Override
    public float getAddedHungerWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getNutrition() * 5;
    }

    @Override
    public List<Pair<MobEffectInstance, Float>> getAdditionalEffectsWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getEffects();
    }
}
