package doggytalents.api.forge_imitate.inventory;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class ContainerWrapper implements IItemHandler {
    
    private Container container;

    public ContainerWrapper(Container container) {
        this.container = container;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return container.getItem(i);
    }

    @Override
    public void setStackInSlot(int i, ItemStack stack) {
        container.setItem(i, stack);
    }

    @Override
    public int getSlots() {
        return container.getContainerSize();
    }

    public Container getContainer() {
        return this.container;
    }

    @Override
    public ItemStack insertItem(int id, ItemStack stack, boolean simulate) {
        simulate = false;
        int container_max_stack_size = container.getMaxStackSize();
        var current = this.getStackInSlot(id);
        if (current.isEmpty()) {
            if (!this.container.canPlaceItem(id, stack))
                return stack;
            var placeStack = stack.copy();
            var returnStack =  ItemStack.EMPTY;
            boolean needsCut = stack.getCount() > container_max_stack_size;
            if (needsCut) {
                returnStack = placeStack;
                placeStack = returnStack.split(container_max_stack_size);
            }
            this.container.setItem(id, placeStack);
            return returnStack;
        }
        if (hasTag(stack) || hasTag(current))
            return stack;
        if (!ItemStack.isSameItem(stack, current))
            return stack;
        int current_max_grow = Math.min(current.getMaxStackSize(), container_max_stack_size);
        int current_can_recieve = current_max_grow - current.getCount();
        if (current_can_recieve <= 0)
            return stack;
        var addStack = stack.copy();
        var returnStack = ItemStack.EMPTY;
        boolean needsCut = 
            addStack.getCount() > current_can_recieve;
        if (needsCut) {
            returnStack = addStack;
            addStack = returnStack.split(current_can_recieve);
        }
        int totalAdd = current.getCount() + addStack.getCount();
        this.container.setItem(id, current.copyWithCount(totalAdd));
        return returnStack;
    }

    @Override
    public boolean isItemValid(int id, ItemStack stack) {
        return this.container.canPlaceItem(id, stack);
    }

    public static boolean hasTag(ItemStack stack) {
        return stack.has(DataComponents.CUSTOM_DATA);
    }

}