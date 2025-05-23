package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.RingSearchIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

/**
 * @author DashieDev
 */
public class DogFindWaterGoal extends Goal {
    private final int SEARCH_RANGE = 12;

    private Dog dog;

    private int tickUntilSearch;
    private int tickUntilPathRecalc;

    private BlockPos waterPos;
    private Path tempPath;

    public DogFindWaterGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.dog.fireImmune()) return false;

        if (this.dog.shouldDogNotAfraidOfFire())
            return false;

        if (!dog.onGround()) return false;

        if (!dog.isOnFire()) return false;

        this.waterPos = null; //This is why sometimes there used to seems to be WaterPos Saving .......
        if (--this.tickUntilSearch <= 0) {
            this.tickUntilSearch = 5;
            this.waterPos = this.searchForWaterPos();
        }
        if (this.waterPos == null) return false;

        var path = dog.getNavigation().createPath(this.waterPos, 1);
        if (path == null || 
            !DogUtil.canPathReachTargetBlock(dog, path, waterPos, 1, 1)) {
            this.tickUntilSearch += 10;
            return false;
        }
        this.tempPath = path;

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!dog.isOnFire()) return false;

        if (this.waterPos == null) return false;

        return true;
    }

    @Override
    public void start() {
        this.dog.getNavigation().moveTo(tempPath, dog.getUrgentSpeedModifier());
        this.tickUntilPathRecalc = 10;
        this.tempPath = null;
    }

    @Override
    public void stop() {
        this.tempPath = null;
    }

    @Override
    public void tick() {

        var dog_bp = dog.blockPosition();
        var n = dog.getNavigation();

        if (this.isWaterPos(waterPos)) {
            if (n.isDone() && dog_bp.distSqr(waterPos) <= 1 ) {
                dog.getMoveControl().setWantedPosition(this.waterPos.getX() + 0.5, this.waterPos.getY(), this.waterPos.getZ() + 0.5, 1.0);
            }

            if (--this.tickUntilPathRecalc <= 0) {
                this.tickUntilPathRecalc = 10;
                DogUtil.moveToIfReachOrElse(
                    dog, waterPos, dog.getUrgentSpeedModifier(), 1, dog.getMaxFallDistance(), 
                    dog1 -> {
                        this.waterPos = null;
                    }    
                );
            }
        } else {
            this.waterPos = this.searchForWaterPos();
        }
    }

    private BlockPos searchForWaterPos() {
        for (var pos : RingSearchIterator.create(this.dog.blockPosition(), 4, SEARCH_RANGE, true)) {
            if (this.isWaterPos(pos)) {
                return pos;
            }
        }
        return null;
    }

    private boolean isWaterPos(BlockPos pos) {
        if (pos == null) return false;
        if (dog.level().getFluidState(pos).is(FluidTags.WATER)) {
            return true;
        }
        if (dog.level().isRainingAt(pos)) {
            return true;
        }
        return false;
    }

}
