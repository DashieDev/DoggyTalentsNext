package doggytalents.api.backward_imitate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.effect.MobEffectInstance;
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

    public static List<ItemStack> getHandSlot(LivingEntity entity) {
        var ret = new ArrayList<ItemStack>();
        for (var slot : EquipmentSlotGroup.HAND) {
            if (slot.getType() != EquipmentSlot.Type.HAND)
                continue;
            var stack = entity.getItemBySlot(slot);
            if (stack.isEmpty())
                continue;
            ret.add(stack);
        }
        return ret;
    }

    public static CompoundTag tryEncodeEffect(MobEffectInstance ins) {
        try {
            var result = MobEffectInstance.CODEC.encodeStart(NbtOps.INSTANCE, ins).result();
            if (!result.isPresent())
                return new CompoundTag();
            if (!(result.get() instanceof CompoundTag effect_tag))
                return new CompoundTag();
            return effect_tag;
        } catch (Exception e) {

        }
        return new CompoundTag();
    }

}
