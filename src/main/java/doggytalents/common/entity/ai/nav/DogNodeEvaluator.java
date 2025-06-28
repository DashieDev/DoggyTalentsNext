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
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
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
            double maxUpStep, Direction dir, BlockPathTypes centerType) {
        
        if (centerType == BlockPathTypes.DOOR_WOOD_CLOSED && dogGetter.get().canDogPassGate()) {
            centerType = BlockPathTypes.WALKABLE;
        }
        return super.findAcceptedNode(x, y, z, floorLevel, maxUpStep, dir, centerType);
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter getter, int x, int y, int z) {
        var retType =  super.getBlockPathType(getter, x, y, z);
        
        var dog = dogGetter.get();
        if (retType == BlockPathTypes.FENCE && dog.canDogPassGate()) {
            var state = dog.level().getBlockState(new BlockPos(x, y, z));
            if (state.getBlock() instanceof FenceGateBlock) {
                retType = BlockPathTypes.WALKABLE;
            }  
        }
        if (retType == BlockPathTypes.DANGER_FIRE && !dog.isInLava()) {
            var check_pos = new BlockPos(x, y - 1, z);
            var state = dog.level().getBlockState(check_pos);
            if (!state.isCollisionShapeFullBlock(dog.level(), check_pos)) {
                retType = BlockPathTypes.DAMAGE_FIRE;
            }
        }
        return retType;
    }

    @Override
    public Node getBlockedNode(int x, int y, int z) {
        // Override to avoid unecessary overriding and polluting the 
        // Node cache and potentially the building Path with blocked Nodes 
        
        // We return a new Node entirely solely for the Diagonal Check 
        // and overrided the isNeighborValid check to ensure that this never
        // get polluted in the pathfinding context.
        var ret = new Node(x, y, z);
        ret.type = PathType.OPEN; // we don't want OPEN as a valid neighbor anyawys.  
        ret.costMalus = -1;
        return ret;
    }

    @Override
    protected boolean isNeighborValid(@Nullable Node neighbor, Node center) {
        if (neighbor != null && neighbor.type == PathType.OPEN)
            return false;
        return super.isNeighborValid(neighbor, center);
    }

    public static BlockPathTypes dogGetPathTypeFromState(BlockGetter getter, BlockPos pos) {
        return WalkNodeEvaluator.getBlockPathTypeRaw(getter, pos);
    }
}