package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;

public class DogWaterBoundNavigation extends WaterBoundPathNavigation implements IDogNavLock {

    private Dog dog;
    private boolean locked;

    public DogWaterBoundNavigation(Dog dog, Level level) {
        super(dog, level);
        this.dog = dog;
    }

    @Override
    protected PathFinder createPathFinder(int p_26598_) {
        this.nodeEvaluator = new DogSwimNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, p_26598_);
     }

    @Override
    protected boolean canUpdatePath() {
        return this.isInLiquid() && !dog.isOnSwitchNavCooldown()
            && !locked;
    }

    @Override
    public void recomputePath() {
        boolean prevLock = locked;
        locked = false;
        super.recomputePath();
        locked = prevLock;
    }

    @Override
    public void lockDogNavigation() {
        this.locked = true;
    }

    @Override
    public void unlockDogNavigation() {
        this.locked = false;
    }
}
