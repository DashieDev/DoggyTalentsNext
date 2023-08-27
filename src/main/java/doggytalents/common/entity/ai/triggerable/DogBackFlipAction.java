package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;

public class DogBackFlipAction extends AnimationAction {

    private int tickTillJump = 3;

    public DogBackFlipAction(Dog dog) {
        super(dog, DogAnimation.BACKFLIP);
    }

    @Override
    public void tick() {
        super.tick();
        if (--this.tickTillJump == 0) {
            this.dog.getJumpControl().jump();
        }
    }

    @Override
    public boolean blockMove() {
        return true;
    }

    
    
}
