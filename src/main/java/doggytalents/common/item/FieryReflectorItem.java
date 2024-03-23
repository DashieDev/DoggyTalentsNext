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

public class FieryReflectorItem extends AccessoryItem {

    public FieryReflectorItem(Supplier<? extends Accessory> type, Properties properties) {
        super(type, properties);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = "item.doggytalents.divine_retribution.description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
        var desc_id2 = "item.doggytalents.divine_retribution.description2";
        components.add(Component.translatable(desc_id2).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    
}
