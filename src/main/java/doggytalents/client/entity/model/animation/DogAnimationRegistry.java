package doggytalents.client.entity.model.animation;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.api.anim.DogAnimation;
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
        register(DogAnimation.SIT_LOOK_AROUND, DogAnimationSequences.SIT_LOOK_AROUND);
        register(DogAnimation.HOWL, DogAnimationSequences3.HOWL);
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
        register(DogAnimation.TOUCHY_TOUCH, DogAnimationSequences2.TOUCHY_TOUCH);
        register(DogAnimation.DOWN_THE_HOLE, DogAnimationSequences2.DOWN_THE_HOLE);
        register(DogAnimation.REST_BELLY_START, DogAnimationSequences2.REST_BELLY_START);
        register(DogAnimation.REST_BELLY_LOOP, DogAnimationSequences2.REST_BELLY_LOOP);
        register(DogAnimation.REST_BELLY_END, DogAnimationSequences2.REST_BELLY_END);
        register(DogAnimation.NAKEY, DogAnimationSequences3.NAKEY);
        register(DogAnimation.DRUNK_START, DogAnimationSequences3.DRUNK_START);
        register(DogAnimation.DRUNK_LOOP, DogAnimationSequences2.REST_BELLY_LOOP);
        register(DogAnimation.SNIFFER_DOG_POINT_DOWNARD, SnifferDogAnimationSequences.POINT_DOWNARD);
        register(DogAnimation.SNIFFER_DOG_POINT_STRAIGHT, SnifferDogAnimationSequences.POINT_STRAIGHT);
        register(DogAnimation.SNIFFER_DOG_POINT_UPWARD, SnifferDogAnimationSequences.POINT_UPWARD);
        register(DogAnimation.PLAY_WITH_MEH, DogAnimationSequences3.PLAY_WITH_MEH);
        register(DogAnimation.GREET, DogAnimationSequences.GREET);
        register(DogAnimation.SNIFF_AWW_HAPPY, DogAnimationSequences3.SNIFF_AWW_HAPPY);
        register(DogAnimation.FACERUB_START, DogFaceRubAnimationSequences.FACERUB_START);
        register(DogAnimation.FACERUB_PP, DogFaceRubAnimationSequences.FACERUB_PP);
        register(DogAnimation.FACERUB_PP2, DogFaceRubAnimationSequences.FACERUB_PP2);
        register(DogAnimation.FACERUB_P, DogFaceRubAnimationSequences.FACERUB_P);
        register(DogAnimation.FACERUB_P2, DogFaceRubAnimationSequences.FACERUB_P2);
        register(DogAnimation.FACERUB_F, DogFaceRubAnimationSequences.FACERUB_F);
        register(DogAnimation.FACERUB_F2, DogFaceRubAnimationSequences.FACERUB_F2);
        register(DogAnimation.FACERUB_FF, DogFaceRubAnimationSequences.FACERUB_FF);
        register(DogAnimation.FACERUB_FF2, DogFaceRubAnimationSequences.FACERUB_FF2);
        register(DogAnimation.FACERUB_END, DogFaceRubAnimationSequences.FACERUB_END);
        register(DogAnimation.HUG_START, DogHugAnimationSequences.HUG_START);
        register(DogAnimation.HUG_PP, DogHugAnimationSequences.HUG_PP);
        register(DogAnimation.HUG_PP2, DogHugAnimationSequences.HUG_PP2);
        register(DogAnimation.HUG_P, DogHugAnimationSequences.HUG_P);
        register(DogAnimation.HUG_P2, DogHugAnimationSequences.HUG_P2);
        register(DogAnimation.HUG_F, DogHugAnimationSequences.HUG_F);
        register(DogAnimation.HUG_F2, DogHugAnimationSequences.HUG_F2);
        register(DogAnimation.HUG_FF, DogHugAnimationSequences.HUG_FF);
        register(DogAnimation.HUG_FF2, DogHugAnimationSequences.HUG_FF2);
        register(DogAnimation.HUG_END, DogHugAnimationSequences.HUG_END);
        register(DogAnimation.BELLY_PET_START, DogBellyRubPetAnimationSequences.BELLY_START);
        register(DogAnimation.BELLY_PET_PP, DogBellyRubPetAnimationSequences.BELLY_PP);
        register(DogAnimation.BELLY_PET_PP2, DogBellyRubPetAnimationSequences.BELLY_PP2);
        register(DogAnimation.BELLY_PET_P, DogBellyRubPetAnimationSequences.BELLY_P);
        register(DogAnimation.BELLY_PET_P2, DogBellyRubPetAnimationSequences.BELLY_P2);
        register(DogAnimation.BELLY_PET_F, DogBellyRubPetAnimationSequences.BELLY_F);
        register(DogAnimation.BELLY_PET_F2, DogBellyRubPetAnimationSequences.BELLY_F2);
        register(DogAnimation.BELLY_PET_FF, DogBellyRubPetAnimationSequences.BELLY_FF);
        register(DogAnimation.BELLY_PET_FF2, DogBellyRubPetAnimationSequences.BELLY_FF2);
        register(DogAnimation.BELLY_PET_END, DogBellyRubPetAnimationSequences.BELLY_END);
        register(DogAnimation.BACKHUG_START, DogBackHugAnimationSequences.BACKHUG_START);
        register(DogAnimation.BACKHUG_PP, DogFaceRubAnimationSequences.FACERUB_PP);
        register(DogAnimation.BACKHUG_PP2, DogFaceRubAnimationSequences.FACERUB_PP2);
        register(DogAnimation.BACKHUG_P, DogFaceRubAnimationSequences.FACERUB_P);
        register(DogAnimation.BACKHUG_P2, DogFaceRubAnimationSequences.FACERUB_P2);
        register(DogAnimation.BACKHUG_F, DogFaceRubAnimationSequences.FACERUB_F);
        register(DogAnimation.BACKHUG_F2, DogFaceRubAnimationSequences.FACERUB_F2);
        register(DogAnimation.BACKHUG_FF, DogFaceRubAnimationSequences.FACERUB_FF);
        register(DogAnimation.BACKHUG_FF2, DogFaceRubAnimationSequences.FACERUB_FF2);
        register(DogAnimation.BACKHUG_END, DogBackHugAnimationSequences.BACKHUG_END);
    }
    
}
