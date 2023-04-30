package doggytalents.common.talent.doggy_tools.tool_actions;

import doggytalents.ChopinLogger;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DogFarmerAction extends ToolAction {

    private static final int SEARCH_RADIUS = 4;

    private BlockPos nextFarmBlock;
    private int tickTillPathRecalc;
    private int cooldown;


    public DogFarmerAction(Dog dog, DoggyToolsTalent talent) {
        super(dog, talent);
    }

    @Override
    public void onStart() {
        this.tickTillPathRecalc = 3;
    }

    @Override
    public void tick() {

        if (this.nextFarmBlock == null) {
            this.setState(ActionState.FINISHED);
            return;
        }
        
        var farmState = this.getFarmState(this.nextFarmBlock);
        if (farmState == FarmState.NONE) {
            this.setState(ActionState.FINISHED);
            return;
        }

        var dog_b0 = dog.blockPosition();

        if (dog_b0.distSqr(nextFarmBlock) < 4) {
            switch (farmState) {
                case HARVEST:
                    harvest();
                    break;
                case PLACE_SEED:
                    placeSeed();
                    break;
                default:
                    break;
            }
            this.setState(ActionState.FINISHED);
            return;
        }

        if (--tickTillPathRecalc <= 0) {
            tickTillPathRecalc = 20;
            this.dog.getNavigation().moveTo(
                this.nextFarmBlock.getX(),
                this.nextFarmBlock.getY(),
                this.nextFarmBlock.getZ()    
            , 1);
        }
    }

    @Override
    public void onStop() {
        this.setState(ActionState.PENDING);
        this.nextFarmBlock = null;
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
        for (BlockPos pos : BlockPos.betweenClosed(
            bp.offset(-SEARCH_RADIUS, -4, -SEARCH_RADIUS), 
            bp.offset(SEARCH_RADIUS, 4, SEARCH_RADIUS))) {
            if (this.getFarmState(pos) != FarmState.NONE) { 
                ChopinLogger.lwn(this.dog, pos.toString()); 
                return pos;
            }
        }
        return null;
    }

    private void placeSeed() {
        var wheatState = Blocks.WHEAT.defaultBlockState();
        this.dog.level.setBlockAndUpdate(this.nextFarmBlock.above(), wheatState);
        var soundtype = wheatState.getSoundType(
            this.dog.level, this.nextFarmBlock.above(), this.dog);
        this.dog.playSound(soundtype.getPlaceSound(), 
            (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        ChopinLogger.lwn(this.dog,"planted crop!");
    }

    private void harvest() {
        this.dog.level.destroyBlock(this.nextFarmBlock.above(), true);
        ChopinLogger.lwn( this.dog, "harvested crop" );
        this.nextFarmBlock = this.findNextFarmBlock();
    }

    private FarmState getFarmState(BlockPos pos) { 
        var state = this.dog.level.getBlockState(pos);
        if(state.getBlock() != Blocks.FARMLAND) return FarmState.NONE;
        var state_above = this.dog.level.getBlockState(pos.above());
        if(state_above.getBlock() == Blocks.AIR) {
            return FarmState.PLACE_SEED;
        }
        if(
            state_above.getBlock() == Blocks.WHEAT 
            && ((CropBlock) Blocks.WHEAT).isMaxAge(state_above)
        ) {
            return FarmState.HARVEST;
        }
        return FarmState.NONE;
    }

    private static enum FarmState { NONE, PLACE_SEED, HARVEST }
}
