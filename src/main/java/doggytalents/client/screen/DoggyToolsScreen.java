package doggytalents.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.inventory.container.DoggyToolsMenu;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DoggyToolsScreen extends AbstractContainerScreen<DoggyToolsMenu> {

    DoggyToolsMenu container;

    public DoggyToolsScreen(DoggyToolsMenu toolsMenu, Inventory inventory, Component component) {
        super(toolsMenu, inventory, component);
        container = toolsMenu;
        this.imageHeight = 127;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        p_281635_.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(Resources.DOGGY_TOOLS_GUI, x, y, 0, 0, this.imageWidth, this.imageHeight);
        
        //blit slots
        int slotTexX = 1, slotTexY = 128;
        int mX = x + 89;
        int aY = y + 22;
        int toolsSize = this.container.getToolsSize();
        int toolsSlotsOffsetX = toolsSize/2*18 + toolsSize%2*9;
        int pX = mX - toolsSlotsOffsetX;
        
        for (int i = 0; i < toolsSize; ++i) {
            graphics.blit(Resources.DOGGY_TOOLS_GUI, pX, aY, slotTexX, slotTexY, 18, 18);
            pX += 18;
        }
    }
    
    
}
