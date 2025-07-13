package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsAccessorMixin_1_21_7 {
    
    @Accessor("scissorStack")
    GuiGraphics.ScissorStack dtn__getScissorStack();

    @Accessor("guiRenderState")
    GuiRenderState dtn__getGuiRenderState();

}
