package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogRestWhenSitGoal extends Goal {

    private Dog dog;
    private int restPeriod;
    private int reSitTime;

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
        return (--restPeriod > 0 ? true : ++this.reSitTime <= DogAnimation.REST_TO_SIT.getLengthTicks());
    }

    @Override
    public void start() {
        restPeriod = 40 + this.dog.getRandom().nextInt(9) * 20;
        reSitTime = 0;
        this.dog.setResting(true);
        this.dog.setAnim(DogAnimation.SIT_TO_REST);
    }

    @Override
    public void tick() {
        if (this.restPeriod > 0)
        if (this.dog.getAnim() == DogAnimation.NONE && this.dog.getDogPose() == DogPose.REST) {
            this.dog.setAnim(DogAnimation.REST_IDLE);
        }
        if (this.restPeriod <= 0) {
            if (this.dog.getAnim() == DogAnimation.REST_IDLE) {
                this.dog.setResting(false);
                this.dog.setAnim(DogAnimation.REST_TO_SIT);
            }   
        }
    }

    @Override
    public void stop() {
        this.dog.resetTickTillRest();
        this.dog.setResting(false);
        if (this.dog.getAnim() == DogAnimation.REST_IDLE) {
            this.dog.setAnim(DogAnimation.NONE);
        }
    }
}
