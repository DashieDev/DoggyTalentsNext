package doggytalents.common.entity.stats;

import net.minecraft.stats.StatFormatter;

public class DoggyStats {

    public static DogStat DISTANCE_WALKED = new DogStat("distanceWalked", StatFormatter.DEFAULT);
    

    public static class DogStat {
        private final String id;
        private final StatFormatter formatter;
        public DogStat(String id, StatFormatter formatter) {
            this.id = id;
            this.formatter = formatter;
        }

        public String getId() {
            return this.id;
        }

        public String format(int val) {
            return this.formatter.format(val);
        }
    }

}
