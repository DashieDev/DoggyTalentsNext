package doggytalents.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.render.RenderUtil;
import doggytalents.common.inventory.container.RiceMillMenu;
import doggytalents.common.lib.Resources;
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
    public void render(PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack graphics, float partialTicks, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        RenderUtil.blit_for_1_19_2below(this, graphics, Resources.RICE_MILL_GUI, x, y, 0, 0, this.imageWidth, this.imageHeight, true);
        blitProgressArrow(graphics);
        blitWaterBucket(graphics);
    }

    private void blitProgressArrow(PoseStack graphics) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        var grindProg = this.menu.getGrindProgress();
        var filledArrow = getFilledArrow(grindProg);
        RenderUtil.blit_for_1_19_2below(this, graphics, Resources.RICE_MILL_GUI, x + 79, y + 34, 
            176, 14, filledArrow, 17, true);
    }

    private int getFilledArrow(float progress) {
        var ret = Mth.ceil(24 * progress);
        return Mth.clamp(ret, 0, 24);
    }

    private void blitWaterBucket(PoseStack graphics) {
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
        RenderUtil.blit_for_1_19_2below(this, graphics, Resources.RICE_MILL_GUI, x + 82, y + 29, 
            176, 31, 11, 10, true);
    }
    
}
