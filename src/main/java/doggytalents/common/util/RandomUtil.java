package doggytalents.common.util;

import java.util.Random;

public class RandomUtil {
    
    public static float nextFloatRemapped(Random random) {
        return random.nextFloat() * 2 - 1;
    }

}
