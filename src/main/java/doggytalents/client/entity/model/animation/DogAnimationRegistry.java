package doggytalents.client.entity.model.animation;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.api.enu.forward_imitate.anim.AnimationDefinition;
import doggytalents.common.entity.anim.DogAnimation;

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
        register(DogAnimation.FAINT, DogAnimationSequences.FAINT);
        register(DogAnimation.FAINT_2, DogAnimationSequences.FAINT2);
        register(DogAnimation.SIT_DOWN, DogAnimationSequences.SIT2);
        register(DogAnimation.STAND_UP, DogAnimationSequences.STAND);
        register(DogAnimation.LYING_DOWN, DogAnimationSequences.LIE_DOWN);
        register(DogAnimation.STAND_QUICK, DogAnimationSequences.STAND_QUCK);
        register(DogAnimation.DROWN, DogAnimationSequences.DROWN);
        register(DogAnimation.HURT_1, DogAnimationSequences.HURT_1);
        register(DogAnimation.HURT_2, DogAnimationSequences.HURT_2);
        register(DogAnimation.FAINT_STAND_1, DogAnimationSequences.FAINT_STAND_1);
        register(DogAnimation.FAINT_STAND_2, DogAnimationSequences.FAINT_STAND_2);
        register(DogAnimation.BACKFLIP, DogAnimationSequences.BACKFLIP);
        register(DogAnimation.PROTEST, DogAnimationSequences.PROTEST);
        register(DogAnimation.STAND_IDLE_2, DogAnimationSequences.STAND_IDLE_2);
        register(DogAnimation.DIG, DogAnimationSequences.DIG);
        register(DogAnimation.SIT_IDLE, DogAnimationSequences.SIT_IDLE);
        register(DogAnimation.SCRATCHIE, DogAnimationSequences.SCRATCHIE);
        register(DogAnimation.CHOPIN_TAIL, DogAnimationSequences.CHOPIN_TAIL);
    }
    
}
