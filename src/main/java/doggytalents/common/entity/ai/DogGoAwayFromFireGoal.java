package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class DogGoAwayFromFireGoal extends Goal {
    private final int LAVA_SEARCH_RANGE = 4;
    private final int DEFAULT_SEARCH_RANGE = 2;

    private Dog dog;

    private int tickUntilSearch;
    private int tickUntilPathRecalc;

    //If the safePos search fails, the dog is going to wait at least 0.5s to search again.
    //This is for performance issues that make the search cost relatively a bit more time.
    //ex : The pathfinding - eventhough the dog barely do any pathfind, it stills cost 
    //the dog's time for some reasons, perhaps there is some caching going on.
    //Maybe a ring search is better for this, or a ring search with spread alg seems ok.
    //Yeah, i don't trust minecraft's pathfinding performance after this...
    private int failedSearchPenalty;

    private BlockPos safePos;

    public DogGoAwayFromFireGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.dog.fireImmune()) return false;

        if (this.failedSearchPenalty > 0) {
            --this.failedSearchPenalty;
        }

        if (!dog.isOnGround()) return false;

        byte dangerSpot = -1;
        if (--tickUntilSearch <= 0) {
            tickUntilSearch = 3;
            dangerSpot = isDogInDangerSpot(this.dog.position());
        }
        if (dangerSpot == -1) return false;

        // //This goal has no effect when dog is in lava by the way.
        // if (dog.isInLava()) return false;

        this.safePos = null;

        if (this.failedSearchPenalty <= 0) {
            this.safePos = this.searchForSafePos(
                dangerSpot == 0 ? DEFAULT_SEARCH_RANGE : LAVA_SEARCH_RANGE, 2
            );
            if (this.safePos != null) {
                var p = dog.getNavigation().createPath(this.safePos, 1);
                //TODO Have to reach ??????
                boolean flag = p == null ? false :
                    DogUtil.canPathReachTargetBlock(dog, p, this.safePos, dog.getMaxFallDistance());
                if (!flag) this.safePos = null;
            }
            
            if (this.safePos == null) this.failedSearchPenalty = 10;
        }
            
        if (this.safePos == null) return false;

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !isSafePos(this.dog.blockPosition()) && this.safePos != null;
    }

    @Override
    public void start() {
        
        var n = this.dog.getNavigation();

        //TODO Have to reach ??????
        //The path is cached, so it is not matter if this is called twice.
        boolean flag =
            DogUtil.moveToIfReachOrElse(dog, safePos, dog.getUrgentSpeedModifier(), 
                    dog.getMaxFallDistance(), dog1 -> {
                        this.safePos = null;
                    });
        if (!flag) return;

        var p = n.getPath();

        if (p != null && p.getNodeCount() >= 2) {
            var s = p.getNode(1).asBlockPos();
            dog.getMoveControl().setWantedPosition(s.getX() + 0.5, s.getY(), s.getZ() + 0.5, 1.0);
        }

        

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
                //Prevent dog from locking into the safePos
                safePos = null;
            }

            if (--this.tickUntilPathRecalc <= 0) {
                this.tickUntilPathRecalc = 5;
                //TODO Have to reach ??????
                DogUtil.moveToIfReachOrElse(dog, safePos, dog.getUrgentSpeedModifier(), 
                    dog.getMaxFallDistance(), dog1 -> {
                        this.safePos = null;
                    });
            }
        } else {
            this.safePos = this.searchForSafePos(LAVA_SEARCH_RANGE, 2);
        }
    }

    @Override
    public void stop() {
        this.safePos = null;
    }

    /**
     * 1 means lava,
     * 0 means fire,
     * -1 means safe,
     * 
     * @param pos
     * @return the preceeding (not param)
     */
    private byte isDogInDangerSpot(Vec3 pos) {
        var half_bbw = 0.5*dog.getBbWidth();
        int minX = Mth.floor(pos.x - half_bbw)-1;
        int minY = Mth.floor(pos.y);
        int minZ = Mth.floor(pos.z - half_bbw)-1;

        int maxX = Mth.ceil(pos.x + half_bbw)+1;
        int maxY = Mth.ceil(pos.y)+1;
        int maxZ = Mth.ceil(pos.z + half_bbw)+1;

        byte ret = -1; //Assume all is safe

        for (BlockPos x : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            var state = dog.level.getBlockState(x);
            var isBurning = WalkNodeEvaluator.isBurningBlock(state);

            if (isBurning) {
                ret = 0; //Definitly not safe
                if (state.is(Blocks.LAVA)) {
                    return 1; //if lava then return 1;
                }
            }
        }

        return ret;
    }

    private BlockPos searchForSafePos(int searchRangeXZ, int searchRangeY) {
        // BlockPos b0 = this.dog.blockPosition();

        // BlockPos bMin = b0.offset(-searchRange, -2, -searchRange);
        // BlockPos bMax = b0.offset(searchRange, 2, searchRange);

        // this.region = new PathNavigationRegion(this.dog.level, bMin, bMax);

        // for (BlockPos x : BlockPos.betweenClosed(
        //     bMin, 
        //     bMax)
        // ) {
            
        //     if (this.isSafePos(x)) return x;
        // }

        // return null;

        //Ring Search is THE BEST!!!

        var b0 = this.dog.blockPosition();
        var b0m = b0.mutable();
        
        int inflate = 1; // this is to prevent dog from repeating himself
        while (inflate <= searchRangeXZ) {
            final int minX = b0.getX() - inflate;
            final int maxX = b0.getX() + inflate;
            final int minZ = b0.getZ() - inflate;
            final int maxZ = b0.getZ() + inflate;

            b0m.setX(minX);
            b0m.setZ(minZ);
            //ChopinLogger.l("blockpos" + b0);

            if (inflate <= 0) {
                //Maybe treat the center block as an optional return
                //If nothing else outside satisfies this, then return this.
                for (int j = -searchRangeY; j <= searchRangeY; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isSafePos(b0m)) {
                        return b0m.immutable();
                    }
                }
                ++inflate;
                continue;
            }

            for (int i = minX; i <= maxX; ++i) {
                b0m.setX(i);
                //ChopinLogger.l("" + b0m);
                for (int j = -searchRangeY; j <= searchRangeY; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isSafePos(b0m)) {
                        return b0m.immutable();
                    }
                }
            }

            //b0m: maxX, minZ
            for (int i = minZ + 1; i <= maxZ; ++i) {
                b0m.setZ(i);
                //ChopinLogger.l("" + b0m);
                for (int j = -searchRangeY; j <= searchRangeY; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isSafePos(b0m)) {
                        return b0m.immutable();
                    }
                }
            }

            //b0m: maxX, maxZ
            for (int i = maxX-1; i >= minX; --i) {
                b0m.setX(i);
                //ChopinLogger.l("" + b0m);
                for (int j = -searchRangeY; j <= searchRangeY; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isSafePos(b0m)) {
                        return b0m.immutable();
                    }
                }
            }

            //b0m: minX, maxZ
            for (int i = maxZ - 1; i >= minZ + 1; --i) {
                b0m.setZ(i);
                //ChopinLogger.l("" + b0m);
                for (int j = -searchRangeY; j <= searchRangeY; ++j) {
                    b0m.setY(b0.getY() + j);
                    if (this.isSafePos(b0m)) {
                        return b0m.immutable();
                    }
                }
            }
            ++inflate;
            //ChopinLogger.l("inflate!" + inflate);
        }

        //Ring Search is THE BEST!!!

        return null;
    }

    private boolean isSafePos(BlockPos pos) {
        var blockType = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level, pos.mutable());

        if (blockType == BlockPathTypes.WALKABLE) {
            return true;
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
