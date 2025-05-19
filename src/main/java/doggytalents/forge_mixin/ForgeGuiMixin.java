package doggytalents.forge_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.DTNWolfMountCustomGuiOverlay;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;

@Mixin(ForgeGui.class)
public class ForgeGuiMixin {
    
    @Inject(
        remap = false,
        at = @At("HEAD"),  
        method = "renderHealthMount(IILnet/minecraft/client/gui/GuiGraphics;)V", cancellable = true)
    protected void dtn__renderVehicleHealth(int width, int height, GuiGraphics graphics, CallbackInfo info) {
        var self = (ForgeGui)(Object)this;
        if (DTNWolfMountCustomGuiOverlay.onRenderVehicleHealth(graphics, self))
            info.cancel();
    }

}
