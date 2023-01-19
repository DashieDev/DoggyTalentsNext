package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.Random;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogGreetOwnerGoal extends Goal {

    Dog dog;
    LivingEntity owner;
    boolean isNearOwner;
    final int OWNER_FAR_AWAY_DISTANCE_SQR = 400;
    final int OWNER_CLOSE_BY_SQR = 64;
    final int START_MISSING_OWNER_TIME = 30*20; // 72000; // 1 hour
    final int GREET_STOP_TIME = 20*20;
    final int START_GREET_DISTANCE_SQR = 4;
    final int GREET_RADIUS = 2;
    final int JUMP_BASE_INTERVAL = 20;
    final int WHINE_BASE_INTERVAL = 20;

    long ownerLeftTime;
    boolean shouldGreet;
    int greetTime;

    int tickTillPathRecalc;
    int tickTillPathRecalc2;

    BlockPos rbAroundOwner;
    int tickTillWhine;

    public DogGreetOwnerGoal(Dog dog) {
        this.dog = dog;
        //?
        this.ownerLeftTime = dog.level.getGameTime();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {

        if (!this.dog.isInSittingPose()) return false;
        //TODO EnumMode.canFollowOwner
        owner = this.dog.getOwner();
        if (owner == null) return false; 
        this.updateOwnerNearOrFarState(owner);
        if (!this.shouldGreet) return false;
        this.shouldGreet = false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.greetTime >= GREET_STOP_TIME) return false;
        owner = this.dog.getOwner();
        if (owner == null) return false;
        return true;
    }

    @Override
    public void start() {
        this.dog.setOrderedToSit(false);
    }

    @Override
    public void tick() {
        var n = this.dog.getNavigation();
        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (this.dog.distanceToSqr(this.owner) > START_GREET_DISTANCE_SQR) {
            
            if (--this.tickTillPathRecalc <= 0) {
                this.tickTillPathRecalc = 10;
                n.moveTo(this.owner, this.dog.getUrgentSpeedModifier());
            }
            

        } else {
            ++greetTime;
            if (n.isDone() && this.dog.tickCount % 5 == 0) {
                this.rbAroundOwner = this.getRandomPosAroundOwner(owner);
                n.moveTo(
                    this.rbAroundOwner.getX(),
                    this.rbAroundOwner.getY(),
                    this.rbAroundOwner.getZ(), 1
                );
            } else {
                if (this.dog.getRandom().nextInt(3) == 0) {
                    this.dog.getJumpControl().jump();
                }
                if (--tickTillWhine <= 0) {
                    tickTillWhine = WHINE_BASE_INTERVAL 
                        + dog.getRandom().nextInt(60);
                    this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
                }
            }
            
            
        }
    }

    private void updateOwnerNearOrFarState(LivingEntity owner) {
        if (this.isNearOwner) {
            if (owner.distanceToSqr(this.dog) >= OWNER_FAR_AWAY_DISTANCE_SQR) {
                this.isNearOwner = false;
                this.onOwnerLeave(owner);
            }
        }  else {
            if (owner.distanceToSqr(this.dog) <= OWNER_CLOSE_BY_SQR ) {
                this.isNearOwner = true;
                this.onOwnerArrived(owner);
            }
        }
    }

    private BlockPos getRandomPosAroundOwner(LivingEntity owner) {
        var owner_b0 = owner.blockPosition();
        var r = owner.getRandom();
        int rX = r.nextIntBetweenInclusive(-GREET_RADIUS, GREET_RADIUS);
        int rY = r.nextIntBetweenInclusive(-1, 1);
        int rZ = r.nextIntBetweenInclusive(-GREET_RADIUS, GREET_RADIUS);
        return owner_b0.offset(rX, rY, rZ);
    }
    private void onOwnerLeave(LivingEntity owner) {
        this.ownerLeftTime = this.dog.level.getGameTime();
    }

    private void onOwnerArrived(LivingEntity owner) {
        long timeSinceOwnerLeft = this.dog.level.getGameTime() - this.ownerLeftTime;
        if (timeSinceOwnerLeft >= START_MISSING_OWNER_TIME) {
            this.shouldGreet = true;
        }
    }
    
}
