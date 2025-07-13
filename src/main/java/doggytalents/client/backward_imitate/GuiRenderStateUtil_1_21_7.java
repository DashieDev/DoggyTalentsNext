package doggytalents.client.backward_imitate;

import java.util.function.Function;

import doggytalents.client.backward_imitate.fabric_util.FabricGuiGraphicsUtil_1_21_7;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;

public class GuiRenderStateUtil_1_21_7 {
    
    public static void sumbitPIPRenderStateToGuiGraphics(GuiGraphics graphics, 
        Function<ScreenRectangle, ? extends PictureInPictureRenderState> renderStateCreator) {
        var scissor = FabricGuiGraphicsUtil_1_21_7.peekScissorStack(graphics);
        FabricGuiGraphicsUtil_1_21_7.submitPictureInPictureRenderState(graphics, renderStateCreator.apply(scissor));
    }

}
