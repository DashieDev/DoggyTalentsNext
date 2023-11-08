package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
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
    private DogAnimation currentAnimation = DogAnimation.NONE;
    private int tickAnim = 0;

    public DogRandomSniffGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!dog.canDoIdileAnim()) return false;
        if (dog.isLowHunger()) return false;
        if (!this.dog.onGround()) return false;
        if (this.dog.getRandom().nextFloat() >= 0.007f)
            return false;
        int xOffset = this.dog.getRandom().nextInt(3) - 1;
        int zOffset = this.dog.getRandom().nextInt(3) - 1;
        var sniffPos = this.dog.blockPosition().offset(xOffset, 0, zOffset);
        var sniffState = this.dog.level().getBlockState(sniffPos);
        if (!isBlockSniffable(sniffPos, sniffState))
            return false;
        sniffAtState = sniffState;
        var sniffUnderPos = sniffPos.below();
        sniffUnderState = this.dog.level().getBlockState(sniffUnderPos);
        this.sniffAtPos = sniffPos;
        this.sniffUnderPos = sniffUnderPos;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (dog.isLowHunger()) return false;
        if (!dog.canContinueDoIdileAnim()) return false;
        if (!this.validateSniff()) return false;
        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        this.currentAnimation = getSniffAnim();
        this.stopTick = dog.tickCount + currentAnimation.getLengthTicks();
        this.dog.setAnimForIdle(currentAnimation);
        tickAnim = 0;
        if (this.currentAnimation == DogAnimation.SNIFF_HOT)
            this.dog.setDogCurious(true);
    }

    @Override
    public void tick() {
        this.dog.getLookControl().setLookAt(Vec3.atBottomCenterOf(sniffAtPos));
        if (this.currentAnimation == DogAnimation.SNIFF_HOT) {
            var d_sqr = this.dog.distanceToSqr(sniffAtPos.getX(), this.dog.getY(), sniffAtPos.getZ());
            if (tickAnim < 20 && d_sqr > 0.35 && sniffAtState.isAir()) {
                this.dog.getMoveControl().setWantedPosition(sniffAtPos.getX(), this.dog.getY(),
                sniffAtPos.getZ(), 0.5f);
            }
            if (tickAnim == 25) {
                var a1 = dog.yBodyRot;
                var dx1 = -Mth.sin(a1 * Mth.DEG_TO_RAD);
                var dz1 = Mth.cos(a1 * Mth.DEG_TO_RAD);
                this.dog.push(-dx1 * 0.3, 0, -dz1 * 0.3);
                this.dog.playSound(SoundEvents.WOLF_HURT, 0.6f, this.dog.getVoicePitch());
                this.dog.playSound(SoundEvents.GENERIC_BURN, 0.3F, 2.0F + this.dog.getRandom().nextFloat() * 0.4F);
            }
            ++tickAnim;
        }
        if (this.currentAnimation == DogAnimation.SNIFF_SNEEZE) {
            if (this.tickAnim == 25) {
                this.dog.playSound(SoundEvents.WOLF_AMBIENT, 1, (this.dog.getRandom().nextFloat() - this.dog.getRandom().nextFloat()) * 0.2F + 1.5F);
            }
            ++tickAnim;
        }
    }

    @Override
    public void stop() {
        if (dog.getAnim() == currentAnimation)
            dog.setAnim(DogAnimation.NONE);
        dog.setDogCurious(false);
    }

    private boolean isBlockSniffable(BlockPos pos, BlockState state) {
        if (state.isAir())
            return true;
        return !state.isCollisionShapeFullBlock(dog.level(), pos);
    }

    private DogAnimation getSniffAnim() {
        boolean fireImmune = this.dog.fireImmune();
        if (!fireImmune && 
            WalkNodeEvaluator.isBurningBlock(sniffUnderState))
            return DogAnimation.SNIFF_HOT;
        var atBlock = sniffAtState.getBlock();
        if (atBlock instanceof FlowerBlock || atBlock instanceof TorchBlock)
            return DogAnimation.SNIFF_SNEEZE;
        if (atBlock instanceof FireBlock)
            return DogAnimation.SNIFF_HOT;
        return DogAnimation.SNIFF_NEUTRAL;
    }

    private boolean validateSniff() {
        var newAtState = dog.level().getBlockState(sniffAtPos);
        if (newAtState.getBlock() != sniffAtState.getBlock())
            return false;
        var newUnderState = dog.level().getBlockState(sniffUnderPos);
        if (newUnderState.getBlock() != sniffUnderState.getBlock())
            return false;    
        return true;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
    
}
