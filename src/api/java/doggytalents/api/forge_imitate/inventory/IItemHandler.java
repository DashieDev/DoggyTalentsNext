package doggytalents.api.forge_imitate.inventory;

import net.minecraft.world.item.ItemStack;

public interface IItemHandler {
    
    public ItemStack getStackInSlot(int i);

    public void setStackInSlot(int i, ItemStack stack);

    public int getSlots();

    public boolean isItemValid(int id, ItemStack stack);

    public ItemStack insertItem(int id, ItemStack stack, boolean simulate);

}
