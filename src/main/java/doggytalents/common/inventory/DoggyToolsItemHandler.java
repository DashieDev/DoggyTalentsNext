package doggytalents.common.inventory;

import doggytalents.api.backward_imitate.CompoundTag_1_21_5;
import doggytalents.api.backward_imitate.ListTag_1_21_5;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class DoggyToolsItemHandler extends ItemStackHandler {
    public DoggyToolsItemHandler() {
        super(5);
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
        compound.put("item_list", itemsList);

        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound_1_21_5) {
        var compound = CompoundTag_1_21_5.wrap(compound_1_21_5); // 1.21.5+

        if (!compound.contains("item_list", Tag.TAG_LIST)) return;
        ListTag_1_21_5 tagList = compound.getList("item_list", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag_1_21_5 itemTag = tagList.getCompound(i);
            int slot = itemTag.getInt("Slot");

            if (slot >= 0 && slot < this.stacks.size()) {
                ItemStack.parse(prov, itemTag.wrapped()).ifPresent(stack -> stacks.set(slot, stack));
            }
        }
        this.onLoad();
    }

    public boolean hasStackRef(ItemStack stack) {
        //do not depends on the equals() like contains() do.
        for (var s : this.stacks) {
            if (stack == s) return true;
        }
        return false;
    }
}
