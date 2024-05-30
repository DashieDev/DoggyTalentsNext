package doggytalents.common.advancements.triggers;

import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.DoggyAdvancementTriggers;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.util.Util;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class DogRecoveredTrigger extends SimpleCriterionTrigger<DogRecoveredTrigger.TriggerInstance> {

    public static ResourceLocation ID = Util.getResource("dog_recovered_trigger");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player,
            DeserializationContext context) {
        var special = json.get("best_dog_condition").getAsBoolean();
        return new TriggerInstance(player, Optional.of(special));
    }

    public void trigger(AbstractDog dog, ServerPlayer player, boolean special) {
        this.trigger(player, x -> x.special == special);
    }
    
    public static TriggerInstance getInstance(boolean special) {
        return new TriggerInstance(ContextAwarePredicate.ANY, Optional.of(special));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final boolean special;

        public TriggerInstance(ContextAwarePredicate player, Optional<Boolean> special) {
            super(ID, player);
            this.special = special.orElse(false);
        }

        public Optional<Boolean> specialOptional() {
            return Optional.of(this.special);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext p_16979_) {
            var json = super.serializeToJson(p_16979_);
            json.add("best_dog_condition", new JsonPrimitive(this.special));
            return json;
        }
        
    }

    

}

