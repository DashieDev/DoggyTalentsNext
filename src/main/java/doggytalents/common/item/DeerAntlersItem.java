package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.api.registry.Accessory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class DeerAntlersItem extends AccessoryItem {

    public DeerAntlersItem(Supplier<? extends Accessory> type, Properties properties) {
        super(type, properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    
}