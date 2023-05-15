package doggytalents.common.util.CachedSearchUtil;

import doggytalents.common.entity.Dog;
import net.minecraft.world.level.Level;

import static doggytalents.common.util.CachedSearchUtil.PoolValues.*;

public class CachedSearchPool {
    public static final int MAX_RADIUS_XZ = 5;
    public static final int MAX_RADIUS_Y = 3;
    public static final int POOL_SIZE_XZ = MAX_RADIUS_XZ*2+1;
    public static final int POOL_SIZE_Y = MAX_RADIUS_Y*2+1;
    private static byte[][][] CACHED_TYPE_POOL = 
        new byte[POOL_SIZE_XZ][POOL_SIZE_Y][POOL_SIZE_XZ];

    public static byte getPoolValue(Level level, int x, int y, int z) {
        if (level.isClientSide) {
            return ERR;
        }
        if (x >= POOL_SIZE_XZ || x < 0) return ERR;
        if (z >= POOL_SIZE_XZ || z < 0) return ERR;
        if (y >= POOL_SIZE_Y || y < 0) return ERR;
        return CACHED_TYPE_POOL[x][y][z];
    }
    
    public static void setPoolValue(Level level, int x, int y, int z, byte value) {
        if (level.isClientSide) {
            return;
        }
        if (x >= POOL_SIZE_XZ || x < 0) return;
        if (z >= POOL_SIZE_XZ || z < 0) return;
        if (y >= POOL_SIZE_Y || y < 0) return;
        CACHED_TYPE_POOL[x][y][z] = value;
    }
}
