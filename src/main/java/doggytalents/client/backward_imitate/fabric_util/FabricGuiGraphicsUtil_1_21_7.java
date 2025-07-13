package doggytalents.client.backward_imitate.fabric_util;

import doggytalents.fabric_mixin.GuiGraphicsAccessorMixin_1_21_7;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;

public class FabricGuiGraphicsUtil_1_21_7 {
 
    public static ScreenRectangle peekScissorStack(GuiGraphics graphics) {
        return ((GuiGraphicsAccessorMixin_1_21_7) graphics).dtn__getScissorStack().peek();
    }

    public static void submitPictureInPictureRenderState(GuiGraphics graphics, 
        PictureInPictureRenderState renderState) {
        
        ((GuiGraphicsAccessorMixin_1_21_7) graphics).dtn__getGuiRenderState()
            .submitPicturesInPictureState(renderState);
    }

}
