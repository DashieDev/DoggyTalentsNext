package doggytalents.common.util;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LangUtil {
    
    public static <T> Optional<T> getRandomItem(Random random, List<T> list) {
        if (list.isEmpty())
            return Optional.empty();
        int size = list.size();
        if (size == 1)
            return Optional.of(list.get(0));
        int r = random.nextInt(size);
        return Optional.of(list.get(r));
    }

}
