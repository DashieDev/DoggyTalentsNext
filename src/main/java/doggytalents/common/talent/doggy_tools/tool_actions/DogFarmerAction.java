package doggytalents.common.talent.doggy_tools.tool_actions;

import doggytalents.ChopinLogger;
import doggytalents.DoggyBlocks;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DogFarmerAction extends ToolAction {

    private static final int SEARCH_RADIUS = 4;

    private BlockPos nextFarmBlock;
    private int tickTillPathRecalc;
    private int tickTillResearch;
    private int cooldown;

    private static enum SeedState { WHEAT, RICE }
    private SeedState seedState = SeedState.WHEAT;


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

        var owner = this.dog.getOwner();
        if (owner == null || dog.distanceToSqr(owner) > this.talent.getMaxOwnerDistSqr()) {
            this.setState(ActionState.FINISHED);
            return;
        }

        if (owner.isAlive() || owner.isSpectator()) {
            this.setState(ActionState.FINISHED);
            return;
        }

        var stack = this.dog.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack == null || stack.getItem() != Items.STONE_HOE) {
            this.setState(ActionState.FINISHED);
            return;
        }

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

        if (hurtTools) {
            var hurtStack = dog.getMainHandItem();
            if (hurtStack != null && hurtStack.getItem() instanceof HoeItem) {
                hurtStack.hurtAndBreak(1, dog, (p_150845_) -> {
                    p_150845_.broadcastBreakEvent(InteractionHand.MAIN_HAND);
                });
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

        if (--tickTillPathRecalc <= 0) {
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
    public boolean shouldUse() {
        if (--this.cooldown > 0) return false;
        this.cooldown = 10;
        this.nextFarmBlock = findNextFarmBlock();
        return this.nextFarmBlock != null;
    }

    private BlockPos findNextFarmBlock() {
        var bp = this.dog.blockPosition();
        var owner = this.dog.getOwner();
        if (owner == null) return null;
        for (BlockPos pos : BlockPos.betweenClosed(
            bp.offset(-SEARCH_RADIUS, -4, -SEARCH_RADIUS), 
            bp.offset(SEARCH_RADIUS, 4, SEARCH_RADIUS))) {
            if (this.getFarmState(pos) != FarmState.NONE 
                && owner.distanceToSqr(Vec3.atBottomCenterOf(pos)) + 1 < this.talent.getMaxOwnerDistSqr()) { 
                return pos;
            }
        }
        return null;
    }

    private void placeSeed() {
        var wheatState = 
            seedState == SeedState.RICE ?
            DoggyBlocks.RICE_CROP.get().defaultBlockState()
            : Blocks.WHEAT.defaultBlockState();
        this.dog.level().setBlockAndUpdate(this.nextFarmBlock.above(), wheatState);
        var soundtype = wheatState.getSoundType(
            this.dog.level(), this.nextFarmBlock.above(), this.dog);
        this.dog.playSound(soundtype.getPlaceSound(), 
            (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        
    }

    private void harvest() {
        var harvestState = this.dog.level().getBlockState(nextFarmBlock.above());
        if (harvestState.is(DoggyBlocks.RICE_CROP.get()))
            seedState = SeedState.RICE;
        else 
            seedState = SeedState.WHEAT;
        this.dog.level().destroyBlock(this.nextFarmBlock.above(), true);
    }

    private FarmState getFarmState(BlockPos pos) { 
        if (pos == null) return FarmState.NONE;
        var state = this.dog.level().getBlockState(pos);
        if(state.getBlock() != Blocks.FARMLAND) return FarmState.NONE;
        var state_above = this.dog.level().getBlockState(pos.above());
        if(state_above.getBlock() == Blocks.AIR) {
            return FarmState.PLACE_SEED;
        }
        if(
            state_above.getBlock() == Blocks.WHEAT
            && ((CropBlock) Blocks.WHEAT).isMaxAge(state_above)
        ) {
            return FarmState.HARVEST;
        }
        if(
            state_above.getBlock() == DoggyBlocks.RICE_CROP.get()
            && DoggyBlocks.RICE_CROP.get().isMaxAge(state_above)
        ) {
            return FarmState.HARVEST;
        }
        return FarmState.NONE;
    }

    private static enum FarmState { NONE, PLACE_SEED, HARVEST }
}
