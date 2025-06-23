package doggytalents.common.entity.ai.nav;

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

    private final Dog dog;

    public DogNodeEvaluator(Dog dog) {
        this.dog = dog;
    }

    @Override
    protected double getFloorLevel(BlockPos pos) {
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
        if (centerType == PathType.DOOR_WOOD_CLOSED && dog.canDogPassGate()) {
            centerType = PathType.WALKABLE;
        }
        return super.findAcceptedNode(x, y, z, floorLevel, maxUpStep, dir, centerType);
    }

    @Override
    public PathType getPathTypeOfMob(PathfindingContext context, int x, int y, int z, Mob mon) {
        var retType =  super.getPathTypeOfMob(context, x, y, z, dog);
        
        if (retType == PathType.FENCE && dog.canDogPassGate()) {
            var state = dog.level().getBlockState(new BlockPos(x, y, z));
            if (state.getBlock() instanceof FenceGateBlock) {
                retType = PathType.WALKABLE;
            }  
        } 
        return retType;
    }

    public static PathType dogGetPathTypeFromState(BlockGetter getter, BlockPos pos) {
        return WalkNodeEvaluator.getPathTypeFromState(getter, pos);
    }
}