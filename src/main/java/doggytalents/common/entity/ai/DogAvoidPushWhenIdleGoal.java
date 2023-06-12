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
        if (!this.dog.onGround()) return false;

        if (this.dog.isVehicle()) return false;

        if (this.dog.isPathFinding()) return false;

        if (!DogUtil.mayGetPushedIntoHazard(dog, dog.getDeltaMovement())) return false;

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        var v0 = this.dog.getDeltaMovement();
        this.dog.setDeltaMovement(0, v0.y, 0);
        this.dog.setZza(0);
        this.dog.setXxa(0);
    }

    @Override
    public void tick() {
        var v0 = this.dog.getDeltaMovement();
        this.dog.setDeltaMovement(0, v0.y, 0);
        this.dog.setZza(0);
        this.dog.setXxa(0);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    
}
