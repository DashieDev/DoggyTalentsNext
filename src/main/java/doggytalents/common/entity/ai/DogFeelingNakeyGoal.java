package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogFeelingNakeyGoal extends Goal {

    private Dog dog;

    private boolean prevNaked = true;
    private boolean turnedNaked = false;
    private int updateInterval = 3 * 20;
    private int lastUpdateTick;
    private int stopTick;
    private int tickAnim;
    
    public DogFeelingNakeyGoal(Dog dog) {
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        this.dog = dog;
    }

    @Override
    public boolean canUse() {

        findOutIfTurningToNakid();

        if (!this.dog.isOrderedToSit())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (!dog.canDoIdileAnim()) return false;
        if (!dog.onGround()) return false;
    
        return this.turnedNaked && isNaked(dog);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.dog.isOrderedToSit())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (!this.dog.canContinueDoIdileAnim())
            return false;
        if (!dog.onGround()) return false;

        if (!isNaked(dog)) return false;

        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        this.turnedNaked = false;
        var currentAnimation = DogAnimation.NAKEY;
        this.stopTick = dog.tickCount + currentAnimation.getLengthTicks();
        this.dog.setAnimForIdle(currentAnimation);
        tickAnim = 0;
    }

    @Override
    public void tick() {
        if (tickAnim == 67)
            this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
        ++tickAnim;
    }

    @Override
    public void stop() {
        if (dog.getAnim() == DogAnimation.NAKEY)
            dog.setAnim(DogAnimation.NONE);
    }

    private void findOutIfTurningToNakid() {
        int updateAtTick = lastUpdateTick + updateInterval;
        if (dog.tickCount < updateAtTick)
            return;
        lastUpdateTick = dog.tickCount;
        boolean naked = isNaked(dog);
        if (!naked)
            turnedNaked = false;
        if (naked && !prevNaked)
            turnedNaked = dog.getRandom().nextFloat() <= 0.9f;
        if (naked)
            updateInterval = (5 + dog.getRandom().nextInt(21)) * 20;
        else 
            updateInterval = (1 + dog.getRandom().nextInt(3)) * 20;
        prevNaked = naked;
    }

    private boolean isNaked(Dog dog) {
        if (!dog.hideArmor()) {
            var armors = dog.getArmorSlots();
            boolean hasArmor = false;
            for (var s : armors) {
                if (!s.isEmpty()) {
                    hasArmor = true;
                    break;
                }
            }
            if (hasArmor)
                return false;
                
            if (dog.hasWolfArmor())
                return false;
        }

        var accessories = dog.getAccessories();
        if (accessories.isEmpty())
            return true;
        
        boolean hasNonNaked = false; 
        for (var x : accessories) {
            var accessory = x.getAccessory();
            if (!accessory.isDogStillNakedWhenWear()) {
                hasNonNaked = true;
                break;
            }
        }
        if (!hasNonNaked)
            return true;
    
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

}
