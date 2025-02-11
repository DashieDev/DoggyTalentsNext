package doggytalents.api.backward_imitate;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

public class ItemUtil_1_21_3 {
    public static EquipmentSlot getEquipmentSlot(ItemStack stack) {
        var data = getEquippable_1_21_3(stack);
        if (data == null)
            return null;
        return data.slot();
    }
    public static Equippable getEquippable_1_21_3(ItemStack stack) {
        return stack.get(DataComponents.EQUIPPABLE);
    }
}
