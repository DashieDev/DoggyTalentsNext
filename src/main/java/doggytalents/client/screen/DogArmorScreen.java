package doggytalents.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.inventory.container.DogArmorContainer;
import doggytalents.common.lib.Resources;
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
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.DOGGY_ARMOR_GUI);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(stack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        InventoryScreen.renderEntityInInventory(x + 85, y + 62, 30, x + 85 - mouseX,
            y + 65 - mouseY, this.container.getDog());
        renderArmorBar(stack, x + 90, y + 5);

    
    }

    public void renderArmorBar(PoseStack stack, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);

        var i3 = this.menu.getDog().getArmorValue();
        
        for(int k3 = 0; k3 < 10; ++k3) {
            if (i3 > 0) {
               int l3 = x + k3 * 8;
               if (k3 * 2 + 1 < i3) {
                  this.blit(stack, l3, y, 34, 9, 9, 9);
               }

               if (k3 * 2 + 1 == i3) {
                  this.blit(stack, l3, y, 25, 9, 9, 9);
               }

               if (k3 * 2 + 1 > i3) {
                  this.blit(stack, l3, y, 16, 9, 9, 9);
               }
            }
         }
    }
    
}
