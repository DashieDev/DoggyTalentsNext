package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogRandomStandIdleGoal extends Goal {
    
    private Dog dog;
    private int stopTick;

    private DogAnimation currentAnimation = DogAnimation.NONE;

    public DogRandomStandIdleGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!dog.canDoIdileAnim()) return false;
        if (dog.isLowHunger()) return false;
        if (!this.dog.onGround()) return false;
        double use_chance = this.dog.isChopinTail() ? 0.08 : 0.02;
        return this.dog.getRandom().nextFloat() < use_chance;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.dog.onGround())
            return false;
        if (dog.isLowHunger()) return false;
        if (!dog.canContinueDoIdileAnim()) return false;
        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        this.currentAnimation = getIdleAnim();
        this.stopTick = dog.tickCount + currentAnimation.getLengthTicks();
        this.dog.setAnim(currentAnimation);
    }

    @Override
    public void tick() {
    }

    @Override
    public void stop() {
        if (dog.getAnim() == currentAnimation)
            dog.setAnim(DogAnimation.NONE);
    }

    private DogAnimation getIdleAnim() {
        float r = dog.getRandom().nextFloat();
        if (dog.isChopinTail()) {
            return r <= 0.7f ? DogAnimation.CHOPIN_TAIL : DogAnimation.STAND_IDLE_2;
        } 
        if (r <= 0.10f) {
            return DogAnimation.DIG;
        } else if (r <= 0.35f) {
            return DogAnimation.STRETCH;
        } else {
            return DogAnimation.STAND_IDLE_2;
        }

    }

}
