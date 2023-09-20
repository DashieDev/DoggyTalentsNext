package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;

public class DogBounceAction extends AnimationAction{

    public DogBounceAction(Dog dog) {
        super(dog, DogAnimation.STAND_QUICK);
    }

    @Override
    public boolean blockSitStandAnim() {
        return true;
    }
    
}
