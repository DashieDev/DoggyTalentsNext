package doggytalents.common.entity.ai.triggerable;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;

public class DogDrownAction extends AnimationAction {

    private int tickTillShink = 70;
    private int tickSink = 0;
    private boolean swimming = false;

    public DogDrownAction(Dog dog) {
        super(dog, DogAnimation.DROWN);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getState() == ActionState.FINISHED)
            return;
        if (this.tickTillShink <= 0 && !this.dog.isDogSwimming()) {
            this.dog.setDogSwimming(true);
            this.swimming = true;
        }
        if (this.tickTillShink <= 0) {
            this.tickSink += 1;
        }
        if (tickSink >= 50 && swimming) {
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
        if (swimming)
        this.dog.setDogSwimming(false);
    }

    private boolean stillInLiquid() {
        return this.dog.isInLava() || this.dog.isInWater();
    }
    
}
