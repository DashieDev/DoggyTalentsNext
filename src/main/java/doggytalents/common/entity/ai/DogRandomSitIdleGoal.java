package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
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
        if (!this.dog.isInSittingPose())
            return false;
        if (!dog.canDoIdileAnim()) return false;
        if (!dog.isOnGround()) return false;

        return this.dog.getRandom().nextFloat() < 0.02;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.dog.isOrderedToSit())
            return false;
        if (!this.dog.isInSittingPose())
            return false;
        if (!dog.isOnGround()) return false;

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
        if (r <= 0.15f) {
            return DogAnimation.SCRATCHIE;
        } else {
            return DogAnimation.SIT_IDLE;
        }

    }
    
}
