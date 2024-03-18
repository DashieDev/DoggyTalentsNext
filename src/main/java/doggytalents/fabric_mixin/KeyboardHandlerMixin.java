package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.InputEvent;
import net.minecraft.client.KeyboardHandler;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    
    @Inject(at = @At("TAIL"),  method = "keyPress(JIIII)V")
    public void dtn__keyPress(long window, int keyCode, int scanCode, int k, int modifers, CallbackInfo info) {
        EventCallbacksRegistry.postEvent(new InputEvent.Key(keyCode, scanCode, modifers));        
    }

}
