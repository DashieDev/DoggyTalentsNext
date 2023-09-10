package doggytalents.client.screen.widget;

import java.util.Random;

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
    private static enum Style { CHOPIN, BACKFLIP, SIT, AMMY }
    private Style style = Style.CHOPIN;

    public DoggySpin(int x, int y, int size) {
        super(x, y, size, size, Component.empty());
        this.size = size;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        switch(this.style) {
        default:
            drawSpin(graphics, this.getX(), this.getY(), size);
            break;
        case BACKFLIP:
            drawSpin2(graphics, this.getX(), this.getY(), size);
            break;
        case SIT:
            drawSpin3(graphics, this.getX(), this.getY(), size);
            break;
        case AMMY:
            drawAmmy(graphics, this.getX(), this.getY(), size);
            break;
        }
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

    private void drawSpin2(GuiGraphics graphics, int x, int y, int size)  {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 7 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 35;
        }
        int uvX = ((int)Mth.floor(indx%7));
        int uvY =  ((int)Mth.floor(indx/7));
        graphics.blit(Resources.SPIN2, x, y, 0, uvX * size, uvY* size, size , size, 7 * size, 5 * size);
        RenderSystem.disableBlend();
    }

    private void drawSpin3(GuiGraphics graphics, int x, int y, int size)  {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 7 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 120;
        }
        int uvX = ((int)Mth.floor(indx%7));
        int uvY =  ((int)Mth.floor(indx/7));
        graphics.blit(Resources.SPIN3, x, y, 0, uvX * size, uvY* size, size , size, 7 * size, 18 * size);
        RenderSystem.disableBlend();
    }

    private void drawAmmy(GuiGraphics graphics, int x, int y, int size)  {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 7 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 50;
        }
        int uvX = ((int)Mth.floor(indx%7));
        int uvY =  ((int)Mth.floor(indx/7));
        graphics.blit(Resources.SPIN4, x, y, 0, uvX * size, uvY* size, size , size, 7 * size, 8 * size);
        RenderSystem.disableBlend();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }

    public void chooseStyle() {
        int r = new Random().nextInt(8);
        switch (r) {
        default:
            this.style = Style.CHOPIN;
            break;
        case 0:
            this.style = Style.BACKFLIP;
            break;
        case 1:
            this.style = Style.SIT;
            break;
        case 2:
            this.style = Style.AMMY;
            break;
        }
        indx = 0;
        lastRender = System.currentTimeMillis();
    }
    
}
