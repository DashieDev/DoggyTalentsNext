package doggytalents.common.item;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import com.mojang.datafixers.util.Pair;
import doggytalents.api.enu.forward_imitate.ComponentUtil;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class GyudonItem extends DogEddibleBowlFoodItem {

    public GyudonItem() {
        super(
            b -> b
                .nutrition(20)
                .saturationMod(1f)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400, 1), 1)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 1), 1)
                .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1), 1)
                .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1), 1)
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
    public List<Pair<MobEffectInstance, Float>> getAdditionalEffectsWhenDogConsume(ItemStack useStack,
            AbstractDog dog) {
        var ret = super.getAdditionalEffectsWhenDogConsume(useStack, dog);
        var newRet = new ArrayList<Pair<MobEffectInstance, Float>>(ret.size());
        for (var pair : ret) {
            var effect = pair.getFirst();
            var newDuration = effect.getEffect().isInstantenous() ?
                effect.getDuration()
                : effect.mapDuration(x -> x + 2 * 60 * 20);
            var newEffect = new MobEffectInstance(
                effect.getEffect(),
                newDuration,
                effect.getAmplifier()
            );
            var newPair = new Pair<>(newEffect, 1f);
            newRet.add(newPair);
        }
        return newRet;
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
}
