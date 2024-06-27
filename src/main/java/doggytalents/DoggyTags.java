package doggytalents;

import doggytalents.common.util.Util;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class DoggyTags {

    public static TagKey<Item> BEG_ITEMS_TAMED = tag("beg_items_tamed");
    public static TagKey<Item> BEG_ITEMS_UNTAMED = tag("beg_items_untamed");
    public static TagKey<Item> BREEDING_ITEMS = tag("breeding_items");
    public static TagKey<Item> PACK_PUPPY_BLACKLIST = tag("pack_puppy_blacklist");
    public static TagKey<Item> TREATS = tag("treats");
    public static TagKey<Item> WHITELIST_FOOD = tag("whitelist_food");
    public static TagKey<Item> DOGGY_TOOLS_BLACKLIST = tag("doggy_tools_blacklist");

    public static TagKey<EntityType<?>> DOG_SHOULD_IGNORE = tagEntity("dog_should_ignore");
    public static TagKey<EntityType<?>> DROP_SOY_WHEN_DOG_KILL = tagEntity("drop_soy_when_dog_kill");
    public static TagKey<EntityType<?>> MOB_RETRIEVER_MUST_IGNORE = tagEntity("mob_retriever_must_ignore");

    private static TagKey<Item> tag(String name) {
        return ItemTags.create(Util.getResource(name));
    }

    private static TagKey<EntityType<?>> tagEntity(String name) {
        return ForgeRegistries.ENTITY_TYPES.tags()
            .createTagKey(Util.getResource(name));
    }
}
