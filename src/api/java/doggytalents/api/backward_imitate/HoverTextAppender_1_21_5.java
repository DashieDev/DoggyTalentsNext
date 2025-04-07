package doggytalents.api.backward_imitate;

import java.util.List;
import java.util.Optional;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public interface HoverTextAppender_1_21_5 {
    
    default void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
        TooltipFlag flags) {};

    public static Optional<HoverTextAppender_1_21_5> findFromStack(ItemStack stack) {
        var item = stack.getItem();
        if (item instanceof HoverTextAppender_1_21_5 appender) {
            return Optional.of(appender);
        }
        if (
            item instanceof BlockItem block_item
            && block_item.getBlock() instanceof HoverTextAppender_1_21_5 appender
        ) {
            return Optional.of(appender);
        }
        return Optional.empty();
    }

}
