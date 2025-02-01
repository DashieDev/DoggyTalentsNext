package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.PlayerWakeUpEvent;
import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public class PlayerMixin {
    
    @Inject(method = "stopSleepInBed(ZZ)V", at = @At("HEAD"))
    public void dtn__stopSleepInBed(boolean wake_immediately, boolean update_level, CallbackInfo info) {
        var self = (Player)(Object)this;
        var event = new PlayerWakeUpEvent(self);
        EventCallbacksRegistry.postEvent(event);
    }

}
