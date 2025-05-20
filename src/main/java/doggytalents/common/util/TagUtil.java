package doggytalents.common.util;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class TagUtil {

    public static final TagKey<EntityType<?>> CAPTURING_NOT_SUPPORTED = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", "capturing_not_supported"));
    
    public static <T> List<T> queryAllValuesForTag(Registry<T> reg, TagKey<T> key) {
        var tag = reg.get(key).orElse(null);
        if (tag == null)
            return List.of();
        var ret = tag.stream()
            .map(h -> h.value())
            .collect(Collectors.toList());
        return ret;
    }
}
