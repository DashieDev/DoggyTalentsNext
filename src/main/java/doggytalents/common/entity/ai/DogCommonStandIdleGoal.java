package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogCommonStandIdleGoal extends Goal {
    
    private Dog dog;
    private int stopTick;

    public DogCommonStandIdleGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!dog.canDoIdileAnim()) return false;
        if (dog.isOnFire()) return false;
        if (dog.isLowHunger()) return false;
        //if (!this.dog.onGround()) return false;
        double use_chance = 0.02f;
        return this.dog.getRandom().nextFloat() < use_chance;
    }

    @Override
    public boolean canContinueToUse() {
        // if (!this.dog.onGround())
        //     return false;
        if (dog.isLowHunger()) return false;
        if (!dog.canContinueDoIdileAnim()) return false;
        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        var currentAnimation = DogAnimation.STAND_IDLE_2;
        this.stopTick = dog.tickCount + currentAnimation.getLengthTicks();
        this.dog.setAnimForIdle(currentAnimation);
    }

    @Override
    public void tick() {
    }

    @Override
    public void stop() {
        if (dog.getAnim() == DogAnimation.STAND_IDLE_2)
            dog.setAnim(DogAnimation.NONE);
    }
}
