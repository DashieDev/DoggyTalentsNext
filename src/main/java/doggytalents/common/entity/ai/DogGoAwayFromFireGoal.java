package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.CachedSearchUtil.CachedSearchUtil;
import doggytalents.common.util.CachedSearchUtil.DogGreedyFireSafeSearchPath;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DogGoAwayFromFireGoal extends Goal {

    private Dog dog;

    private int tickUntilSearch;

    private DogGreedyFireSafeSearchPath path;

    public DogGoAwayFromFireGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.dog.fireImmune()) return false;

        if (this.dog.shouldDogNotAfraidOfFire())
            return false;

        byte dangerSpot = -1;
        if (--tickUntilSearch <= 0) {
            tickUntilSearch = 3;
            dangerSpot = isDogInDangerSpot(this.dog.position());
        }
        if (dangerSpot == -1) return false;

        this.path = DogGreedyFireSafeSearchPath.create(dog, 10);
        if (this.path == null) {
            this.tickUntilSearch = 10;
            return false;
        }

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (isSafePos(this.dog.blockPosition()))
            return false;
        if (dog.getNavigation().isDone())
            return false;
        
        return true;
    }

    @Override
    public void start() {
        if (this.path == null) return;
        var n = this.dog.getNavigation();
        n.moveTo((Path) null, 1);
        n.moveTo(this.path, this.dog.getUrgentSpeedModifier());
        
        if (this.path == null) return;

        var b0 = this.path.getNode(0).asBlockPos();
        this.dog.getMoveControl().setWantedPosition(b0.getX() + 0.5f, b0.getY(), b0.getZ() + 0.5f, 
            this.dog.getUrgentSpeedModifier());
    }

    @Override
    public void stop() {
        if (this.path == null) return;
        var end_node = this.path.getEndNode();
        if (end_node == null) return;
        var b0 = end_node.asBlockPos();
        this.dog.getMoveControl().setWantedPosition(b0.getX() + 0.5f, b0.getY(), b0.getZ() + 0.5f, 
            this.dog.getUrgentSpeedModifier());
        this.dog.getNavigation().stop();
        
        //Penalty
        if (end_node.type != PathType.WALKABLE)
            this.tickUntilSearch = 20 + dog.getRandom().nextInt(3)*10;
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
        final double FLUID_BB_DEFLATE = 0.001D;
        var half_bbw = 0.5*dog.getBbWidth() - FLUID_BB_DEFLATE;
        int minX = Mth.floor(pos.x - half_bbw)-1;
        int minY = Mth.floor(pos.y);
        int minZ = Mth.floor(pos.z - half_bbw)-1;

        int maxX = Mth.floor(pos.x + half_bbw)+1;
        int maxY = Mth.floor(pos.y+1);
        int maxZ = Mth.floor(pos.z + half_bbw)+1;

        byte ret = -1; //Assume all is safe
        for (BlockPos x : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            boolean isCorner = 
                (x.getX() == minX || x.getX() == maxX)
                && (x.getZ() == minZ || x.getZ() == maxZ);
            if (isCorner)
                continue;
            boolean isWithinDogBb =
                dog.getBoundingBox().intersects(new AABB(x));
            if (isWithinDogBb)
                continue;
            var state = dog.level().getBlockState(x);
            var isBurning = WalkNodeEvaluator.isBurningBlock(state);

            if (state.is(Blocks.LAVA)) {
                return 1;
            }
            var blockBb = new AABB(Vec3.atLowerCornerOf(x),Vec3.atLowerCornerWithOffset(x, 1, 1, 1));

            if (isBurning && dog.getBoundingBox().intersects(blockBb)) {
                return 1;
            }
        }

        // {
        //     var x = new BlockPos(pos.x, pos.y-1, pos.z);
        //     var state = dog.level().getBlockState(x);
        //     var isBurning = WalkNodeEvaluator.isBurningBlock(state);

        //     if (isBurning) {
        //         ret = 0; //Definitly not safe
        //         if (state.is(Blocks.LAVA)) {
        //             return 1; //if lava then return 1;
        //         }
        //     }
        // }

        return ret;
    }

    private boolean isSafePos(BlockPos pos) {
        var blockType = WalkNodeEvaluator.getPathTypeStatic(dog, pos.mutable());

        if (blockType == PathType.WALKABLE) {
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
