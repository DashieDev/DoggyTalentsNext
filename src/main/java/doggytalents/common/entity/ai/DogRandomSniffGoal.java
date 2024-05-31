package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.DogAiManager.IHasTickNonRunning;
import doggytalents.common.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class DogRandomSniffGoal extends Goal implements IHasTickNonRunning {

    private Dog dog;
    private int stopTick;
    private BlockPos sniffAtPos;
    private BlockPos sniffUnderPos;
    private BlockState sniffAtState;
    private BlockState sniffUnderState;
    private BlockPos currentPos;
    private DogAnimation currentAnimation = DogAnimation.NONE;
    private int tickAnim = 0;

    private boolean isDoingAnim = false;
    private boolean shouldMoveSignificantly = false;
    private final int EXPLORE_RADIUS = 6;
    private int stillRememberBeingBurnedTick = 0;

    public DogRandomSniffGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!dog.canDoIdileAnim()) return false;
        if (dog.isOnFire()) return false;
        if (dog.isLowHunger()) return false;
        if (!this.dog.onGround()) return false;
        if (this.dog.getRandom().nextFloat() >= 0.01f)
            return false;
        return true;
    }

    @Override
    public void tickDogWhenNotRunning() {
        if (stillRememberBeingBurnedTick > 0)
            --stillRememberBeingBurnedTick;
    }

    @Override
    public boolean canContinueToUse() {
        if (dog.isLowHunger()) return false;
        if (!this.isDoingAnim) {
            return true;
        }
        if (!dog.canContinueDoIdileAnim()) return false;
        if (dog.getAnim() != currentAnimation) return false;
        validateSniff();
        if (sniffAtPos == null) 
            return false;
        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        this.isDoingAnim = false;
        var moveToPos = this.findMoveToPos();
        this.dog.getNavigation().moveTo(moveToPos.getX(),
            moveToPos.getY(), moveToPos.getZ(), 1f);
        this.dog.setDogCurious(true);
    }

    @Override
    public void tick() {
        if (this.isDoingAnim)
            tickDoingAnim();
        else {
            tickMoveTo();
        }
    }

    @Override
    public void stop() {
        if (dog.getAnim() == currentAnimation && currentAnimation != DogAnimation.NONE)
            dog.setAnim(DogAnimation.NONE);
        dog.setDogCurious(false);
        resetSniffPos();
        dog.getNavigation().stop();
    }

    private void startDoingAnim() {
        this.currentAnimation = getSniffAnim();
        this.stopTick = dog.tickCount + currentAnimation.getLengthTicks();
        this.dog.setAnimForIdle(currentAnimation);
        tickAnim = 0;
    }

    private void tickDoingAnim() {
        if (sniffAtPos == null)
            return;
        this.dog.getLookControl().setLookAt(Vec3.atBottomCenterOf(sniffAtPos));
        switch (this.currentAnimation) {
        default:
            break;
        case SNIFF_HOT: 
        {
            float maxAwayDist = shouldMoveSignificantly ? 0.35f : 0.1f;
            var current_center = Vec3.atBottomCenterOf(currentPos);
            var d_sqr = this.dog.distanceToSqr(current_center);
            var d_sqr_2 = this.dog.distanceToSqr(sniffAtPos.getX() + 0.5f, 
                this.dog.getY(), sniffAtPos.getZ() + 0.5f);
            if (tickAnim < 20 && (d_sqr < maxAwayDist || d_sqr_2 > 1) && sniffAtState.isAir()) {
                this.dog.getMoveControl().setWantedPosition(sniffAtPos.getX(), this.dog.getY(),
                sniffAtPos.getZ(), 0.5f);
            }
            if (tickAnim == 25) {
                var pushBackVec = current_center.subtract(this.dog.position())
                    .normalize();
                this.dog.push(pushBackVec.x() * 0.3, 0, pushBackVec.z() * 0.3);
                this.dog.playSound(SoundEvents.WOLF_HURT, 0.6f, this.dog.getVoicePitch());
                this.dog.playSound(SoundEvents.GENERIC_BURN, 0.3F, 2.0F + this.dog.getRandom().nextFloat() * 0.4F);
                rememberBeingBurned();
            }
            ++tickAnim;
            break;
        }
        case SNIFF_SNEEZE:
        {
            if (this.tickAnim == 25) {
                this.dog.playSound(SoundEvents.WOLF_AMBIENT, 1, (this.dog.getRandom().nextFloat() - this.dog.getRandom().nextFloat()) * 0.2F + 1.5F);
            }
            ++tickAnim;
            break;
        }
        case TOUCHY_TOUCH:
        {
            if (this.tickAnim == 35) {
                this.dog.playSound(SoundEvents.WOLF_HURT, 0.6f, this.dog.getVoicePitch());
                this.dog.playSound(SoundEvents.GENERIC_BURN, 0.3F, 2.0F + this.dog.getRandom().nextFloat() * 0.4F);
                rememberBeingBurned();
            }
            ++tickAnim;
        }
        case DOWN_THE_HOLE:
        {
            float maxAwayDist = 0.1f;
            var current_center = Vec3.atBottomCenterOf(currentPos);
            var d_sqr = this.dog.distanceToSqr(current_center);
            var d_sqr_2 = this.dog.distanceToSqr(sniffAtPos.getX() + 0.5f, 
                this.dog.getY(), sniffAtPos.getZ() + 0.5f);
            if ((d_sqr < maxAwayDist || d_sqr_2 > 1) && sniffAtState.isAir()) {
                this.dog.getMoveControl().setWantedPosition(sniffAtPos.getX(), this.dog.getY(),
                sniffAtPos.getZ(), 0.5f);
            }
        }
        }
    }

    private void tickMoveTo() {
        if (this.dog.getNavigation().isDone() || almostOutOfRestrict()) {
            this.dog.getNavigation().stop();
            this.isDoingAnim = true;
            if (!this.dog.onGround())
                return;
            findSniffPos();
            if (sniffAtPos != null)
                startDoingAnim();
        }
    }

    private boolean almostOutOfRestrict() {
        if (!this.dog.hasRestriction())
            return false;
        var restrict_b0 = this.dog.getRestrictCenter();
        if (restrict_b0 == null)
            return false;
        var restrict_r = this.dog.getRestrictRadius();
        var restrict_d0_sqr = this.dog.distanceToSqr(Vec3.atBottomCenterOf(restrict_b0));
        var d_inside_sqr = restrict_r * restrict_r - restrict_d0_sqr;
        return d_inside_sqr <= 1;
    }

    private BlockPos findMoveToPos() {
        if (dog.hasRestriction() && dog.getRestrictCenter() != null) {
            var restrict_b0 = dog.getRestrictCenter();
            var restrict_r = dog.getRestrictRadius();
            int explore_r = Mth.floor(restrict_r) - 1;
            if (explore_r <= 0)
                return this.dog.blockPosition();
            var r = this.dog.getRandom();
            int offX = EntityUtil.getRandomNumber(dog, -explore_r, explore_r);
            int offY = EntityUtil.getRandomNumber(dog, -1, 1);
            int offZ = EntityUtil.getRandomNumber(dog, -explore_r, explore_r);
            return restrict_b0.offset(offX, offY, offZ);
        }
        var r = this.dog.getRandom();
        int offX = EntityUtil.getRandomNumber(dog, -EXPLORE_RADIUS, EXPLORE_RADIUS);
        int offY = EntityUtil.getRandomNumber(dog, -1, 1);
        int offZ = EntityUtil.getRandomNumber(dog, -EXPLORE_RADIUS, EXPLORE_RADIUS);
        return this.dog.blockPosition().offset(offX, offY, offZ);
    }

    private boolean findSniffPos() {
        int offset = this.dog.getRandom().nextBoolean() ? 1 : -1;
        boolean offsetX = this.dog.getRandom().nextBoolean();
        var currentPos = this.dog.blockPosition();
        var sniffPos = offsetX ?
            currentPos.offset(offset, 0, 0)
            : currentPos.offset(0, 0, offset);
        var sniffState = this.dog.level().getBlockState(sniffPos);
        if (!isBlockSniffable(sniffPos, sniffState))
            return false;
        var sniffPosUnder = sniffPos.below();
        var sniffStateUnder = this.dog.level().getBlockState(sniffPosUnder);
        if (!isBlockBelowSniffable(sniffPosUnder, sniffStateUnder))
            return false;
        populateSniffPos(currentPos, sniffPos, sniffState, sniffPosUnder, sniffStateUnder);
        return true;
    }

    private boolean isBlockSniffable(BlockPos pos, BlockState state) {
        if (state.isAir())
            return true;
        if (this.stillRememberBeingBurned()) {
            if (WalkNodeEvaluator.isBurningBlock(state))
                return false;
        }
        return !state.isCollisionShapeFullBlock(dog.level(), pos);
    }

    private boolean isBlockBelowSniffable(BlockPos posBelow, BlockState state) {
        if (this.stillRememberBeingBurned()) {
            if (WalkNodeEvaluator.isBurningBlock(state))
                return false;
        }
        return true;
    }

    private DogAnimation getSniffAnim() {
        boolean fireImmune = this.dog.fireImmune();
        shouldMoveSignificantly = !sniffUnderState.isCollisionShapeFullBlock(dog.level(), sniffUnderPos);
        if (!fireImmune && 
            WalkNodeEvaluator.isBurningBlock(sniffUnderState)
            && sniffAtState.isAir())
            return DogAnimation.SNIFF_HOT;
        var atBlock = sniffAtState.getBlock();
        if (atBlock instanceof FlowerBlock || atBlock instanceof TorchBlock)
            return DogAnimation.SNIFF_SNEEZE;
        if (!fireImmune && 
            WalkNodeEvaluator.isBurningBlock(sniffAtState))
            return DogAnimation.TOUCHY_TOUCH;
        if (sniffAtState.isAir() && sniffUnderState.isAir()) {
            return DogAnimation.DOWN_THE_HOLE;
        }
        if (fireImmune && atBlock == (Blocks.LAVA)) {
            return DogAnimation.SNIFF_SNEEZE;
        }

        return DogAnimation.SNIFF_NEUTRAL;
    }

    private void validateSniff() {
        boolean invalidated = false;
        if (sniffAtPos == null)
            invalidated = true;
        
        if (!invalidated) {
            if (dog.distanceToSqr(Vec3.atBottomCenterOf(sniffAtPos)) > 4)
                invalidated = true; 
        }

        if (!invalidated) {
            var newAtState = dog.level().getBlockState(sniffAtPos);
            if (newAtState.getBlock() != sniffAtState.getBlock())
                invalidated = true;
        }
        
        if (!invalidated) {
            var newUnderState = dog.level().getBlockState(sniffUnderPos);
            if (newUnderState.getBlock() != sniffUnderState.getBlock())
                invalidated = true;
        }

        if (invalidated) {
            resetSniffPos();
            return;
        }
        
        return;
    }

    private void populateSniffPos(BlockPos current, BlockPos sniffPos, BlockState sniffState,
        BlockPos sniffUnderPos, BlockState sniffUnderState) {
        if (sniffUnderPos == null || sniffUnderState == null ||
            sniffState == null || current == null) 
            sniffPos = null;
        this.sniffAtState = sniffState;
        this.sniffUnderState = sniffUnderState;
        this.sniffAtPos = sniffPos;
        this.sniffUnderPos = sniffUnderPos;
        this.currentPos = current;
    }

    private void resetSniffPos() {
        this.sniffAtPos = null;
        this.sniffAtState = null;
        this.sniffUnderPos = null;
        this.sniffUnderState = null;
        this.currentPos = null;
    }

    private void rememberBeingBurned() {
        this.stillRememberBeingBurnedTick = this.dog.getRandom().nextInt(5) * 10 * 20;
    }

    private boolean stillRememberBeingBurned() {
        return this.stillRememberBeingBurnedTick > 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
    
}
