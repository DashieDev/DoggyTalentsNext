package doggytalents.client.screen.DogNewInfoScreen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import doggytalents.DoggyItems;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.ItemUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class AccessoryStatusHover extends AbstractWidget {

    private ItemStack logoIcon = ItemStack.EMPTY;
    private DogModel.AccessoryState state;

    public AccessoryStatusHover(int x, int y, DogModel.AccessoryState state) {
        super(x, y, 20, 20, Component.empty());
        var collar = DoggyItems.WOOL_COLLAR.get();
        logoIcon = new ItemStack(collar);
        ItemUtil.setDyeColorForStack(logoIcon, 0xFFB02E26);
        if (state == null)
            state = DogModel.AccessoryState.HAVE_NOT_TESTED;
        else
            this.state = state;
        this.setTooltip(getAccTooltip());
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        if (this.logoIcon == ItemStack.EMPTY)
            return;
        graphics.renderItem(logoIcon, this.getX()+1, this.getY()+1);
        int iX = getIconXState();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        graphics.blit(Resources.STYLE_ADD_REMOVE, getX()+11, getY()+11, iX, 0, 9, 9);
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
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
        
    }

    private Tooltip getAccTooltip() {
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
        return Tooltip.create(Component.translatable("doggui.style.skins.accessory_state." + id));
    }
    
}
