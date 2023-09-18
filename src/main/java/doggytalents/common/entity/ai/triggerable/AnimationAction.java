package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;

public class AnimationAction extends TriggerableAction {

    protected DogAnimation anim;
    protected int tickAnim;

    public AnimationAction(Dog dog, DogAnimation animation) {
        super(dog, false, false);
        this.anim = animation;
    }

    @Override
    public void onStart() {
        if (!this.validateAnim()) {
            this.setState(ActionState.FINISHED);
            return;
        }
        this.dog.setAnim(anim);
    }

    @Override
    public void tick() {
        if (tickAnim >= this.anim.getLengthTicks()) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (!this.validateAnim()) {
            this.setState(ActionState.FINISHED);
            return;
        }
        ++tickAnim;
    }

    @Override
    public void onStop() {
        if (this.dog.getAnim() == this.anim)
            this.dog.setAnim(DogAnimation.NONE);
    }

    public boolean validateAnim() {
        return this.dog.getNavigation().isDone();
    }

    public boolean blockMove() {
        return false;
    }

    public boolean blockLook() {
        return false;
    }
    
}
