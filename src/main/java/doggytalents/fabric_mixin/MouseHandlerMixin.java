package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.backward_imitate.GuiUtil_1_21_9;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.InputEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    
    @Inject(at = @At("TAIL"), method = "onButton(JLnet/minecraft/client/input/MouseButtonInfo;I)V")
    public void dtn__onButton(long x, MouseButtonInfo button_info_1_21_10, int action, CallbackInfo info) {
        var button = button_info_1_21_10.button();
        
        var mc = Minecraft.getInstance();
        var option = mc.options;
        if (!GuiUtil_1_21_9.matchesMouse(option.keyUse, button))
            return;
        var event = new InputEvent.MouseButton.Pre(button, action);
        EventCallbacksRegistry.postEvent(event);
        if (event.isCanceled()) {
            option.keyUse.consumeClick();
            option.keyUse.setDown(false);
        }
    }

}
