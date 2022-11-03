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

/**
 * Items to utilize when dealing with various doggy stuffs
 * NOTE: Functions here will not check if the thing involved is null or not
 * ex: dog.getOwner(), that must be already done outside of here.
 * @author DashieDev
 */
public class DogUtil {


    /**
     * Dog teleport with considerations from owner and always success when there is available block :
     * <p>1. Chose the best block according to {@link DogUtil#chooseSafePosNearOwner} .</p>
     * <p>2. Set dog fall distance to 0</p>
     * <p>3. Teleport</p>
     * 
     * @param dog The Dog who will teleport
     * @param radius Radius of the area to search for block to teleport
     * @return true if teleport is success.
     */
    public static boolean searchAndTeleportToOwner(Dog dog, int radius) {
        var target = chooseSafePosNearOwner(dog, radius);
        if (target == null) {
            return false;
        }
        teleportInternal(dog, target);
        
        return true;
    }


    /**
     * This function will search for all of the eligible position into a list,
     * then pick a random item and return;
     * 
     * @param dog The Dog
     * @param radius Radius of the area to search for best pos
     * @return the best block or null if no block is found.
     */
    public static BlockPos chooseSafePosNearOwner(Dog dog, int radius) {

        var owner_b0 = dog.getOwner().blockPosition();

        // Get BlockPos Arround the owner
        var blockposes = BlockPos.betweenClosed(
                owner_b0.offset(-radius, -1, -radius),
                owner_b0.offset(radius, 1, radius));

        // Filter out the safe pos
        var safeblockposes = new ArrayList<BlockPos>();
        for (var i : blockposes) {

            if (wantToTeleportToThePosition(dog, i)) {
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
     * Dog will pick randomly 10 block around the owner per call to this function 
     * and teleport to one of them if it is eligible. This is the default behaviour,
     * and may require several calls to success even if there is a spot.
     * 
     * @param dog The Dog
     * @param radius Radius of the area to guess
     * @return true if success
     */
    public static boolean guessAndTryToTeleportToOwner(Dog dog, int radius) {
        var owner_b0 = dog.getOwner().blockPosition();

        for (int i = 0; i < 10; ++i) {
            int randX = owner_b0.getX() + EntityUtil.getRandomNumber(dog, -radius, radius);
            int randY= owner_b0.getY() + EntityUtil.getRandomNumber(dog, -1, 1);
            int randZ = owner_b0.getZ() + EntityUtil.getRandomNumber(dog, -radius, radius);
            var b0 = new BlockPos(randX, randY, randZ);

            if (wantToTeleportToThePosition(dog, b0)) {
                teleportInternal(dog, b0);
                return true;
            }
        }

        return false;
    }

    
    /**
     * this function returns whether the position is eligible for the dog or not
     * a position is eligible if : 
     * <p><b>1.</b> The position is not closer than 2 blocks from the owner</p>
     * <p><b>2.</b> The block is safe for the dog to teleport, a safe block is defined by 
     * the function {@link DogUtil#isTeleportSafeBlock}</p>
     * <p><b>3.</b> If the owner is sprinting then the dog must not obstruct the owner's
     * sprint path, check based on {@link DogUtil#posWillCollideWithOwnerMovingForward}</p>
     * 
     * @param dog The dog
     * @param pos The position
     */
    public static boolean wantToTeleportToThePosition(Dog dog, BlockPos pos) {
        var owner_b0 = dog.getOwner().blockPosition();
        boolean flag = 
                // Not too close to owner
                !(
                    Mth.abs(owner_b0.getX() - pos.getX()) < 2
                    && Mth.abs(owner_b0.getZ() - pos.getZ()) < 2 
                ) 

                // safe?
                && isTeleportSafeBlock(dog, pos, false) 

                // Can see owner at that pos
                // && hasLineOfSightToOwnerAtPos(dog, pos)

                // if Owner is sprinting then don't obstruct the owner sprint path
                && !(
                    dog.getOwner().isSprinting()
                    && posWillCollideWithOwnerMovingForward(dog, pos)
                ); 
        return flag;
    }


    /**
     * this function will check if the position the dog is trying to teleport
     * is not going to obstruct the owner when he is moving towards a direction by teleporting
     * in front of the ray that the owner is moving towards.
     * dog.getOwner() must not be null before the call or else this function will crash the game.
     * 
     * @param dog The dog who is teleporting
     * @param pos The position to check
     */
    public static boolean posWillCollideWithOwnerMovingForward(Dog dog, BlockPos pos) {
        final var DISTANCE_AWAY = 1.5;

        var ownerPos = dog.getOwner().position();

        //get owner position and target position
        var ownerPos2d = new Vec3(ownerPos.x(), 0, ownerPos.z()); 
        var targetPos2d = new Vec3(pos.getX() + 0.5, 0, pos.getZ() + 0.5);
        
        //get owner look vector
        var a1 = dog.getOwner().getYHeadRot();
        var dx1 = -Mth.sin(a1*Mth.DEG_TO_RAD);
        var dz1 = Mth.cos(a1*Mth.DEG_TO_RAD);
        var ownerLookUnitVector = new Vec3(dx1, 0, dz1);
        
        //Check according to the below function
        var x = distanceFromPointToLineOfUnitVector2DSqr(targetPos2d, ownerPos2d, ownerLookUnitVector);

        if (x < 0 || x > DISTANCE_AWAY) {
            return false;
        }
        return true;
    }


    /**
     * Check if the dog can see the owner at that position
     * 
     * @param dog The Dog
     * @param pos Block to consider
     */
    public static boolean hasLineOfSightToOwnerAtPos(Dog dog, BlockPos pos) {
        Vec3 pos1 = new Vec3(pos.getX() + 0.5, pos.getY() + dog.getOwner().getEyeHeight(), pos.getZ() + 0.5);
        Vec3 pos2 = new Vec3(dog.getOwner().getX(), 
            dog.getOwner().getY() + dog.getOwner().getEyeHeight(), dog.getOwner().getZ());
        if (pos1.distanceTo(pos2) > 128.0D) {
            return false;
        } else {
            return dog.level.clip(new ClipContext(pos1, pos2, ClipContext.Block.COLLIDER, 
                ClipContext.Fluid.NONE, dog)).getType() == HitResult.Type.MISS;
        }
    }


    //TODO will check is Safe Block according to the IDogAlteration
    public static boolean isTeleportSafeBlock(Dog dog, BlockPos pos, boolean teleportToLeaves) {
        BlockPathTypes pathnodetype = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level, pos.mutable());
        if (pathnodetype != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = dog.level.getBlockState(pos.below());
            if (!teleportToLeaves && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = pos.subtract(dog.blockPosition());
                return dog.level.noCollision(dog, dog.getBoundingBox().move(blockpos));
            }
        }
    }


    /**
     * <p>Let the context be in a 2d Cartesian system</p>
     * <p>Let A be a point</p>
     * <p>Let B be another point</p>
     * <p>Let v be a unit vector</p>
     * <p>Let d be a line from B and v</p>
     * <p>Let u be vector from B to A</p>
     * If Angle(u, v) < 90 this function will return the squared distance between A and d
     * else this function will return -1.
     * 
     * <p>NOTE : Every parameter here accept Vec3, but all have to have exactly one component being zeroed
     * and it should be the same component across, like all Zs zero or else the function wont 
     * work</p>
     * 
     * @author DashieDev
     * @param A 
     * @param B
     * @param v have to be a unit vector
     * @return the distance squared, -1 if Angle(u, v) > 90 
     */
    public static double distanceFromPointToLineOfUnitVector2DSqr(Vec3 A, Vec3 B, Vec3 v) {
        var u = A.add(B.scale(-1));
        var dot_u_v = u.dot(v);
        if (dot_u_v < 0) return -1;
        var dis_sqr = u.lengthSqr() - (dot_u_v*dot_u_v);
        return dis_sqr;
    }


    public static boolean isSafeBlock() {
        return false;
    }

    private static void teleportInternal(Dog dog, BlockPos target) {
        dog.fallDistance = 0;
        dog.moveTo(target.getX() + 0.5F, target.getY(), target.getZ() + 0.5F, dog.getYRot(), dog.getXRot());
        dog.getNavigation().stop();
    }


}
