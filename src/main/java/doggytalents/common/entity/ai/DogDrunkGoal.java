package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogDrunkGoal extends Goal {
    
    private Dog dog;
    private int extendedGoalTick;

    public DogDrunkGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!dog.isDoingFine())
            return false;
        if (!dog.isDogDrunk())
            return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!dog.isDoingFine())
            return false;
        return extendedGoalTick > 0;
    }

    @Override
    public void start() {
        dog.setAnim(DogAnimation.DRUNK_START);
        dog.setInDrunkPose(true);
        extendedGoalTick = DogAnimation.STAND_QUICK.getLengthTicks();
        dog.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (dog.getAnim() == DogAnimation.NONE)
            dog.setAnim(DogAnimation.DRUNK_LOOP);
        if (!dog.isDogDrunk()) {
            dog.setInDrunkPose(false);
            if (dog.getAnim() == DogAnimation.DRUNK_LOOP)
                dog.setAnim(DogAnimation.STAND_QUICK);
            --extendedGoalTick;
        }
    }

    @Override
    public void stop() {
        dog.setInDrunkPose(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

}
