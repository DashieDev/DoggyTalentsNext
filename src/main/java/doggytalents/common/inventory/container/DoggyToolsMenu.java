package doggytalents.common.inventory.container;

import doggytalents.DoggyContainerTypes;
import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.DoggyToolsItemHandler;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class DoggyToolsMenu extends AbstractContainerMenu {

    private Dog dog;
    private DoggyToolsItemHandler tools;
    private int beginToolsIndx = -1;
    private int toolsSize;

    public DoggyToolsMenu(int windowId, Inventory playerInventory, Dog dog) {
        super(DoggyContainerTypes.DOG_TOOLS.get(), windowId);

        this.dog = dog;

        var talent = dog.getTalent(DoggyTalents.DOGGY_TOOLS)
            .map((inst) -> inst.cast(DoggyToolsTalent.class));
        if (!talent.isPresent()) return;
        var dogTools = talent
            .map((inst) -> inst.getTools())
            .orElse(null);
        if (dogTools == null) return;
        this.tools = dogTools;

        for (int j = 0; j < 3; j++) {
            for (int i1 = 0; i1 < 9; i1++) {
                this.addSlot(new Slot(playerInventory, i1 + j * 9 + 9, 8 + i1 * 18, 45 + j * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 103));
        }

        this.beginToolsIndx = this.slots.size();

        int mX = 90;
        int aY = 23;

        this.toolsSize = DoggyToolsTalent.getSize(talent.map(inst -> inst.level()).orElse(0));
        int toolsSlotsOffsetX = toolsSize/2*18 + toolsSize%2*9;
        int pX = mX - toolsSlotsOffsetX;
        
        for (int i = 0; i < toolsSize; ++i) {
            this.addSlot(new SlotItemHandler(this.tools, i, pX, aY));
            pX += 18;
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index >= this.beginToolsIndx) {
                if (!moveItemStackTo(itemstack1, 0, this.beginToolsIndx, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!moveItemStackTo(itemstack1, this.beginToolsIndx, this.slots.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        var toolsLevel = dog.getDogLevel(DoggyTalents.DOGGY_TOOLS.get());
        if (toolsLevel <= 0) return false;
        if (getToolsSize() != DoggyToolsTalent.getSize(toolsLevel))
            return false;
        
        return dog.isDoingFine();
    }

    public int getToolsSize() {
        return this.toolsSize;
    }
    
}
