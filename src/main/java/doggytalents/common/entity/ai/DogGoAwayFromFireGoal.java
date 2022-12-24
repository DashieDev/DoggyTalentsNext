package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DogGoAwayFromFireGoal extends Goal {
    private final int SEARCH_RANGE = 4;

    private Dog dog;

    private int tickUntilSearch;
    private int tickUntilPathRecalc;

    private BlockPos safePos;

    public DogGoAwayFromFireGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.dog.fireImmune()) return false;

        if (!dog.isOnGround()) return false;

        boolean dangerSpot = false;
        if (--tickUntilSearch <= 0) {
            tickUntilSearch = 3;
            dangerSpot = isDogInDangerSpot(this.dog.position());
        }
        if (!dangerSpot) return false;

        this.safePos = this.searchForSafePos();

        if (this.safePos == null) return false;

        

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !isSafePos(this.dog.blockPosition()) && this.safePos != null;
    }

    @Override
    public void start() {
        
        this.dog.getNavigation().moveTo(
            safePos.getX(), safePos.getY(), safePos.getZ(),
            1.5
        );

        this.tickUntilPathRecalc = 10;
        
    }

    @Override
    public void tick() {
        var dog_bp = dog.blockPosition();
        var n = dog.getNavigation();

        if (safePos == null) return;
        
        if (isSafePos(safePos)) {
            if (n.isDone() && dog_bp.distSqr(safePos) <= 1 ) {
                dog.getMoveControl().setWantedPosition(this.safePos.getX() + 0.5, this.safePos.getY(), this.safePos.getZ() + 0.5, 1.0);
            }

            if (--this.tickUntilPathRecalc <= 0) {
                this.tickUntilPathRecalc = 5;
                DogUtil.moveToIfReachOrElse(dog, safePos, 1.5, 
                    dog.getMaxFallDistance(), dog1 -> {
                        this.safePos = null;
                    });
            }
        } else {
            this.safePos = this.searchForSafePos();
        }
    }

    @Override
    public void stop() {
        this.safePos = null;
    }

    private boolean isDogInDangerSpot(Vec3 pos) {
        var half_bbw = 0.5*dog.getBbWidth();
        int minX = Mth.floor(pos.x - half_bbw)-1;
        int minY = Mth.floor(pos.y);
        int minZ = Mth.floor(pos.z - half_bbw)-1;

        int maxX = Mth.ceil(pos.x + half_bbw)+1;
        int maxY = Mth.ceil(pos.y)+1;
        int maxZ = Mth.ceil(pos.z + half_bbw)+1;

        for (BlockPos x : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            var isBurning = WalkNodeEvaluator.isBurningBlock(dog.level.getBlockState(x));

            if (isBurning) {
                return true;
            }
        }

        return false;
    }

    private BlockPos searchForSafePos() {
        BlockPos b0 = this.dog.blockPosition();

        for (BlockPos x : BlockPos.betweenClosed(
            b0.offset(-this.SEARCH_RANGE, -2, -this.SEARCH_RANGE), 
            b0.offset(this.SEARCH_RANGE, 2, this.SEARCH_RANGE))
        ) {
            if (this.isSafePos(x)) return x;
        }

        return null;
    }

    private boolean isSafePos(BlockPos pos) {
        var blockType = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level, pos.mutable());

        if (blockType == BlockPathTypes.WALKABLE) {
            if (dog.blockPosition().distSqr(pos) <= 2.25) return true;

            //Due to the search radius is meant to be small, the path
            //that need to be calc each time is not big
            //, therefore, the pathfinding becomes relatively light.
            var path = this.dog.getNavigation().createPath(pos, 1);
            if (path == null) return false;
            boolean flag = 
                DogUtil.canPathReachTargetBlock(dog, path, pos, 0);
            if (flag) {
                return true;
            }
        }

        return false;
    }

    //NOTE : Any goal with this need to be careful that 
    //canContinueToUse doesn't always get checked before tick(), 
    //so any nullable object that is checked via canContinueToUse
    //Should be re check at tick()
    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
    
}
