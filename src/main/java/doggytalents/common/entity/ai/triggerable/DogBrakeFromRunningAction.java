package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;

public class DogBrakeFromRunningAction extends AnimationAction {

    public DogBrakeFromRunningAction(Dog dog) {
        super(dog, DogAnimation.BREAK_FROM_RUNNING);
    }

    @Override
    public boolean blockMove() {
        return true;
    }




    
}
