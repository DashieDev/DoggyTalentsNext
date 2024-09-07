package doggytalents.common.inventory;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class PackPuppyItemHandler extends ItemStackHandler {

    public PackPuppyItemHandler() {
        super(15);
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
        compound.put("items", itemsList);

        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound) {
        if (compound.contains("items", Tag.TAG_LIST)) {
            try {
                ListTag tagList = compound.getList("items", Tag.TAG_COMPOUND);
                for (int i = 0; i < tagList.size(); i++) {
                    CompoundTag itemTag = tagList.getCompound(i);
                    int slot = itemTag.getInt("Slot");

                    if (slot >= 0 && slot < this.stacks.size()) {
                        ItemStack.parse(prov, itemTag).ifPresent(stack -> stacks.set(slot, stack));
                    }
                }
                this.onLoad();
            } catch (Exception e) {

            }
        } else if (compound.contains("packpuppyitems", Tag.TAG_LIST)) {
            ListTag tagList = compound.getList("packpuppyitems", Tag.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag itemTag = tagList.getCompound(i);
                int slot = itemTag.getInt("Slot");

                if (slot >= 0 && slot < this.stacks.size()) {
                    ItemStack.parse(prov, itemTag).ifPresent(stack -> stacks.set(slot, stack));
                }
            }
            this.onLoad();
        }
    }
}
