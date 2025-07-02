package doggytalents.client.backward_imitate;

import java.util.function.Function;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;

public class GuiRenderStateUtil_1_21_7 {
    
    public static void sumbitPIPRenderStateToGuiGraphics(GuiGraphics graphics, 
        Function<ScreenRectangle, ? extends PictureInPictureRenderState> renderStateCreator) {
        var scissor = graphics.peekScissorStack();
        graphics.submitPictureInPictureRenderState(renderStateCreator.apply(scissor));
    }

}
