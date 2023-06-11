package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * @author DashieDev
 */
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

        this.waterPos = null; //This is why sometimes there used to seems to be WaterPos Saving .......
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
        var b0 = this.dog.blockPosition();
        var b0m = b0.mutable();

        // for (BlockPos x : BlockPos.betweenClosed(
        //     b0m.offset(-this.SEARCH_RANGE, -4, -this.SEARCH_RANGE), 
        //     b0m.offset(this.SEARCH_RANGE, 4, this.SEARCH_RANGE))
        // ) {
        //     if (this.isWaterPos(x)) {
        //         return x; // If dog can path to it.
        //     }
        // }
        
        int inflate = 1;
        while (inflate <= SEARCH_RANGE) {
            final int minX = b0.getX() - inflate;
            final int maxX = b0.getX() + inflate;
            final int minZ = b0.getZ() - inflate;
            final int maxZ = b0.getZ() + inflate;

            b0m.setX(minX);
            b0m.setZ(minZ);
            //ChopinLogger.l("blockpos" + b0);

            for (int i = minX; i <= maxX; ++i) {
                b0m.setX(i);
                //ChopinLogger.l("" + b0m);
                for (int j = -4; j <= 4; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isWaterPos(b0m)) {
                        return b0m.immutable();
                    }
                }
            }

            //b0m: maxX, minZ
            for (int i = minZ + 1; i <= maxZ; ++i) {
                b0m.setZ(i);
                //ChopinLogger.l("" + b0m);
                for (int j = -4; j <= 4; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isWaterPos(b0m)) {
                        return b0m.immutable();
                    }
                }
            }

            //b0m: maxX, maxZ
            for (int i = maxX-1; i >= minX; --i) {
                b0m.setX(i);
                //ChopinLogger.l("" + b0m);
                for (int j = -4; j <= 4; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isWaterPos(b0m)) {
                        return b0m.immutable();
                    }
                }
            }

            //b0m: minX, maxZ
            for (int i = maxZ - 1; i >= minZ + 1; --i) {
                b0m.setZ(i);
                //ChopinLogger.l("" + b0m);
                for (int j = -4; j <= 4; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isWaterPos(b0m)) {
                        return b0m.immutable();
                    }
                }
            }
            ++inflate;
            //ChopinLogger.l("inflate!" + inflate);
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
