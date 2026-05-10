package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;



import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SussySickleItem extends Item {

    public SussySickleItem(Properties p_43272_) {
        super(p_43272_.sword(ToolMaterial.IRON, 3, -2.4F));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<net.minecraft.network.chat.Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId() + ".description";
        components.accept(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(0xff8B0000
            )))
        );
    }
}
