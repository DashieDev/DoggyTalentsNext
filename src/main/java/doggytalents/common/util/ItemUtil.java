package doggytalents.common.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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

    public static CompoundTag getTag(ItemStack stack) {
        var custom_data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (custom_data == CustomData.EMPTY)
            return new CompoundTag();
        return custom_data.copyTag();
    }

    public static void clearTag(ItemStack stack) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    }

    public static void putTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static void modifyTag(ItemStack stack, Consumer<CompoundTag> tag_modifier) {
        var custom_data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        var current_tag = new CompoundTag();
        if (custom_data != CustomData.EMPTY)
            current_tag = custom_data.copyTag();
        tag_modifier.accept(current_tag);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(current_tag));
    }
}
