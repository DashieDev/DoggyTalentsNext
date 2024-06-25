package doggytalents.common.util.CachedSearchUtil;

import doggytalents.common.entity.Dog;
import doggytalents.common.fabric_helper.util.FabricUtil;
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

public class CachedSearchUtil {

    public static void resetPool(Level level, int radiusXZ, int radiusY, CachedSearchPool pool) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    pool.setPoolValue(level, i, j, k, NULL);
                }
            }
        }
    }

    public static void populatePoolRaw(Dog dog, BlockPos targetPos, int radiusXZ, int radiusY, CachedSearchPool pool) {
        var b0 = targetPos;
        var bMin = b0.offset(-radiusXZ, -radiusY, -radiusXZ);
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    var type = WalkNodeEvaluatorDelegate
                        .getTypeDelegate(dog.level(), bMin.offset(i, j, k));
                    byte val = inferType(dog, type);
                    pool.setPoolValue(dog.level(), i, j, k, val);
                }
            }
        }
    }

    public static void populatePoolRaw(Level level, List<Dog> dogs, BlockPos targetPos, int radiusXZ, int radiusY, CachedSearchPool pool) {
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
                    pool.setPoolValue(level, i, j, k, val);
                }
            }
        }
    }

    public static void populateCollideOwner(LivingEntity owner, int radiusXZ, int radiusY, CachedSearchPool pool) {
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
                        pool.setPoolValue(owner.level(), i, j, k, COLLIDE);
                    }
                }
                    
            }
        }
        //Too near owner pos
        int cXZ = radiusXZ;
        for (int i = -1; i <= 1; ++i) {
            for (int k = -1; k <= 1; ++k) {
                for (int j = 0; j <= maxY; ++j) {
                    pool.setPoolValue(owner.level(), cXZ + i, j, cXZ + k, COLLIDE);
                } 
            }
        }
    }

    public static void populateBlockCollision(Dog dog, int radiusXZ, int radiusY, CachedSearchPool pool) {
        int bbWExt = Mth.ceil((dog.getBbWidth() - 1)*0.5);
        int bbHExt = Mth.ceil((dog.getBbHeight() - 1)*0.5);
        if (bbWExt <= 0 && bbHExt <= 0) return; 
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    if (pool.getPoolValue(dog.level(), i, j, k) == BLOCKED) {
                        for (int i1 = i-bbWExt; i1 <= i+bbWExt; ++i1 ) {
                            for (int j1 = j-bbHExt; j1 <= j; ++j1) {
                                for (int k1 = k-bbWExt; k1 <= k+bbWExt; ++k1) {
                                    if (pool.getPoolValue(dog.level(), i1, j1, k1) != BLOCKED
                                        && pool.getPoolValue(dog.level(), i1, j1, k1) != ERR
                                    )
                                    pool.setPoolValue(dog.level(), i1, j1, k1, COLLIDE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateBlockCollision(Level level, List<Dog> dogs, int radiusXZ, int radiusY, CachedSearchPool pool) {

        var dog = DogUtil.findBiggestDog(dogs);

        int bbWExt = Mth.ceil((dog.getBbWidth() - 1)*0.5);
        int bbHExt = Mth.ceil((dog.getBbHeight() - 1)*0.5);
        if (bbWExt <= 0 && bbHExt <= 0) return;
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    if (pool.getPoolValue(level, i, j, k) == BLOCKED) {
                        for (int i1 = i-bbWExt; i1 <= i+bbWExt; ++i1 ) {
                            for (int j1 = j-bbHExt; j1 <= j; ++j1) {
                                for (int k1 = k-bbWExt; k1 <= k+bbWExt; ++k1) {
                                    if (pool.getPoolValue(level, i1, j1, k1) != BLOCKED
                                        && pool.getPoolValue(level, i1, j1, k1) != ERR
                                    )
                                    pool.setPoolValue(level, i1, j1, k1, COLLIDE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateDangerPos(Level level, int radiusXZ, int radiusY, CachedSearchPool pool) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    if (pool.getPoolValue(level, i, j, k) == DAMAGE) {
                        for (int i1 = i-1; i1 <= i+1; ++i1 ) {
                            for (int j1 = j-1; j1 <= j+1; ++j1) {
                                for (int k1 = k-1; k1 <= k+1; ++k1) {
                                    if (pool.getPoolValue(level, i1, j1, k1) == OPEN)
                                    pool.setPoolValue(level, i1, j1, k1, DANGER);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateWalkablePos(Level level, int radiusXZ, int radiusY, CachedSearchPool pool) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    byte val = pool.getPoolValue(level, i, j, k);
                    byte val_below = pool.getPoolValue(level, i, j-1, k);
                    if (val == OPEN && val_below == BLOCKED) {
                        pool.setPoolValue(level, i, j, k, OK);
                    }
                }
            }
        }
    }

    public static int countWalkablePos(Level level, int radiusXZ, int radiusY, CachedSearchPool pool) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        int count = 0;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    byte val = pool.getPoolValue(level, i, j, k);
                    if (val == OK) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }

    public static void populatePool(Dog dog, BlockPos targetPos, int radiusXZ, int radiusY, CachedSearchPool pool) {
        resetPool(dog.level(), radiusXZ, radiusY, pool);
        // long startTime1 = System.nanoTime();
        populatePoolRaw(dog, targetPos, radiusXZ, radiusY, pool);
        // long stopTime1 = System.nanoTime();
        //     ChopinLogger.l("populate collision in : " + (stopTime1-startTime1) + " nanoseconds");
        populateBlockCollision(dog, radiusXZ, radiusY, pool);
        populateDangerPos(dog.level(), radiusXZ, radiusY, pool);
        populateWalkablePos(dog.level(), radiusXZ, radiusY, pool);
    }

    public static void populatePool(Level level, List<Dog> dogs, BlockPos targetPos, int radiusXZ, int radiusY, CachedSearchPool pool) {
        resetPool(level, radiusXZ, radiusY, pool);
        // long startTime1 = System.nanoTime();
        populatePoolRaw(level, dogs, targetPos, radiusXZ, radiusY, pool);
        // long stopTime1 = System.nanoTime();
        //     ChopinLogger.l("populate collision in : " + (stopTime1-startTime1) + " nanoseconds");
        populateBlockCollision(level, dogs, radiusXZ, radiusY, pool);
        populateDangerPos(level, radiusXZ, radiusY, pool);
        populateWalkablePos(level, radiusXZ, radiusY, pool);
    }

    public static List<BlockPos> collectSafePos(Level level, BlockPos targetPos, int radiusXZ, int radiusY, CachedSearchPool pool) {
        
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
                    if (pool.getPoolValue(level, i, j, k) == OK) {
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
        var pool = new CachedSearchPool();
        populatePool(dog, targetPos, poolXZ, poolY, pool);
        
        var safePosList = collectSafePos(dog.level(), targetPos, poolXZ, poolY, pool);

        return safePosList;
    }

    public static List<BlockPos> getAllSafePosUsingPoolExcludeInfrontOfOwner(Dog dog, LivingEntity owner, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        // ChopinLogger.l("Begining search: unit: nanoseconds");
        // long startTime = System.nanoTime();
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        var pool = new CachedSearchPool();
        populatePool(dog, targetPos, poolXZ, poolY, pool);
        populateCollideOwner(owner, poolXZ, poolY, pool);
        
        var safePosList = collectSafePos(dog.level(), targetPos, poolXZ, poolY, pool);

        return safePosList;
    }

    public static List<BlockPos> getAllSafePosUsingPool(Level level, List<Dog> dogs, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        // ChopinLogger.l("Begining search: unit: nanoseconds");
        // long startTime = System.nanoTime();
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        var pool = new CachedSearchPool();
        populatePool(level, dogs, targetPos, poolXZ, poolY, pool);
        
        var safePosList = collectSafePos(level, targetPos, poolXZ, poolY, pool);

        return safePosList;
    }

    public static List<BlockPos> getAllSafePosUsingPoolExcludeInfrontOfOwner(Level level, List<Dog> dogs, LivingEntity owner, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        // ChopinLogger.l("Begining search: unit: nanoseconds");
        // long startTime = System.nanoTime();
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        var pool = new CachedSearchPool();
        populatePool(level, dogs, targetPos, poolXZ, poolY, pool);
        populateCollideOwner(owner, poolXZ, poolY, pool);
        
        var safePosList = collectSafePos(level, targetPos, poolXZ, poolY, pool);

        return safePosList;
    }

    public static String dumpPool(Level level, int radiusXZ, int radiusY, CachedSearchPool pool) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        var builder = new StringBuilder();

        for (int i = maxY; i >= 0; --i) {
            builder.append("Layer " + i + ": X -> \n");
            for (int j = 0; j <= maxXZ; ++j) {
                builder.append("-" + j + "-  ");
                for (int k = 0; k <= maxXZ; ++k) {
                    builder.append(pool.getPoolValue(level, k, i, j) + ", ");
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
        type = dog.inferType(type);
        if (type == BlockPathTypes.WALKABLE) return OK;
        if (type == BlockPathTypes.OPEN) return OPEN;
        if (type.getDanger() != null) return DAMAGE;
        if (type == BlockPathTypes.BLOCKED) return BLOCKED;
        //if (dog.getPathfindingMalus(type) < 0) return DANGER;
        return DANGER;
    }

    public static byte inferType(List<Dog> dogs, BlockPathTypes type) {

        //If the pos is OK for a dogs via talent, then it must be
        //OK for all the other dogs to be considered OK here. 
        boolean all_dog_OK = true;
        for (var dog : dogs) {
            boolean is_ok = false;
            var infer_type = dog.inferType(type);
            if (infer_type == BlockPathTypes.WALKABLE) {
                is_ok = true;
            }
            if (!is_ok) {
                all_dog_OK = false; break;
            }
        }

        if (all_dog_OK) return OK;
        if (type == BlockPathTypes.OPEN) return OPEN;
        if (FabricUtil.getDanger(type) != null) return DAMAGE;
        if (type == BlockPathTypes.BLOCKED) return BLOCKED;
        // for (var dog : dogs) {
        //     if (dog.getPathfindingMalus(type) < 0) return DANGER;
        // }
        return DANGER;
    }

    private static class WalkNodeEvaluatorDelegate extends WalkNodeEvaluator {

        public static BlockPathTypes getTypeDelegate(BlockGetter getter, BlockPos pos) {
            return WalkNodeEvaluator.getBlockPathTypeRaw(getter, pos);
        }

    }
}
