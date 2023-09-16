package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogProtestSitOrderGoal extends Goal {

    private final int MIN_TOLERABLE_SPAM_TIME = 3;

    private Dog dog;
    private int lastOrderedToSitTick0;
    private int spamTime = 0;
    private int spamTimeMax = MIN_TOLERABLE_SPAM_TIME;
    private int stopAnimTick;

    public DogProtestSitOrderGoal(Dog dog) {
        this.dog = dog;
        this.spamTimeMax = MIN_TOLERABLE_SPAM_TIME + dog.getRandom().nextInt(4);
    }

    @Override
    public boolean canUse() {
        updateDetectSpamSit();

        if (!this.dog.onGround()) return false;
        if (this.dog.isVehicle()) return false;

        if (spamTime >= spamTimeMax) {
            spamTime = 0;
            return true;
        }

        return false;
    }

    @Override
    public void start() {
        this.dog.setAnim(DogAnimation.PROTEST);
        if (!this.dog.isInSittingPose()) {
            this.dog.setSitAnim(DogAnimation.NONE);
        }
        this.dog.setProtesting(true);
        this.dog.setOrderedToSit(true);
        this.stopAnimTick = dog.tickCount + DogAnimation.PROTEST.getLengthTicks();
    }

    @Override
    public void tick() {
        var owner = this.dog.getOwner();
        if (owner == null) return;
        if (owner.distanceToSqr(dog) > 25) return;
        this.dog.getLookControl().setLookAt(owner, 10.0F, this.dog.getMaxHeadXRot());
    }

    @Override
    public void stop() {
        if (this.dog.getAnim() == DogAnimation.PROTEST) {
            this.dog.setAnim(DogAnimation.NONE);
        }
        this.dog.setProtesting(false);
        this.spamTimeMax = MIN_TOLERABLE_SPAM_TIME + dog.getRandom().nextInt(4);
    }

    @Override
    public boolean canContinueToUse() {
        return this.dog.isOrderedToSit() && this.dog.tickCount <= this.stopAnimTick;
    }

    private void updateDetectSpamSit() {
        if (dog.lastOrderedToSitTick != this.lastOrderedToSitTick0) {
            int interval = Math.abs(dog.lastOrderedToSitTick -this.lastOrderedToSitTick0);
            if (interval <= getSpamInterval()) {
                ++this.spamTime;
            } else {
                this.spamTime = 0;
            }
            this.lastOrderedToSitTick0 = dog.lastOrderedToSitTick;
        }
    }

    private int getSpamInterval() {return 45;}

    
}
