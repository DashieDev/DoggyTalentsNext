package doggytalents.client.screen;

import doggytalents.common.inventory.container.TreatBagContainer;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TreatBagScreen extends AbstractContainerScreen<TreatBagContainer> {

    public TreatBagScreen(TreatBagContainer treatBag, Inventory playerInventory, Component displayName) {
        super(treatBag, playerInventory, displayName, 176, 127);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int var1, int var2) {
        graphics.text(font, this.title.getString(), 10, 8, 4210752, false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.GUI_TREAT_BAG, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }

}
