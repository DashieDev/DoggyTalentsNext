package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.common.entity.Dog;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

@Mixin(ServerLevel.EntityCallbacks.class)
public class ServerLevel_EntityCallbackMixin {

    @Inject(at = @At("RETURN"),  method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V")
    public void dtn__onTrackingEnd(Entity entity, 
        CallbackInfo info) {
        if (entity instanceof Dog dog) {
            dog.setAddedToWorld(false);
            dog.onRemovedFromWorld();
        }
    }

}
