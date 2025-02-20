package doggytalents;

import doggytalents.common.util.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class DoggyTags {

    public static final TagKey<Item> BEG_ITEMS_TAMED = tag("beg_items_tamed");
    public static final TagKey<Item> BEG_ITEMS_UNTAMED = tag("beg_items_untamed");
    public static final TagKey<Item> BREEDING_ITEMS = tag("breeding_items");
    public static final TagKey<Item> PACK_PUPPY_BLACKLIST = tag("pack_puppy_blacklist");
    public static final TagKey<Item> TREATS = tag("treats");
    public static final TagKey<Item> WHITELIST_FOOD = tag("whitelist_food");
    public static final TagKey<Item> DOGGY_TOOLS_BLACKLIST = tag("doggy_tools_blacklist");

    public static final TagKey<EntityType<?>> DOG_SHOULD_IGNORE = tagEntity("dog_should_ignore");
    public static final TagKey<EntityType<?>> DROP_SOY_WHEN_DOG_KILL = tagEntity("drop_soy_when_dog_kill");
    public static final TagKey<EntityType<?>> MOB_RETRIEVER_MUST_IGNORE = tagEntity("mob_retriever_must_ignore");

    private static TagKey<Item> tag(String name) {
        return ItemTags.create(Util.getResource(name));
    }

    private static TagKey<EntityType<?>> tagEntity(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, (Util.getResource(name)));
    }
}
