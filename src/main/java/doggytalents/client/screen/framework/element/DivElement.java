package doggytalents.client.screen.framework.element;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;

public class DivElement extends AbstractElement {

    public DivElement(AbstractElement parent, Screen screen) {
        super(parent, screen);
    }

    @Override
    public void renderElement(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
    }
    
}
