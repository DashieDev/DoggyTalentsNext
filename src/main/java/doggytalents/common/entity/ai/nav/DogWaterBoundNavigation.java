package doggytalents.common.entity.ai.nav;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
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
        return this.dog.isInLiquid() && !dog.isOnSwitchNavCooldown()
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

    @Override
    @Nullable
    protected Path createPath(@Nonnull Set<BlockPos> pos, int p_148224_, boolean p_148225_, int p_148226_,
            float p_148227_) {
        dogThrowIfLockAndDebug();  
        return super.createPath(pos, p_148224_, p_148225_, p_148226_, p_148227_);
    }

    //Debug only
    private void dogThrowIfLockAndDebug() {
        // if (locked) {
        //     ChopinLogger.lwn(dog, "Someone trying to create path from outside!");
        //     throw new IllegalStateException(dog.getName().getString() + ": Someone trying to create path from outside!");
        // }
    }
}
