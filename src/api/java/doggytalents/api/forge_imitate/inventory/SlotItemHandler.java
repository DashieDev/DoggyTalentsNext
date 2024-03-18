package doggytalents.api.forge_imitate.inventory;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotItemHandler extends Slot {

    protected ItemStackHandler handler;

    public SlotItemHandler(ItemStackHandler container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.handler = container;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return handler.isItemValid(this.index, itemStack);
    }
    
}
