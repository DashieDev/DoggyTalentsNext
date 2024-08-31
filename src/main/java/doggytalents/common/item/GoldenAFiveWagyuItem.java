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

public class GoldenAFiveWagyuItem extends DogEddibleItem {

    public GoldenAFiveWagyuItem() {
        super(
            b -> b
                .rarity(Rarity.UNCOMMON),
            b -> b
                .nutrition(8)
                .saturationModifier(10F)
                .effect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 1), 1)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1), 1)
                .effect(new MobEffectInstance(MobEffects.SATURATION, 1200, 1), 1)
        
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
    // @Override
    // public Rarity getRarity(ItemStack stack) {
    //     return Rarity.UNCOMMON;
    // }
    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
}
