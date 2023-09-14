package doggytalents.client.screen.widget;

import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
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
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        switch(this.style) {
        default:
            drawSpin(stack, this.x, this.y, size);
            break;
        case BACKFLIP:
            drawSpin2(stack, this.x, this.y, size);
            break;
        case SIT:
            drawSpin3(stack, this.x, this.y, size);
            break;
        case AMMY:
            drawAmmy(stack, this.x, this.y, size);
            break;
        }
    }

    private int indx = 0;
    private long lastRender;
    private void drawSpin(PoseStack stack, int x, int y, int size)  {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.SPIN);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 7 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 40;
        }
        int uvX = ((int)Mth.floor(indx%7));
        int uvY =  ((int)Mth.floor(indx/7));
        blit(stack, x, y, 0, uvX * size, uvY* size, size , size, 7 * size, 6 * size);
        RenderSystem.disableBlend();
    }

    private void drawSpin2(PoseStack stack, int x, int y, int size)  {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.SPIN2);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 7 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 35;
        }
        int uvX = ((int)Mth.floor(indx%7));
        int uvY =  ((int)Mth.floor(indx/7));
        blit(stack, x, y, 0, uvX * size, uvY* size, size , size, 7 * size, 5 * size);
        RenderSystem.disableBlend();
    }

    private void drawSpin3(PoseStack stack, int x, int y, int size)  {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.SPIN3);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 7 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 120;
        }
        int uvX = ((int)Mth.floor(indx%7));
        int uvY =  ((int)Mth.floor(indx/7));
        blit(stack, x, y, 0, uvX * size, uvY* size, size , size, 7 * size, 18 * size);
        RenderSystem.disableBlend();
    }

    private void drawAmmy(PoseStack stack, int x, int y, int size)  {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.SPIN4);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 7 * size;
        if (System.currentTimeMillis() - lastRender > 50) {
            lastRender = System.currentTimeMillis();
            indx = (indx + 1) % 50;
        }
        int uvX = ((int)Mth.floor(indx%7));
        int uvY =  ((int)Mth.floor(indx/7));
        blit(stack, x, y, 0, uvX * size, uvY* size, size , size, 7 * size, 8 * size);
        RenderSystem.disableBlend();
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
    }

    public void chooseStyle() {
        int r = new Random().nextInt(8);
        switch (r) {
        default:
            this.style = Style.CHOPIN;
            break;
        case 0:
        case 1:
            this.style = Style.BACKFLIP;
            break;
        case 2:
            this.style = Style.SIT;
            break;
        case 3:
            this.style = Style.AMMY;
            break;
        }
        indx = 0;
        lastRender = System.currentTimeMillis();
    }
    
}
