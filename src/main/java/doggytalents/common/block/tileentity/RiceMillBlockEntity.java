package doggytalents.common.block.tileentity;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import doggytalents.DoggyItems;
import doggytalents.DoggyTileEntityTypes;
import doggytalents.api.forge_imitate.inventory.ContainerWrapper;
import doggytalents.common.block.RiceMillBlock;
import doggytalents.common.inventory.container.RiceMillMenu;
import doggytalents.forge_imitate.network.NetworkHooks;
import doggytalents.common.util.InventoryUtil;
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
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RiceMillBlockEntity extends BlockEntity { 
    
    public static final int TOTOAL_SLOTS = 3;
    public static final int[] GRAIN_SLOTS = {0};
    public static final int BOWL_SLOT = 1;
    public static final int[] INPUT_SLOT = {0, 1};
    public static final int[] OUTPUT_SLOT = {2};
    private RiceMillContainer container = new RiceMillContainer(this);
    private ContainerWrapper containerWrapper = new ContainerWrapper(container);

    public static final int TOTOAL_DATA_SLOT = 2;
    public static final int GRINDING_TIME_ID = 0;
    public static final int GRINDING_TINE_FINISH_ID = 1;
    private int grindingTime;
    private int grindingTimeWhenFinish = 50;
    private RiceMillSyncedData syncedData = new RiceMillSyncedData(this);

    private RiceMillMenuProvider menuProvider = new RiceMillMenuProvider(this);

    public static final int GRIND_ANIM_TICK_LEN = 200;
    private int animationTick = 0;
    private boolean isSpinning = false;
    private MillRecipe currentRecipe = MillRecipe.EMPTY;
    private ItemStack prevInputStack = ItemStack.EMPTY;
    private boolean prevHasBowl = false;

    private static final ArrayList<MillRecipe> MILL_RECIPES = new ArrayList<>();
    public static void initGrindMap() {
        MILL_RECIPES.clear();
        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.RICE_GRAINS.get(), 3)
                .needsBowl()
                .withOutput(DoggyItems.UNCOOKED_RICE_BOWL.get(), 1)
                .build());
        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.RICE_GRAINS.get(), 1)
                .withOutput(DoggyItems.UNCOOKED_RICE.get(), 1)
                .build());

        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.RICE_WHEAT.get(), 1)
                .needsBowl()
                .withOutput(DoggyItems.UNCOOKED_RICE_BOWL.get(), 1)
                .build());
        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.RICE_WHEAT.get(), 1)
                .withOutput(DoggyItems.UNCOOKED_RICE.get(), 3)
                .build());
        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.UNCOOKED_RICE.get(), 3)
                .needsBowl()
                .withOutput(DoggyItems.UNCOOKED_RICE_BOWL.get(), 1)
                .build());

        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.SOY_BEANS_DRIED.get(), 3)
                .needsBowl()
                .withOutput(DoggyItems.SOY_MILK.get(), 1)
                .build());
        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.SOY_PODS_DRIED.get(), 1)
                .needsBowl()
                .withOutput(DoggyItems.SOY_MILK.get(), 1)
                .build());

        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.SOY_PODS.get(), 1)
                .withOutput(DoggyItems.SOY_BEANS.get(), 3)
                .build());
        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.SOY_PODS_DRIED.get(), 1)
                .withOutput(DoggyItems.SOY_BEANS_DRIED.get(), 3)
                .build());
        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(DoggyItems.EDAMAME.get(), 1)
                .withOutput(DoggyItems.EDAMAME_UNPODDED.get(), 3)
                .build());

        MILL_RECIPES.add(
            MillRecipe.Builder.withInput(Items.WHEAT, 1)
                .withOutput(Items.BREAD, 1)
                .build());

        // GRIND_MAP.put(DoggyItems.UNCOOKED_RICE.get(), 
        //     MillOutput.of(false, 1, DoggyItems.RICE_FLOUR.get()));

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
        mill.isSpinning = scanIfMillCanSpin(level, mill);
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

    private static boolean scanIfMillCanSpin(Level level, RiceMillBlockEntity mill) {
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
        
        var inputStack = mill.container.getItem(INPUT_SLOT[0]);
        recipeUpdateTick(mill, inputStack);
        var currentRecipe = mill.currentRecipe;

        if (isGrinding(mill, inputStack, currentRecipe)) {
            ++mill.grindingTime;
            if (mill.grindingTime >= mill.grindingTimeWhenFinish) {
                createFinishProduct(mill, inputStack, currentRecipe);
                mill.grindingTime = 0;
            }
        } else {
            mill.grindingTime = 0;
        }
    }

    private static void recipeUpdateTick(RiceMillBlockEntity mill, ItemStack currentInputStack) {
        var bowlStack = mill.container.getItem(BOWL_SLOT);
        var hasBowl = bowlStack.is(Items.BOWL);
        var changed = checkAndDetectChangeInMillInput(mill, currentInputStack, hasBowl);
        if (changed)
            changeRecipeIfNeeded(mill, currentInputStack, hasBowl);
    }

    private static void changeRecipeIfNeeded(RiceMillBlockEntity mill, 
        ItemStack currentInputStack, boolean hasBowl) {

        if (currentInputStack.isEmpty())
            return;
        if (currentRecipeStillMatch(mill, currentInputStack, hasBowl))
            return;
        mill.currentRecipe = findMillRecipe(currentInputStack, hasBowl);
    }

    private static boolean currentRecipeStillMatch(RiceMillBlockEntity mill, 
        ItemStack currentInputStack, boolean hasBowl) {
        var currentRecipe = mill.currentRecipe;
        if (currentRecipe.empty()) 
            return false;
        boolean isMatch = currentRecipe.matches(currentInputStack, hasBowl);
        return isMatch;
    }

    private static boolean checkAndDetectChangeInMillInput(RiceMillBlockEntity mill, ItemStack currentInputStack,
        boolean hasBowl) {
        if (!isChanged(mill, currentInputStack, hasBowl))
            return false;
        mill.prevHasBowl = hasBowl;
        if (currentInputStack.isEmpty()) {
            mill.prevInputStack = ItemStack.EMPTY;
        } else {
            mill.prevInputStack = currentInputStack.copy();
        }
        return true;
    }

    private static boolean isChanged(RiceMillBlockEntity mill, ItemStack currentInputStack, boolean hasBowl) {
        if (currentInputStack.isEmpty() && mill.prevInputStack.isEmpty())
            return false;
        if (currentInputStack.isEmpty() != mill.prevInputStack.isEmpty())
            return true;

        if (currentInputStack.getItem() != mill.prevInputStack.getItem())
            return true;
        if (currentInputStack.getCount() != mill.prevInputStack.getCount())
            return true;
        if (mill.prevHasBowl != hasBowl)
            return true;
        return false;
    }

    private static MillRecipe findMillRecipe(ItemStack stack, boolean hasBowl) {
        if (stack.isEmpty())
            return MillRecipe.EMPTY;
        for (var recipe : MILL_RECIPES) {
            if (recipe.matches(stack, hasBowl))
                return recipe;
        }
        return MillRecipe.EMPTY;
    }

    private static boolean isGrinding(RiceMillBlockEntity mill, ItemStack inputStack, MillRecipe currentRecipe) {
        if (currentRecipe.empty())
            return false;
        return mill.isSpinning && hasEnoughIngredient(mill, inputStack, currentRecipe) && productSlotIsAvailable(mill, currentRecipe);
    }

    private static boolean productSlotIsAvailable(RiceMillBlockEntity mill, MillRecipe recipe) {
        if (recipe.empty())
            return false;
        var currentOutputStack = mill.container.getItem(OUTPUT_SLOT[0]);
        if (currentOutputStack.isEmpty())
            return true;
        var outputItemFromInput = recipe.outputItem();
        if (outputItemFromInput == null)
            return false;
        if (!currentOutputStack.is(outputItemFromInput))
            return false;
        return currentOutputStack.getCount() + recipe.outputAmount() <= InventoryUtil
            .maxStackSizeWithContainer(mill.container, OUTPUT_SLOT[0], currentOutputStack);
    }

    private static boolean hasEnoughIngredient(RiceMillBlockEntity mill, ItemStack inputStack, MillRecipe recipe) {
        if (recipe.empty())
            return false;
        var bowSlotStack = (mill.container.getItem(BOWL_SLOT));
        if (recipe.needBowl() && !bowSlotStack.is(Items.BOWL)) {
            return false;
        }

        int requireGrainLeft = recipe.inputAmount();
        var inputItem = inputStack.getItem();
        for (var grainSlot : GRAIN_SLOTS) {
            if (requireGrainLeft < 0)
                break;
            var grainSlotStack = mill.container.getItem(grainSlot);
            if (!grainSlotStack.is(inputItem))
                continue;
            requireGrainLeft -= grainSlotStack.getCount();
        }
        return requireGrainLeft <= 0;
    }

    private static void createFinishProduct(RiceMillBlockEntity mill, ItemStack currentInput, MillRecipe currentRecipe) {
        if (!isGrinding(mill, currentInput, currentRecipe))
            return;
        
        var inputItem = currentInput.getItem();
        if (currentRecipe.empty())
            return;

        var bowSlotStack = (mill.container.getItem(BOWL_SLOT));
        if (currentRecipe.needBowl() && bowSlotStack.is(Items.BOWL)) {
            bowSlotStack = bowSlotStack.copy();
            bowSlotStack.shrink(1);
            mill.container.setItem(BOWL_SLOT, bowSlotStack);
        }

        int grainsNeeded = currentRecipe.inputAmount();
        for (var grainSlot : GRAIN_SLOTS) {
            if (grainsNeeded <= 0)
                break;
            var grainSlotStack = mill.container.getItem(grainSlot);
            if (!grainSlotStack.is(inputItem))
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
        var resultStack = currentRecipe.produce();
        if (resultStack == ItemStack.EMPTY)
            return;
        mill.containerWrapper.insertItem(OUTPUT_SLOT[0], resultStack, false);
    }

    private boolean isInputMaterialForSlotId(ItemStack stack, int slotId) {
        if (slotId == BOWL_SLOT)
            return stack.is(Items.BOWL);
        return isInputSlotValid(stack);
    }
    
    public static boolean isInputSlotValid(ItemStack stack) {
        if (stack.is(Items.BOWL))
            return false;
        return true;
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
        if (attach_blockEntity instanceof SmokerBlockEntity)
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
        if (recipent instanceof AbstractFurnaceBlockEntity furnace) {
            var furnaceIn = FurnaceBlockEntityDelegate.FURNACE_IN;
            ItemStack current_in = furnace.getItem(furnaceIn);
            if (!current_in.isEmpty() && !ItemStack.isSameItem(currentStack, current_in))
                return currentStack;
            if (!current_in.isEmpty() && current_in.getCount() >= InventoryUtil.maxStackSizeWithContainer(furnace, furnaceIn, current_in))
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
            if (item.getCount() < InventoryUtil.maxStackSizeWithContainer(target, i, item)) {
                freeSlot = i;
                break;
            }
        }
        if (freeSlot < 0)
            return currentStack;
        
        ItemStack targetItem = target.getItem(freeSlot);
        if (!targetItem.isEmpty() && !ItemStack.isSameItem(currentStack, targetItem))
            return currentStack;
        if (!targetItem.isEmpty() && targetItem.getCount() >= InventoryUtil.maxStackSizeWithContainer(target, freeSlot, targetItem))
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

    // @Override
    // public AABB getRenderBoundingBox() {
    //     var pos = this.getBlockPos();
    //     var state = this.getBlockState();
    //     var aabb = new AABB(pos, pos.offset(1, 1, 1));
    //     var facing = RiceMillBlock.getFacing(state);
    //     var facing_norm = facing.getNormal();
    //     var expand_vec = new Vec3(facing_norm.getX(), 1, facing_norm.getZ());
    //     aabb = aabb.expandTowards(expand_vec);
    //     var side_axis = facing.getClockWise().getAxis();
    //     if (side_axis == Axis.X) {
    //         aabb = aabb.inflate(1, 0, 0);
    //     } else {
    //         aabb = aabb.inflate(0, 0, 1);
    //     }

    //     return aabb;
    // }

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

    public static class MillRecipe {

        public static MillRecipe EMPTY = MillRecipe.Builder.empty().build();

        private Item inputItem = null;
        private int inputAmount = 0;
        private boolean needBowl = true;
        private Item outputItem = null;
        private int outputAmount = 1;

        private MillRecipe() {}

        public Item inputItem() {return this.inputItem;}
        public int inputAmount() {return this.inputAmount; }
        public boolean needBowl() { return this.needBowl; }
        public @Nullable Item outputItem() { return this.outputItem; }
        public int outputAmount() {return this.outputAmount; }
        public boolean empty() {
            return outputItem == null || inputItem == null;
        }
        public boolean matches(ItemStack inputStack, boolean hasBowl) {
            if (this.empty())
                return false;
            if (hasBowl != needBowl)
                return false;
            if (inputStack == null)
                return false;
            if (!inputStack.is(this.inputItem))
                return false;
            if (inputStack.getCount() < this.inputAmount)
                return false;
            return true;
        }
        public ItemStack produce() {
            if (this.empty())
                return ItemStack.EMPTY;
            if (this.outputItem == null)
                return ItemStack.EMPTY;
            return new ItemStack(this.outputItem, this.outputAmount);
        }
        
        public static class Builder {

            private Item inputItem = null;
            private int inputAmount = 0;
            private boolean needBowl = false;
            private Item outputItem = null;
            private int outputAmount = 1;

            private Builder() {}

            public static Builder empty() {
                return new Builder();
            }

            public static Builder withInput(Item item, int amount) {
                var ret = new Builder();
                ret.inputItem = item;
                ret.inputAmount = amount;
                return ret;
            }

            public Builder needsBowl() {
                this.needBowl = true;
                return this;
            }

            public Builder withOutput(Item item, int amount) {
                this.outputAmount = amount;
                this.outputItem = item;
                return this;
            }

            public MillRecipe build() {
                var ret = new MillRecipe();
                ret.inputItem = this.inputItem;
                ret.inputAmount = this.inputAmount;
                ret.needBowl = this.needBowl;
                ret.outputItem = this.outputItem;
                ret.outputAmount = this.outputAmount;
                return ret;
            }

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
