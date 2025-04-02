package doggytalents.api.backward_imitate;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public interface HoverTextAppender_1_21_5 {
    
    default void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
        TooltipFlag flags) {};

}
