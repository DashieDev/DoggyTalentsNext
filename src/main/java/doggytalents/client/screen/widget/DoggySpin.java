package doggytalents.client.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class DoggySpin extends AbstractWidget {

    private int size;
    public DoggySpin(int x, int y, int size) {
        super(x, y, size, size, Component.empty());
        this.size = size;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        drawSpin(graphics, this.getX(), this.getY(), size);
    }

    private int indx = 0;
    private long lastRender;
    private void drawSpin(GuiGraphics graphics, int x, int y, int size)  {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 7 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 40;
        }
        int uvX = ((int)Mth.floor(indx%7));
        int uvY =  ((int)Mth.floor(indx/7));
        graphics.blit(Resources.SPIN, x, y, 0, uvX * size, uvY* size, size , size, 7 * size, 6 * size);
        RenderSystem.disableBlend();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }
    
}
