package doggytalents.common.util;

import java.util.function.Consumer;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec2;

public class RingSearchUtil {

    //X_Z
    //0 means not checked
    //-1 means checked and is excluded
    //1 means checked and have potential to be safe
    private static byte[][] SAFE_CACHE;

    private static Point SAFE_CACHE_CENTER;

    public static BlockPos searchForSafePos(Dog dog, int radius) {
        initCache(radius);

        int inflate = 1;

        
        return null;
    }

    private static void initCache(int radius) {
        SAFE_CACHE = new byte[radius*2+1][radius*2+1];
        SAFE_CACHE_CENTER = new Point(radius, radius);
    }


    private static void setCachedPointOffset(Dog dog, BlockPos pos, byte value) {
        var dog_b0 = dog.blockPosition();
        var dx = pos.getX() - dog_b0.getX();
        var dz = pos.getZ() - dog_b0.getZ();
        setCachedPointOffset(new Point(dx, dz), value);
    }

    private static byte getCachedPointOffset(Point offset) {
        return SAFE_CACHE[SAFE_CACHE_CENTER.x+offset.x][SAFE_CACHE_CENTER.y+offset.y];
    }

    private static byte getCachedPointOffset(Dog dog, BlockPos pos) {
        var dog_b0 = dog.blockPosition();
        var dx = pos.getX() - dog_b0.getX();
        var dz = pos.getZ() - dog_b0.getZ();
        return getCachedPointOffset(new Point(dx, dz));
    }

    private static void setCachedPointOffset(Point offset, byte value) {
        SAFE_CACHE[SAFE_CACHE_CENTER.x+offset.x][SAFE_CACHE_CENTER.y+offset.y] = value;
    }

    /**
     * A "canWalkFrom" pos is defined by itself being an OPEN pos checked by vanilla getBlockPathTypesRaw
     * AND below (getMaxFallDistance) blocks there is a BLOCKED pos and all pos in between must be OPEN
     */
    private boolean canWalkOnPosFrom(Dog dog, BlockPos pos, BlockPos from) {
        byte cached = getCachedPointOffset(dog, pos);
        if (cached != 0) {
            if (cached == -1) return false;
            if (cached == 1) return true; 
        }
        var t1 = RingSearchNodeEvaluator.getRaw(dog.level, pos);
        
        if (t1 == BlockPathTypes.RAIL) t1 = BlockPathTypes.OPEN;
        if (t1 == BlockPathTypes.OPEN) {
            for (int i = 1; i <= dog.getMaxFallDistance(); ++i) {
                var t2 = RingSearchNodeEvaluator.getRaw(dog.level, pos.below(i));
                if (t2 != BlockPathTypes.OPEN) {
                    if (t2 == BlockPathTypes.BLOCKED) {
                        setCachedPointOffset(dog, pos, (byte)1);
                        return true;
                    } else {
                        setCachedPointOffset(dog, pos, (byte)-1);
                        return false;
                    }
                }
            }
            setCachedPointOffset(dog, pos, (byte)1);
            return true;
        }

        setCachedPointOffset(dog, pos, (byte)-1);
        return false;
    }

    private void tranverseRing(Dog dog, int inflate, Consumer<BlockPos> action) {
    //     var b0 = dog.blockPosition();
    //     var b0m = b0.mutable();

    //     final int minX = b0.getX() - inflate;
    //     final int maxX = b0.getX() + inflate;
    //     final int minZ = b0.getZ() - inflate;
    //     final int maxZ = b0.getZ() + inflate;

    //     b0m.setX(minX);
    //     b0m.setZ(minZ);
    //     //ChopinLogger.l("blockpos" + b0);

    //     for (int i = minX; i <= maxX; ++i) {
    //         b0m.setX(i);
    //         //ChopinLogger.l("" + b0m);
    //         for (int j = -4; j <= 4; ++j) {
    //             b0m.setY(b0.getY() + j);
    //             action.accept(b0m.mutable());
    //         }
    //     }

    //     //b0m: maxX, minZ
    //     for (int i = minZ + 1; i <= maxZ; ++i) {
    //         b0m.setZ(i);
    //         //ChopinLogger.l("" + b0m);
    //         for (int j = -4; j <= 4; ++j) {
    //             b0m.setY(b0.getY() + j);
    //             if (this.isWaterPos(b0m)) {
    //                 return b0m.immutable();
    //             }
    //         }
    //     }

    //     //b0m: maxX, maxZ
    //     for (int i = maxX-1; i >= minX; --i) {
    //         b0m.setX(i);
    //         //ChopinLogger.l("" + b0m);
    //         for (int j = -4; j <= 4; ++j) {
    //             b0m.setY(b0.getY() + j);
    //             if (this.isWaterPos(b0m)) {
    //                 return b0m.immutable();
    //             }
    //         }
    //     }

    //     //b0m: minX, maxZ
    //     for (int i = maxZ - 1; i >= minZ + 1; --i) {
    //         b0m.setZ(i);
    //         //ChopinLogger.l("" + b0m);
    //         for (int j = -4; j <= 4; ++j) {
    //             b0m.setY(b0.getY() + j);
    //             if (this.isWaterPos(b0m)) {
    //                 return b0m.immutable();
    //             }
    //         }
    //     }
    //     ++inflate;
    //     //ChopinLogger.l("inflate!" + inflate);
    }

    private static class Point {
        public final int x;
        public final int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class RingSearchNodeEvaluator extends WalkNodeEvaluator {
        public static BlockPathTypes getRaw(BlockGetter block, BlockPos pos) {
            //Make this guy visible.
            return getBlockPathTypeRaw(block, pos);
        }
    }

}
