package doggytalents.common.advancements.triggers;

import java.util.Optional;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.DoggyAdvancementTriggers;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.util.Util;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;


public class DogDrunkTrigger extends SimpleCriterionTrigger<DogDrunkTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(AbstractDog dog, ServerPlayer player) {
        this.trigger(player, x -> true);
    }

    public static TriggerInstance getInstance() {
        return new TriggerInstance(Optional.empty());
    }

    public static Criterion<TriggerInstance> getCriterion() {
        return DoggyAdvancementTriggers.DOG_DRUNK_TRIGGER.createCriterion(getInstance());
    }

    public static class TriggerInstance implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<DogDrunkTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
            p_337345_ -> p_337345_.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DogDrunkTrigger.TriggerInstance::player)
            )
            .apply(p_337345_, DogDrunkTrigger.TriggerInstance::new)
        );

        private final Optional<ContextAwarePredicate> player;

        public TriggerInstance(Optional<ContextAwarePredicate> player) {
            this.player = player;
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return player;
        }
        
    }

    

}
