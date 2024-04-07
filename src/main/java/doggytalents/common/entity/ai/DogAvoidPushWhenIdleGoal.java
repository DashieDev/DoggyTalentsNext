package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

/**
 * @author DashieDev
 */
public class DogAvoidPushWhenIdleGoal extends Goal {

    private final float DISTANCE_HAZARD_CHECK = 0.5f;

    private Dog dog;
    
    public DogAvoidPushWhenIdleGoal(Dog dog) {
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        if (!this.dog.isOnGround()) return false;

        if (!this.dog.isDoingFine()) return false;

        if (this.dog.isDogCurious()) return false;

        if (this.dog.isVehicle()) return false;

        if (this.dog.isPathFinding()) return false;

        var delta = this.dog.getDeltaMovement();
        var delta_l = delta.x*delta.x + delta.z*delta.z;
        if (delta_l < (double)2.5000003E-7F)
            return false;

        if (!DogUtil.mayGetPushedIntoHazard(dog, dog.getDeltaMovement())) return false;

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        this.dog.setDogResistingPush(true);
    }

    @Override
    public void stop() {
        this.dog.setDogResistingPush(false);
    }
}
