package doggytalents.client.entity.model.animation;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.api.anim.DogAnimation;
import net.minecraft.client.animation.AnimationDefinition;

public class DogAnimationRegistry {

    private static Map<DogAnimation, AnimationDefinition> DEFINITION_MAP
         = Maps.newHashMap();
    private static AnimationDefinition SLOW_TROT_ANIM = null;
    private static AnimationDefinition GALLOP_ANIM = null;
    
    public static void register(DogAnimation animation, AnimationDefinition sequence) {
        DEFINITION_MAP.putIfAbsent(animation, sequence);
    }

    public static void update(Map<DogAnimation, AnimationDefinition> newMap) {
        DEFINITION_MAP = new HashMap<>(newMap);
        SLOW_TROT_ANIM = newMap.get(DogAnimation.SLOW_TROT);
        GALLOP_ANIM = newMap.get(DogAnimation.GALLOP);
    }

    public static AnimationDefinition getSequence(DogAnimation animation) {
        return DEFINITION_MAP.get(animation);
    }

    public static AnimationDefinition getSlowTrot() {
        return SLOW_TROT_ANIM;
    } 

    public static AnimationDefinition getGallop() {
        return GALLOP_ANIM;
    }

    public static void init() {
    }
    
}
