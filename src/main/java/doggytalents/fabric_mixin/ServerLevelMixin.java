package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.SleepFinishedTimeEvent;
import net.minecraft.server.level.ServerLevel;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    
    @Inject(method = "wakeUpAllPlayers()V", at = @At("TAIL"))
    public void dtn__wakeUpAllPlayers(CallbackInfo info) {
        var self = (ServerLevel)(Object)this;
        if (!self.isDay())
            return;
        var event = new SleepFinishedTimeEvent(self);
        EventCallbacksRegistry.postEvent(event);
    }

}
