package doggytalents;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.common.advancements.triggers.DogBandaidApplyTrigger;
import doggytalents.common.advancements.triggers.DogDrunkTrigger;
import doggytalents.common.advancements.triggers.DogRecoveredTrigger;
import doggytalents.common.advancements.triggers.OokamikazeTrigger;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DoggyAdvancementTriggers {
    
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Constants.MOD_ID);
;
    public static final DogDrunkTrigger DOG_DRUNK_TRIGGER = register("get_dog_drunk", new DogDrunkTrigger());
    public static final OokamikazeTrigger OOKAMIKAZE_TRIGGER  = register("ookamikaze_trigger", new OokamikazeTrigger());
    public static final DogBandaidApplyTrigger DOG_BANDAID_APPLY_TRIGGER  = register("dog_bandaid_apply_trigger", new DogBandaidApplyTrigger());
    public static final DogRecoveredTrigger DOG_RECOVERED_TRIGGER  = register("dog_recovered_trigger", new DogRecoveredTrigger());

    public static <T extends CriterionTrigger<?>> T register(String name, T val) {
        var reg_val = val;
        TRIGGERS.register(name, () -> reg_val);
        return reg_val;
    }


}
