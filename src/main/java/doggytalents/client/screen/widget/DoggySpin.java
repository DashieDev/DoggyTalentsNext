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
    private Random random = new Random();

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
        int imgeSize = 6 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 35;
        }
        int uvX = ((int)Mth.floor(indx%6));
        int uvY =  ((int)Mth.floor(indx/6));
        graphics.blit(Resources.SPIN, x, y, 0, uvX * size, uvY* size, size , size, 6 * size, 6 * size);
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
            indx = (indx + 1) % 30;
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
        int imgeSize = 9 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 100;
        }
        int uvX = ((int)Mth.floor(indx%9));
        int uvY =  ((int)Mth.floor(indx/9));
        graphics.blit(Resources.SPIN3, x, y, 0, uvX * size, uvY* size, size , size, 9 * size, 12 * size);
        RenderSystem.disableBlend();
    }

    private void drawAmmy(GuiGraphics graphics, int x, int y, int size)  {
        var stack = graphics.pose();
        stack.pushPose();
        stack.scale(1.5f, 1.5f, 1.5f);
        
        x /= 1.5f;
        y /= 1.5f;
        x -= 22;
        y -= 22;
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 6 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 40;
        }
        int uvX = ((int)Mth.floor(indx%6));
        int uvY =  ((int)Mth.floor(indx/6));
        graphics.blit(Resources.SPIN4, x, y, 0, uvX * size, uvY* size, size , size, 6 * size, 7 * size);
        RenderSystem.disableBlend();
        stack.popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }

    public void chooseStyle() {
        float r = random.nextFloat();
        var selected_style = Style.CHOPIN;
        if (r >= 0.5f) {
            selected_style = Style.CHOPIN;
        } else if (r >= 0.27f) {
            selected_style = Style.BACKFLIP;
        } else if (r >= 0.04f) {
            selected_style = Style.SIT;
        } else {
            selected_style = Style.AMMY;
        }
        this.style = selected_style;
        indx = 0;
        lastRender = System.currentTimeMillis();
    }
    
}
