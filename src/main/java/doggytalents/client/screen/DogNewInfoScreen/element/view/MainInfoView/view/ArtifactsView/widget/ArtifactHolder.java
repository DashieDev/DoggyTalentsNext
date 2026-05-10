package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView.widget;

import javax.annotation.Nonnull;

import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ChangeArtifactData;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import doggytalents.common.network.PacketDistributor;

public class ArtifactHolder extends AbstractWidget {

    Dog dog;

    public static final int ITEM_SIZE_ORG = 16;
    public static final int WIDGET_SIZE = 18;
    public static final int ICON_ADD_X = 11;

    private static final int BKGCOL_ADD = 0x57009e05;

    ItemStack itemStack = ItemStack.EMPTY;
    int inventorySlotId = 0;

    public ArtifactHolder(int x, int y, Dog dog) {
        super(x, y, WIDGET_SIZE, WIDGET_SIZE, Component.empty());
        this.dog = dog;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
        this.active = !this.itemStack.isEmpty();
        if (!this.active) return;
        if (this.isHovered) {
            graphics.fill(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, BKGCOL_ADD);
        }

        graphics.item(itemStack, this.getX()+1, this.getY()+1);
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.STYLE_ADD_REMOVE, getX()+14, getY()+14, (float)ICON_ADD_X, 0f, 9, 9, 256, 256);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean flag) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new ChangeArtifactData(this.dog.getId(), true, inventorySlotId));
    }

    public void setStack(@Nonnull ItemStack stack) {
        this.itemStack = stack;
    }

    public int getInventorySlotId() {
        return this.inventorySlotId;
    }

    public void setInventorySlotId(int id) {
        this.inventorySlotId = id;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }

}
