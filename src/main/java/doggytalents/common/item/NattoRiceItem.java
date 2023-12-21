package doggytalents.common.item;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import java.util.List;

import doggytalents.DoggyEffects;
import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import doggytalents.api.enu.forward_imitate.ComponentUtil;

public class NattoRiceItem extends DogEddibleBowlFoodItem {

    public NattoRiceItem() {
        super(
            b -> b
                .nutrition(8)
                .saturationMod(0.8f)
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
        return List.of(Pair.of(new MobEffectInstance(DoggyEffects.NATTO_BITE.get(), 300 * 20, 1), 1f));
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
}