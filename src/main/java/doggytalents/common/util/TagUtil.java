package doggytalents.common.util;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class TagUtil {

    public static final TagKey<EntityType<?>> CAPTURING_NOT_SUPPORTED = net.neoforged.neoforge.common.Tags.EntityTypes.CAPTURING_NOT_SUPPORTED;
    
    public static <T> List<T> queryAllValuesForTag(Registry<T> reg, TagKey<T> key) {
        var ret = new java.util.ArrayList<T>();
        for (var h : reg.getTagOrEmpty(key)) {
            ret.add(h.value());
        }
        return ret;
    }
}
