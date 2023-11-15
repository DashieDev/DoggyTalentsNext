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
        register(DogAnimation.BELLY_RUB, DogAnimationSequences.BELLY_RUB);
        register(DogAnimation.SIT_IDLE_2, DogAnimationSequences.SIT_IDLE_2);
        register(DogAnimation.HOWL, DogAnimationSequences.HOWL);
        register(DogAnimation.LIE_DOWN_IDLE, DogAnimationSequences.LIE_DOWN_IDLE);
        register(DogAnimation.SIT_TO_REST, DogAnimationSequences2.SIT_TO_REST);
        register(DogAnimation.REST_IDLE, DogAnimationSequences2.REST_IDLE);
        register(DogAnimation.REST_TO_SIT, DogAnimationSequences2.REST_TO_SIT);
        register(DogAnimation.FLY_JUMP_START, DogAnimationSequences2.FLY_JUMP_START);
        register(DogAnimation.FLY_AIR_BOURNE, DogAnimationSequences2.FLY_AIR_BOURNE);
        register(DogAnimation.FLY_LANDING, DogAnimationSequences2.FLY_LANDING);
        register(DogAnimation.TOUCH_RETREAT, DogAnimationSequences2.TOUCH_RETREAT);
        register(DogAnimation.SNIFF_HOT, DogAnimationSequences2.SNIFF_HOT);
        register(DogAnimation.SNIFF_NEUTRAL, DogAnimationSequences2.SNIFF_NEUTRAL);
        register(DogAnimation.SNIFF_SNEEZE, DogAnimationSequences2.SNIFF_SNEEZE);
        register(DogAnimation.STOP_DROP_ROLL, DogAnimationSequences2.STOP_DROP_ROLL);
    }
    
}
