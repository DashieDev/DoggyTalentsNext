package doggytalents.api.backward_imitate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;

public class EntityUtil_1_21_5 {
    
    public static @Nullable UUID getOwnerUUID(TamableAnimal entity) {
        var ref = entity.getOwnerReference();
        if (ref == null)
            return null;
        return ref.getUUID();
    }

    public static List<ItemStack> getArmorSlots(LivingEntity entity) {
        var ret = new ArrayList<ItemStack>(4);
        for (var slot : EquipmentSlotGroup.ARMOR) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR)
                continue;
            var stack = entity.getItemBySlot(slot);
            if (stack.isEmpty())
                continue;
            ret.add(stack);
        }
        return ret;
    }

}
