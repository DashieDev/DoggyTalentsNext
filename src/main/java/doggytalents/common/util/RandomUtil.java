package doggytalents.common.util;

import net.minecraft.util.RandomSource;

public class RandomUtil {
    
    public static float nextFloatRemapped(RandomSource random) {
        return random.nextFloat() * 2 - 1;
    }

    public static int randomOffsetNonZero(RandomSource random, int radius) {
        int ret = random.nextInt(radius * 2) - radius;
        return ret >= 0 ? ret + 1 : ret;
    }

}
