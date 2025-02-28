package doggytalents.common.util;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;

public class TagUtil {
    
    public static <T> List<T> queryAllValuesForTag(Registry<T> reg, TagKey<T> key) {
        var tag = reg.getTag(key).orElse(null);
        if (tag == null)
            return List.of();
        var ret = tag.stream()
            .map(h -> h.value())
            .collect(Collectors.toList());
        return ret;
    }

}
