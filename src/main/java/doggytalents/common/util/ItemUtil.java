package doggytalents.common.util;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import doggytalents.api.backward_imitate.CompoundTag_1_21_5;
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

    public static CompoundTag_1_21_5 getTag(ItemStack stack) {
        var custom_data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (custom_data == CustomData.EMPTY)
            return CompoundTag_1_21_5.createEmpty();
        return CompoundTag_1_21_5.wrap(custom_data.copyTag());
    }

    public static void clearTag(ItemStack stack) {
        stack.set(DataComponents.CUSTOM_DATA, null);
    }

    public static void putTag(ItemStack stack, CompoundTag_1_21_5 tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag.wrapped()));
    }

    public static void modifyTag(ItemStack stack, Consumer<CompoundTag_1_21_5> tag_modifier) {
        var custom_data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        var current_tag = CompoundTag_1_21_5.createEmpty();
        if (custom_data != CustomData.EMPTY)
            current_tag = CompoundTag_1_21_5.wrap(custom_data.copyTag());
        tag_modifier.accept(current_tag);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(current_tag.wrapped()));
    }

    public static CompoundTag_1_21_5 getTagElement(ItemStack stack, String id) {
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
        return stack.has(DataComponents.DAMAGE_RESISTANT) && DamageTypeTags.IS_FIRE.equals(stack.get(DataComponents.DAMAGE_RESISTANT).types());
    }

    public static boolean isEddible(ItemStack stack) {
        return stack.has(DataComponents.FOOD);
    }

    public static boolean hasCustomHoverName(ItemStack stack) {
        return stack.has(DataComponents.CUSTOM_NAME);
    }

    public static Component getCustomHoverName(ItemStack stack) {
        return stack.get(DataComponents.CUSTOM_NAME);
    }

    public static void clearCustomHoverName(ItemStack stack) {
        stack.set(DataComponents.CUSTOM_NAME, null);
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
        if (!stack.has(DataComponents.TRIM) || !isValidTrim_1_21_3(stack))
            return Optional.empty();
        return Optional.ofNullable(stack.get(DataComponents.TRIM));
    }

    public static CustomData getWrappedTag(ItemStack stack) {
        var custom_data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return custom_data;
    }

    public static int getEnchantmentLevelForItem(ResourceKey<Enchantment> key, RegistryAccess prov, ItemStack stack) {
        var reg = prov.lookupOrThrow(Registries.ENCHANTMENT);
        var holder = reg.get(key);
        if (!holder.isPresent())
            return 0;
        return stack.getEnchantmentLevel(holder.get());
    }

    public static EquipmentSlot getEquipmentSlot(ItemStack stack) {
        var data = getEquippable_1_21_3(stack);
        if (data == null)
            return null;
        return data.slot();
    }


    
    //1.21.3+
    public static Equippable getEquippable_1_21_3(ItemStack stack) {
        return stack.get(DataComponents.EQUIPPABLE);
    }
    public static boolean isValidTrim_1_21_3(ItemStack stack) {
        var equip = getEquippable_1_21_3(stack);
        if (equip == null)
            return false;
        if (!equip.assetId().isPresent())
            return false;
        return true;
    }
    public static ResourceKey<EquipmentAsset> getEquippableModelUnsafe_1_21_3(ItemStack stack) {
        return getEquippable_1_21_3(stack).assetId().get();
    }
    public static FoodProperties food_1_21_3(ItemStack stack) {
        return stack.get(DataComponents.FOOD);
    }
    public static Consumable consumable_1_21_3(ItemStack stack) {
        return stack.get(DataComponents.CONSUMABLE);
    }
    public static List<MobEffectInstance> foodEffect_1_21_3(ItemStack stack) {
        var consumable = consumable_1_21_3(stack);
        if (consumable == null)
            return List.of();
        var effects = consumable.onConsumeEffects();
        if (effects == null)
            return List.of();
        var mob_add_effects_list = new ArrayList<MobEffectInstance>();
        for (var x : effects) {
            if (x instanceof ApplyStatusEffectsConsumeEffect apply_status)
                mob_add_effects_list.addAll(apply_status.effects());
        }
        return mob_add_effects_list;
    }
}
