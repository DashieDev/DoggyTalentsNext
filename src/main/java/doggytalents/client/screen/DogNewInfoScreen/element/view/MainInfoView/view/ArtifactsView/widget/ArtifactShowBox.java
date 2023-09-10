package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView.widget;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ChangeAccessoriesData;
import doggytalents.common.network.packet.data.ChangeArtifactData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.PacketDistributor;

public class ArtifactShowBox extends AbstractWidget {

    private ItemRenderer itemRenderer;
    private Dog dog;

    public static final int ITEM_SIZE_ORG = 32;
    public static final int WIDGET_SIZE = 36;
    public static final int ICON_REM_X = 0;
    private static final int BKGCOL_REM = 0x579c0202;
    private static final int BKGCOL = 0x48a6a6a6;
    private static final int TXTCOL = 0x68a6a6a6;

    private ItemStack itemStack = ItemStack.EMPTY;
    private final int order;
    private final Font font;

    public ArtifactShowBox(int x, int y, ItemRenderer renderer, Dog dog, int order) {
        super(x, y, WIDGET_SIZE, WIDGET_SIZE, ComponentUtil.empty());
        this.itemRenderer = renderer;
        this.dog = dog;
        this.order = order;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        this.active = !this.itemStack.isEmpty();
        fill(stack, this.x, this.y, this.x+this.width, this.y+this.height, BKGCOL);
        if (!this.active) {
            var order_str = "" + (this.order + 1);
            int order_width = font.width(order_str);
            font.draw(stack, order_str, 
                this.x + this.width/2 - order_width/2,
                this.y + this.height/2 - font.lineHeight/2, TXTCOL);
            return;
        }
        if (this.isHovered) {
            int bkg_col = BKGCOL_REM;
            fill(stack, this.x, this.y, this.x+this.width, this.y+this.height, bkg_col);
        } else {
            fill(stack, this.x, this.y, this.x+this.width, this.y+this.height, BKGCOL);
        }
        
        graphics.renderItem(itemStack, Mth.floor((this.getX() + this.width/2 - 8)), Mth.floor((this.getY() + this.height/2 - 8)));
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Resources.STYLE_ADD_REMOVE);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int iX = ICON_REM_X;
        this.blit(stack, x+this.width - 2, y+this.height -2, iX, 0, 9, 9);
    }

    @Override
    public void onClick(double x, double y) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
            new ChangeArtifactData(this.dog.getId(), false, order));
    }

    public void setStack(@Nonnull ItemStack stack) {
        this.itemStack = stack;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
        // TODO Auto-generated method stub
        
    }
    
}
