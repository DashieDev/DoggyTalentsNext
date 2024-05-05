package doggytalents;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.common.advancements.triggers.DogDrunkTrigger;
import doggytalents.common.advancements.triggers.OokamikazeTrigger;
import doggytalents.common.lib.Constants;
import doggytalents.forge_imitate.registry.DeferredRegister;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class DoggyAdvancementTriggers {
    
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(() -> BuiltInRegistries.TRIGGER_TYPES, Constants.MOD_ID);
;
    public static final DogDrunkTrigger DOG_DRUNK_TRIGGER = register("get_dog_drunk", new DogDrunkTrigger());
    public static final OokamikazeTrigger OOKAMIKAZE_TRIGGER  = register("ookamikaze_trigger", new OokamikazeTrigger());

    public static <T extends CriterionTrigger<?>> T register(String name, T val) {
        var reg_val = val;
        TRIGGERS.register(name, () -> reg_val);
        return reg_val;
    }


}

