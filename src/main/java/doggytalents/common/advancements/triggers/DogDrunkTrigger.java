package doggytalents.common.advancements.triggers;

import com.google.gson.JsonObject;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.util.Util;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;


public class DogDrunkTrigger extends SimpleCriterionTrigger<DogDrunkTrigger.TriggerInstance> {
    
    public static ResourceLocation ID = Util.getResource("get_dog_drunk");

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
