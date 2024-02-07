package doggytalents.common.block.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import doggytalents.DoggyItems;
import doggytalents.DoggyTileEntityTypes;
import doggytalents.common.block.RiceMillBlock;
import doggytalents.common.inventory.container.RiceMillMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.InvWrapper;

public class RiceMillBlockEntity extends BlockEntity implements WorldlyContainerHolder { 

    public static final int GRAINS_REQUIRED = 5;
    
    public static final int TOTOAL_SLOTS = 5;
    public static final int[] GRAIN_SLOTS = {0, 1, 2};
    public static final int BOWL_SLOT = 3;
    public static final int[] INPUT_SLOT = {0, 1, 2, 3};
    public static final int[] OUTPUT_SLOT = {4};
    private RiceMillContainer container = new RiceMillContainer(this);
    private InvWrapper containerWrapper = new InvWrapper(container);

    public static final int TOTOAL_DATA_SLOT = 2;
    public static final int GRINDING_TIME_ID = 0;
    public static final int GRINDING_TINE_FINISH_ID = 1;
    private int grindingTime;
    private int grindingTimeWhenFinish = 100;
    private RiceMillSyncedData syncedData = new RiceMillSyncedData(this);

    private RiceMillMenuProvider menuProvider = new RiceMillMenuProvider(this);

    public RiceMillBlockEntity(BlockPos pos, BlockState blockState) {
        super(DoggyTileEntityTypes.RICE_MILL.get(), pos, blockState);
    }

    public static void openContainer(ServerPlayer player, Level level, BlockPos pos) {
        var blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof RiceMillBlockEntity mill))
            return;
        player.openMenu(mill.menuProvider);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        if (!(blockEntity instanceof RiceMillBlockEntity mill))
            return;
        grindTick(level, pos, blockState, mill);
    }

    private static void grindTick(Level level, BlockPos pos, BlockState blockState, RiceMillBlockEntity mill) {
        if (level.isClientSide)
            return;
        
        if (isGrinding(mill)) {
            ++mill.grindingTime;
            if (mill.grindingTime >= mill.grindingTimeWhenFinish) {
                createFinishProduct(mill);
                mill.grindingTime = 0;
            }
        } else {
            mill.grindingTime = 0;
        }
    }

    private static boolean isGrinding(RiceMillBlockEntity mill) {
        return hasEnoughIngredient(mill) && productSlotEmpty(mill);
    }

    private static boolean productSlotEmpty(RiceMillBlockEntity mill) {
        var stack = mill.container.getItem(OUTPUT_SLOT[0]);
        if (stack.isEmpty())
            return true;
        if (!stack.is(DoggyItems.UNCOOKED_RICE_BOWL.get()))
            return false;
        return stack.getCount() < DoggyItems.UNCOOKED_RICE_BOWL.get().getMaxStackSize(stack);
    }

    private static boolean hasEnoughIngredient(RiceMillBlockEntity mill) {
        var bowSlotStack = (mill.container.getItem(BOWL_SLOT));
        if (!bowSlotStack.is(Items.BOWL)) {
            return false;
        }

        int requireGrainLeft = GRAINS_REQUIRED;
        for (var grainSlot : GRAIN_SLOTS) {
            if (requireGrainLeft < 0)
                break;
            var grainSlotStack = mill.container.getItem(grainSlot);
            if (!grainSlotStack.is(DoggyItems.RICE_GRAINS.get()))
                continue;
            requireGrainLeft -= grainSlotStack.getCount();
        }
        return requireGrainLeft <= 0;
    }

    private static void createFinishProduct(RiceMillBlockEntity mill) {
        if (!isGrinding(mill))
            return;

        var bowSlotStack = (mill.container.getItem(BOWL_SLOT));
        if (bowSlotStack.is(Items.BOWL)) {
            bowSlotStack = bowSlotStack.copy();
            bowSlotStack.shrink(1);
            mill.container.setItem(BOWL_SLOT, bowSlotStack);
        }

        int grainsNeeded = GRAINS_REQUIRED;
        for (var grainSlot : GRAIN_SLOTS) {
            if (grainsNeeded <= 0)
                break;
            var grainSlotStack = mill.container.getItem(grainSlot);
            if (!grainSlotStack.is(DoggyItems.RICE_GRAINS.get()))
                continue;
            grainSlotStack = grainSlotStack.copy();
            var stackCount = grainSlotStack.getCount();
            if (stackCount <= grainsNeeded) {
                grainsNeeded -= stackCount;
                grainSlotStack = ItemStack.EMPTY;
            } else {
                grainSlotStack.shrink(grainsNeeded);
                grainsNeeded = 0;
            }
            mill.container.setItem(grainSlot, grainSlotStack);
        }
        mill.containerWrapper.insertItem(OUTPUT_SLOT[0], new ItemStack(DoggyItems.UNCOOKED_RICE_BOWL.get()), false);
    }

    private boolean isInputMaterialForSlotId(ItemStack stack, int slotId) {
        if (slotId <= 2)
            return stack.is(DoggyItems.RICE_GRAINS.get());
        if (slotId <= 3)
            return stack.is(Items.BOWL);
        return false;
    }

    @Override
    public WorldlyContainer getContainer(BlockState p_19242_, LevelAccessor p_19243_, BlockPos p_19244_) {
        return this.container;
    }

    public static class RiceMillSyncedData implements ContainerData {

        private final RiceMillBlockEntity mill;

        public RiceMillSyncedData(RiceMillBlockEntity mill) {
            this.mill = mill;
        }

        @Override
        public int get(int syncedSlotId) {
            switch (syncedSlotId) {
            case GRINDING_TIME_ID:
                return mill.grindingTime;
            case GRINDING_TINE_FINISH_ID:
                return mill.grindingTimeWhenFinish;
            default:
                return 0;
            }
        }

        @Override
        public void set(int syncedSlotId, int val) {
            switch (syncedSlotId) {
            case GRINDING_TIME_ID:
                mill.grindingTime = val;
                return;
            case GRINDING_TINE_FINISH_ID:
                mill.grindingTimeWhenFinish = val;
                return;
            default:
                return;
            }
        }

        @Override
        public int getCount() {
            return TOTOAL_DATA_SLOT;
        }
    }

    public static class RiceMillContainer extends SimpleContainer implements WorldlyContainer {

        private final RiceMillBlockEntity mill;

        public RiceMillContainer(RiceMillBlockEntity mill) {
            super(TOTOAL_SLOTS);
            this.mill = mill;
        }

        @Override
        public int[] getSlotsForFace(Direction dir) {
            if (dir.getAxis() == Direction.Axis.Y)
                return new int[0];
            var state = this.mill.getBlockState();
            var facing = state.getValue(RiceMillBlock.FACING);
            if (facing.getAxis() == Direction.Axis.Y)
                return new int[0];
            if (facing.getClockWise() == dir) 
                return OUTPUT_SLOT;
            if (facing.getCounterClockWise() == dir)
                return INPUT_SLOT;
            return new int[0];
        }

        @Override
        public boolean canPlaceItemThroughFace(int slotId, ItemStack stack, @Nullable Direction dir) {
            if (dir == null)
                return false;
            if (dir.getAxis() == Direction.Axis.Y)
                return false;
            var state = mill.getBlockState();
            var facing = state.getValue(RiceMillBlock.FACING);
            if (facing.getAxis() == Direction.Axis.Y)
                return false;
            if (facing.getCounterClockWise() == dir)
                return mill.isInputMaterialForSlotId(stack, slotId);
            return false;
        }

        @Override
        public boolean canTakeItemThroughFace(int slotId, ItemStack stack, Direction dir) {
            if (dir.getAxis() == Direction.Axis.Y)
                return false;
            var state = mill.getBlockState();
            var facing = state.getValue(RiceMillBlock.FACING);
            if (facing.getAxis() == Direction.Axis.Y)
                return false;
            if (facing.getClockWise() == dir)
                return true;
            return false;
        }

        @Override
        public void setChanged() {
            super.setChanged();
            this.mill.setChanged();
        }

        @Override
        public boolean stillValid(Player player) {
            // TODO Auto-generated method stub
            return Container.stillValidBlockEntity(this.mill, player);
        }
        
    }

    public static class RiceMillMenuProvider implements MenuProvider {

        private final RiceMillBlockEntity mill;

        public RiceMillMenuProvider(RiceMillBlockEntity mill) {
            this.mill = mill;
        }

        @Override
        @Nullable
        public AbstractContainerMenu createMenu(int containerId, Inventory inv, Player player) {
            return new RiceMillMenu(containerId, inv, mill.container, mill.syncedData);
        }

        @Override
        public Component getDisplayName() {
            return Component.empty();
        }
        
    }
    
}
