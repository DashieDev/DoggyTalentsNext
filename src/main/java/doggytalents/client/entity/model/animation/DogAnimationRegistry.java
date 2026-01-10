package doggytalents.client.entity.model.animation;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.api.anim.DogAnimation;
import net.minecraft.client.animation.AnimationDefinition;

public class DogAnimationRegistry {

    private static Map<DogAnimation, AnimationDefinition> DEFINITION_MAP
         = Maps.newHashMap();
    
    public static void register(DogAnimation animation, AnimationDefinition sequence) {
        DEFINITION_MAP.putIfAbsent(animation, sequence);
    }

    public static void update(Map<DogAnimation, AnimationDefinition> newMap) {
        DEFINITION_MAP = new HashMap<>(newMap);
    }

    public static AnimationDefinition getSequence(DogAnimation animation) {
        return DEFINITION_MAP.get(animation);
    }

    public static void init() {
    }
    
}
