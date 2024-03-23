package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.ProjectileImpactEvent;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.phys.HitResult;

@Mixin(ThrownEgg.class)
public class ThrownEggMixin {
    
    @Inject(at = @At("HEAD"),  method = "onHit(Lnet/minecraft/world/phys/HitResult;)V")
    public void dtn__onHit(HitResult hitResult, CallbackInfo info) {
        var self = (ThrownEgg)(Object)this;
        EventCallbacksRegistry.postEvent(new ProjectileImpactEvent(self, hitResult));
    }

}
