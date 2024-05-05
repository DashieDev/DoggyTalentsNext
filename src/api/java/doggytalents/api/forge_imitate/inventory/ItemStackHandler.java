package doggytalents.api.forge_imitate.inventory;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
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

    public CompoundTag serializeNBT(HolderLookup.Provider prov) { return new CompoundTag(); }

    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound) {}

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
