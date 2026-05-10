package doggytalents.common.inventory;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class DoggyToolsItemHandler extends ItemStackHandler {
    public DoggyToolsItemHandler() {
        super(5);
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
        compound.put("item_list", itemsList);

        return compound;
    }

    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound) {
        if (!compound.contains("item_list")) return;
        ListTag tagList = compound.getListOrEmpty("item_list");
        var nbtOps = prov.createSerializationContext(NbtOps.INSTANCE);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTag = tagList.getCompoundOrEmpty(i);
            int slot = itemTag.getIntOr("Slot", 0);

            if (slot >= 0 && slot < this.stacks.size()) {
                ItemStack.CODEC.parse(nbtOps, itemTag).result().ifPresent(stack -> stacks.set(slot, stack));
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
