package doggytalents.client.screen;

import doggytalents.client.entity.render.DogScreenOverlays;
import doggytalents.common.inventory.container.DogArmorContainer;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DogArmorScreen extends AbstractContainerScreen<DogArmorContainer> {

    DogArmorContainer container;

    public DogArmorScreen(DogArmorContainer p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        container = p_97741_;
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
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.DOGGY_ARMOR_GUI, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        ScreenUtil.renderEntityInInventoryFollowsMouse(graphics, x + 85, y + 62, 30, x + 85 - mouseX,
            y + 65 - mouseY, this.container.getDog());
        renderArmorBar(graphics, x + 90, y + 5);
    }

    public void renderArmorBar(GuiGraphicsExtractor graphics, int x, int y) {
        var i3 = this.menu.getDog().getArmorValue();

        for (int k3 = 0; k3 < 10; ++k3) {
            if (i3 > 0) {
               int l3 = x + k3 * 8;
               if (k3 * 2 + 1 < i3) {
                  graphics.blit(RenderPipelines.GUI_TEXTURED, DogScreenOverlays.GUI_ICONS_LOCATION, l3, y, 34.0F, 9.0F, 9, 9, 256, 256);
               }

               if (k3 * 2 + 1 == i3) {
                  graphics.blit(RenderPipelines.GUI_TEXTURED, DogScreenOverlays.GUI_ICONS_LOCATION, l3, y, 25.0F, 9.0F, 9, 9, 256, 256);
               }

               if (k3 * 2 + 1 > i3) {
                  graphics.blit(RenderPipelines.GUI_TEXTURED, DogScreenOverlays.GUI_ICONS_LOCATION, l3, y, 16.0F, 9.0F, 9, 9, 256, 256);
               }
            }
         }
    }

}
