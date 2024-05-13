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

public class DogRecoveredTrigger extends SimpleCriterionTrigger<DogRecoveredTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(AbstractDog dog, ServerPlayer player, boolean special) {
        this.trigger(player, x -> x.special == special);
    }

    public static TriggerInstance getInstance(boolean special) {
        return new TriggerInstance(Optional.empty(), Optional.of(special));
    }

    public static Criterion<TriggerInstance> getCriterion(boolean special) {
        return DoggyAdvancementTriggers.DOG_RECOVERED_TRIGGER.createCriterion(getInstance(special));
    }

    public static class TriggerInstance implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<DogRecoveredTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
            p_337345_ -> p_337345_.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DogRecoveredTrigger.TriggerInstance::player),
                Codec.BOOL.optionalFieldOf("best_dog_condition").forGetter(DogRecoveredTrigger.TriggerInstance::specialOptional)
            )
            .apply(p_337345_, DogRecoveredTrigger.TriggerInstance::new)
        );

        private final Optional<ContextAwarePredicate> player;
        private final boolean special;

        public TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Boolean> special) {
            this.player = player;
            this.special = special.orElse(false);
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return player;
        }

        public Optional<Boolean> specialOptional() {
            return Optional.of(this.special);
        }
        
    }

    

}

