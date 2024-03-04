package doggytalents.common.entity.ai.triggerable;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;

public class DogFaintStandAction extends AnimationAction {

    public DogFaintStandAction(Dog dog, DogAnimation anim) {
        super(dog, anim);
    }

    @Override
    public boolean validateAnim() {
        if (!this.dog.onGround()) {
            return false;
        }
        return super.validateAnim();
    }

    @Override
    public boolean blockMove() {
        return true;
    }
}
