package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.CachedSearchUtil.CachedSearchUtil;
import doggytalents.common.util.CachedSearchUtil.DogGreedyFireSafeSearchPath;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DogGoAwayFromFireGoal extends Goal {

    private Dog dog;

    private int tickUntilSearch;
    private int lastGoAwayTimestamp;
    private int walkableUntilStop = 1;

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
        if (this.path == null || !checkCanReplaceCurrentPath(dog, this.path)) {
            this.tickUntilSearch = 5;
            this.path = null;
            return false;
        }

        return true;
    }

    private boolean checkCanReplaceCurrentPath(Dog dog, DogGreedyFireSafeSearchPath new_path) {
        var current_path = this.dog.getNavigation().getPath();
        if (current_path == null)
            return true;
        if (current_path.isDone())
            return true;
        var current_next_node = current_path.getNextNode();
        var current_next_malus = dog.getPathfindingMalus(current_next_node.type);
        var new_next_malus = dog.getPathfindingMalus(new_path.getNode(0).type);
        if (new_next_malus < current_next_malus)
            return true;
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.path == null)
            return false;
        if (dog.getNavigation().isDone())
            return false;
        boolean is_safe = 
            this.path.getWalkableCount() >= this.walkableUntilStop
            && this.checkAboveForFallingLava(this.dog.blockPosition());
        if (is_safe)
            return false;
        
        return true;
    }

    @Override
    public void start() {
        if (this.path == null) 
            return;
        var nav = this.dog.getNavigation();
        nav.stop();
        nav.moveTo(this.path, this.dog.getUrgentSpeedModifier());
        var b0 = this.path.getNode(0).asBlockPos();
        DogUtil.stopAndForceLook(dog, b0.getCenter());
        this.dog.getMoveControl().setWantedPosition(b0.getX() + 0.5f, b0.getY(), b0.getZ() + 0.5f, 
            this.dog.getUrgentSpeedModifier());
        this.dog.setDogForcePushAvoid(true);
        int tick_since_last = this.dog.tickCount - this.lastGoAwayTimestamp;
        this.walkableUntilStop = tick_since_last >= 20 ? 1 : 2;
    }

    @Override
    public void stop() {
        this.dog.setDogForcePushAvoid(false);
        this.tickUntilSearch = 5;
        this.lastGoAwayTimestamp = this.dog.tickCount;
        proccessEndNode();
        this.dog.getNavigation().stop();
    }

    private void proccessEndNode() {
        if (this.path == null) 
            return;
        var end_node = this.path.getEndNode();
        if (end_node == null) 
            return;
        var b0 = end_node.asBlockPos();
        this.dog.getMoveControl().setWantedPosition(b0.getX() + 0.5f, b0.getY(), b0.getZ() + 0.5f, 
            this.dog.getUrgentSpeedModifier());
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
        int min_x = Mth.floor(pos.x - half_bbw)-1;
        int min_y = Mth.floor(pos.y);
        int min_z = Mth.floor(pos.z - half_bbw)-1;

        int max_x = Mth.floor(pos.x + half_bbw)+1;
        int max_y = Mth.floor(pos.y+1);
        int max_z = Mth.floor(pos.z + half_bbw)+1;

        byte ret = -1; //Assume all is safe
        for (var check_pos : BlockPos.betweenClosed(min_x, min_y, min_z, max_x, max_y, max_z)) {
            boolean is_corner = 
                (check_pos.getX() == min_x || check_pos.getX() == max_x)
                && (check_pos.getZ() == min_z || check_pos.getZ() == max_z);
            if (is_corner)
                continue;

            var dog_bb = dog.getBoundingBox();

             /*
                * TODO Absoluteness of dog bbW.
                * Currently this implementation relies on the fact that currently a dog's bbHeight never
                * surpass one block like DogSwimNodeEval
            */
            boolean above_outside =
                check_pos.getY() > min_y
                && !dog_bb.intersects(new AABB(new BlockPos(check_pos.getX(), min_y, check_pos.getZ())));
            if (above_outside)
                continue;
            
            var state = dog.level().getBlockState(check_pos);
            if (state.getFluidState().is(FluidTags.LAVA))
                return 1;
            

            var check_pos_bb = new AABB(check_pos);
            boolean pos_within_dog_bb = dog.getBoundingBox().intersects(check_pos_bb);
            if (!pos_within_dog_bb) 
                continue;

            boolean is_burning = WalkNodeEvaluator.isBurningBlock(state);
            if (is_burning)
                return 1;

            if (check_pos.getY() == min_y) {
                var pos_below = DogUtil.getSurfaceStandingInPos(dog, check_pos.getX(), check_pos.getZ()).below();
                var state_below = dog.level().getBlockState(pos_below);
                boolean full_collision_burning_block = 
                    WalkNodeEvaluator.isBurningBlock(state_below)
                    && state_below.isCollisionShapeFullBlock(dog.level(), pos_below);
                if (full_collision_burning_block)
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

    private boolean checkAboveForFallingLava(BlockPos pos) {
        var pos_above = pos.above();
        var state_above = dog.level().getBlockState(pos_above);
        if (state_above.is(Blocks.LAVA))
            return false;

        return true;
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
