package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.common.entity.DogProjectileHitAllyHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;

@Mixin(Projectile.class)
public class ProjectileMixin {
    
    @Inject(at = @At("HEAD"),  method = "canHitEntity(Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    public void dtn__canHitEntity(Entity target, CallbackInfoReturnable<Boolean> info) {
        var self = (Projectile)(Object)this;
        boolean result = DogProjectileHitAllyHandler.onCheckIfCanHitTarget(self, target);
        if (result)
            info.setReturnValue(false);
    }

}
