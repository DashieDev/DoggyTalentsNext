package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.Dog.RestingState;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogRestWhenSitGoal extends Goal {

    private Dog dog;
    private int restPeriod;
    private int reSitTime;

    private boolean isBellyUpRest;

    public DogRestWhenSitGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.dog.isDefeated())
            return false;
        if (!this.dog.isOrderedToSit())
            return false;
        if (!this.dog.canDoIdileAnim())
            return false;
        if (dog.isOnFire())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (!this.dog.onGround())
            return false;
        if (!dog.wantsToRest()) 
            return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.dog.isDefeated())
            return false;
        if (!this.dog.isOrderedToSit())
            return false;
        if (!this.dog.canContinueDoIdileAnim())
            return false;
        if (!this.dog.isInSittingPose())
            return false;
        if (!this.dog.onGround())
            return false;
        return this.reSitTime <= getEndAnim().getLengthTicks();
    }

    @Override
    public void start() {
        this.isBellyUpRest = this.dog.getRandom().nextInt(3) == 0;
        if (isBellyUpRest)
            restPeriod = getStartAnim().getLengthTicks() + 
                (1 + this.dog.getRandom().nextInt(4)) * (getLoopAnim().getLengthTicks());
        else
            restPeriod = 100 + this.dog.getRandom().nextInt(11) * 20;
        reSitTime = 0;
        this.dog.setDogRestingState(isBellyUpRest ? RestingState.BELLY : RestingState.LYING);
        this.dog.setAnimForIdle(getStartAnim());
    }

    @Override
    public void tick() {
        if (this.restPeriod > 0) {
            if (this.dog.getAnim() == DogAnimation.NONE && this.dog.getDogPose() == getRestingPose()) {
                this.dog.setAnim(getLoopAnim());
            }
            --this.restPeriod;
        }
        
        if (this.restPeriod <= 0) {
            if (this.dog.getAnim() == getLoopAnim()) {
                this.dog.setAnim(getEndAnim());
            }
            this.dog.setDogRestingState(RestingState.NONE);
            ++this.reSitTime;
        }
    }

    @Override
    public void stop() {
        this.dog.resetTickTillRest();
        this.dog.setDogRestingState(RestingState.NONE);
        var anim = this.dog.getAnim();
        if (!anim.isNone() && !anim.interupting()) {
            this.dog.setAnim(DogAnimation.NONE);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private DogAnimation getStartAnim() {
        return this.isBellyUpRest ?
            DogAnimation.REST_BELLY_START
            : DogAnimation.SIT_TO_REST;
    }

    private DogAnimation getLoopAnim() {
        return this.isBellyUpRest ? 
            DogAnimation.REST_BELLY_LOOP
            : DogAnimation.REST_IDLE;
    }

    private DogAnimation getEndAnim() {
        return this.isBellyUpRest ?
            DogAnimation.REST_BELLY_END
            : DogAnimation.REST_TO_SIT;
    }

    private DogPose getRestingPose() {
        return this.isBellyUpRest ? 
            DogPose.REST_BELLY
            : DogPose.REST;
    }
}
