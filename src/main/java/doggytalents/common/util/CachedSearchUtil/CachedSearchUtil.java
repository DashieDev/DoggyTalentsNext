package doggytalents.common.util.CachedSearchUtil;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import static doggytalents.common.util.CachedSearchUtil.PoolValues.*;

import java.util.ArrayList;
import java.util.List;

import doggytalents.ChopinLogger;

public class CachedSearchUtil {

    public static void resetPool(Level level, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    CachedSearchPool.setPoolValue(level, i, j, k, NULL);
                }
            }
        }
    }

    public static void populatePoolRaw(Dog dog, BlockPos targetPos, int radiusXZ, int radiusY) {
        var b0 = targetPos;
        var bMin = b0.offset(-radiusXZ, -radiusY, -radiusXZ);
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    var type = WalkNodeEvaluatorDelegate
                        .getTypeDelegate(dog.level, bMin.offset(i, j, k));
                    byte val = inferType(dog, type);
                    CachedSearchPool.setPoolValue(dog.level, i, j, k, val);
                }
            }
        }
    }

    public static void populatePoolRaw(Level level, List<Dog> dogs, BlockPos targetPos, int radiusXZ, int radiusY) {
        var b0 = targetPos;
        var bMin = b0.offset(-radiusXZ, -radiusY, -radiusXZ);
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    var type = WalkNodeEvaluatorDelegate
                        .getTypeDelegate(level, bMin.offset(i, j, k));
                    byte val = inferType(dogs, type);
                    CachedSearchPool.setPoolValue(level, i, j, k, val);
                }
            }
        }
    }

    public static void populateCollideOwner(LivingEntity owner, int radiusXZ, int radiusY) {
        final var DISTANCE_AWAY = 1.5;

        //get owner look vector
        var a1 = owner.getYHeadRot();
        var dx1 = -Mth.sin(a1*Mth.DEG_TO_RAD);
        var dz1 = Mth.cos(a1*Mth.DEG_TO_RAD);
        var ownerLookUnitVector = new Vec3(dx1, 0, dz1);
    
        var ownerPosRelative2d = new Vec3(
            radiusXZ,0, radiusXZ
        );

        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int k = 0; k <= maxXZ; ++k) {
                var x = 
                    DogUtil.distanceFromPointToLineOfUnitVector2DSqr(
                        new Vec3(i, 0, k)    
                    , ownerPosRelative2d, ownerLookUnitVector);
                if (!(x < 0 || x > DISTANCE_AWAY)) {
                    for (int j = 0; j <= maxY; ++j) {
                        CachedSearchPool.setPoolValue(owner.level, i, j, k, COLLIDE);
                    }
                }
                    
            }
        }
        //Too near owner pos
        int cXZ = radiusXZ;
        for (int i = -1; i <= 1; ++i) {
            for (int k = -1; k <= 1; ++k) {
                for (int j = 0; j <= maxY; ++j) {
                    CachedSearchPool.setPoolValue(owner.level, cXZ + i, j, cXZ + k, COLLIDE);
                } 
            }
        }
    }

    public static void populateBlockCollision(Dog dog, int radiusXZ, int radiusY) {
        int bbWExt = Mth.ceil((dog.getBbWidth() - 1)*0.5);
        int bbHExt = Mth.ceil((dog.getBbHeight() - 1)*0.5);
        if (bbWExt <= 0 && bbHExt <= 0) return; 
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    if (CachedSearchPool.getPoolValue(dog.level, i, j, k) == BLOCKED) {
                        for (int i1 = i-bbWExt; i1 <= i+bbWExt; ++i1 ) {
                            for (int j1 = j-bbHExt; j1 <= j; ++j1) {
                                for (int k1 = k-bbWExt; k1 <= k+bbWExt; ++k1) {
                                    if (CachedSearchPool.getPoolValue(dog.level, i1, j1, k1) != BLOCKED
                                        && CachedSearchPool.getPoolValue(dog.level, i1, j1, k1) != ERR
                                    )
                                    CachedSearchPool.setPoolValue(dog.level, i1, j1, k1, COLLIDE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateBlockCollision(Level level, List<Dog> dogs, int radiusXZ, int radiusY) {

        var dog = DogUtil.findBiggestDog(dogs);

        int bbWExt = Mth.ceil((dog.getBbWidth() - 1)*0.5);
        int bbHExt = Mth.ceil((dog.getBbHeight() - 1)*0.5);
        if (bbWExt <= 0 && bbHExt <= 0) return;
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    if (CachedSearchPool.getPoolValue(level, i, j, k) == BLOCKED) {
                        for (int i1 = i-bbWExt; i1 <= i+bbWExt; ++i1 ) {
                            for (int j1 = j-bbHExt; j1 <= j; ++j1) {
                                for (int k1 = k-bbWExt; k1 <= k+bbWExt; ++k1) {
                                    if (CachedSearchPool.getPoolValue(level, i1, j1, k1) != BLOCKED
                                        && CachedSearchPool.getPoolValue(level, i1, j1, k1) != ERR
                                    )
                                    CachedSearchPool.setPoolValue(level, i1, j1, k1, COLLIDE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateDangerPos(Level level, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    if (CachedSearchPool.getPoolValue(level, i, j, k) == DAMAGE) {
                        for (int i1 = i-1; i1 <= i+1; ++i1 ) {
                            for (int j1 = j-1; j1 <= j+1; ++j1) {
                                for (int k1 = k-1; k1 <= k+1; ++k1) {
                                    if (CachedSearchPool.getPoolValue(level, i1, j1, k1) == OPEN)
                                    CachedSearchPool.setPoolValue(level, i1, j1, k1, DANGER);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateWalkablePos(Level level, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    byte val = CachedSearchPool.getPoolValue(level, i, j, k);
                    byte val_below = CachedSearchPool.getPoolValue(level, i, j-1, k);
                    if (val == OPEN && val_below == BLOCKED) {
                        CachedSearchPool.setPoolValue(level, i, j, k, OK);
                    }
                }
            }
        }
    }

    public static int countWalkablePos(Level level, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        int count = 0;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    byte val = CachedSearchPool.getPoolValue(level, i, j, k);
                    if (val == OK) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }

    public static void populatePool(Dog dog, BlockPos targetPos, int radiusXZ, int radiusY) {
        resetPool(dog.level, radiusXZ, radiusY);
        // long startTime1 = System.nanoTime();
        populatePoolRaw(dog, targetPos, radiusXZ, radiusY);
        // long stopTime1 = System.nanoTime();
        //     ChopinLogger.l("populate collision in : " + (stopTime1-startTime1) + " nanoseconds");
        populateBlockCollision(dog, radiusXZ, radiusY);
        populateDangerPos(dog.level, radiusXZ, radiusY);
        populateWalkablePos(dog.level, radiusXZ, radiusY);
    }

    public static void populatePool(Level level, List<Dog> dogs, BlockPos targetPos, int radiusXZ, int radiusY) {
        resetPool(level, radiusXZ, radiusY);
        // long startTime1 = System.nanoTime();
        populatePoolRaw(level, dogs, targetPos, radiusXZ, radiusY);
        // long stopTime1 = System.nanoTime();
        //     ChopinLogger.l("populate collision in : " + (stopTime1-startTime1) + " nanoseconds");
        populateBlockCollision(level, dogs, radiusXZ, radiusY);
        populateDangerPos(level, radiusXZ, radiusY);
        populateWalkablePos(level, radiusXZ, radiusY);
    }

    public static List<BlockPos> collectSafePos(Level level, BlockPos targetPos, int radiusXZ, int radiusY) {
        
        var safePosList = new ArrayList<BlockPos>(); 
        
        var b0 = targetPos;
        var bMin = b0.offset(-radiusXZ, -radiusY, -radiusXZ);
        
        // ChopinLogger.outputToFile(
        //     "name = " + dog.getName().getString() + "\n"
        //     + "0, 0, 0 = " + bMin + "\n"
        //     + "OwnerLookVec = " + dx1 + ", " + dz1 + "\n"
        //     + dumpPool(dog, poolXZ, poolY) + "\n\n"); 
        
        int maxXZ = radiusXZ*2-1;
        int maxY = radiusY*2-1;
        
        for (int i = 1; i <= maxXZ; ++i) {
            for (int j = 1; j <= maxY; ++j) {
                for (int k = 1; k <= maxXZ; ++k) {
                    if (CachedSearchPool.getPoolValue(level, i, j, k) == OK) {
                        var pos = bMin.offset(i, j, k);
                        safePosList.add(pos);
                    }
                }
            }
        }

        return safePosList;

    }

    public static BlockPos getRandomSafePosUsingPool(Dog dog, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        var safePosList = getAllSafePosUsingPool(dog, targetPos, realRadiusXZ, realRadiusY);
        
        if (safePosList.isEmpty()) {
            // long stopTime = System.nanoTime();
            // ChopinLogger.l("search failed in : " + (stopTime-startTime) + " nanoseconds");
            return null;
        }
        int index = dog.getRandom().nextInt(safePosList.size());
        // long stopTime = System.nanoTime();
        // ChopinLogger.l("search succeed in : " + (stopTime-startTime) + " nanoseconds");
        return safePosList.get(index);
    }

    public static BlockPos getRandomSafePosUsingPoolExcludeInfrontOfOwner(Dog dog, LivingEntity owner, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        var safePosList = getAllSafePosUsingPoolExcludeInfrontOfOwner(dog, owner, targetPos, realRadiusXZ, realRadiusY);
        
        if (safePosList.isEmpty()) {
            // long stopTime = System.nanoTime();
            // ChopinLogger.l("search failed in : " + (stopTime-startTime) + " nanoseconds");
            return null;
        }
        int index = dog.getRandom().nextInt(safePosList.size());
        // long stopTime = System.nanoTime();
        // ChopinLogger.l("search succeed in : " + (stopTime-startTime) + " nanoseconds");
        return safePosList.get(index);
    }

    public static List<BlockPos> getAllSafePosUsingPool(Dog dog, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        // ChopinLogger.l("Begining search: unit: nanoseconds");
        // long startTime = System.nanoTime();
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        populatePool(dog, targetPos, poolXZ, poolY);
        
        var safePosList = collectSafePos(dog.level, targetPos, poolXZ, poolY);

        return safePosList;
    }

    public static List<BlockPos> getAllSafePosUsingPoolExcludeInfrontOfOwner(Dog dog, LivingEntity owner, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        // ChopinLogger.l("Begining search: unit: nanoseconds");
        // long startTime = System.nanoTime();
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        populatePool(dog, targetPos, poolXZ, poolY);
        populateCollideOwner(owner, poolXZ, poolY);
        
        var safePosList = collectSafePos(dog.level, targetPos, poolXZ, poolY);

        return safePosList;
    }

    public static List<BlockPos> getAllSafePosUsingPool(Level level, List<Dog> dogs, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        // ChopinLogger.l("Begining search: unit: nanoseconds");
        // long startTime = System.nanoTime();
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        populatePool(level, dogs, targetPos, poolXZ, poolY);
        
        var safePosList = collectSafePos(level, targetPos, poolXZ, poolY);

        return safePosList;
    }

    public static List<BlockPos> getAllSafePosUsingPoolExcludeInfrontOfOwner(Level level, List<Dog> dogs, LivingEntity owner, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        // ChopinLogger.l("Begining search: unit: nanoseconds");
        // long startTime = System.nanoTime();
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        populatePool(level, dogs, targetPos, poolXZ, poolY);
        populateCollideOwner(owner, poolXZ, poolY);
        
        var safePosList = collectSafePos(level, targetPos, poolXZ, poolY);

        return safePosList;
    }

    public static String dumpPool(Level level, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        var builder = new StringBuilder();

        for (int i = maxY; i >= 0; --i) {
            builder.append("Layer " + i + ": X -> \n");
            for (int j = 0; j <= maxXZ; ++j) {
                builder.append("-" + j + "-  ");
                for (int k = 0; k <= maxXZ; ++k) {
                    builder.append(CachedSearchPool.getPoolValue(level, k, i, j) + ", ");
                }
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    /**
     * This function will infer the type into 3 categories
     * if the dog suffocate on the block
     * if the dog can be in the block
     * if the dog will take damage in the block
     * 
     * @param dog
     * @param type
     * @return
     */
    public static byte inferType(Dog dog, BlockPathTypes type) {
        for (var x : dog.getAlterations()) {
            if (x.isBlockTypeWalkable(dog, type).shouldSwing()) {
                return OK;
            }
        }
        if (type == BlockPathTypes.OPEN) return OPEN;
        if (type.getDanger() != null) return DAMAGE;
        if (dog.getPathfindingMalus(type) < 0) return BLOCKED;
        return DANGER;
    }

    public static byte inferType(List<Dog> dogs, BlockPathTypes type) {

        //If the pos is OK for a dogs via talent, then it must be
        //OK for all the other dogs to be considered OK here. 
        boolean all_dog_OK = true;
        for (var dog : dogs) {
            boolean is_ok = false;
            for (var x : dog.getAlterations()) {
                if (x.isBlockTypeWalkable(dog, type).shouldSwing()) {
                    is_ok = true;
                    break;
                }
            }
            if (!is_ok) {
                all_dog_OK = false; break;
            }
        }

        if (all_dog_OK) return OK;
        if (type == BlockPathTypes.OPEN) return OPEN;
        if (type.getDanger() != null) return DAMAGE;
        for (var dog : dogs) {
            if (dog.getPathfindingMalus(type) < 0) return BLOCKED;
        }
        return DANGER;
    }

    private static class WalkNodeEvaluatorDelegate extends WalkNodeEvaluator {

        public static BlockPathTypes getTypeDelegate(BlockGetter getter, BlockPos pos) {
            return WalkNodeEvaluator.getBlockPathTypeRaw(getter, pos);
        }

    }
}
