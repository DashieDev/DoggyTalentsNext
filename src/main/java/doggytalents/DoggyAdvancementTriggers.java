package doggytalents;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.common.advancements.triggers.DogBandaidApplyTrigger;
import doggytalents.common.advancements.triggers.DogDrunkTrigger;
import doggytalents.common.advancements.triggers.DogRecoveredTrigger;
import doggytalents.common.advancements.triggers.OokamikazeTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.ResourceLocation;

public class DoggyAdvancementTriggers {
    
    private static final Map<ResourceLocation, CriterionTrigger<?>> TRIGGERS = Maps.newHashMap();
    public static final DogDrunkTrigger DOG_DRUNK_TRIGGER = register(new DogDrunkTrigger());
    public static final OokamikazeTrigger OOKAMIKAZE_TRIGGER  = register(new OokamikazeTrigger());
    public static final DogBandaidApplyTrigger DOG_BANDAID_APPLY_TRIGGER  = register(new DogBandaidApplyTrigger());
    public static final DogRecoveredTrigger DOG_RECOVERED_TRIGGER  = register(new DogRecoveredTrigger());

    public static <T extends CriterionTrigger<?>> T register(T p_10596_) {
        if (!TRIGGERS.containsKey(p_10596_.getId())) {
            TRIGGERS.put(p_10596_.getId(), p_10596_);
            return p_10596_;
        }
        return null;
    }

    public static void registerAll() {
        for (var x : TRIGGERS.entrySet()) {
            CriteriaTriggers.register(x.getValue());
        }
    }

}
