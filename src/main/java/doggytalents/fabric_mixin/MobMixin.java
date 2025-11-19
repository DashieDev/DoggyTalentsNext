package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.util.EventBus;
import doggytalents.forge_imitate.event.LivingChangeTargetEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

@Mixin(Mob.class)
public class MobMixin {
    
    @Inject(
        method = "setTarget(Lnet/minecraft/world/entity/LivingEntity;)V", 
        at = @At("HEAD")
    )
    public void dtn__setTarget(LivingEntity target, CallbackInfo info) {
        var self = (Mob)(Object)this;
        EventBus.COMMON_BUS.post(new LivingChangeTargetEvent(self, target));
    }

}
