package doggytalents.common.util;

import java.util.ArrayList;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DogUtil {
    /**
     * Dog teleport with considerations from owner and always success when there is available block :
     * <p>1. Chose the best block according to {@link chooseBestPosNearOwner} .</p>
     * <p>2. Set dog fall distance to 0</p>
     * <p>3. Teleport</p>
     * 
     * @param dog The Dog who will teleport
     * @param radius Radius of the area to search for block to teleport
     * @return true if teleport is success.
     */
    public static boolean searchAndTeleportToOwner(Dog dog, int radius) {
        var target = chooseBestPosNearOwner(dog, radius);
        if (target == null) {
            return false;
        }
        dog.fallDistance = 0;
        dog.moveTo(target.getX() + 0.5F, target.getY(), target.getZ() + 0.5F, dog.getYRot(), dog.getXRot());
        dog.getNavigation().stop();
        return true;
    }

    /**
     * This function will search for the best pos near the owner for the dog to teleport to.
     * Searching procedure :
     * <p>1. Get all of the pos in the radius</p>
     * <p>2. Filter out the unsafe block according to {@link isTeleportSafeBlock} and the block
     * which the owner cannot see</p>
     * <p>3. If there are blocks, randomly pick one and return else return null</p>
     * 
     * @param dog The Dog
     * @param radius Radius of the area to search for best pos
     * @return the best block or null if no block is found.
     */
    public static BlockPos chooseBestPosNearOwner(Dog dog, int radius) {

        var owner_b0 = dog.getOwner().blockPosition();

        // Get BlockPos Arround the owner
        var blockposes = BlockPos.betweenClosed(
                owner_b0.offset(-radius, -1, -radius),
                owner_b0.offset(radius, 1, radius));

        // Filter out the safe pos
        var safeblockposes = new ArrayList<BlockPos>();
        for (var i : blockposes) {
            //Omit blocks which are too close
            if (Mth.abs(owner_b0.getX() - i.getX()) < 2) continue;
            if (Mth.abs(owner_b0.getZ() - i.getZ()) < 2) continue;

            if (isTeleportSafeBlock(dog, i, false) && hasLineOfSightToOwnerAtPos(dog, i)) {
                safeblockposes.add(new BlockPos(i.getX(), i.getY(), i.getZ()));
            }         
        }

        // If no safe pos then return null
        if (safeblockposes.size() <= 0) {
            return null;
        }

        //Pick a random block from the final list and teleport
        BlockPos finaltp = safeblockposes.get(EntityUtil.getRandomNumber(dog, 0, safeblockposes.size() - 1));
        return finaltp;
    }

    /**
     * Check if the dog can see the owner at that position
     * 
     * @param dog The Dog
     * @param pos Block to consider
     */
    public static boolean hasLineOfSightToOwnerAtPos(Dog dog, BlockPos pos) {
        Vec3 pos1 = new Vec3(pos.getX() + 0.5, pos.getY() + dog.getEyeHeight(), pos.getZ() + 0.5);
        Vec3 pos2 = new Vec3(dog.getOwner().getX(), dog.getOwner().getY(), dog.getOwner().getZ());
        if (pos1.distanceTo(pos2) > 128.0D) {
            return false;
        } else {
            return dog.level.clip(new ClipContext(pos1, pos2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, dog))
                    .getType() == HitResult.Type.MISS;
        }
    }

    public static boolean isTeleportSafeBlock(Dog entityIn, BlockPos pos, boolean teleportToLeaves) {
        BlockPathTypes pathnodetype = WalkNodeEvaluator.getBlockPathTypeStatic(entityIn.level, pos.mutable());
        if (pathnodetype != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = entityIn.level.getBlockState(pos.below());
            if (!teleportToLeaves && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = pos.subtract(entityIn.blockPosition());
                return entityIn.level.noCollision(entityIn, entityIn.getBoundingBox().move(blockpos));
            }
        }
    }

    public static boolean isSafeBlock() {
        return false;
    }
}
