package doggytalents.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.common.inventory.container.FoodBowlContainer;
import doggytalents.common.lib.Resources;
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
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
        renderDogProTip(stack);
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
        this.font.draw(stack, this.title.getString(), 10.0F, 8.0F, 4210752);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.GUI_FOOD_BOWL);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(stack, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    private void renderDogProTip(PoseStack stack) {
        int mX = this.width / 2;
        var title = Component.translatable("block.doggytalents.food_bowl.pro_tip.title")
            .withStyle(Style.EMPTY.withBold(true));
        var desc = Component.translatable("block.doggytalents.food_bowl.pro_tip.desc");
        var max_width = Math.min(360, this.width - 10);
        var desc_lines = font.split(desc, max_width);
        int tX = mX - font.width(title)/2;
        int tY = this.height/2 + 70;
        font.draw(stack, title, tX, tY, 0xffffffff);
        tY += font.lineHeight + 2;
        for (var line : desc_lines) {
            tX = mX - font.width(line)/2;
            font.draw(stack, line, tX, tY, 0xffffffff);
            tY += font.lineHeight + 2;
        }
    }
}
