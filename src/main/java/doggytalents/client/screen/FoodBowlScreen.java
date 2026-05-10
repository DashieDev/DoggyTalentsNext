package doggytalents.client.screen;

import doggytalents.common.inventory.container.FoodBowlContainer;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;

public class FoodBowlScreen extends AbstractContainerScreen<FoodBowlContainer> {

    public FoodBowlScreen(FoodBowlContainer foodBowl, Inventory playerInventory, Component displayName) {
        super(foodBowl, playerInventory, displayName, 176, 127);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.GUI_FOOD_BOWL, x, y, 0f, 0f, this.imageWidth, this.imageHeight, 256, 256);
        super.extractContents(graphics, mouseX, mouseY, partialTicks);
        renderDogProTip(graphics);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        graphics.text(font, this.title.getString(), 10, 8, 4210752, false);
    }

    private void renderDogProTip(GuiGraphicsExtractor graphics) {
        int mX = this.width / 2;
        var title = Component.translatable("block.doggytalents.food_bowl.pro_tip.title")
            .withStyle(Style.EMPTY.withBold(true));
        var desc = Component.translatable("block.doggytalents.food_bowl.pro_tip.desc");
        var max_width = Math.min(360, this.width - 10);
        var desc_lines = font.split(desc, max_width);
        int tX = mX - font.width(title)/2;
        int tY = this.height/2 + 70;
        graphics.text(font, title, tX, tY, 0xffffffff);
        tY += font.lineHeight + 2;
        for (var line : desc_lines) {
            tX = mX - font.width(line)/2;
            graphics.text(font, line, tX, tY, 0xffffffff);
            tY += font.lineHeight + 2;
        }
    }
}
