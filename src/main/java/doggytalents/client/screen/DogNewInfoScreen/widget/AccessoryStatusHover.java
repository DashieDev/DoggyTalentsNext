package doggytalents.client.screen.DogNewInfoScreen.widget;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyItems;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.common.lib.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class AccessoryStatusHover extends AbstractWidget {

    private ItemStack logoIcon = ItemStack.EMPTY;
    private DogModel.AccessoryState state;
    private Component statusTooltip = Component.empty();
    private ItemRenderer itemRenderer;

    public AccessoryStatusHover(int x, int y, DogModel.AccessoryState state) {
        super(x, y, 20, 20, Component.empty());
        var collar = DoggyItems.WOOL_COLLAR.get();
        logoIcon = new ItemStack(collar);
        collar.setColor(logoIcon, 0xFFB02E26);
        if (state == null)
            state = DogModel.AccessoryState.HAVE_NOT_TESTED;
        else
            this.state = state;
        getAccTooltip();
        itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        if (this.isHovered) {
            ToolTipOverlayManager.get().setComponents(List.of(this.statusTooltip));
        }
        if (this.logoIcon == ItemStack.EMPTY)
            return;
        itemRenderer.renderGuiItem(logoIcon, this.getX()+1, this.getY()+1);
        int iX = getIconXState();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.setShaderTexture(0, Resources.STYLE_ADD_REMOVE);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        blit(stack, getX()+11, getY()+11, iX, 0, 9, 9);
    }

    private int getIconXState() {
        switch (state) {
            case HAVE_NOT_TESTED:
                return 33;
            case NON_COMPATIBLE:
                return 44;
            case RECOMMENDED:
                return 55;
            case SOME_WILL_FIT:
                return 22;
            default:
                return 33;
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput p_259858_) {
        
    }

    private void getAccTooltip() {
        int id = 0;
        switch (state) {
            case HAVE_NOT_TESTED:
                id = 0;
                break;
            case NON_COMPATIBLE:
                id = 1;
                break;
            case RECOMMENDED:
                id = 2;
                break;
            case SOME_WILL_FIT:
                id = 3;
                break;
            default:
                break;
        }
        this.statusTooltip = (Component.translatable("doggui.style.skins.accessory_state." + id));
    }

    private int getX() { return this.x; }
    private int getY() { return this.y; }

}
