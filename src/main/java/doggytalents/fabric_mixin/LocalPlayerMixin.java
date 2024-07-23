package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.MovementInputUpdateEvent;
import net.minecraft.client.player.LocalPlayer;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    
    @Inject(
        method = "aiStep()V", 
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/Input;tick(ZF)V",
            ordinal = 0,
            shift = At.Shift.AFTER
        )
    )
    public void dtn__aiStep(CallbackInfo info) {
        var self = (LocalPlayer)(Object) this;
        var input = self.input;
        EventCallbacksRegistry.postEvent(new MovementInputUpdateEvent(input, self));
    }

}
