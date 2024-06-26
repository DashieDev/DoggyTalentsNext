package doggytalents.common.util;

import net.minecraft.util.RandomSource;

public class RandomUtil {
    
    public static float nextFloatRemapped(RandomSource random) {
        return random.nextFloat() * 2 - 1;
    }

}
