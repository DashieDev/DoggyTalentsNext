package doggytalents.common.util;

import net.minecraft.world.item.Item;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;

public class ItemUtil {

    private static int MAX_OVERVIEW = 3;
    public static ContentOverview getContentOverview(IItemHandler inventory) {
        var retMap = new HashMap<Item, Integer>(MAX_OVERVIEW);
        int isMore = 0;
        for (int i = 0; i < inventory.getSlots(); ++i) {
            var stack = inventory.getStackInSlot(i);
            if (stack.isEmpty())
                continue;
            var item = stack.getItem();
            var existing = retMap.get(item);
            if (existing != null) {
                retMap.put(item, existing + stack.getCount());
                continue;
            }
            if (retMap.size() >= MAX_OVERVIEW) {
                ++isMore;
                continue;
            }
            retMap.put(item, stack.getCount());
        }
        return new ContentOverview(retMap, isMore);        
    }

    public static class ContentOverview {
        
        private final Map<Item, Integer> contents;
        private int isMore = 0;

        private ContentOverview(Map<Item, Integer> contents, int isMore) {
            this.contents = contents;
            this.isMore = isMore;
        }

        public int isMore() {
            return isMore;
        }

        public Map<Item, Integer> contents() {
            return this.contents;
        }

    }
}
