package doggytalents.common.inventory.container;

import doggytalents.DoggyContainerTypes;
import doggytalents.DoggyItems;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class RiceMillMenu extends AbstractContainerMenu {

    private int grainSlotEnd;
    private int bowlSlotId;
    private int outputSlotId;
    private int playerInventoryBegin;

    private Container millContainer;
    private ContainerData syncedData;

    private RiceMillBlockEntity clientMill = null;

    //Client constructor and functions
    public RiceMillMenu(int containerId, Inventory inv, BlockPos millPos) {
        this(containerId, inv, new SimpleContainer(RiceMillBlockEntity.TOTOAL_SLOTS), new SimpleContainerData(RiceMillBlockEntity.TOTOAL_DATA_SLOT));
        var player = inv.player;
        var level = player.level();
        if (level.isClientSide) {
            findClientMill(level, millPos);
        }
    }
    private void findClientMill(Level level, BlockPos pos) {
        var blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof RiceMillBlockEntity mill))
            return;
        this.clientMill = mill;
    }
    public RiceMillBlockEntity getClientMill() {
        return this.clientMill;
    }

    //Server constructor
    public RiceMillMenu(int containerId, Inventory inv, Container millContainer, ContainerData syncedData) {
        super(DoggyContainerTypes.RICE_MILL.get(), containerId);
        checkContainerSize(millContainer, RiceMillBlockEntity.TOTOAL_SLOTS);
        checkContainerDataCount(syncedData, RiceMillBlockEntity.TOTOAL_DATA_SLOT);
        this.millContainer = millContainer;
        this.syncedData = syncedData;

        for (int i = 0; i < RiceMillBlockEntity.GRAIN_SLOTS.length; ++i) {
            int grainSlot = RiceMillBlockEntity.GRAIN_SLOTS[i]; 
            this.addSlot(new Slot(millContainer, grainSlot, 20, 17 + (i+1) * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return RiceMillBlockEntity.isInputSlotValid(stack);
                }
            });
        }
        grainSlotEnd = RiceMillBlockEntity.GRAIN_SLOTS.length - 1;

        var bowlSlot = 
            this.addSlot(new Slot(millContainer, RiceMillBlockEntity.BOWL_SLOT, 58, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    // TODO Auto-generated method stub
                    return stack.is(Items.BOWL);
                }
            });

        bowlSlotId = bowlSlot.index;
        
        var outputSlot =
            this.addSlot(new Slot(millContainer, RiceMillBlockEntity.OUTPUT_SLOT[0], 116, 35) {
                @Override
                public boolean mayPlace(ItemStack p_40231_) {
                    return false;
                }
            });
        outputSlotId = outputSlot.index;
        playerInventoryBegin = outputSlotId + 1;

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
    public ItemStack quickMoveStack(Player player, int clickedSlotId) {
        var clickedSlot = this.slots.get(clickedSlotId);
        if (clickedSlot == null || !clickedSlot.hasItem())
            return ItemStack.EMPTY;

        var clickedItem = clickedSlot.getItem();
        var clickedItem0 = clickedItem.copy();

        boolean moveResult = false;
        if (clickedSlotId >= this.playerInventoryBegin) {
            moveResult = moveItemStackTo(
                clickedItem, 
                0, this.playerInventoryBegin, 
                false
            );
        } else {
            moveResult = moveItemStackTo(
                clickedItem, 
                this.playerInventoryBegin, this.slots.size(), 
                false
            );
        }
        if (!moveResult)
            return ItemStack.EMPTY;

        if (clickedItem.isEmpty()) {
            clickedSlot.set(ItemStack.EMPTY);
        } else {
            clickedSlot.setChanged();
        }

        if (clickedItem.getCount() == clickedItem0.getCount()) {
            return ItemStack.EMPTY;
        }
        
        return clickedItem0;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.millContainer.stillValid(player);
    }

    public float getGrindProgress() {
        float grindTime = (float) this.syncedData.get(RiceMillBlockEntity.GRINDING_TIME_ID);
        float finishTime = (float) this.syncedData.get(RiceMillBlockEntity.GRINDING_TINE_FINISH_ID);
        var ret = grindTime/finishTime;
        return Mth.clamp(ret, 0, 1);
    }
    
}
