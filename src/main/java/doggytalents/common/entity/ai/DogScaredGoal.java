package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.DogSounds;
import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.DogAiManager.IHasTickNonRunning;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogScaredGoal extends Goal implements IHasTickNonRunning {
    
    private Dog dog;
    private int animTick = 0;
    private int stopTick;
    private int cooldown;

    public DogScaredGoal(Dog dog) {
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0)
            return false;
        if (!this.dog.dogFear.hasAnyFear())
            return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        this.stopTick = dog.tickCount + DogAnimation.SCARED.getLengthTicks();
        this.dog.setScared(1);
        animTick = 0;
        this.dog.dogSoundManager.playInterruptible(DogSounds.SAD_WHINE.get(), this.dog.getSoundVolume(), this.dog.getVoicePitch());
        this.dog.dogSoundManager.setAmbientLocked(true);
    }

    @Override
    public void tick() {
        ++animTick;
    }

    @Override
    public void stop() {
        //this.cooldown = 100;
        this.dog.setScared(0);
        
        this.dog.dogSoundManager.setAmbientLocked(false);
    }

    @Override
    public void tickDogWhenNotRunning() {
        if (this.cooldown > 0) --this.cooldown;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

}
