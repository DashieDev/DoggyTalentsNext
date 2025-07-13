package doggytalents.api.forge_imitate.inventory;

import doggytalents.api.backward_imitate.CodecUtil_1_21_7;
import doggytalents.api.backward_imitate.CompoundTag_1_21_5;
import doggytalents.api.backward_imitate.ListTag_1_21_5;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class ItemStackHandler extends SimpleContainer {

    protected NonNullList<ItemStack> stacks;
    
    public ItemStackHandler(int i) {
        super(i);
        this.stacks = this.items;
    }

    public CompoundTag serializeNBT(HolderLookup.Provider prov) {
        var itemsList = new ListTag();

        for(int i = 0; i < this.stacks.size(); i++) {
           ItemStack stack = this.stacks.get(i);
           if (!stack.isEmpty()) {
              CompoundTag itemTag = new CompoundTag();
              itemTag.putByte("Slot", (byte) i);
              itemsList.add(CodecUtil_1_21_7.saveItemStack(stack, prov, itemTag));
           }
        }

        CompoundTag compound = new CompoundTag();
        compound.put("item_list", itemsList);

        return compound;
    }

    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound_1_21_5) {
        var compound = CompoundTag_1_21_5.wrap(compound_1_21_5); // 1.21.5+
        if (!compound.contains("item_list", Tag.TAG_LIST)) return;
        ListTag_1_21_5 tagList = compound.getList("item_list", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag_1_21_5 itemTag = tagList.getCompound(i);
            int slot = itemTag.getInt("Slot");

            if (slot >= 0 && slot < this.stacks.size()) {
                this.stacks.set(slot, CodecUtil_1_21_7.parseItemStack(prov, itemTag.wrapped()).orElse(ItemStack.EMPTY));
            }
        }
        this.onLoad();
    }

    public void onLoad() {}

    public ItemStack getStackInSlot(int i) {
        return this.getItem(i);
    };

    public void setStackInSlot(int i, ItemStack stack) {
        this.setItem(i, stack);
    };

    public int getSlots() {
        return this.getContainerSize();
    };

    @Override
    public boolean canPlaceItem(int i, ItemStack itemStack) {
        return isItemValid(i, itemStack);
    }

    public boolean isItemValid(int i, ItemStack itemStack) { return true; }
    
    @Override
    public void setChanged() {
        this.onContentsChanged();
    }

    protected void onContentsChanged() {

    }

    @Override
    public boolean canTakeItem(Container container, int i, ItemStack itemStack) {
        return false;
    }

}
