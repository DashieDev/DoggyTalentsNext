package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.InputEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    
    @Inject(at = @At("TAIL"), method = "onPress(JIII)V")
    public void dtn__onPress(long x, int button, int action, int a, CallbackInfo info) {
        var mc = Minecraft.getInstance();
        var option = mc.options;
        if (!option.keyUse.matchesMouse(button))
            return;
        var event = new InputEvent.MouseButton.Pre(button, action);
        EventCallbacksRegistry.postEvent(event);
        if (event.isCancelled()) {
            option.keyUse.consumeClick();
            option.keyUse.setDown(false);
        }
    }

}
