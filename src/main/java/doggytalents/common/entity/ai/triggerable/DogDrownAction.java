package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;

public class DogDrownAction extends TriggerableAction {

    private int tickTillShink = 70;
    private int tickSink = 0;
    private int tickAnim = 0;

    public DogDrownAction(Dog dog) {
        super(dog, false, false);
    }

    @Override
    public void onStart() {
        this.dog.setAnim(DogAnimation.DROWN);
    }

    @Override
    public void tick() {
        if (!stillInLiquid()) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (this.tickAnim >= DogAnimation.DROWN.getLengthTicks()) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (this.tickTillShink <= 0 && !this.dog.isDogSwimming()) {
            this.dog.setDogSwimming(true);
        }
        if (this.tickTillShink <= 0) {
            this.tickSink += 1;
        }
        if (tickSink >= 50) {
            this.dog.setDogSwimming(false);
        }
        ++tickAnim;
        --tickTillShink;
    }

    @Override
    public void onStop() {
        this.dog.setDogSwimming(false);
        if (this.dog.getAnim() == DogAnimation.DROWN) {
            this.dog.setAnim(DogAnimation.NONE);
        }
    }

    private boolean stillInLiquid() {
        return this.dog.isInLava() || this.dog.isInWater();
    }
    
    @Override
    public boolean isIdleAction() {
        return true;
    }
    
}
