package doggytalents.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.render.DogScreenOverlays;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.container.DogArmorContainer;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;

public class DogArmorScreen extends AbstractContainerScreen<DogArmorContainer> {

    DogArmorContainer container;

    public DogArmorScreen(DogArmorContainer p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        container = p_97741_;
        //TODO Auto-generated constructor stub
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(Resources.DOGGY_ARMOR_GUI, x, y, 0, 0, this.imageWidth, this.imageHeight);
        //TODO 1.19.4 ??
        //Fixed
        ScreenUtil.renderInInventory1_20_2(graphics, x + 85, y + 62, 30, x + 85 - mouseX,
         y + 65 - mouseY, this.container.getDog());
        renderArmorBar(graphics, x + 90, y + 5);

    
    }

    public void renderArmorBar(GuiGraphics graphics, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        var i3 = this.menu.getDog().getArmorValue();
        
        for(int k3 = 0; k3 < 10; ++k3) {
            if (i3 > 0) {
               int l3 = x + k3 * 8;
               if (k3 * 2 + 1 < i3) {
                  graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, l3, y, 34, 9, 9, 9);
               }

               if (k3 * 2 + 1 == i3) {
                  graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION,  l3, y, 25, 9, 9, 9);
               }

               if (k3 * 2 + 1 > i3) {
                  graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION,  l3, y, 16, 9, 9, 9);
               }
            }
         }
    }
    
}
