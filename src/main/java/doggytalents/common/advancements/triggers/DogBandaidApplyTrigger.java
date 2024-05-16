package doggytalents.common.advancements.triggers;

import java.util.Optional;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.DoggyAdvancementTriggers;
import doggytalents.api.inferface.AbstractDog;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class DogBandaidApplyTrigger extends SimpleCriterionTrigger<DogBandaidApplyTrigger.TriggerInstance> {

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
        return DoggyAdvancementTriggers.DOG_BANDAID_APPLY_TRIGGER.createCriterion(getInstance());
    }

    public static class TriggerInstance implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<DogBandaidApplyTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
            p_337345_ -> p_337345_.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DogBandaidApplyTrigger.TriggerInstance::player)
            )
            .apply(p_337345_, DogBandaidApplyTrigger.TriggerInstance::new)
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

