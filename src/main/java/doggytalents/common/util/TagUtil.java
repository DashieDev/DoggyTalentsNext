package doggytalents.common.util;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraft.world.entity.EntityType;

public class TagUtil {

    public static final TagKey<EntityType<?>> CAPTURING_NOT_SUPPORTED = TagKey.create(Registries.ENTITY_TYPE, Util.getResource("c", "capturing_not_supported"));
    
    public static <T> List<T> queryAllValuesForTag(IForgeRegistry<T> reg, TagKey<T> key) {
        var tag = reg.tags().getTag(key);
        if (tag == null)
            return List.of();
        var ret = tag.stream()
            .map(h -> h)
            .collect(Collectors.toList());
        return ret;
    }
}
