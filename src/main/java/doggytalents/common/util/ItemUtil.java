package doggytalents.common.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import doggytalents.common.item.IDyeableArmorItem;

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
        stack.set(DataComponents.CUSTOM_DATA, null);
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

    public static CompoundTag getTagElement(ItemStack stack, String id) {
        var tag = getTag(stack);
        if (!tag.contains(id, Tag.TAG_COMPOUND))
            return null;
        return tag.getCompound(id);
    }

    public static boolean hasTag(ItemStack stack) {
        var custom = stack.get(DataComponents.CUSTOM_DATA);
        return custom != null && !custom.isEmpty();
    }

    public static void copyTag(ItemStack from, ItemStack to) {
        var fromTag = from.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
            .copyTag();
        to.set(DataComponents.CUSTOM_DATA, CustomData.of(fromTag));
    }

    public static boolean fireResistant(ItemStack stack) {
        return stack.has(DataComponents.FIRE_RESISTANT);
    }

    public static boolean isEddible(ItemStack stack) {
        return stack.has(DataComponents.FOOD);
    }

    public static boolean hasCustomHoverName(ItemStack stack) {
        return stack.has(DataComponents.CUSTOM_NAME);
    }

    public static int getDyeColorForStack(ItemStack stack) {
        int default_color = 0xffffff;
        int color_mask = 0xff000000;
        if (stack.getItem() instanceof IDyeableArmorItem dye) {
            default_color = dye.getDefaultColor(stack);
        }
        if (!stack.has(DataComponents.DYED_COLOR))
            return default_color;
        return stack.getOrDefault(
            DataComponents.DYED_COLOR, 
            new DyedItemColor(default_color, false)
        ).rgb() | color_mask;
    }

    public static void setDyeColorForStack(ItemStack stack, int color) {
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color, true));
    }

    public static Optional<ArmorTrim> getTrim(ItemStack stack) {
        if (!stack.has(DataComponents.TRIM))
            return Optional.empty();
        return Optional.ofNullable(stack.get(DataComponents.TRIM));
    }
}
