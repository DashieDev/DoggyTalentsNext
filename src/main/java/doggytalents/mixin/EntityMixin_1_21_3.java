package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import doggytalents.DoggyEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

@Mixin(Entity.class)
public class EntityMixin_1_21_3 {
    
    //Workaround for player no longer be able to force the Dog on their head in 1.21.3+
    //thanks to the canSerialize check.
    @Redirect(
        method = "startRiding(Lnet/minecraft/world/entity/Entity;Z)Z", 
        at = @At(
            value = "INVOKE", 
            target = "Lnet/minecraft/world/entity/EntityType;canSerialize()Z"
        )
    )
    private boolean dtn__startRiding__canSerialize_bypass(EntityType<?> vehicle_type) {
        var self = (Entity)(Object)this;
        var rider_type = self.getType();
        boolean dog_attempting_to_ride_player = 
            rider_type == DoggyEntityTypes.DOG.get()
            && vehicle_type == EntityType.PLAYER;
        if (!dog_attempting_to_ride_player)
            return vehicle_type.canSerialize();
        return true;
    }

}
