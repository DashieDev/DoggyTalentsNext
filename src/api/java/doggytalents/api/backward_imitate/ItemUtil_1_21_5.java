package doggytalents.api.backward_imitate;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ItemUtil_1_21_5 {
    
    public static boolean isHumanoidArmor(ItemStack stack) {
        var slot = ItemUtil_1_21_3.getEquipmentSlot(stack);
        if (slot == null)
            return false;
        return slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR;
    }

    public static boolean isMeleeWeapon(ItemStack stack) {
        return stack.has(DataComponents.WEAPON);
    }

    public static boolean isWolfArmor(ItemStack stack) {
        var slot = ItemUtil_1_21_3.getEquipmentSlot(stack);
        if (slot == null)
            return false;
        return slot.getType() == EquipmentSlot.Type.ANIMAL_ARMOR;
    }

}
