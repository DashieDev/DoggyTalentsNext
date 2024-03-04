package doggytalents.common.inventory.container.slot;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class DogInventorySlot extends SlotItemHandler {

    private boolean enabled = true;
    private Player player;
    private Dog dog;
    private int absolute_col, relative_row, relative_col;

    public DogInventorySlot(Dog dogIn, Player playerIn, IItemHandler itemHandler, int absolute_col, int relative_row, int relative_col, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = playerIn;
        this.absolute_col = absolute_col;
        this.relative_row = relative_row;
        this.relative_col = relative_col;
        this.dog = dogIn;
    }

    public DogInventorySlot(DogInventorySlot prev, int newX) {
        super(prev.getItemHandler(), prev.getSlotIndex(), newX, prev.y);
        this.player = prev.player;
        this.absolute_col = prev.absolute_col;
        this.relative_row = prev.relative_row;
        this.relative_col = prev.relative_col;
        this.dog = prev.dog;
        // Work around to set Slot#slotNumber (MCP name) which is Slot#index in official
        // mappings. Needed because SlotItemHandler#index shadows the latter.
        {
            Slot n = this;
            Slot o = prev;
            n.index = o.index;
        }
    }

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    // Don't accept items when disabled, this means disabled slots cannot be shift clicked into
    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.isActive() && super.mayPlace(stack);
    }

//    @Override
//    public boolean canTakeStack(PlayerEntity playerIn) {
//        return super.canTakeStack(playerIn);
//    }

    @Override
    public boolean isActive() {
        return this.enabled && this.dog.isDoingFine() && this.dog.distanceToSqr(this.player) < 400;
    }

    public Dog getDog() {
        return this.dog;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getAbsoluteCol() {
        return this.absolute_col;
    }

    public int getRelativeRow() {
        return this.relative_row;
    }

    public int getRelativeCol() {
        return this.relative_col;
    }
}
