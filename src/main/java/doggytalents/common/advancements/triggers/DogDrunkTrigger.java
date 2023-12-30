package doggytalents.common.advancements.triggers;

import java.util.Optional;

import com.google.gson.JsonObject;

import doggytalents.DoggyAdvancementTriggers;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.util.Util;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;


public class DogDrunkTrigger extends SimpleCriterionTrigger<DogDrunkTrigger.TriggerInstance> {

    @Override
    protected TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player,
            DeserializationContext context) {
        return new TriggerInstance(player);
    }

    public void trigger(AbstractDog dog, ServerPlayer player) {
        this.trigger(player, x -> true);
    }

    public static Criterion<DogDrunkTrigger.TriggerInstance> getInstance() {
        return DoggyAdvancementTriggers.DOG_DRUNK_TRIGGER.createCriterion(new TriggerInstance(Optional.empty()));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(Optional<ContextAwarePredicate> player) {
            super(player);
        }
        
    }

    

}
