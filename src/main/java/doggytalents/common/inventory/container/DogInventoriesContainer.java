package doggytalents.common.inventory.container;

import doggytalents.DoggyContainerTypes;
import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.inventory.container.slot.DogInventorySlot;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ProPercivalalb
 */
public class DogInventoriesContainer extends AbstractContainerMenu {

    private Level level;
    private Player player;
    //Read only view, for write, use setData instead.
    private DataSlot slotViewOffset;
    private final List<Dog> chestDogs;
    private final List<DogInventorySlot> dogSlots = new ArrayList<>();
    private int totalDogColsCount = 0;
    public static final int VIEW_OFFSET_DATA_ID = 0;

    private int dogSlotsStartsAt = 0;

    public DogInventoriesContainer(int windowId, Inventory playerInventory, List<Dog> chestDogs) {
        super(DoggyContainerTypes.DOG_INVENTORIES.get(), windowId);
        
        this.level = playerInventory.player.level;
        this.player = playerInventory.player;

        this.slotViewOffset = DataSlot.standalone();
        this.addDataSlot(this.slotViewOffset);

        this.chestDogs = chestDogs;

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
        
        this.dogSlotsStartsAt = this.slots.size();

        this.addDogSlots();
    }

    public void addDogSlots() {
        final int VIEWPORT_COLS = 9;

        int view_offset = this.slotViewOffset.get();
        int current_col = 0;

        for (var dog : chestDogs) {
            var packInventory = dog.getTalent(DoggyTalents.PACK_PUPPY)
                    .map((inst) -> inst.cast(PackPuppyTalent.class).inventory())
                    .orElse(null);
            if (packInventory == null) {
                continue;
            }

            int dog_total_cols = Mth.clamp(dog.getDogLevel(DoggyTalents.PACK_PUPPY), 0, 5);
    
            for (int col = 0; col < dog_total_cols; col++) {
                for (int row = 0; row < 3; row++) {
                    var slot_indx = col * 3 + row;
                    var abs_col = current_col + col;
                    var viewX = 8 + 18 * (current_col + col - view_offset);
                    var viewY = 18 * row + 18;
                    var slot = new DogInventorySlot(
                        dog, this.player, packInventory, 
                        abs_col, row, col, slot_indx, viewX, viewY);
                    this.addDogSlot(slot);
                    int viewport_col = slot.getAbsoluteCol() - view_offset;
                    boolean inside_of_viewport = 
                        viewport_col >= 0 && viewport_col < VIEWPORT_COLS;
                    if (!inside_of_viewport)
                        slot.setEnabled(false);
                }
            }

            this.totalDogColsCount += dog_total_cols;
            current_col += dog_total_cols;
        }

    }

    @Override
    public void setData(int id, int data) {
        super.setData(id, data);

        if (id == VIEW_OFFSET_DATA_ID) {
            reDrawDogSlots(data);
        }

    }

    private void reDrawDogSlots(int newViewOffset) {
        for (int i = 0; i < this.dogSlots.size(); i++) {
            var slot0 = this.dogSlots.get(i);
            var new_slot = new DogInventorySlot(slot0, 8 + 18 * (slot0.getAbsoluteCol() - newViewOffset));
            this.replaceDogSlot(i, new_slot);
            int viewport_col = slot0.getAbsoluteCol() - newViewOffset;
            if (viewport_col < 0 || viewport_col >= 9) {
                new_slot.setEnabled(false);
            }
        }
    }

    private void addDogSlot(DogInventorySlot slotIn) {
        this.addSlot(slotIn);
        this.dogSlots.add(slotIn);
    }

    private void replaceDogSlot(int i, DogInventorySlot slotIn) {
        this.dogSlots.set(i, slotIn);
        // Work around to set Slot#slotNumber (MCP name) which is Slot#index in official
        // mappings. Needed because SlotItemHandler#index shadows the latter.
        Slot s = slotIn;
        this.slots.set(s.index, slotIn);
    }

    public int getTotalNumColumns() {
        return this.totalDogColsCount;
    }

    public int getViewOffset() {
        return this.slotViewOffset.get();
    }

    public void setViewOffset(int offset) {
        this.setData(VIEW_OFFSET_DATA_ID, offset);
    }

    public List<DogInventorySlot> getSlots() {
        return this.dogSlots;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        Slot slot = this.slots.get(i);

        if (slot == null || !slot.hasItem())
            return ItemStack.EMPTY;

        var moveStack0 = ItemStack.EMPTY;
        var moveStack = slot.getItem();
        moveStack0 = moveStack.copy();

        boolean moveResult = false;
        boolean clickedDogSlot = i >= dogSlotsStartsAt && i < this.slots.size();
        if (clickedDogSlot)
            moveResult = moveItemStackTo(moveStack, 0, this.dogSlotsStartsAt, true);
        else
            moveResult = moveItemStackTo(moveStack, this.dogSlotsStartsAt, this.slots.size(), false);
        if (!moveResult)
            return ItemStack.EMPTY;

        if (moveStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (moveStack.getCount() == moveStack0.getCount()) {
            return ItemStack.EMPTY;
        }
        return moveStack0;
    }
}
