package doggytalents.client.screen;

import doggytalents.common.inventory.container.DoggyToolsMenu;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DoggyToolsScreen extends AbstractContainerScreen<DoggyToolsMenu> {

    DoggyToolsMenu container;

    public DoggyToolsScreen(DoggyToolsMenu toolsMenu, Inventory inventory, Component component) {
        super(toolsMenu, inventory, component, 176, 127);
        container = toolsMenu;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor p_281635_, int p_282681_, int p_283686_) {
        p_281635_.text(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.DOGGY_TOOLS_GUI, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        //blit slots
        int slotTexX = 1, slotTexY = 128;
        int mX = x + 89;
        int aY = y + 22;
        int toolsSize = this.container.getToolsSize();
        int toolsSlotsOffsetX = toolsSize/2*18 + toolsSize%2*9;
        int pX = mX - toolsSlotsOffsetX;

        for (int i = 0; i < toolsSize; ++i) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.DOGGY_TOOLS_GUI, pX, aY, (float) slotTexX, (float) slotTexY, 18, 18, 256, 256);
            pX += 18;
        }
    }


}
