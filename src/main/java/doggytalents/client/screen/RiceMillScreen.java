package doggytalents.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import doggytalents.common.inventory.container.RiceMillMenu;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
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
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(Resources.RICE_MILL_GUI, x, y, 0, 0, this.imageWidth, this.imageHeight);
        blitProgressArrow(graphics);
        blitWaterBucket(graphics);
    }

    private void blitProgressArrow(GuiGraphics graphics) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        var grindProg = this.menu.getGrindProgress();
        var filledArrow = getFilledArrow(grindProg);
        graphics.blit(Resources.RICE_MILL_GUI, x + 79, y + 34, 
            176, 14, filledArrow, 17);
    }

    private int getFilledArrow(float progress) {
        var ret = Mth.ceil(24 * progress);
        return Mth.clamp(ret, 0, 24);
    }

    private void blitWaterBucket(GuiGraphics graphics) {
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
        graphics.blit(Resources.RICE_MILL_GUI, x + 82, y + 29, 
            176, 31, 11, 10);
    }
    
}
