package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;

public class DogDrownAction extends AnimationAction {

    private int tickTillShink = 70;
    private int tickSink = 0;

    public DogDrownAction(Dog dog) {
        super(dog, DogAnimation.DROWN);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickTillShink <= 0 && !this.dog.isDogSwimming()) {
            this.dog.setDogSwimming(true);
        }
        if (this.tickTillShink <= 0) {
            this.tickSink += 1;
        }
        if (tickSink >= 50) {
            this.dog.setDogSwimming(false);
        }
        --tickTillShink;
    }

    @Override
    public boolean validateAnim() {
        if (!stillInLiquid()) {
            return false;
        }
        return super.validateAnim();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.dog.setDogSwimming(false);
    }

    private boolean stillInLiquid() {
        return this.dog.isInLava() || this.dog.isInWater();
    }

    @Override
    public boolean blockMove() {
        return true;
    }
    
}
