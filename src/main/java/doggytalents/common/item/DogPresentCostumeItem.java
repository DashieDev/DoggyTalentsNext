package doggytalents.common.item;

import java.util.function.Supplier;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.accessory.DoubleDyableAccessory;

public class DogPresentCostumeItem extends DoubleDyableAccessoryItem {
    
    public DogPresentCostumeItem(Supplier<? extends DoubleDyableAccessory> type, Properties properties) {
        super(type, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level context, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(ComponentUtil.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }

    @Override
    public int getDefaultBgColor() {
        return 0xffAA0000;
    }
    @Override
    public int getDefaultFgColor() {
        return 0xffFFFF55;
    }
}
