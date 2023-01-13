package doggytalents.common.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class DoggyToolsItemHandler extends ItemStackHandler {
    public DoggyToolsItemHandler(int size) {
        super(size);
    }

    @Override
    public CompoundTag serializeNBT() {
        ListTag itemsList = new ListTag();

        for(int i = 0; i < this.stacks.size(); i++) {
           ItemStack stack = this.stacks.get(i);
           if (!stack.isEmpty()) {
              CompoundTag itemTag = new CompoundTag();
              itemTag.putByte("Slot", (byte) i);
              stack.save(itemTag);
              itemsList.add(itemTag);
           }
        }

        CompoundTag compound = new CompoundTag();
        compound.put("doggyTools", itemsList);

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        if (compound.contains("doggyTools", Tag.TAG_LIST)) {
            ListTag tagList = compound.getList("doggyTools", Tag.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag itemTag = tagList.getCompound(i);
                int slot = itemTag.getInt("Slot");

                if (slot >= 0 && slot < this.stacks.size()) {
                    this.stacks.set(slot, ItemStack.of(itemTag));
                }
            }
            this.onLoad();
        }
    }
}
