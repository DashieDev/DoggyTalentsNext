package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class MisoSoupItem extends BowlFoodItem{
    public MisoSoupItem() {
        super(
            (new Properties()).food(
                (new FoodProperties.Builder())
                    .nutrition(8)
                    .saturationMod(0.6F)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1), 1)
                    .build()
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
}
