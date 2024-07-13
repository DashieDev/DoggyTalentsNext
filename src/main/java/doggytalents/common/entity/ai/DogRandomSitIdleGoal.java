package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogRandomSitIdleGoal extends Goal {

    private Dog dog;
    private int stopTick;

    private DogAnimation currentAnimation = DogAnimation.NONE;

    public DogRandomSitIdleGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.dog.isOrderedToSit())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (!dog.canDoIdileAnim()) return false;
        if (dog.getAnim() != DogAnimation.NONE)
            return false;
        if (!dog.onGround()) return false;

        if (this.dog.getRandom().nextFloat() >= 0.02)
            return false;
        
        this.currentAnimation = getIdleAnim();
        if (this.currentAnimation == DogAnimation.NONE)
            return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.dog.isOrderedToSit())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (!this.dog.canContinueDoIdileAnim())
            return false;
        if (!dog.onGround()) return false;

        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        this.stopTick = dog.tickCount + currentAnimation.getLengthTicks();
        this.dog.setAnimForIdle(currentAnimation);
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
        if (r <= 0.10f) {
            return DogAnimation.SCRATCHIE;
        } else if (r <= 0.20f) {
            return DogAnimation.SIT_LOOK_AROUND;
        } else if (r <= 0.30f) {
            return DogAnimation.BELLY_RUB;
        } else {
            return DogAnimation.NONE;
        }

    }
    
}
