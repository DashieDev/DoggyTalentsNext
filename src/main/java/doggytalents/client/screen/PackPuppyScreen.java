package doggytalents.client.screen;

import doggytalents.common.inventory.container.PackPuppyContainer;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class PackPuppyScreen extends AbstractContainerScreen<PackPuppyContainer> {

    public PackPuppyScreen(PackPuppyContainer packPuppy, Inventory playerInventory, Component displayName) {
        super(packPuppy, playerInventory, displayName);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int par1, int par2) {
        graphics.text(font, this.title.getString(), this.imageWidth / 2 - 10, 10, 4210752, false);
        graphics.text(font, this.playerInventoryTitle, 8, this.imageHeight - 96 - 2, 4210752, false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int xMouse, int yMouse, float partialTicks) {
        int l = (this.width - this.imageWidth) / 2;
        int i1 = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.GUI_PACK_PUPPY, l, i1, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        for (int j1 = 0; j1 < 3; j1++)
            for (int k1 = 0; k1 < Mth.clamp(this.getMenu().getDogLevel(), 0, 5); k1++)
                graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.GUI_PACK_PUPPY, l + 78 + 18 * k1, i1 + 9 + 18 * j1 + 15, 197.0F, 2.0F, 18, 18, 256, 256);

        ScreenUtil.renderEntityInInventoryFollowsMouse(graphics, l + 42, i1 + 51, 30, (float)(l + 51) - xMouse, (float)((i1 + 75) - 50) - yMouse, this.getMenu().getDog());
    }
}
