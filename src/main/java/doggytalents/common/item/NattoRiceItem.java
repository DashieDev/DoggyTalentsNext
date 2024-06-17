package doggytalents.common.item;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import java.util.List;

import doggytalents.DoggyEffects;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.accessory.CeremonialGarb.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class NattoRiceItem extends DogEddibleBowlFoodItem {

    public NattoRiceItem() {
        super(
            b -> b
                .nutrition(8)
                .saturationModifier(0.8f)
        );
    }
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }

    @Override
    public List<PossibleEffect> getAdditionalEffectsWhenDogConsume(ItemStack useStack,
            AbstractDog dog) {
        return List.of(new PossibleEffect(() -> new MobEffectInstance(DoggyEffects.NATTO_BITE, 300 * 20, 1), 1f));
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
}