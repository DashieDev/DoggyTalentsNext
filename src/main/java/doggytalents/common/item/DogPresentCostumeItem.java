package doggytalents.common.item;

import java.util.function.Supplier;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import doggytalents.common.entity.accessory.DoubleDyableAccessory;

public class DogPresentCostumeItem extends DoubleDyableAccessoryItem {
    
    public DogPresentCostumeItem(Supplier<? extends DoubleDyableAccessory> type, Properties properties) {
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

    @Override
    public int getDefaultBgColor() {
        return 0xffAA0000;
    }
    @Override
    public int getDefaultFgColor() {
        return 0xffFFFF55;
    }
}
