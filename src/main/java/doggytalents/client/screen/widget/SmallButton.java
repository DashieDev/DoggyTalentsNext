package doggytalents.client.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.lib.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.math.MathHelper;

public class SmallButton extends Button {

    public SmallButton(int x, int y, Component text, OnPress onPress) {
        super(x, y, 12, 12, text, onPress);
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
       Minecraft mc = Minecraft.getInstance();
       Font font = mc.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.setShaderTexture(0, Resources.SMALL_WIDGETS);
       int i = this.getTextureY();
       RenderSystem.enableBlend();
       RenderSystem.defaultBlendFunc();
       RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
       blit(stack, this.getX(), this.getY(), 0, i, this.width, this.height); 
       this.renderBg(stack, mc, mouseX, mouseY);
       int j = getFGColor();
       this.drawCenteredString(stack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    private int getTextureY() {
        int i = 1;
        if (!this.active) {
           i = 0;
        } else if (this.isHoveredOrFocused()) {
           i = 2;
        }
  
        return i * 12;
    }

    private int getX() { return this.x; }
    private int getY() { return this.y; }

}
