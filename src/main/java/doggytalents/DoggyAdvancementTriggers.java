package doggytalents;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.common.advancements.triggers.DogDrunkTrigger;
import doggytalents.common.util.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.ResourceLocation;

public class DoggyAdvancementTriggers {
    
    private static final Map<ResourceLocation, CriterionTrigger<?>> TRIGGERS = Maps.newHashMap();
    public static final DogDrunkTrigger DOG_DRUNK_TRIGGER = register("get_dog_drunk", new DogDrunkTrigger());

    public static <T extends CriterionTrigger<?>> T register(String name, T p_10596_) {
        var id = Util.getResource(name);
        if (!TRIGGERS.containsKey(id)) {
            TRIGGERS.put(id, p_10596_);
            return p_10596_;
        }
        return null;
    }

    public static void registerAll() {
        for (var x : TRIGGERS.entrySet()) {
            CriteriaTriggers.register(x.getKey().toString(), x.getValue());
        }
    }

}
