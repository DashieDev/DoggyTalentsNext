package doggytalents.client.entity.model.animation;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.client.animation.AnimationDefinition;

public class DogAnimationRegistry {

    private static Map<DogAnimation, AnimationDefinition> DEFINITION_MAP
         = Maps.newConcurrentMap();
    
    public static void register(DogAnimation animation, AnimationDefinition sequence) {
        DEFINITION_MAP.putIfAbsent(animation, sequence);
    }

    public static AnimationDefinition getSequence(DogAnimation animation) {
        return DEFINITION_MAP.get(animation);
    }

    public static void init() {
        DEFINITION_MAP = Maps.newConcurrentMap();
        register(DogAnimation.STRETCH, DogAnimationSequences.STRETCH);
    }
    
}
