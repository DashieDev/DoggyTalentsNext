package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogFindWaterGoal extends Goal {
    private final int SEARCH_RANGE = 12;

    private Dog dog;

    private int tickUntilSearch;
    private int tickUntilPathRecalc;

    private BlockPos waterPos;

    public DogFindWaterGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.dog.fireImmune()) return false;

        if (!dog.isOnGround()) return false;

        if (!dog.isOnFire()) return false;

        if (--this.tickUntilSearch <= 0) {
            this.tickUntilSearch = 5;
            this.waterPos = this.searchForWaterPos();
        }
        if (this.waterPos == null) return false;

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
        this.tickUntilPathRecalc = 0;
    }

    @Override
    public void tick() {
        if (this.isWaterPos(waterPos)) {
            if (--this.tickUntilPathRecalc <= 0) {
                this.tickUntilPathRecalc = 10;
                this.dog.getNavigation().moveTo(
                    waterPos.getX(), waterPos.getY(), waterPos.getZ(),
                    1.5
                );
            }
        } else {
            this.waterPos = this.searchForWaterPos();
        }
    }

    private BlockPos searchForWaterPos() {
        BlockPos b0 = this.dog.blockPosition();

        for (BlockPos x : BlockPos.betweenClosed(
            b0.offset(-this.SEARCH_RANGE, -4, -this.SEARCH_RANGE), 
            b0.offset(this.SEARCH_RANGE, 4, this.SEARCH_RANGE))
        ) {
            if (this.isWaterPos(x)) {
                return x;
            }
        }

        return null;
    }

    private boolean isWaterPos(BlockPos pos) {
        if (pos == null) return false;
        if (dog.level.getFluidState(pos).is(FluidTags.WATER)) {
            return true;
        }
        if (dog.level.isRainingAt(pos)) {
            return true;
        }
        return false;
    }

}
