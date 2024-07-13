package doggytalents.common.entity.ai.triggerable;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;

public class AnimationAction extends TriggerableAction {

    protected DogAnimation anim;
    protected int tickAnim;

    public AnimationAction(Dog dog, DogAnimation animation) {
        super(dog, false, false);
        this.anim = animation;
    }

    @Override
    public void onStart() {
        this.dog.setAnim(anim);
        if (!this.validateAnim()) {
            this.setState(ActionState.FINISHED);
            return;
        }
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
        if (dog.getAnim() != anim)
            return false;
        return this.dog.getNavigation().isDone();
    }
    
}
