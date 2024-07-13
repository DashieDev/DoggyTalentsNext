package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogRandomStandIdleGoal extends Goal {
    
    private Dog dog;
    private int stopTick;
    private int startTick;
    private boolean jumped;

    private DogAnimation currentAnimation = DogAnimation.NONE;

    public DogRandomStandIdleGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!dog.canDoIdileAnim()) return false;
        if (dog.getAnim() != DogAnimation.NONE)
            return false;
        if (dog.isOnFire()) return false;
        if (dog.isLowHunger()) return false;
        if (!this.dog.onGround()) return false;
        double use_chance = this.dog.isChopinTail() ? 0.08 : 0.02;
        if (this.dog.getRandom().nextFloat() >= use_chance) return false;
        this.currentAnimation = getIdleAnim();
        if (currentAnimation == DogAnimation.NONE)
            return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.dog.onGround() && this.currentAnimation != DogAnimation.BACKFLIP)
            return false;
        if (dog.isLowHunger()) return false;
        if (!dog.canContinueDoIdileAnim()) return false;
        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        this.stopTick = dog.tickCount + currentAnimation.getLengthTicks();
        this.startTick = dog.tickCount;
        this.jumped = false;
        this.dog.setAnimForIdle(currentAnimation);
    }

    @Override
    public void tick() {
        if (currentAnimation == DogAnimation.BACKFLIP) {
            checkJumpIfBackfilip();
        }
    }

    private void checkJumpIfBackfilip() {
        if (!jumped && dog.tickCount - this.startTick >= 3) {
            jumped = true;
            this.dog.getJumpControl().jump();
        }
    }

    @Override
    public void stop() {
        if (dog.getAnim() == currentAnimation)
            dog.setAnim(DogAnimation.NONE);
    }

    private DogAnimation getIdleAnim() {
        float r = dog.getRandom().nextFloat();
        if (dog.isChopinTail()) {
            if (r <= 0.1f) 
                return DogAnimation.NONE;
            if (r <= 0.4f)
                return DogAnimation.BACKFLIP;
            return DogAnimation.CHOPIN_TAIL;
        } 
        if (r <= 0.02f) {
            return DogAnimation.CHOPIN_TAIL;
        } else if (r <= 0.08f) {
            return DogAnimation.DIG;
        } else if (r <= 0.35f) {
            return DogAnimation.STRETCH;
        } else {
            return DogAnimation.NONE;
        }

    }

}
