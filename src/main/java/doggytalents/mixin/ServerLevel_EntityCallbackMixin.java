package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.DoggyEntityTypes;
import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.Entity;


@Mixin(targets = "net/minecraft/server/level/ServerLevel$EntityCallbacks")
public class ServerLevel_EntityCallbackMixin {
    
    @Inject(at = @At("HEAD"),  method = "onTrackingStart(Lnet/minecraft/world/entity/Entity;)V", cancellable = false)
    public void dtn__onTrackingStart(Entity entity, CallbackInfo info) {
        if (entity.getType() != DoggyEntityTypes.DOG.get())
            return;
        if (!(entity instanceof Dog dog))
            return;
        dog.dogTrackingTracker.setTracking(true);
        dog.onDogStartTracking();
    }

    @Inject(at = @At("HEAD"),  method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V", cancellable = false)
    public void dtn__onTrackingEnd(Entity entity, CallbackInfo info) {
        if (entity.getType() != DoggyEntityTypes.DOG.get())
            return;
        if (!(entity instanceof Dog dog))
            return;
        dog.dogTrackingTracker.setTracking(false);
        dog.onDogStopTracking();
    }

}
