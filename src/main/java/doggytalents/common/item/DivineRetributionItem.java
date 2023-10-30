package doggytalents.common.item;

import java.util.function.Supplier;

import javax.annotation.Nullable;
import java.util.List;

import doggytalents.api.registry.Accessory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class DivineRetributionItem extends AccessoryItem {

    public DivineRetributionItem(Supplier<? extends Accessory> type, Properties properties) {
        super(type, properties);
        //TODO Auto-generated constructor stub
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
