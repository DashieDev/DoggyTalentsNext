package doggytalents.common.entity.ai.triggerable;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;

public class DogFaintStandAction extends AnimationAction {

    public DogFaintStandAction(Dog dog, DogAnimation anim) {
        super(dog, anim);
    }

    @Override
    public boolean validateAnim() {
        if (!this.dog.isOnGround()) {
            return false;
        }
        return super.validateAnim();
    }
}
