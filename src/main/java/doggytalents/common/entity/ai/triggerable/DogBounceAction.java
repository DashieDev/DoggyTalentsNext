package doggytalents.common.entity.ai.triggerable;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;

public class DogBounceAction extends AnimationAction{

    public DogBounceAction(Dog dog) {
        super(dog, DogAnimation.STAND_QUICK);
    }
    
}
