package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import doggytalents.common.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class DogRandomSniffGoal extends Goal {

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
        if (this.dog.noDogCurious()) return false;
        if (this.dog.getRandom().nextFloat() >= 0.01f)
            return false;
        return true;
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
        if (this.dog.getNavigation().isDone()) {
            this.isDoingAnim = true;
            if (!this.dog.onGround())
                return;
            findSniffPos();
            if (sniffAtPos != null)
                startDoingAnim();
        }
    }

    private BlockPos findMoveToPos() {
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
        populateSniffPos(currentPos, sniffPos, sniffState);
        return true;
    }

    private boolean isBlockSniffable(BlockPos pos, BlockState state) {
        if (state.isAir())
            return true;
        return !state.isCollisionShapeFullBlock(dog.level(), pos);
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
        return DogAnimation.SNIFF_NEUTRAL;
    }

    private void validateSniff() {
        boolean invalidated = false;
        if (sniffAtPos == null)
            invalidated = true;

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

    private void populateSniffPos(BlockPos current, BlockPos sniffPos, BlockState sniffState) {
        if (sniffPos == null || sniffState == null) 
            return;
        sniffAtState = sniffState;
        var sniffUnderPos = sniffPos.below();
        sniffUnderState = this.dog.level().getBlockState(sniffUnderPos);
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

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
    
}
