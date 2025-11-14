package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.InputEvent;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    
    @Inject(at = @At("TAIL"),  method = "keyPress(JILnet/minecraft/client/input/KeyEvent;)V")
    public void dtn__keyPress(long window, int k, KeyEvent event_1_21_10, CallbackInfo info) {
        int keyCode = event_1_21_10.key(); int scanCode = event_1_21_10.scancode(); int modifers = event_1_21_10.modifiers();

        EventCallbacksRegistry.postEvent(new InputEvent.Key(keyCode, scanCode, modifers));        
    }

}
