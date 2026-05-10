package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;

// renderVehicleHealth and getVehicleMaxHearts removed from Gui in 26.1.2.
// Vehicle health rendering is now handled via RenderGuiLayerEvent.Pre in ClientEventHandler.
@Mixin(Gui.class)
public class GuiMixin {
}
