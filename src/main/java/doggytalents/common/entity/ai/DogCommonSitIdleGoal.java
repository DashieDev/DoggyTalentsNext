package doggytalents.common.entity.ai;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogCommonSitIdleGoal extends Goal {

    private Dog dog;
    private int stopTick;

    public DogCommonSitIdleGoal(Dog dog) {
        this.dog = dog;
    }
 
    @Override
    public boolean canUse() {
        if (!this.dog.isOrderedToSit())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (this.dog.getAnim() != DogAnimation.NONE)
            return false;
        if (!dog.canDoIdileAnim()) return false;
        if (!dog.onGround()) return false;

        return this.dog.getRandom().nextFloat() < 0.02;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.dog.isOrderedToSit())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (this.dog.getAnim() != DogAnimation.SIT_IDLE)
            return false;
        if (!this.dog.canContinueDoIdileAnim())
            return false;
        if (!dog.onGround()) return false;

        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        var currentAnimation = DogAnimation.SIT_IDLE;
        this.stopTick = dog.tickCount + currentAnimation.getLengthTicks();
        this.dog.setAnimForIdle(currentAnimation);
    }

    @Override
    public void stop() {
        if (dog.getAnim() == DogAnimation.SIT_IDLE)
            dog.setAnim(DogAnimation.NONE);
    }

}

