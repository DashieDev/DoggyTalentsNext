package doggytalents.client.screen;

import doggytalents.common.inventory.container.RiceMillMenu;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class RiceMillScreen extends AbstractContainerScreen<RiceMillMenu> {

    private RiceMillMenu menu;

    public RiceMillScreen(RiceMillMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.menu = menu;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.RICE_MILL_GUI, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        blitProgressArrow(graphics);
        blitWaterBucket(graphics);
    }

    private void blitProgressArrow(GuiGraphicsExtractor graphics) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        var grindProg = this.menu.getGrindProgress();
        var filledArrow = getFilledArrow(grindProg);
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.RICE_MILL_GUI, x + 79, y + 34, 176.0F, 14.0F, filledArrow, 17, 256, 256);
    }

    private int getFilledArrow(float progress) {
        var ret = Mth.ceil(24 * progress);
        return Mth.clamp(ret, 0, 24);
    }

    private void blitWaterBucket(GuiGraphicsExtractor graphics) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        var clientMill = this.menu.getClientMill();
        if (clientMill == null)
            return;
        if (clientMill.isRemoved())
            return;
        var isSpinning = clientMill.isSpinning();
        if (!isSpinning)
            return;
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.RICE_MILL_GUI, x + 82, y + 29, 176.0F, 31.0F, 11, 10, 256, 256);
    }

}
