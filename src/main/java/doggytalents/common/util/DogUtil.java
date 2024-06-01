package doggytalents.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import doggytalents.api.registry.Talent;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.storage.DogLocationData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.util.CachedSearchUtil.CachedSearchUtil;
import doggytalents.common.util.dogpromise.DogPromiseManager;
import doggytalents.common.util.dogpromise.promise.DogDistantTeleportToBedPromise;
import doggytalents.common.util.dogpromise.promise.DogDistantTeleportToOwnerCrossDimensionPromise;
import doggytalents.common.util.dogpromise.promise.DogDistantTeleportToOwnerPromise;
import net.minecraft.SharedConstants;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Items to utilize when dealing with various doggy stuffs
 * @author DashieDev
 */
public class DogUtil {


    // /**
    //  * Dog teleport with considerations from owner and always success when there is available block :
    //  * <p><b>1.</b> Chose the best block according to {@link DogUtil#chooseSafePosNearOwner} .</p>
    //  * <p><b>2.</b> Set dog fall distance to 0</p>
    //  * <p><b>3.</b> Teleport</p>
    //  * 
    //  * @param dog The Dog who will teleport
    //  * @param radius Radius of the area to search for block to teleport
    //  * @return true if teleport is success.
    //  */
    // public static boolean searchAndTeleportToOwner(Dog dog, int radius) {
    //     var target = chooseSafePosNearOwner(dog, radius);
        
    //     teleportInternal(dog, target);
        
    //     return true;
    // }

    /**
     * This is a search for Tp Pos procedure which is heavily optimized using Dynamic Programming
     * This is going to use lazy search by first getting every single block type on its own without
     * regards to surroundings and store the result into a pool, and every further calculation 
     * (like Danger block, Collide with owner, Collide with block ...) will be calculated only
     * in that pool without further getChunk, which take a lot of time somehow. 
     * 
     * @param dog The Dog who will teleport
     * @param radius Radius of the area to search for block to teleport
     * @return true if teleport is success.
     */
    public static boolean dynamicSearchAndTeleportToOwnwer(Dog dog, LivingEntity owner, int radius) {
        
        BlockPos target;
        if (owner.isSprinting() || dog.isMiningCautious()) {
            target = CachedSearchUtil
                .getRandomSafePosUsingPoolExcludeInfrontOfOwner(dog, owner, owner.blockPosition(), radius, 1);
        } else {
            target = CachedSearchUtil
                .getRandomSafePosUsingPool(dog, owner.blockPosition(), radius, 1);
        }
   
        if (target == null) {
            return false;
        }
        teleportInternal(dog, target);
        
        return true;
    }

    public static boolean dynamicSearchAndTeleportToOwnwerInBatch(Level level, List<Dog> dogs, LivingEntity owner, int radius) {
        
        if (dogs.isEmpty()) return false;

        List<BlockPos> safePosList;
        if (owner.isSprinting()) {
            safePosList = CachedSearchUtil
                .getAllSafePosUsingPoolExcludeInfrontOfOwner(level, dogs, owner, owner.blockPosition(), radius, 1);
        } else {
            safePosList = CachedSearchUtil
                .getAllSafePosUsingPool(level, dogs, owner.blockPosition(), radius, 1);
        }

        if (safePosList.isEmpty()) return false;
   
        for (var dog : dogs) {
            int r_indx = dog.getRandom().nextInt(safePosList.size());
            teleportInternal(dog, safePosList.get(r_indx));
        }

        return true;
    }

    public static boolean dynamicSearchAndTeleportToOwnwerInBatchMaxDensity(Level level, List<Dog> dogs, LivingEntity owner, int radius, int max_density) {
        
        if (dogs.isEmpty()) return false;

        List<BlockPos> safePosList;
        if (owner.isSprinting()) {
            safePosList = CachedSearchUtil
                .getAllSafePosUsingPoolExcludeInfrontOfOwner(level, dogs, owner, owner.blockPosition(), radius, 1);
        } else {
            safePosList = CachedSearchUtil
                .getAllSafePosUsingPool(level, dogs, owner.blockPosition(), radius, 1);
        }

        if (safePosList.isEmpty()) return false;

        var densityMap = new ArrayList<Integer>(safePosList.size());
        for (int i = 0; i < safePosList.size(); ++i) {
            densityMap.add(0); 
        }

        var sorted_dogs = new ArrayList<>(dogs);
        Collections.sort(sorted_dogs, new EntityUtil.Sorter(owner));
   
        for (var dog : sorted_dogs) {
            if (safePosList.isEmpty()) break;
            int r_indx = dog.getRandom().nextInt(safePosList.size());
            teleportInternal(dog, safePosList.get(r_indx));
            int density_count = densityMap.get(r_indx) + 1;
            if (density_count >= max_density) {
                densityMap.remove(r_indx);
                safePosList.remove(r_indx);
            } else {
                densityMap.set(r_indx, density_count);
            }
        }
        
        return true;
    }

    /**
     * This is a search for Tp Pos procedure which is heavily optimized using Dynamic Programming
     * This is going to use lazy search by first getting every single block type on its own without
     * regards to surroundings and store the result into a pool, and every further calculation 
     * (like Danger block, Collide with owner, Collide with block ...) will be calculated only
     * in that pool without further getChunk, which take a lot of time somehow. 
     * 
     * @param dog The Dog who will teleport
     * @param radius Radius of the area to search for block to teleport
     * @return true if teleport is success.
     */
    public static boolean dynamicSearchAndTeleportToBlockPos(Dog dog, BlockPos pos, int radius) {
    
        var target = CachedSearchUtil.getRandomSafePosUsingPool(
            dog, pos, radius, 1
        );
   
        if (target == null) {
            return false;
        }
        teleportInternal(dog, target);
        
        return true;
    }


    // /**
    //  * This function will search for all of the eligible position into a list,
    //  * then pick a random item and return;
    //  * 
    //  * @param dog The Dog
    //  * @param radius Radius of the area to search for best pos
    //  * @return the best block or null if no block is found.
    //  */
    // public static BlockPos chooseSafePosNearOwner(Dog dog, LivingEntity owner, int radius) {

    //     var owner_b0 = dog.getOwner().blockPosition();

    //     // Get BlockPos Arround the owner
    //     var blockposes = BlockPos.betweenClosed(
    //             owner_b0.offset(-radius, -1, -radius),
    //             owner_b0.offset(radius, 1, radius));

    //     // Filter out the safe pos
    //     var safeblockposes = new ArrayList<BlockPos>();
    //     for (var i : blockposes) {

    //         if (wantToTeleportToThePosition(dog, owner, i)) {
    //             safeblockposes.add(new BlockPos(i.getX(), i.getY(), i.getZ()));
    //         }         

    //     }

    //     // If no safe pos then return null
    //     if (safeblockposes.size() <= 0) {
    //         return null;
    //     }

    //     //Pick a random block from the final list and teleport
    //     BlockPos finaltp = safeblockposes.get(EntityUtil.getRandomNumber(dog, 0, safeblockposes.size() - 1));
    //     return finaltp;
    // }

    /**
     * Dog will pick randomly 10 block around the owner per call to this function 
     * and teleport to one of them if it is eligible. This is the default behaviour,
     * and may require several calls to success even if there is a spot.
     * 
     * @param dog The Dog
     * @param radius Radius of the area to guess
     * @return true if success
     */
    public static boolean guessAndTryToTeleportToOwner(Dog dog, LivingEntity owner, int radius) {
        var owner_b0 = owner.blockPosition();

        for (int i = 0; i < 10; ++i) {
            int randX = owner_b0.getX() + EntityUtil.getRandomNumber(dog, -radius, radius);
            int randY= owner_b0.getY() + EntityUtil.getRandomNumber(dog, -1, 1);
            int randZ = owner_b0.getZ() + EntityUtil.getRandomNumber(dog, -radius, radius);
            var b0 = new BlockPos(randX, randY, randZ);

            if (wantToTeleportToThePosition(dog, owner, b0)) {
                teleportInternal(dog, b0);
                return true;
            }
        }

        return false;
    }

    public static boolean guessAndTryToTeleportToBlockPos(Dog dog, BlockPos pos, int radius) {

        for (int i = 0; i < 10; ++i) {
            int randX = pos.getX() + EntityUtil.getRandomNumber(dog, -radius, radius);
            int randY= pos.getY() + EntityUtil.getRandomNumber(dog, -1, 1);
            int randZ = pos.getZ() + EntityUtil.getRandomNumber(dog, -radius, radius);
            var b0 = new BlockPos(randX, randY, randZ);

            if (isTeleportSafeBlock(dog, b0)) {
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
    public static boolean wantToTeleportToThePosition(Dog dog, LivingEntity owner, BlockPos pos) {
        var owner_b0 = owner.blockPosition();
        boolean flag = 
                // Not too close to owner
                !(
                    Mth.abs(owner_b0.getX() - pos.getX()) < 2
                    && Mth.abs(owner_b0.getZ() - pos.getZ()) < 2 
                ) 

                // safe?
                && isTeleportSafeBlock(dog, pos) 

                // Can see owner at that pos
                // && hasLineOfSightToOwnerAtPos(dog, pos)

                && !(
                    (owner.isSprinting() || dog.isMiningCautious())
                    && posWillCollideWithOwnerMovingForward(dog, owner, pos)
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
    public static boolean posWillCollideWithOwnerMovingForward(Dog dog, LivingEntity owner, BlockPos pos) {
        final var DISTANCE_AWAY = 1.5;

        var ownerPos = owner.position();

        //get owner position and target position
        var ownerPos2d = new Vec3(ownerPos.x(), 0, ownerPos.z()); 
        var targetPos2d = new Vec3(pos.getX() + 0.5, 0, pos.getZ() + 0.5);
        
        //get owner look vector
        var a1 = owner.getYHeadRot();
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
    public static boolean hasLineOfSightToOwnerAtPos(Dog dog, LivingEntity owner, BlockPos pos) {
        var pos1 = new Vec3(pos.getX() + 0.5, pos.getY() + owner.getEyeHeight(), pos.getZ() + 0.5);
        var pos2 = new Vec3(owner.getX(), 
            owner.getY() + owner.getEyeHeight(), owner.getZ());
        if (pos1.distanceTo(pos2) > 128.0D) {
            return false;
        } else {
            return dog.level.clip(new ClipContext(pos1, pos2, ClipContext.Block.COLLIDER, 
                ClipContext.Fluid.NONE, dog)).getType() == HitResult.Type.MISS;
        }
    }


    //check is Walakable Block according to the IDogAlteration
    //Allow dog to teleportToLeaves, there is no reason to not to consider the existance of the push a.i
    //And height danger exist everywhere not just leaves
    public static boolean isTeleportSafeBlock(Dog dog, BlockPos pos) {
        var pathnodetype = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level, pos.mutable());
        boolean alterationWalkable = false;
        for (var x : dog.getAlterations()) {
            var type_res = x.inferType(dog, pathnodetype);
            if (type_res.getResult().shouldSwing()
                && type_res.getObject() == BlockPathTypes.WALKABLE) {
                alterationWalkable = true;
                break;
            }
        }
        
        if (dog.canDogFly() && pathnodetype == BlockPathTypes.OPEN)
            alterationWalkable = true;
        if (dog.fireImmune() && pathnodetype == BlockPathTypes.OPEN
            && dog.level().getFluidState(pos.below()).is(FluidTags.LAVA))
            alterationWalkable = true;
        if (pathnodetype != BlockPathTypes.WALKABLE && !alterationWalkable) {
            return false;
        } else {
            var blockpos = pos.subtract(dog.blockPosition());
            return dog.level.noCollision(dog, dog.getBoundingBox().move(blockpos));
        }
    }

    public static boolean isTeleportSafeBlockMidAir(Dog dog, BlockPos pos) {
        var pathnodetype = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level, pos.mutable());
        if (pathnodetype != BlockPathTypes.OPEN) {
            return false;
        } else {
            var blockpos = pos.subtract(dog.blockPosition());
            return dog.level.noCollision(dog, dog.getBoundingBox().move(blockpos));
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

    /**
     * This check if the dog can get to the target via the given path.
     * 
     * @param dog The Dog
     * @param path The Path the Dog is following
     * @param pos The target the Dog wants to go to
     * @param dY The maximum amount of blocks can the y coords between the target and the actual path destination diffrentiate
     * while still being eligible
     * @return
     * 
     */
    public static boolean canPathReachTargetBlock(Dog dog, @Nonnull Path path, BlockPos pos, int maxDY_up, int maxDY_down) {
        var endNode = path.getEndNode();
        if (endNode == null) return false;
        var dx = endNode.x - pos.getX();
        var dz = endNode.z - pos.getZ();
        var d_sqr = dx*dx + dz*dz;
        var dy = pos.getY() - endNode.y;
        return d_sqr <= 1 && ( -maxDY_down <= dy && dy <= maxDY_up);
    }

    public static boolean pathObstructOwnerMining(Dog dog) {

        var n = dog.getNavigation(); 
        var p = n.getPath();
        if (p == null || p.getNodeCount() <= 0) return false;

        var owner = dog.getOwner();
        if (owner == null) return false;
        
        //Iterate through the next 5 blocks of the path and check if obstruct owner.
        int i0 = p.getNextNodeIndex();
        int i_end = Mth.clamp(i0+5, i0, p.getNodeCount());
        for (int i = i0; i < i_end; ++i) {

            boolean flag = 
                DogUtil.posWillCollideWithOwnerMovingForward(dog, owner, p.getNodePos(i));

            if (flag) {
                return true;
            }

        }

        return false;
    }

    /**
     * move to a position if can reach it, otherwise execute orElse
     * 
     * @param dog The dog
     * @param pos The pos
     * @param speedModifier speed modifier
     * @param dY The maximum amount of blocks can the y coords between the target and the actual path destination diffrentiate
     * while still being eligible
     * @param orElse function to execute when can't reach
     * @return true if dog can reach.
     */
    public static boolean moveToIfReachOrElse(Dog dog, BlockPos pos, double speedModifier, 
        int maxDY_up, int maxDY_down, Consumer<Dog> orElse) {
        var p = dog.getNavigation().createPath(pos, 1);
        if (p == null) {
            orElse.accept(dog);
            return false;
        }

        if (DogUtil.canPathReachTargetBlock(dog, p, pos, maxDY_up, maxDY_down)) {
            dog.getNavigation().moveTo(p, speedModifier);
            return true;
        } else {
            orElse.accept(dog);
            return false;
        }
    }
    
    /**
     * Move to a position. If too far away, teleport, 
     * else if cannot reach, execute orElse. 
     * 
     * @param dog The dog
     * @param entity The entity to move to
     * @param speedModifier speed modifier
     * @param distanceToTeleportSqr when the dog is further or equal to this away, teleport
     * @param continueToMoveWhenTryTp if enable, the dog will continue to path find to owner at this point.
     * @param forceTeleport Option to enable force teleport, which make the dog explicitly search for position
     * dog finally stop pathfinding at this point.
     * @param distanceToForceTeleportSqr Distance to make the dog force teleport
     * @param dY The maximum amount of blocks can the y coords between the target and the actual path destination diffrentiate
     * while still being eligible
     */
    public static void moveToOwnerOrTeleportIfFarAway(Dog dog, LivingEntity owner, double speedModifier,
        double distanceToTeleportSqr, boolean continueToMoveWhenTryTp, boolean forceTeleport, 
        double distanceToForceTeleportSqr, int dY) {
        if (owner == null) return;
        var distance = dog.distanceToSqr(owner);
        if (!dog.isLeashed() && !dog.isPassenger()) {
            if (distance >= distanceToForceTeleportSqr) {
                if (forceTeleport) DogUtil.dynamicSearchAndTeleportToOwnwer(dog, owner, 4);
                else DogUtil.guessAndTryToTeleportToOwner(dog, owner, 4);
            } else {
                if (distance >= distanceToTeleportSqr) {
                    DogUtil.guessAndTryToTeleportToOwner(dog, owner, 4);
                    if (continueToMoveWhenTryTp) {
                        dog.getNavigation().moveTo(owner, speedModifier);
                    }
                } else {
                    dog.getNavigation().moveTo(owner, speedModifier);
                }
            }
        }
    }

    public static List<Dog> getOtherIncapacitatedDogNearby(Dog dog) {
        int SEARCH_RADIUS = 12;
        var l = dog.level.getEntitiesOfClass(
            Dog.class, 
            dog.getBoundingBox().inflate(SEARCH_RADIUS, 2, SEARCH_RADIUS),
            d -> d.isDefeated());
        return l;
    }

    public static void attemptToTeleportDogNearbyOrSendPromise(@Nonnull UUID dogUUID, @Nonnull ServerPlayer owner) {
        if (owner.level instanceof ServerLevel sLevel) {
            var e = sLevel.getEntity(dogUUID);
            if (e != null && e instanceof Dog d) {
                dynamicSearchAndTeleportToOwnwer(d, owner, 4);
            } else {
                DogLocationStorage storage = DogLocationStorage.get(sLevel);
                DogLocationData data = storage.getData(dogUUID);
                if (data == null) return;
                var dataPos = data.getPos();
                if (dataPos == null) return;
                var pos = new BlockPos(Mth.floor(dataPos.x), Mth.floor(dataPos.y), Mth.floor(dataPos.z));
                var dataDim = data.getDimension();
                if (dataDim == null) {
                    dataDim = Level.OVERWORLD;
                }
                var server = owner.getServer();
                if (server == null)
                    return;
                var dogLevel = server.getLevel(dataDim);
                if (dogLevel == null)
                    return;
                if (dogLevel != sLevel && ConfigHandler.SERVER.CONDUCTING_BONE_CROSS_ORIGIN.get()) {
                    DogPromiseManager.addPromiseWithOwner(
                        new DogDistantTeleportToOwnerCrossDimensionPromise(dogUUID, owner, pos, dogLevel, sLevel),
                        owner
                    );
                } else {
                    DogPromiseManager.addPromiseWithOwner(
                        new DogDistantTeleportToOwnerPromise(dogUUID, owner, pos),
                        owner
                    );
                }

                
            }
        }
    }

    public static void attemptToTeleportDogToBedOrSendPromise(@Nonnull Dog dog) {
        var bedPos = dog.getBedPos();
        if (!bedPos.isPresent()) return;
        var chunkpos = new ChunkPos(bedPos.get());
        var owner = dog.getOwner();
        if (dog.level.hasChunk(chunkpos.x, chunkpos.z)) {
            if (isTeleportSafeBlockMidAir(dog, bedPos.get().above())) {
                teleportInternal(dog, bedPos.get().above());
                dog.setOrderedToSit(true);
            }
        } else if (owner != null && owner instanceof ServerPlayer sP) {
            DogPromiseManager.addPromiseWithOwner(
                new DogDistantTeleportToBedPromise(dog),
                sP
            );
        }
    }

    public static Dog findBiggestDog(List<Dog> dogs) {
        if (dogs.isEmpty()) return null;
        var biggest_dog = dogs.get(0);
        for (var dog : dogs) {
            if (dog.getDogSize().getId() > biggest_dog.getDogSize().getId()) {
                biggest_dog = dog;
            }
        }
        return biggest_dog;
    }

    public static boolean isSafeBlock() {
        return false;
    }

    private static void teleportInternal(Dog dog, BlockPos target) {
        dog.fallDistance = 0;
        dog.moveTo(target.getX() + 0.5F, target.getY(), target.getZ() + 0.5F, dog.getYRot(), dog.getXRot());
        dog.getNavigation().stop();
        dog.breakMoveControl();
    }

    public static boolean playerCanTrainTalent(Player player, Talent talent) {
        if (player != null && player.hasPermissions(4))
            return true;
        return (ConfigHandler.TALENT.getFlag(talent));
    }

    private static List<Character> INVALID_NAME_CHARS = List.of('\"');
    public static String checkAndCorrectInvalidName(String name) {
        if (name == null)
            return name;
        String str = name;
        if (str.isEmpty())
            return name;
        
        str = checkInvalidChar(str);
        str = stripIfNeccessary(str);

        return str;
    }

    private static String stripIfNeccessary(String x) {
        var str = x;
        if (str == null) str = "";
        if (str.isEmpty())
            return x;
        if (str.charAt(0) == ' ' || str.charAt(str.length() - 1) == ' ') {
            str = str.strip();
            return str;
        }
        return x;
    }

    private static String checkInvalidChar(String x) {
        var str = x;
        if (str == null) str = "";
        if (str.isEmpty())
            return x;
        boolean isInvalidStr = false;
        for (int i = 0; i < str.length(); ++i) {
            var c = str.charAt(i);
            if (!isValidChar(c)) {
                isInvalidStr = true;
                break;
            }
        }

        if (!isInvalidStr)
            return x;
        
        var builder = new StringBuilder("");
        for (int i = 0; i < str.length(); ++i) {
            var c = str.charAt(i);
            if (isValidChar(c)) {
                builder.append(c);
            }
        }
        str = builder.toString();

        return str;
    } 

    private static boolean isValidChar(Character x) {
        return !INVALID_NAME_CHARS.contains(x) && SharedConstants.isAllowedChatCharacter(x);
    }

    public static boolean checkIfOwnerIsLooking(Dog dog, LivingEntity owner) {
        var v_look_owner = owner.getViewVector(1);
        var v_look_wanted = dog.getEyePosition()
            .subtract(owner.getEyePosition())
            .normalize();
        var dot = v_look_wanted.dot(v_look_owner);
        return dot > 0.7;
    }

    // public static boolean isDangerPathType(BlockPathTypes pathType) {
    //     switch (pathType) {
    //     case POWDER_SNOW:
    //     case DANGER_POWDER_SNOW:
    //     case LAVA:
    //     case DANGER_FIRE:
    //     case DAMAGE_FIRE:
    //     case DANGER_OTHER:
    //     case DAMAGE_OTHER:
    //     case DAMAGE_CAUTIOUS:
    //     //case DANGER_TRAPDOOR:
    //     case TRAPDOOR:
    //         return true;
    //     default:
    //         return false;
    //     }
    // }

    // public static boolean isScute(ItemStack stack) {
    //     return ArmorMaterials.ARMADILLO.value().repairIngredient()
    //         .get().test(stack);
    // }

    public static int getWolfArmorRepairVal(ItemStack stack) {
        float repair_val = stack.getMaxDamage() * 0.125f;
        return (int) repair_val;
    }

    public static void stopAndForceLook(Dog dog, Vec3 target) {
        dog.setSpeed(0);
        dog.setZza(0);
        dog.lookAt(Anchor.EYES, target);
    }

    // public static Optional<Holder<WolfVariant>> getWolfVariantHolderIfLoaded(
    //         HolderLookup.Provider prov, ResourceKey<WolfVariant> key) {
    //     var wolf_variant_reg = prov.lookupOrThrow(Registries.WOLF_VARIANT);
    //     var holder_optional = wolf_variant_reg.get(key);
    //     if (holder_optional.isEmpty())
    //         return Optional.empty();
        
    //     var holder = holder_optional.get();
    //     return Optional.of(holder);
    // }

}
