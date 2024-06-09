package doggytalents.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import doggytalents.common.entity.Dog;
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
        var tag = stack.getTag();
        if (tag == null)
            return new CompoundTag();
        return tag;
    }

    public static void clearTag(ItemStack stack) {
        stack.setTag(null);
    }

    public static void putTag(ItemStack stack, CompoundTag tag) {
        stack.setTag(tag);
    }

    public static void modifyTag(ItemStack stack, Consumer<CompoundTag> tag_modifier) {
        var tag = stack.getOrCreateTag();
        tag_modifier.accept(tag);
    }

    public static CompoundTag getTagElement(ItemStack stack, String id) {
        var tag = getTag(stack);
        if (!tag.contains(id, Tag.TAG_COMPOUND))
            return null;
        return tag.getCompound(id);
    }

    public static boolean hasTag(ItemStack stack) {
        return stack.hasTag();
    }

    public static void copyTag(ItemStack from, ItemStack to) {
        var from_tag = from.getTag();
        if (from_tag != null) {
            from_tag = from_tag.copy();
        } else {
            from_tag = new CompoundTag();
        }
        to.setTag(from_tag);
    }

    public static boolean fireResistant(ItemStack stack) {
        return stack.getItem().isFireResistant();
    }

    public static boolean isEddible(ItemStack stack) {
        return stack.isEdible();
    }

    public static boolean hasCustomHoverName(ItemStack stack) {
        return stack.hasCustomHoverName();
    }

    public static Component getCustomHoverName(ItemStack stack) {
        return stack.getHoverName();
    }

    public static void clearCustomHoverName(ItemStack stack) {
        stack.resetHoverName();
    }

    public static int getDyeColorForStack(ItemStack stack) {
        if (stack.getItem() instanceof IDyeableArmorItem dye) {
            return 0xff000000 | dye.getColor(stack);
        }
        return 0xffffffff;
    }

    public static void setDyeColorForStack(ItemStack stack, int color) {
        var item = stack.getItem();
        if (!(item instanceof IDyeableArmorItem dye))
            return;
        dye.setColor(stack, color);
    }

    // public static Optional<ArmorTrim> getTrim(Dog dog, ItemStack stack) {
    //     return ArmorTrim.getTrim(dog.level().registryAccess(), stack);
    // }

    public static CompoundTag getWrappedTag(ItemStack stack) {
        return getTag(stack);
    }

    public static void addCrossbowProj(ItemStack crossbow_stack, List<ItemStack> proj_stacks) {
        final String CHARGED_PROJ_TAG = "ChargedProjectiles";
        var crossbow_tag = crossbow_stack.getOrCreateTag();
        ListTag listtag;
        if (crossbow_tag.contains(CHARGED_PROJ_TAG, Tag.TAG_LIST)) {
            listtag = crossbow_tag.getList(CHARGED_PROJ_TAG, Tag.TAG_COMPOUND);
        } else {
            listtag = new ListTag();
        }

        for (var proj_stack : proj_stacks) {
            var proj_tag = new CompoundTag();
            proj_stack.save(proj_tag);
            listtag.add(proj_tag);
        }
        
        crossbow_tag.put(CHARGED_PROJ_TAG, listtag);
        
        CrossbowItem.setCharged(crossbow_stack, true);
    } 
}
