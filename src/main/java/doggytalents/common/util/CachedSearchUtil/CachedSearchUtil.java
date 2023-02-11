package doggytalents.common.util.CachedSearchUtil;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import static doggytalents.common.util.CachedSearchUtil.PoolValues.*;

import java.util.ArrayList;

public class CachedSearchUtil {

    public static void resetPool(Dog dog, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    CachedSearchPool.setPoolValue(dog, i, j, k, NULL);
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
                    CachedSearchPool.setPoolValue(dog, i, j, k, val);
                }
            }
        }
    }

    public static void populateCollideOwner(Dog dog, int radiusXZ, int radiusY) {
        final var DISTANCE_AWAY = 1.5;

        var owner = dog.getOwner();
        if (owner == null) return;

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
                        CachedSearchPool.setPoolValue(dog, i, j, k, COLLIDE);
                    }
                }
                    
            }
        }
        //Too near owner pos
        int cXZ = radiusXZ;
        for (int i = -1; i <= 1; ++i) {
            for (int k = -1; k <= 1; ++k) {
                for (int j = 0; j <= maxY; ++j) {
                    CachedSearchPool.setPoolValue(dog, cXZ + i, j, cXZ + k, COLLIDE);
                } 
            }
        }
    }

    // public static void populateEntityCollision(Dog dog, int radiusXZ, int radiusY) {
    //     var entities = 
    //         dog.level.getEntities(
    //             dog, 
    //             new AABB(
    //                 dog.blockPosition().offset(-radiusXZ, -radiusY, -radiusXZ ),
    //                 dog.blockPosition().offset(radiusXZ, radiusY, radiusXZ )
    //             ), 
    //             EntitySelector.NO_SPECTATORS.and(dog::canCollideWith)
    //         );
    //     for (var e : entities) {
    //         var bb = e.getBoundingBox();
    //         var bStart = new BlockPos(
    //             Mth.floor(bb.minX),
    //             Mth.floor(bb.minY),
    //             Mth.floor(bb.minZ)
    //         );
    //         var bEnd = new BlockPos(
    //             Mth.ceil(bb.maxX),
    //             Mth.ceil(bb.maxY),
    //             Mth.ceil(bb.maxZ)
    //         );
    //         for (var b : BlockPos.betweenClosed(bStart, bEnd)) {
    //             var off = b.subtract(dog.blockPosition());
    //             CachedSearchPool.setPoolValue(
    //                 dog, 
    //                 off.getX(), 
    //                 off.getY(), 
    //                 off.getZ(), 
    //                 BLOCKED
    //             );
    //         }
    //     }
    // }

    public static void populateBlockCollision(Dog dog, int radiusXZ, int radiusY) {
        int bbWExt = Mth.ceil((dog.getBbWidth() - 1)*0.5);
        int bbHExt = Mth.ceil((dog.getBbHeight() - 1)*0.5);
        if (bbWExt <= 0 && bbHExt <= 0) return; 
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    if (CachedSearchPool.getPoolValue(dog, i, j, k) == BLOCKED) {
                        for (int i1 = i-bbWExt; i1 <= i+bbWExt; ++i1 ) {
                            for (int j1 = j-bbHExt; j1 <= j; ++j1) {
                                for (int k1 = k-bbWExt; k1 <= k+bbWExt; ++k1) {
                                    if (CachedSearchPool.getPoolValue(dog, i1, j1, k1) != BLOCKED
                                        && CachedSearchPool.getPoolValue(dog, i1, j1, k1) != ERR
                                    )
                                    CachedSearchPool.setPoolValue(dog, i1, j1, k1, COLLIDE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateDangerPos(Dog dog, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    if (CachedSearchPool.getPoolValue(dog, i, j, k) == DAMAGE) {
                        for (int i1 = i-1; i1 <= i+1; ++i1 ) {
                            for (int j1 = j-1; j1 <= j+1; ++j1) {
                                for (int k1 = k-1; k1 <= k+1; ++k1) {
                                    if (CachedSearchPool.getPoolValue(dog, i1, j1, k1) == OPEN)
                                    CachedSearchPool.setPoolValue(dog, i1, j1, k1, DANGER);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void populateWalkablePos(Dog dog, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    byte val = CachedSearchPool.getPoolValue(dog, i, j, k);
                    byte val_below = CachedSearchPool.getPoolValue(dog, i, j-1, k);
                    if (val == OPEN && val_below == BLOCKED) {
                        CachedSearchPool.setPoolValue(dog, i, j, k, OK);
                    }
                }
            }
        }
    }

    public static int countWalkablePos(Dog dog, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        int count = 0;
        for (int i = 0; i <= maxXZ; ++i) {
            for (int j = 0; j <= maxY; ++j) {
                for (int k = 0; k <= maxXZ; ++k) {
                    byte val = CachedSearchPool.getPoolValue(dog, i, j, k);
                    if (val == OK) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }

    public static BlockPos getRandomSafePosUsingPool(Dog dog, BlockPos targetPos, boolean exludeInfrontOfOwner, int realRadiusXZ, int realRadiusY) {
        // ChopinLogger.l("Begining search: unit: nanoseconds");
        // long startTime = System.nanoTime();
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        resetPool(dog, poolXZ, poolY);
        // long startTime1 = System.nanoTime();
        populatePoolRaw(dog, targetPos, poolXZ, poolY);
        // long stopTime1 = System.nanoTime();
        //     ChopinLogger.l("populate collision in : " + (stopTime1-startTime1) + " nanoseconds");
        if (exludeInfrontOfOwner) populateCollideOwner(dog, poolXZ, poolY);
        populateBlockCollision(dog, poolXZ, poolY);
        populateDangerPos(dog, poolXZ, poolY);
        populateWalkablePos(dog, poolXZ, poolY);
        
        var b0 = targetPos;
        var bMin = b0.offset(-poolXZ, -poolY, -poolXZ);

        int maxXZ = poolXZ*2-1;
        int maxY = poolY*2-1;
        var safePosList = new ArrayList<BlockPos>(); 
        for (int i = 1; i <= maxXZ; ++i) {
            for (int j = 1; j <= maxY; ++j) {
                for (int k = 1; k <= maxXZ; ++k) {
                    if (CachedSearchPool.getPoolValue(dog, i, j, k) == OK) {
                        var pos = bMin.offset(i, j, k);
                        safePosList.add(pos);
                    }
                }
            }
        }
        
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

    public static BlockPos ringSearchSafePosUsingPool(Dog dog, BlockPos targetPos, int realRadiusXZ, int realRadiusY) {
        int poolXZ = realRadiusXZ+1;
        int poolY = realRadiusY+1;
        if (poolXZ > CachedSearchPool.MAX_RADIUS_XZ || poolXZ < 0) return null;
        if (poolY > CachedSearchPool.MAX_RADIUS_Y || poolY < 0) return null;
        resetPool(dog, poolXZ, poolY);
        populatePoolRaw(dog, targetPos, poolXZ, poolY);
        populateDangerPos(dog, poolXZ, poolY);
        populateWalkablePos(dog, poolXZ, poolY);
        var b0 = targetPos;
        var bMin = b0.offset(-poolXZ, -poolY, -poolXZ);
        int mXZ = poolXZ;
        int mY = poolY;
        
        int inflate = 1; // this is to prevent dog from repeating himself
        while (inflate <= realRadiusXZ) {
            final int minX = mXZ - inflate;
            final int maxX = mXZ + inflate;
            final int minZ = mXZ - inflate;
            final int maxZ = mXZ + inflate;

            //ChopinLogger.l("blockpos" + b0);

            if (inflate <= 0) {
                //Maybe treat the center block as an optional return
                //If nothing else outside satisfies this, then return this.
                for (int j = -realRadiusY; j <= realRadiusY; ++j) {
                    int x = mXZ, y = mY + j, z =  mXZ;
                    if (CachedSearchPool.getPoolValue(dog, x, y, z) == OK) {
                        var pos = bMin.offset(x, y, z);
                        return pos;
                    }
                }
                ++inflate;
                continue;
            }

            int x = minX, y = 0, z = minZ;

            for (int i = minX; i <= maxX; ++i) {
                for (int j = -realRadiusY; j <= realRadiusY; ++j) {
                    x = i; y = mY + j;
                    if (CachedSearchPool.getPoolValue(dog, x, y, z) == OK) {
                        var pos = bMin.offset(x, y, z);
                        return pos;
                    }
                }
            }

            //b0m: maxX, minZ
            for (int i = minZ + 1; i <= maxZ; ++i) {
                for (int j = -realRadiusY; j <= realRadiusY; ++j) {
                    y = mY + j; z = i;
                    if (CachedSearchPool.getPoolValue(dog, x, y, z) == OK) {
                        var pos = bMin.offset(x, y, z);
                        return pos;
                    }
                }
            }

            //b0m: maxX, maxZ
            for (int i = maxX-1; i >= minX; --i) {
                for (int j = -realRadiusY; j <= realRadiusY; ++j) {
                    x = i; y = mY + j;
                    if (CachedSearchPool.getPoolValue(dog, x, y, z) == OK) {
                        var pos = bMin.offset(x, y, z);
                        return pos;
                    }
                }
            }

            //b0m: minX, maxZ
            for (int i = maxZ - 1; i >= minZ + 1; --i) {
                for (int j = -realRadiusY; j <= realRadiusY; ++j) {
                    y = mY + j; z = i;
                    if (CachedSearchPool.getPoolValue(dog, x, y, z) == OK) {
                        var pos = bMin.offset(x, y, z);
                        return pos;
                    }
                }
            }
            ++inflate;
            //ChopinLogger.l("inflate!" + inflate);
        }
        return null;
    }

    public static String dumpPool(Dog dog, int radiusXZ, int radiusY) {
        int maxXZ = radiusXZ * 2;
        int maxY = radiusY * 2;
        var builder = new StringBuilder();

        for (int i = maxY; i >= 0; --i) {
            builder.append("Layer " + i + ": X -> \n");
            for (int j = 0; j <= maxXZ; ++j) {
                builder.append("-" + j + "-  ");
                for (int k = 0; k <= maxXZ; ++k) {
                    builder.append(CachedSearchPool.getPoolValue(dog, k, i, j) + ", ");
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
        return OPEN;
    }

    private static class WalkNodeEvaluatorDelegate extends WalkNodeEvaluator {

        public static BlockPathTypes getTypeDelegate(BlockGetter getter, BlockPos pos) {
            return WalkNodeEvaluator.getBlockPathTypeRaw(getter, pos);
        }

    }
}
