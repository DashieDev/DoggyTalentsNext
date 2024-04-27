package doggytalents.common.advancements.triggers;

import com.google.gson.JsonObject;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.util.Util;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;


public class DogDrunkTrigger extends SimpleCriterionTrigger<DogDrunkTrigger.TriggerInstance> {

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player,
            DeserializationContext context) {
        return new TriggerInstance(player);
    }

    public void trigger(AbstractDog dog, ServerPlayer player) {
        this.trigger(player, x -> true);
    }

    public static TriggerInstance getInstance() {
        return new TriggerInstance(ContextAwarePredicate.ANY);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ContextAwarePredicate player) {
            super(ID, player);
        }
        
    }

    

}
