package doggytalents.common.entity.ai.nav;

import javax.annotation.Nullable;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DogSwimNodeEvaluator extends SwimNodeEvaluator {

    private boolean checkLand = false;

    public DogSwimNodeEvaluator() {
        super(false);
    }

    @Override
    public int getNeighbors(Node[] buffer, Node currentNode) {
        this.checkLand = (currentNode.type == PathType.WATER);
        int ret = super.getNeighbors(buffer, currentNode);
        this.checkLand = false;
        return ret;
    }

    @Override
    @Nullable
    protected Node findAcceptedNode(int p_263032_, int p_263066_, int p_263105_) {
        Node node = null;
        var blockpathtypes = this.getCachedBlockType(p_263032_, p_263066_, p_263105_);
        if (blockpathtypes == PathType.WATER) {
            float f = this.mob.getPathfindingMalus(blockpathtypes);
            if (f >= 0.0F) {
                node = this.getNode(p_263032_, p_263066_, p_263105_);
                node.type = blockpathtypes;
                node.costMalus = Math.max(node.costMalus, f);
                if (this.mob.level().getFluidState(new BlockPos(p_263032_, p_263066_, p_263105_)).isEmpty()) {
                    node.costMalus += 8.0F;
                }
            }
        } else if (checkLand && blockpathtypes == PathType.WALKABLE) {
            float f = this.mob.getPathfindingMalus(blockpathtypes);
            if (f >= 0.0F) {
                node = this.getNode(p_263032_, p_263066_ + 1, p_263105_);
                node.type = blockpathtypes;
                node.costMalus = Math.max(node.costMalus, f);
            }
        }

        return node;
    }

    @Override
    public PathType getPathTypeOfMob(PathfindingContext context, int x, int y, int z, Mob dog) {
        /*
         * TODO Absoluteness of dog bbW.
         * Currently this implementation relies on the fact that currently a dog's bbWidth never
         * surpass one block. The same also applies to DogPathNavigation maxDistance to advance.
         */
        var level = dog.level();
        
        var checking_pos = new BlockPos.MutableBlockPos();
        for (int i = y; i < y + this.entityHeight; ++i) {
            checking_pos.set(x, i, z);
            var state = level.getBlockState(checking_pos);
            var fluid = state.getFluidState();

            boolean checkedWalkable = 
                checkLand && i == y
                && checkLand(checking_pos, state, level, dog);
            if (checkedWalkable)
                return PathType.WALKABLE;

            if (!fluid.is(FluidTags.WATER))
                return PathType.BLOCKED;
        }

        return PathType.WATER;

        // for(int i = x; i < x + this.entityWidth; ++i) {
        //    for(int j = y; j < y + this.entityHeight; ++j) {
        //       for(int k = z; k < z + this.entityDepth; ++k) {
        //          var blockstate = level.getBlockState(checking_pos.set(i, j, k));
        //          var fluidstate = blockstate.getFluidState();
        //          if (checkLand && !blockstate.isPathfindable(level, checking_pos, PathComputationType.LAND)) {
        //             if (level.getBlockState(checking_pos.above()).isAir()) {
        //                 var walkType = WalkNodeEvaluator.getPathTypeStatic(level, checking_pos.above().mutable());
        //                 if (walkType == BlockPathTypes.WALKABLE || walkType == BlockPathTypes.WATER_BORDER) {
        //                     return BlockPathTypes.WALKABLE;
        //                 }
        //             }
        //          }
  
        //          if (!fluidstate.is(FluidTags.WATER)) {
        //             return BlockPathTypes.BLOCKED;
        //          }
        //       }
        //    }
        // }
  
        // BlockState blockstate1 = level.getBlockState(checking_pos);
        // return blockstate1.isPathfindable(level, checking_pos, PathComputationType.WATER) ? BlockPathTypes.WATER : BlockPathTypes.BLOCKED;
    }

    private boolean checkLand(BlockPos currentPos, BlockState currenState, BlockGetter level, Mob dog) {
        if (currenState.isPathfindable(PathComputationType.LAND))
            return false;
        if (!level.getBlockState(currentPos.above()).isAir())
            return false;
        var walkType = WalkNodeEvaluator.getPathTypeStatic(dog, currentPos.above().mutable());
        return walkType == PathType.WATER_BORDER
            || walkType == PathType.WALKABLE;
    }
    
}
