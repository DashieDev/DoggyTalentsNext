package doggytalents.common.inventory;

import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class PackPuppyItemHandler extends ItemStackHandler {

    private PackPuppyTalent inst;

    public PackPuppyItemHandler(PackPuppyTalent inst) {
        super(15);
        this.inst = inst;
    }

    public CompoundTag serializeNBT(HolderLookup.Provider prov) {
        ListTag itemsList = new ListTag();
        var ops = prov.createSerializationContext(NbtOps.INSTANCE);

        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putByte("Slot", (byte) i);
                ItemStack.CODEC.encodeStart(ops, stack).ifSuccess(tag -> {
                    if (tag instanceof CompoundTag ct) {
                        ct.keySet().forEach(k -> itemTag.put(k, ct.get(k)));
                    }
                    itemsList.add(itemTag);
                });
            }
        }

        CompoundTag compound = new CompoundTag();
        compound.put("items", itemsList);
        return compound;
    }

    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound) {
        var ops = prov.createSerializationContext(NbtOps.INSTANCE);
        if (compound.contains("items")) {
            try {
                ListTag tagList = compound.getListOrEmpty("items");
                for (int i = 0; i < tagList.size(); i++) {
                    CompoundTag itemTag = tagList.getCompoundOrEmpty(i);
                    int slot = itemTag.getIntOr("Slot", 0);
                    if (slot >= 0 && slot < this.stacks.size()) {
                        int finalSlot = slot;
                        ItemStack.CODEC.parse(ops, itemTag).ifSuccess(stack -> stacks.set(finalSlot, stack));
                    }
                }
                this.onLoad();
            } catch (Exception e) {
            }
        } else if (compound.contains("packpuppyitems")) {
            ListTag tagList = compound.getListOrEmpty("packpuppyitems");
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag itemTag = tagList.getCompoundOrEmpty(i);
                int slot = itemTag.getIntOr("Slot", 0);
                if (slot >= 0 && slot < this.stacks.size()) {
                    int finalSlot = slot;
                    ItemStack.CODEC.parse(ops, itemTag).ifSuccess(stack -> stacks.set(finalSlot, stack));
                }
            }
            this.onLoad();
        }
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (slot >= inst.level() * 3)
            return false;
        return super.isItemValid(slot, stack);
    }
}
