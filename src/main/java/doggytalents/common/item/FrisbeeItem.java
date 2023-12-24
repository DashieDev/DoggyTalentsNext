package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.World;
import doggytalents.api.enu.forward_imitate.ComponentUtil;

public class FrisbeeItem extends ThrowableItem implements IDyeableArmorItem {

    public FrisbeeItem(Supplier<? extends Item> altBone, Supplier<? extends Item> renderBone, Properties properties) {
        super(altBone, renderBone, properties);
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
    public ItemStack getCustomRenderStack(ItemStack stack) {
        return null;
    }

}
