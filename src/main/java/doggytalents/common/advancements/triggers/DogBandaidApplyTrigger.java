package doggytalents.common.advancements.triggers;

import java.util.Optional;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.DoggyAdvancementTriggers;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.util.Util;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class DogBandaidApplyTrigger extends SimpleCriterionTrigger<DogBandaidApplyTrigger.TriggerInstance> {

    public static ResourceLocation ID = Util.getResource("dog_bandaid_apply_trigger");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite player,
            DeserializationContext context) {
        return new TriggerInstance(player);
    }

    public void trigger(AbstractDog dog, ServerPlayer player) {
        this.trigger(player, x -> true);
    }

    public static TriggerInstance getInstance() {
        return new TriggerInstance(EntityPredicate.Composite.ANY);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(EntityPredicate.Composite player) {
            super(ID, player);
        }
        
    }

    

}

