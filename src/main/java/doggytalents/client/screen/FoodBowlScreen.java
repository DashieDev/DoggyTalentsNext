package doggytalents.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.common.inventory.container.FoodBowlContainer;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;

public class FoodBowlScreen extends AbstractContainerScreen<FoodBowlContainer> {

    public FoodBowlScreen(FoodBowlContainer foodBowl, Inventory playerInventory, Component displayName) {
        super(foodBowl, playerInventory, displayName);
        this.imageHeight = 127;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
        renderDogProTip(graphics);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, this.title.getString(), 10, 8, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(Resources.GUI_FOOD_BOWL, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    private void renderDogProTip(GuiGraphics graphics) {
        int mX = this.width / 2;
        var title = Component.translatable("block.doggytalents.food_bowl.pro_tip.title")
            .withStyle(Style.EMPTY.withBold(true));
        var desc = Component.translatable("block.doggytalents.food_bowl.pro_tip.desc");
        var max_width = Math.min(360, this.width - 10);
        var desc_lines = font.split(desc, max_width);
        int tX = mX - font.width(title)/2;
        int tY = this.height/2 + 70;
        graphics.drawString(font, title, tX, tY, 0xffffffff);
        tY += font.lineHeight + 2;
        for (var line : desc_lines) {
            tX = mX - font.width(line)/2;
            graphics.drawString(font, line, tX, tY, 0xffffffff);
            tY += font.lineHeight + 2;
        }
    }
}
