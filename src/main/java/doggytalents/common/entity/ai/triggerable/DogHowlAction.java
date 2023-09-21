package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class DogHowlAction extends TriggerableAction {

    private int stopTick;
    private int tickTillHowl = 50;
    
    public DogHowlAction(Dog dog) {
        super(dog, false, false);
    }

    @Override
    public void onStart() {
        this.stopTick = dog.tickCount + DogAnimation.HOWL.getLengthTicks();
        this.dog.setAnim(DogAnimation.HOWL);
    }

    @Override
    public void tick() {
        if (dog.tickCount >= stopTick) {
            this.setState(ActionState.FINISHED);
            return;
        }
        --tickTillHowl;
        if (tickTillHowl == 0) {
            dog.howl();
        } else if (tickTillHowl == 30) {
            this.dog.playSound(SoundEvents.WOLF_GROWL, 0.3F, (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F);
        }
    }

    @Override
    public void onStop() {
        if (dog.getAnim() == DogAnimation.HOWL) {
            dog.setAnim(DogAnimation.NONE);
        }
    }
    
}
