package doggytalents.common.entity.ai.nav;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DogNodeEvaluator extends WalkNodeEvaluator {

    private final Supplier<Dog> dogGetter;

    public DogNodeEvaluator(Supplier<Dog> dogGetter) {
        this.dogGetter = dogGetter;
    }

    @Override
    protected double getFloorLevel(BlockPos pos) {
        var dog = dogGetter.get();
        if (dog.fireImmune()) {
            if (dog.level().getFluidState(pos).is(FluidTags.LAVA)) {
                return pos.getY();
            }
        }
        return super.getFloorLevel(pos);
    }

    @Override
    @Nullable
    protected Node findAcceptedNode(int x, int y, int z, int floorLevel,
            double maxUpStep, Direction dir, PathType centerType) {
        
        if (centerType == PathType.DOOR_WOOD_CLOSED && dogGetter.get().canDogPassGate()) {
            centerType = PathType.WALKABLE;
        }
        return super.findAcceptedNode(x, y, z, floorLevel, maxUpStep, dir, centerType);
    }

    @Override
    public PathType getPathTypeOfMob(PathfindingContext context, int x, int y, int z, Mob mob) {
        var retType =  super.getPathTypeOfMob(context, x, y, z, mob);
        
        var dog = dogGetter.get();
        if (retType == PathType.FENCE && dog.canDogPassGate()) {
            var state = dog.level().getBlockState(new BlockPos(x, y, z));
            if (state.getBlock() instanceof FenceGateBlock) {
                retType = PathType.WALKABLE;
            }  
        }
        if (retType == PathType.DANGER_FIRE && !dog.isInLava()) {
            var check_pos = new BlockPos(x, y - 1, z);
            var state = dog.level().getBlockState(check_pos);
            if (!state.isCollisionShapeFullBlock(dog.level(), check_pos)) {
                retType = PathType.DAMAGE_FIRE;
            }
        }
        return retType;
    }

    @Override
    public Node getBlockedNode(int x, int y, int z) {
        // Override to avoid unecessary overriding and polluting the 
        // Node cache and potentially the building Path with blocked Nodes 
        return null;
    }

    public static PathType dogGetPathTypeFromState(BlockGetter getter, BlockPos pos) {
        return WalkNodeEvaluator.getPathTypeFromState(getter, pos);
    }
}