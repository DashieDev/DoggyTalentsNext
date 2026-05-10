package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView.widget;

import javax.annotation.Nonnull;

import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ChangeArtifactData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import doggytalents.common.network.PacketDistributor;

public class ArtifactShowBox extends AbstractWidget {

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

    public ArtifactShowBox(int x, int y, Dog dog, int order) {
        super(x, y, WIDGET_SIZE, WIDGET_SIZE, Component.empty());
        this.dog = dog;
        this.order = order;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
        this.active = !this.itemStack.isEmpty();
        graphics.fill(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, BKGCOL);
        if (!this.active) {
            var order_str = "" + (this.order + 1);
            int order_width = font.width(order_str);
            graphics.text(font, order_str,
                this.getX() + this.width/2 - order_width/2,
                this.getY() + this.height/2 - font.lineHeight/2, TXTCOL);
            return;
        }
        if (this.isHovered) {
            graphics.fill(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, BKGCOL_REM);
        } else {
            graphics.fill(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, BKGCOL);
        }

        graphics.item(itemStack, Mth.floor((this.getX() + this.width/2 - 8)), Mth.floor((this.getY() + this.height/2 - 8)));
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.STYLE_ADD_REMOVE, this.getX()+this.width - 2, getY()+this.height - 2, (float)ICON_REM_X, 0f, 9, 9, 256, 256);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean flag) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new ChangeArtifactData(this.dog.getId(), false, order));
    }

    public void setStack(@Nonnull ItemStack stack) {
        this.itemStack = stack;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }

}
