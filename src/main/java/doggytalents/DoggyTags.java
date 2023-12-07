package doggytalents;

import doggytalents.common.util.Util;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class DoggyTags {

    public static TagKey<Item> BEG_ITEMS_TAMED = tag("beg_items_tamed");
    public static TagKey<Item> BEG_ITEMS_UNTAMED = tag("beg_items_untamed");
    public static TagKey<Item> BREEDING_ITEMS = tag("breeding_items");
    public static TagKey<Item> PACK_PUPPY_BLACKLIST = tag("pack_puppy_blacklist");
    public static TagKey<Item> TREATS = tag("treats");
    public static TagKey<Item> DOG_BOOSTING_FOOD = tag("dog_boosting_food");

    public static TagKey<EntityType<?>> DOG_SHOULD_IGNORE = tagEntity("dog_should_ignore");

    private static TagKey<Item> tag(String name) {
        return ItemTags.create(Util.getResource(name));
    }

    private static TagKey<EntityType<?>> tagEntity(String name) {
        return TagKey.create(ForgeRegistries.Keys.ENTITY_TYPES, Util.getResource(name));
    } 
}
