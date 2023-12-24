package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.World;

public class AburaageItem extends DogEddibleItem {

    public AburaageItem() {
        super(
            b -> b
            .nutrition(10)
            .saturationMod(0.8F)
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
}
