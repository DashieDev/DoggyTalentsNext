package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.forge_imitate.event.EntityTravelToDimensionEvent;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.TeleportTransition;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    
    @Inject(at = @At("HEAD"),  method = "teleport(Lnet/minecraft/world/level/portal/TeleportTransition;)Lnet/minecraft/server/level/ServerPlayer;")
    public void dtn__teleportTo(TeleportTransition transition, CallbackInfoReturnable<ServerPlayer> info) {
        var self = (ServerPlayer)(Object)this;
        if (transition.newLevel() != self.level()) {
            EventCallbacksRegistry.postEvent(new EntityTravelToDimensionEvent(self, transition.newLevel().dimension()));
        }
    }

    // @Inject(at = @At("HEAD"),  method = "changeDimension(Lnet/minecraft/world/level/portal/DimensionTransition;)Lnet/minecraft/world/entity/Entity;")
    // public void dtn_changeDimension(DimensionTransition level, CallbackInfoReturnable<Entity> info) {
    //     var self = (ServerPlayer)(Object)this;
    //     EventCallbacksRegistry.postEvent(new EntityTravelToDimensionEvent(self, level.newLevel().dimension()));
    // }


}
