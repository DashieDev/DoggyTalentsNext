package doggytalents.common.entity.ai.triggerable;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;

public class DogBackFlipAction extends AnimationAction {

    private int tickTillJump = 3;

    public DogBackFlipAction(Dog dog) {
        super(dog, DogAnimation.BACKFLIP);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getState() == ActionState.FINISHED)
            return;
        if (--this.tickTillJump == 0) {
            this.dog.getJumpControl().jump();
        }
    }    
    
}
