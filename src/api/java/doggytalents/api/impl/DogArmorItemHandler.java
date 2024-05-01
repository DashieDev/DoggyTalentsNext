package doggytalents.api.impl;

import org.jetbrains.annotations.NotNull;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class DogArmorItemHandler extends ItemStackHandler {
    
    private AbstractDog dog;

    public DogArmorItemHandler(AbstractDog dog) {
        super(4);
        this.dog = dog;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider prov) {
        ListTag itemsList = new ListTag();

        for(int i = 0; i < this.stacks.size(); i++) {
           ItemStack stack = this.stacks.get(i);
           if (!stack.isEmpty()) {
              CompoundTag itemTag = new CompoundTag();
              itemTag.putByte("Slot", (byte) i);
              itemsList.add(stack.save(prov, itemTag));
           }
        }

        CompoundTag compound = new CompoundTag();
        compound.put("dogArmors", itemsList);

        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound) {
        if (compound.contains("dogArmors", Tag.TAG_LIST)) {
            ListTag tagList = compound.getList("dogArmors", Tag.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag itemTag = tagList.getCompound(i);
                int slot = itemTag.getInt("Slot");

                var stack = ItemStack.parse(prov, itemTag).orElse(ItemStack.EMPTY);
                setArmorInSlot(stack);
                
            }
            this.onLoad();
        }
    }

    public void setArmorInSlot(ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof ArmorItem armor))
            return;
        var slot = armor.getType().getSlot();
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

        var item = stack.getItem();
        if (!(item instanceof ArmorItem armor))
            return false;
        var wantSlot = armor.getType().getSlot();

        return wantSlot == equip;
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
