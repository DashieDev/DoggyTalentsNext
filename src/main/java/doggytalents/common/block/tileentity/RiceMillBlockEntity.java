package doggytalents.common.block.tileentity;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import doggytalents.DoggyItems;
import doggytalents.DoggyTileEntityTypes;
import doggytalents.common.block.RiceMillBlock;
import doggytalents.common.inventory.container.RiceMillMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;

public class RiceMillBlockEntity extends BlockEntity { 

    public static final int GRAINS_REQUIRED = 3;
    
    public static final int TOTOAL_SLOTS = 3;
    public static final int[] GRAIN_SLOTS = {0};
    public static final int BOWL_SLOT = 1;
    public static final int[] INPUT_SLOT = {0, 1};
    public static final int[] OUTPUT_SLOT = {2};
    private RiceMillContainer container = new RiceMillContainer(this);
    private InvWrapper containerWrapper = new InvWrapper(container);

    public static final int TOTOAL_DATA_SLOT = 2;
    public static final int GRINDING_TIME_ID = 0;
    public static final int GRINDING_TINE_FINISH_ID = 1;
    private int grindingTime;
    private int grindingTimeWhenFinish = 100;
    private RiceMillSyncedData syncedData = new RiceMillSyncedData(this);

    private RiceMillMenuProvider menuProvider = new RiceMillMenuProvider(this);

    public static final int GRIND_ANIM_TICK_LEN = 200;
    private int animationTick = 0;
    private boolean isSpinning = false;

    private static Map<Item, Item> GRIND_MAP = null;
    public static void initGrindMap() {
        GRIND_MAP = Maps.newHashMap();
        GRIND_MAP.put(DoggyItems.RICE_GRAINS.get(), DoggyItems.UNCOOKED_RICE_BOWL.get());
        GRIND_MAP.put(DoggyItems.SOY_BEANS_DRIED.get(), DoggyItems.SOY_MILK.get());
    }

    public RiceMillBlockEntity(BlockPos pos, BlockState blockState) {
        super(DoggyTileEntityTypes.RICE_MILL.get(), pos, blockState);
    }

    public static void openContainer(ServerPlayer player, Level level, BlockPos pos) {
        var blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof RiceMillBlockEntity mill))
            return;
        NetworkHooks.openScreen(player, mill.menuProvider, mill.getBlockPos());
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        if (level == null)
            return;
        if (!(blockEntity instanceof RiceMillBlockEntity mill))
            return;
        updateSpinning(level, mill);
        updateAnimation(mill);
        grindTick(level, pos, blockState, mill);
        ejectTick(level, mill);
    }


    private int tickTillUpdateWaterSource = 15;
    private static void updateSpinning(Level level, RiceMillBlockEntity mill) {
        if (--mill.tickTillUpdateWaterSource > 0)
            return;
        mill.tickTillUpdateWaterSource = 15;
        mill.isSpinning = scanForWaterSource(level, mill);
    }

    public boolean isSpinning() {
        return this.isSpinning;
    }

    public int getAnimationTick() {
        return this.animationTick;
    }

    private static void updateAnimation(RiceMillBlockEntity mill) {
        if (mill.isSpinning)
            ++mill.animationTick;
        if (mill.animationTick >= GRIND_ANIM_TICK_LEN) {
            mill.animationTick = 0;
        }
    }

    private static boolean scanForWaterSource(Level level, RiceMillBlockEntity mill) {
        var pos = mill.getBlockPos();
        var state = mill.getBlockState();
        var facing = RiceMillBlock.getFacing(state);
        var side_axis = facing.getClockWise().getAxis();
        for (int i = 0; i < 3; ++i) {
            boolean waterPart = i <= 0;
            var middle_pos = pos.offset(facing.getStepX(), i - 1, facing.getStepZ());
            var middle_state = level.getBlockState(middle_pos);
            if (!isValidBlockForSpiningInPart(waterPart, middle_state))
                return false;
            BlockPos side_pos_1 = null, side_pos_2 = null;
            if (side_axis == Axis.X) {
                side_pos_1 = middle_pos.offset(1, 0, 0);
                side_pos_2 = middle_pos.offset(-1, 0, 0);
            } else {
                side_pos_1 = middle_pos.offset(0, 0, 1);
                side_pos_2 = middle_pos.offset(0, 0, -1);
            }
            var side_state_1 = level.getBlockState(side_pos_1);
            if (!isValidBlockForSpiningInPart(waterPart, side_state_1))
                return false;
            var side_state_2 = level.getBlockState(side_pos_2);
            if (!isValidBlockForSpiningInPart(waterPart, side_state_2))
                return false;
        }
        return true;
    }

    private static boolean isValidBlockForSpiningInPart(boolean waterPart, BlockState state) {
        if (waterPart)
            return state.is(Blocks.WATER);
        return state.isAir();
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
        return mill.isSpinning && hasEnoughIngredient(mill) && productSlotIsAvailable(mill);
    }

    private static boolean productSlotIsAvailable(RiceMillBlockEntity mill) {
        var stack = mill.container.getItem(OUTPUT_SLOT[0]);
        if (stack.isEmpty())
            return true;
        var inputStack = mill.container.getItem(INPUT_SLOT[0]);
        var outputItem = getMIllProductItem(inputStack.getItem());
        if (outputItem == null)
            return false;
        if (!inputStack.is(outputItem))
            return false;
        return stack.getCount() < stack.getMaxStackSize();
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
            if (!isInputGrainsForMIll(grainSlotStack))
                continue;
            requireGrainLeft -= grainSlotStack.getCount();
        }
        return requireGrainLeft <= 0;
    }

    public static boolean isInputGrainsForMIll(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        var item = stack.getItem();
        return GRIND_MAP.get(item) != null;
    }

    public static @Nullable Item getMIllProductItem(Item item) {
        return GRIND_MAP.get(item);
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
        Item inputItem = null;
        for (var grainSlot : GRAIN_SLOTS) {
            if (grainsNeeded <= 0)
                break;
            var grainSlotStack = mill.container.getItem(grainSlot);
            if (!isInputGrainsForMIll(grainSlotStack))
                continue;
            if (inputItem == null)
                inputItem = grainSlotStack.getItem();
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
        if (inputItem == null)
            return;
        var resultItem = getMIllProductItem(inputItem);
        if (resultItem == null)
            return;
        mill.containerWrapper.insertItem(OUTPUT_SLOT[0], new ItemStack(resultItem), false);
    }

    private boolean isInputMaterialForSlotId(ItemStack stack, int slotId) {
        if (slotId == BOWL_SLOT)
            return stack.is(Items.BOWL);
        return isInputGrainsForMIll(stack);
    }

    private int tickTillUpdateEject = 8;
    private static void ejectTick(Level level, RiceMillBlockEntity mill) {
        if (level.isClientSide)
            return;
        if (--mill.tickTillUpdateEject > 0)
            return;
        mill.tickTillUpdateEject = 8;

        var output = mill.container.getItem(OUTPUT_SLOT[0]);
        if (output.isEmpty())
            return;
        
        var recipentOptional = getAttachedRecipent(mill);
        if (!recipentOptional.isPresent())
            return;
        var recipent = recipentOptional.get();
        var remaining = tryEjectToRecipent(recipent, output);
        if (remaining != output) {
            mill.container.setItem(OUTPUT_SLOT[0], remaining);
        }
    }

    private static Optional<BlockEntity> getAttachedRecipent(RiceMillBlockEntity mill) {
        var pos = mill.getBlockPos();
        var state = mill.getBlockState();
        var level = mill.getLevel();
        if (level == null)
            return Optional.empty();
        var facing = RiceMillBlock.getFacing(state);
        var attached_dir = facing.getClockWise();
        var attach_pos =  pos.offset(attached_dir.getStepX(), 0, attached_dir.getStepZ());
        var attach_blockEntity = level.getBlockEntity(attach_pos);
        if (attach_blockEntity instanceof FurnaceBlockEntity)
            return Optional.of(attach_blockEntity);
        if (attach_blockEntity instanceof ChestBlockEntity)
            return Optional.of(attach_blockEntity);
        if (attach_blockEntity instanceof HopperBlockEntity)
            return Optional.of(attach_blockEntity);
        return Optional.empty();
    }

    private static ItemStack tryEjectToRecipent(BlockEntity recipent, ItemStack currentStack) {
        if (currentStack.isEmpty())
            return currentStack;
        if (recipent instanceof FurnaceBlockEntity furnace) {
            var furnaceIn = FurnaceBlockEntityDelegate.FURNACE_IN;
            ItemStack current_in = furnace.getItem(furnaceIn);
            if (!current_in.isEmpty() && !ItemStack.isSameItem(currentStack, current_in))
                return currentStack;
            if (!current_in.isEmpty() && current_in.getCount() >= current_in.getMaxStackSize())
                return currentStack;
            if (current_in.isEmpty()) {
                current_in = new ItemStack(currentStack.getItem());
            } else {
                current_in = current_in.copy();
                current_in.grow(1);
            }
            furnace.setItem(furnaceIn, current_in);
            currentStack = currentStack.copy();
            currentStack.shrink(1);
            return currentStack;
        } else if (recipent instanceof ChestBlockEntity chest) {
            currentStack = tryInputStackInContainer(currentStack, chest);
        } else if (recipent instanceof HopperBlockEntity hopper) {
            currentStack = tryInputStackInContainer(currentStack, hopper);
        }  
        return currentStack;
    }

    private static ItemStack tryInputStackInContainer(ItemStack currentStack, Container target) {
        int freeSlot = -1;
        for (int i = 0; i < target.getContainerSize(); ++i) {
            ItemStack item = target.getItem(i);
            if (item.isEmpty()) {
                freeSlot = i;
                break;
            }
            if (!ItemStack.isSameItem(currentStack, item))
                continue;
            if (item.getCount() < item.getMaxStackSize()) {
                freeSlot = i;
                break;
            }
        }
        if (freeSlot < 0)
            return currentStack;
        
        ItemStack targetItem = target.getItem(freeSlot);
        if (!targetItem.isEmpty() && !ItemStack.isSameItem(currentStack, targetItem))
            return currentStack;
        if (!targetItem.isEmpty() && targetItem.getCount() >= targetItem.getMaxStackSize())
            return currentStack;
        
        if (targetItem.isEmpty()) {
            targetItem = new ItemStack(currentStack.getItem());
        } else {
            targetItem = targetItem.copy();
            targetItem.grow(1);
        }
        target.setItem(freeSlot, targetItem);
        currentStack = currentStack.copy();
        currentStack.shrink(1);
        return currentStack;
    }
    

    public WorldlyContainer getWorldlyContainer() {
        return this.container;
    }

    @Override
    public AABB getRenderBoundingBox() {
        var pos = this.getBlockPos();
        var state = this.getBlockState();
        var aabb = new AABB(pos, pos.offset(1, 1, 1));
        var facing = RiceMillBlock.getFacing(state);
        var facing_norm = facing.getNormal();
        var expand_vec = new Vec3(facing_norm.getX(), 1, facing_norm.getZ());
        aabb = aabb.expandTowards(expand_vec);
        var side_axis = facing.getClockWise().getAxis();
        if (side_axis == Axis.X) {
            aabb = aabb.inflate(1, 0, 0);
        } else {
            aabb = aabb.inflate(0, 0, 1);
        }

        return aabb;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        container.deserializeNBT(tag);
        this.grindingTime = tag.getInt("grindingTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        container.serializeNBT(tag);
        tag.putInt("grindingTime", this.grindingTime);
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
            return;
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
            var facing = RiceMillBlock.getFacing(state);
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
            var facing = RiceMillBlock.getFacing(state);
            if (facing.getCounterClockWise() == dir)
                return mill.isInputMaterialForSlotId(stack, slotId);
            return false;
        }

        @Override
        public boolean canTakeItemThroughFace(int slotId, ItemStack stack, Direction dir) {
            return false;
        }

        @Override
        public void setChanged() {
            this.mill.setChanged();
        }

        @Override
        public boolean stillValid(Player player) {
            return Container.stillValidBlockEntity(this.mill, player);
        }
        
        public void serializeNBT(CompoundTag compound) {
            var itemsList = new ListTag();

            for(int i = 0; i < this.getContainerSize(); i++) {
                ItemStack stack = this.getItem(i);
                if (!stack.isEmpty()) {
                    CompoundTag itemTag = new CompoundTag();
                    itemTag.putByte("Slot", (byte) i);
                    stack.save(itemTag);
                    itemsList.add(itemTag);
                }
            }

            compound.put("MillItems", itemsList);
        }

        public void deserializeNBT(CompoundTag compound) {
            if (compound.contains("MillItems", Tag.TAG_LIST)) {
                ListTag tagList = compound.getList("MillItems", Tag.TAG_COMPOUND);
                for (int i = 0; i < tagList.size(); i++) {
                    CompoundTag itemTag = tagList.getCompound(i);
                    int slot = itemTag.getInt("Slot");

                    if (slot >= 0 && slot < this.getContainerSize()) {
                        this.setItem(slot, ItemStack.of(itemTag));
                    }
                }
            }
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
            return Component.translatable("container.doggytalents.rice_mill");
        }
        
    }

    public static class FurnaceBlockEntityDelegate extends AbstractFurnaceBlockEntity {

        public static int FURNACE_IN = AbstractFurnaceBlockEntity.SLOT_INPUT;

        protected FurnaceBlockEntityDelegate(BlockEntityType<?> p_154991_, BlockPos p_154992_, BlockState p_154993_,
                RecipeType<? extends AbstractCookingRecipe> p_154994_) {
            super(p_154991_, p_154992_, p_154993_, p_154994_);
        }
        @Override
        protected Component getDefaultName() {
            return Component.empty();
        }
        @Override
        protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
            return null;
        }

    }
    
}
