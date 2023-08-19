package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogRandomStrechGoal extends Goal {
    
    private Dog dog;
    private int stopTick;

    public DogRandomStrechGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (dog.tickCount - stopTick < 200) return false;
        if (!dog.canDoIdileAnim()) return false;
        return this.dog.getRandom().nextFloat() < 0.02;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.dog.isOnGround())
            return false;
        if (!dog.canContinueDoIdileAnim()) return false;
        return this.dog.tickCount < this.stopTick;
    }

    @Override
    public void start() {
        this.stopTick = dog.tickCount + DogAnimation.STRETCH.getLengthTicks();
        this.dog.setAnim(DogAnimation.STRETCH);
    }

    @Override
    public void tick() {
    }

    @Override
    public void stop() {
        if (dog.getAnim() == DogAnimation.STRETCH)
            dog.setAnim(DogAnimation.NONE);
    }

}
