package doggytalents.common.inventory;

import org.jetbrains.annotations.NotNull;

import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.DogArmorItemHandler;
import doggytalents.common.util.ItemUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.enchantment.Enchantments;

public class DogArmorItemHandlerImpl extends DogArmorItemHandler {

    public DogArmorItemHandlerImpl(AbstractDog dog) {
        super(dog);
    }

    public CompoundTag serializeNBT(HolderLookup.Provider prov) {
        ListTag itemsList = new ListTag();
        var nbtOps = prov.createSerializationContext(NbtOps.INSTANCE);

        for(int i = 0; i < this.stacks.size(); i++) {
           ItemStack stack = this.stacks.get(i);
           if (!stack.isEmpty()) {
              var encoded = ItemStack.CODEC.encodeStart(nbtOps, stack).result().orElse(null);
              if (encoded instanceof CompoundTag ct) {
                  ct.putByte("Slot", (byte) i);
                  itemsList.add(ct);
              }
           }
        }

        CompoundTag compound = new CompoundTag();
        compound.put("dogArmors", itemsList);

        return compound;
    }

    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound) {
        if (compound.contains("dogArmors")) {
            ListTag tagList = compound.getListOrEmpty("dogArmors");
            var nbtOps = prov.createSerializationContext(NbtOps.INSTANCE);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag itemTag = tagList.getCompoundOrEmpty(i);
                int slot = itemTag.getIntOr("Slot", 0);

                var stack = ItemStack.CODEC.parse(nbtOps, itemTag).result().orElse(ItemStack.EMPTY);
                setArmorInSlot(stack);

            }
            this.onLoad();
        }
    }

    public void setArmorInSlot(ItemStack stack) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable == null)
            return;
        var slot = equippable.slot();
        setArmorInSlot(stack, slot);
    }

    public void setArmorInSlot(ItemStack stack, EquipmentSlot slot) {
        var dogSlot = DogArmorSlots.byEquipment(slot);
        if (dogSlot == null)
            return;
        this.stacks.set(dogSlot.slotId, stack);
    }

    public ItemStack getArmorFromSlot(EquipmentSlot slot) {
        var dogSlot = DogArmorSlots.byEquipment(slot);
        if (dogSlot == null)
            return ItemStack.EMPTY;
        return this.stacks.get(dogSlot.slotId);
    }
    
    public Iterable<ItemStack> armors() {
        return this.stacks;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (!dog.canDogWearArmor())
            return false;

        var dogSlot = DogArmorSlots.bySlotId(slot);
        if (dogSlot == null)
            return false;
        var equip = dogSlot.val;

        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable == null)
            return false;
        var wantSlot = equippable.slot();

        if (wantSlot != equip)
            return false;

        //Workaround for depth strider being a bit overpowered on dogs.    
        boolean is_depth_strider = 
            wantSlot == EquipmentSlot.FEET
            && ItemUtil.getEnchantmentLevelForItem(Enchantments.DEPTH_STRIDER, 
                dog.level().registryAccess(), stack) > 0;
        if (is_depth_strider)
            return false;

        return true;
    }

    public void onPropsUpdated(DogAlterationProps props) {
        if (props.canWearArmor())
            return;
        for (int i = 0; i < this.stacks.size(); ++i)
            stacks.set(i, ItemStack.EMPTY);
    }

    public static enum DogArmorSlots {
        HEAD(0, EquipmentSlot.HEAD),
        CHEST(1, EquipmentSlot.CHEST),
        LEGS(2, EquipmentSlot.LEGS),
        FEET(3, EquipmentSlot.FEET);

        public final int slotId;
        public final EquipmentSlot val;

        private DogArmorSlots(int slotId, EquipmentSlot val) {
            this.slotId = slotId;
            this.val = val;
        }

        public static DogArmorSlots bySlotId(int slotId) {
            var values = DogArmorSlots.values();
            for (var val : values) {
                if (val.slotId == slotId)
                    return val;
            }
            return null;
        }

        public static DogArmorSlots byEquipment(EquipmentSlot slot) {
            var values = DogArmorSlots.values();
            for (var val : values) {
                if (val.val == slot)
                    return val;
            }
            return null;
        }
        
    }

}
