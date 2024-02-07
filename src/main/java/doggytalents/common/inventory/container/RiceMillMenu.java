package doggytalents.common.inventory.container;

import doggytalents.DoggyContainerTypes;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class RiceMillMenu extends AbstractContainerMenu {

    private Container millContainer;

    //Client constructor
    public RiceMillMenu(int containerId, Inventory inv) {
        this(containerId, inv, new SimpleContainer(RiceMillBlockEntity.TOTOAL_SLOTS), new SimpleContainerData(RiceMillBlockEntity.TOTOAL_DATA_SLOT));
    }

    //Server constructor
    public RiceMillMenu(int containerId, Inventory inv, Container millContainer, ContainerData syncedData) {
        super(DoggyContainerTypes.RICE_MILL.get(), containerId);
        checkContainerSize(millContainer, RiceMillBlockEntity.TOTOAL_SLOTS);
        checkContainerDataCount(syncedData, RiceMillBlockEntity.TOTOAL_DATA_SLOT);
        this.millContainer = millContainer;

        for (int i = 0; i < RiceMillBlockEntity.GRAIN_SLOTS.length; ++i) {
            int grainSlot = RiceMillBlockEntity.GRAIN_SLOTS[i]; 
            this.addSlot(new Slot(millContainer, grainSlot, 20, 17 + i * 18));            
        }

        this.addSlot(new Slot(millContainer, RiceMillBlockEntity.BOWL_SLOT, 58, 35));
        this.addSlot(new Slot(millContainer, RiceMillBlockEntity.OUTPUT_SLOT[0], 116, 35));

        //Player inv
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
               this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inv, k, 8 + k * 18, 142));
        }

        this.addDataSlots(syncedData);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.millContainer.stillValid(player);
    }
    
}
