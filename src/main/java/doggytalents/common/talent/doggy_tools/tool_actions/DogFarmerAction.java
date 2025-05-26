package doggytalents.common.talent.doggy_tools.tool_actions;

import java.util.Optional;

import doggytalents.DoggyBlocks;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import doggytalents.common.util.RingSearchIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.phys.Vec3;

public class DogFarmerAction extends ToolAction {

    private static final int SEARCH_RADIUS = 4;

    private BlockPos nextFarmBlock;
    private int tickTillPathRecalc;
    private int tickTillResearch;
    private int checkAgainTimestamp;

    private ItemStack seedTarget = ItemStack.EMPTY;
    private ItemStack replacementStack = ItemStack.EMPTY;

    public DogFarmerAction(Dog dog, DoggyToolsTalent talent) {
        super(dog, talent);
    }

    @Override
    public void onStart() {
        this.tickTillPathRecalc = 3;
        this.tickTillResearch = 20;
    }

    @Override
    public void tick() {
        boolean owner_check = !(
            ConfigHandler.SERVER.DOGGY_TOOLS_WANDER_FARM.get()
            && !this.dog.getMode().shouldFollowOwner()
        );
        if (owner_check) {
            var owner = this.dog.getOwner();
            if (owner == null || dog.distanceToSqr(owner) > this.talent.getMaxOwnerDistSqr()) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (!owner.isAlive() || owner.isSpectator()) {
                this.setState(ActionState.FINISHED);
                return;
            }
        }

        var stack = this.dog.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack == null || stack.getItem() != Items.STONE_HOE) {
            this.setState(ActionState.FINISHED);
            return;
        }

        if (this.tickTillPathRecalc > 0)
            --this.tickTillPathRecalc;

        this.refreshTargetSeed();

        if (this.nextFarmBlock == null && --this.tickTillResearch <= 0) {
            this.tickTillResearch = 10;
            this.nextFarmBlock = this.findNextFarmBlock();
            if (this.nextFarmBlock == null) {
                this.setState(ActionState.FINISHED);
                return;
            }
        }
        
        var farmState = this.getFarmState(this.nextFarmBlock);
        if (farmState == FarmState.NONE) {
            this.nextFarmBlock = null;
            return;
        }

        boolean hurtTools = moveToAndFarmBlock(farmState);

        if (hurtTools && dog.getRandom().nextInt(3) == 0) {
            var hurtStack = dog.getMainHandItem();
            if (hurtStack != null && hurtStack.getItem() instanceof HoeItem) {
                hurtStack.hurtAndBreak(1, dog, EquipmentSlot.MAINHAND);
            }
        }
        
    }

    private void refreshTargetSeed() {
        this.seedTarget = ItemStack.EMPTY;
        this.replacementStack = ItemStack.EMPTY;
        var inv = this.talent.getTools();
        int harvest_slot_id = 0;
        for (int i = 0; i < inv.getSlots(); ++i) {
            var stack = inv.getStackInSlot(i);
            boolean isDogHarvestable = 
                this.getCropBlockFromItem(stack).isPresent();
            if (isDogHarvestable) {
                this.seedTarget = stack.copy();
                harvest_slot_id = i;
                break;
            }
        }
        if (this.seedTarget.isEmpty())
            return;
        int replace_slot_id = harvest_slot_id + 1;
        if (replace_slot_id < inv.getSlots()) {
            var stack = inv.getStackInSlot(replace_slot_id);
            boolean isDogHarvestable = 
                this.getCropBlockFromItem(stack).isPresent();
            if (isDogHarvestable && !stack.is(this.seedTarget.getItem())) {
                this.replacementStack = stack.copy();
            }
        }
    }

    private boolean moveToAndFarmBlock(FarmState farmState) {
        var dog_b0 = dog.blockPosition();
        var dog_nav = dog.getNavigation();
        boolean shouldHurtTool = false;
        
        dog.getLookControl()
            .setLookAt(Vec3.atBottomCenterOf(nextFarmBlock));

        if (  
            dog_nav.isDone() 
            && dog_b0.distSqr(nextFarmBlock) <= 4
        ) {
            dog.getMoveControl().setWantedPosition(
                nextFarmBlock.getX(), 
                nextFarmBlock.getY(),
                nextFarmBlock.getZ(), 1);
        }

        if (dog_b0.distSqr(nextFarmBlock) < 4) {
           
            switch (farmState) {
                case HARVEST:
                    harvest();
                    shouldHurtTool = true;
                    break;
                case PLACE_SEED:
                    placeSeed();
                    shouldHurtTool = true;
                    break;
                default:
                    break;
            }
            this.nextFarmBlock = null;
            return shouldHurtTool;
        }

        if (tickTillPathRecalc <= 0) {
            tickTillPathRecalc = 20;
            dog_nav.moveTo(
                this.nextFarmBlock.getX(),
                this.nextFarmBlock.getY(),
                this.nextFarmBlock.getZ()    
            , 1);
        }
        return shouldHurtTool;
    }
    @Override
    public void onStop() {
        this.setState(ActionState.PENDING);
        this.nextFarmBlock = null;
        dog.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    @Override
    public boolean shouldUse(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        if (!(stack.getItem() instanceof HoeItem))
            return false;
        if (dog.tickCount < this.checkAgainTimestamp) return false;
        this.checkAgainTimestamp = dog.tickCount + 30;
        this.refreshTargetSeed();
        this.nextFarmBlock = findNextFarmBlock();
        return this.nextFarmBlock != null;
    }

    private BlockPos findNextFarmBlock() {
        var bp = this.dog.blockPosition();
        if (this.seedTarget == null || this.seedTarget.isEmpty())
            return null;
        
        boolean owner_check = !(
            ConfigHandler.SERVER.DOGGY_TOOLS_WANDER_FARM.get()
            && !this.dog.getMode().shouldFollowOwner()
        );
        LivingEntity owner = null;
        if (owner_check) {
            owner = this.dog.getOwner(); 
            if (owner == null)
                return null;
        }

        for (BlockPos pos : RingSearchIterator.createWithRandom(bp, 4, SEARCH_RADIUS, true, dog.getRandom())) {
            if (
                owner != null
                && owner.distanceToSqr(Vec3.atBottomCenterOf(pos)) + 1 >= this.talent.getMaxOwnerDistSqr()
            ) {
                continue;
            }
            if (this.getFarmState(pos) != FarmState.NONE) { 
                return pos;
            }
        }
        return null;
    }

    private void placeSeed() {
        var wheatState = 
            getCropBlockFromItem(replacementStack.isEmpty() ? this.seedTarget : replacementStack)
            .orElse(DoggyBlocks.RICE_CROP.get()).defaultBlockState();
        this.dog.level().setBlockAndUpdate(this.nextFarmBlock.above(), wheatState);
        var soundtype = wheatState.getSoundType(
            this.dog.level(), this.nextFarmBlock.above(), this.dog);
        this.dog.playSound(soundtype.getPlaceSound(), 
            (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
    }

    private void harvest() {
        this.dog.level().destroyBlock(this.nextFarmBlock.above(), true);
    }

    private FarmState getFarmState(BlockPos pos) { 
        if (pos == null) return FarmState.NONE;
        if (this.seedTarget == null || this.seedTarget.isEmpty())
            return FarmState.NONE;
        var state = this.dog.level().getBlockState(pos);
        if(state.getBlock() != Blocks.FARMLAND) return FarmState.NONE;
        var state_above = this.dog.level().getBlockState(pos.above());
        if(state_above.getBlock() == Blocks.AIR) {
            return FarmState.PLACE_SEED;
        }
        var crop_block_optional = getCropBlockFromItem(this.seedTarget);
        if (!crop_block_optional.isPresent())
            return FarmState.NONE;
        var crop_block = crop_block_optional.get();
        if (state_above.getBlock() == crop_block
            && crop_block.isMaxAge(state_above)) {
            return FarmState.HARVEST;
        }
        return FarmState.NONE;
    }

    private Optional<CropBlock> getCropBlockFromItem(ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof BlockItem block_item))
            return Optional.empty();
        var block = block_item.getBlock();
        if (!(block instanceof CropBlock crop_block))
            return Optional.empty();
        return Optional.of(crop_block);
    }

    private static enum FarmState { NONE, PLACE_SEED, HARVEST }
}
