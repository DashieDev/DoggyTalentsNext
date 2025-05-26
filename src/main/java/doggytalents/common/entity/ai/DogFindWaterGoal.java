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
    private static final int SEARCH_RANGE = 6;

    private Dog dog;

    private int searchAgainAt;
    private int holdTime;

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

        this.waterPos = null;
        if (this.dog.tickCount >= searchAgainAt) {
            this.searchAgainAt = this.dog.tickCount + 10;
            this.waterPos = this.searchForWaterPos();
        }
        if (this.waterPos == null) return false;

        var path = dog.getNavigation().createPath(this.waterPos, 1);
        if (path == null || 
            !DogUtil.canPathReachTargetBlock(dog, path, waterPos, 1, 1)) {
            this.searchAgainAt = this.dog.tickCount + 20;
            return false;
        }
        this.tempPath = path;

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!dog.isOnFire()) return false;

        if (this.waterPos == null) return false;

        return holdTime > 0;
    }

    @Override
    public void start() {
        this.dog.getNavigation().moveTo(tempPath, dog.getUrgentSpeedModifier());
        this.tempPath = null;
        this.holdTime = 5;
    }

    @Override
    public void stop() {
        this.tempPath = null;
        this.waterPos = null;
    }

    @Override
    public void tick() {
        var nav = dog.getNavigation();
        if (this.waterPos != null && !isWaterPos(waterPos))
            this.waterPos = null;
        if (nav.isDone()) {
            if (
                holdTime == 5
                && this.waterPos != null && nav.isDone() 
                && dog.blockPosition().distSqr(waterPos) <= 1
            ) {
                dog.getMoveControl().setWantedPosition(this.waterPos.getX() + 0.5, this.waterPos.getY(), this.waterPos.getZ() + 0.5, 1.0);
            }
            --holdTime;
        }
    }

    private BlockPos searchForWaterPos() {
        for (var pos : RingSearchIterator.createWithRandom(this.dog.blockPosition(), 4, SEARCH_RANGE, true, this.dog.getRandom())) {
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
