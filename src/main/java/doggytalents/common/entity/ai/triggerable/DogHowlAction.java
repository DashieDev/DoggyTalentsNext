package doggytalents.common.entity.ai.triggerable;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
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
        dog.dogSoundManager.setAmbientLocked(true);
    }

    @Override
    public void tick() {
        if (dog.getAnim() != DogAnimation.HOWL) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (dog.tickCount >= stopTick) {
            this.setState(ActionState.FINISHED);
            return;
        }
        --tickTillHowl;
        if (tickTillHowl == 0) {
            dog.dogSoundManager.playInterruptible(dog.dogMood.getAmbientSound(), 1, dog.getVoicePitch());
        } else if (tickTillHowl == 30) {
            this.dog.playSound(dog.dogMood.getSeriousGrowl(), 0.3F, dog.getVoicePitch());
        }
    }

    @Override
    public void onStop() {
        if (dog.getAnim() == DogAnimation.HOWL) {
            dog.setAnim(DogAnimation.NONE);
        }
        if (this.isStarted()) {
            dog.dogSoundManager.setAmbientLocked(false);
            dog.dogSoundManager.interuptPlaying();
        }
    }

    @Override
    public boolean canOverrideSit() {
        return true;
    }
    
}
