package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.common.entity.accessory.DoubleDyableAccessory;

public class DogPresentCostumeItem extends DoubleDyableAccessoryItem {
    
    public DogPresentCostumeItem(Supplier<? extends DoubleDyableAccessory> type, Properties properties) {
        super(type, properties);
    }

    // @Override
    // public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
    //         TooltipFlag flags) {
    //     var desc_id = this.getDescriptionId(stack) + ".description";
    //     components.add(Component.translatable(desc_id).withStyle(
    //         Style.EMPTY.withItalic(true)
    //     ));
    // }

    @Override
    public int getDefaultBgColor() {
        return 0xffffffff;
    }
    
}
