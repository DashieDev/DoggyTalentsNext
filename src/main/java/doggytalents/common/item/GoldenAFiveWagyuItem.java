package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import doggytalents.DoggyItemGroups;
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

public class GoldenAFiveWagyuItem extends Item {

    public GoldenAFiveWagyuItem() {
        super(
            (new Properties()).food(
                (new FoodProperties.Builder())
                    .nutrition(8)
                    .saturationMod(10F)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 1200, 1), 1)
                    .build()
            )
            .tab(DoggyItemGroups.GENERAL)
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
}
