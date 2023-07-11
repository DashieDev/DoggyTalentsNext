package doggytalents.api.enu.forward_imitate;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;

public class GuiComponentUtil {
    
    public static void enableScissor(int p_239261_, int p_239262_, int p_239263_, int p_239264_) {
        Window window = Minecraft.getInstance().getWindow();
        int i = window.getHeight();
        double d0 = window.getGuiScale();
        double d1 = (double)p_239261_ * d0;
        double d2 = (double)i - (double)p_239264_ * d0;
        double d3 = (double)(p_239263_ - p_239261_) * d0;
        double d4 = (double)(p_239264_ - p_239262_) * d0;
        RenderSystem.enableScissor((int)d1, (int)d2, Math.max(0, (int)d3), Math.max(0, (int)d4));
    }

    public static void disableScissor() {
        RenderSystem.disableScissor();
    }

}
