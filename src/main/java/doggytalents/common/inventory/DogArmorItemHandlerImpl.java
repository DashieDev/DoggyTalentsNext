package doggytalents.common.inventory;

import org.jetbrains.annotations.NotNull;

import doggytalents.api.backward_imitate.CodecUtil_1_21_7;
import doggytalents.api.backward_imitate.CompoundTag_1_21_5;
import doggytalents.api.backward_imitate.ItemUtil_1_21_3;
import doggytalents.api.backward_imitate.ItemUtil_1_21_5;
import doggytalents.api.backward_imitate.ListTag_1_21_5;
import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.DogArmorItemHandler;
import doggytalents.common.util.ItemUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class DogArmorItemHandlerImpl extends DogArmorItemHandler {

    public DogArmorItemHandlerImpl(AbstractDog dog) {
        super(dog);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider prov) {
        ListTag itemsList = new ListTag();

        for(int i = 0; i < this.stacks.size(); i++) {
           ItemStack stack = this.stacks.get(i);
           if (!stack.isEmpty()) {
              CompoundTag itemTag = new CompoundTag();
              itemTag.putByte("Slot", (byte) i);
              itemsList.add(CodecUtil_1_21_7.saveItemStack(stack, prov, itemTag));
           }
        }

        CompoundTag compound = new CompoundTag();
        compound.put("dogArmors", itemsList);

        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound_1_21_5) {
        var compound = CompoundTag_1_21_5.wrap(compound_1_21_5); // 1.21.5+

        if (compound.contains("dogArmors", Tag.TAG_LIST)) {
            ListTag_1_21_5 tagList = compound.getList("dogArmors", Tag.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag_1_21_5 itemTag = tagList.getCompound(i);
                int slot = itemTag.getInt("Slot");

                var stack = CodecUtil_1_21_7.parseItemStack(prov, itemTag.wrapped()).orElse(ItemStack.EMPTY);
                setArmorInSlot(stack);
                
            }
            this.onLoad();
        }
    }

    public void setArmorInSlot(ItemStack stack) {
        var item = stack.getItem();
        if (!(ItemUtil_1_21_5.isHumanoidArmor(stack)))
            return;
        var slot = ItemUtil_1_21_3.getEquipmentSlot(stack);
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
        if (!(ItemUtil_1_21_5.isHumanoidArmor(stack)))
            return false;
        var wantSlot = ItemUtil_1_21_3.getEquipmentSlot(stack);

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
