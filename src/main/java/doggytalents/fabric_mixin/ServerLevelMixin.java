package doggytalents.fabric_mixin;

import java.util.function.BooleanSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.SleepFinishedTimeEvent;
import net.minecraft.server.level.ServerLevel;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    
    @Inject(
        method = "tick(Ljava/util/function/BooleanSupplier;)V", 
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V",
            ordinal = 0
        ),
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/server/players/SleepStatus;areEnoughDeepSleeping(ILjava/util/List;)Z",
                ordinal = 0
            ),
            to = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/server/level/ServerLevel;wakeUpAllPlayers()V",
                ordinal = 0
            )
        )
    )
    public void dtn__tick(BooleanSupplier booleanSupplier, CallbackInfo info) {
        var self = (ServerLevel)(Object)this;
        var event = new SleepFinishedTimeEvent(self);
        EventCallbacksRegistry.postEvent(event);
    }

}
