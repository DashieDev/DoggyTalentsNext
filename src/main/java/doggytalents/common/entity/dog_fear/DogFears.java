package doggytalents.common.entity.dog_fear;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class DogFears {
    
    public static final Map<ResourceLocation, DogFear> ALL_FEARS = Maps.newHashMap();
    public static final DogFear THUNDER = register("thunder");
    public static final DogFear FIRE = register("fire");
    public static final DogFear LAVA = register("lava");
    public static final DogFear EXPLOSION = register("explosion");

    public static DogFear register(String id) {
        return register(Util.getResource(id));
    }

    public static DogFear register(ResourceLocation id) {
        var fear = new DogFear();
        ALL_FEARS.put(id, fear);
        return fear;
    }


}
