package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.forge_imitate.event.EntityTravelToDimensionEvent;
import doggytalents.forge_imitate.event.util.EventBus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    
    @Inject(at = @At("HEAD"),  method = "teleportTo(Lnet/minecraft/server/level/ServerLevel;DDDFF)V")
    public void dtn__teleportTo(ServerLevel level, double x, double y, double z, float f1, float f2, CallbackInfo info) {
        var self = (ServerPlayer)(Object)this;
        if (level != self.level()) {
            EventBus.COMMON_BUS.post(new EntityTravelToDimensionEvent(self, level.dimension()));
        }
    }

    @Inject(at = @At("HEAD"),  method = "changeDimension(Lnet/minecraft/world/level/portal/DimensionTransition;)Lnet/minecraft/world/entity/Entity;")
    public void dtn_changeDimension(DimensionTransition level, CallbackInfoReturnable<Entity> info) {
        var self = (ServerPlayer)(Object)this;
        EventBus.COMMON_BUS.post(new EntityTravelToDimensionEvent(self, level.newLevel().dimension()));
    }


}
