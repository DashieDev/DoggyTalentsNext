package doggytalents.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.inventory.container.DoggyToolsMenu;
import doggytalents.common.lib.Resources;
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
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack stack, int var1, int var2) {
        this.font.draw(stack, this.title.getString(), 10.0F, 8.0F, 4210752);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.DOGGY_TOOLS_GUI);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(stack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        
        //blit slots
        int slotTexX = 1, slotTexY = 128;
        int mX = x + 89;
        int aY = y + 22;
        int toolsSize = this.container.getToolsSize();
        int toolsSlotsOffsetX = toolsSize/2*18 + toolsSize%2*9;
        int pX = mX - toolsSlotsOffsetX;
        
        for (int i = 0; i < toolsSize; ++i) {
            this.blit(stack, pX, aY, slotTexX, slotTexY, 18, 18);
            pX += 18;
        }
    }
    
    
}
