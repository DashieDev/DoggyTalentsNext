package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.widget;

import javax.annotation.Nonnull;

import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ChangeAccessoriesData;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import doggytalents.common.network.PacketDistributor;

public class AccessoryHolder extends AbstractWidget {

    Dog dog;

    public static final int ITEM_SIZE_ORG = 16;
    public static final int WIDGET_SIZE = 18;
    public static final int ICON_ADD_X = 11;
    public static final int ICON_REM_X = 0;
    public static final int ICON_WARN_X = 22;

    private static final int BKGCOL_ADD = 0x57009e05;
    private static final int BKGCOL_REM = 0x579c0202;

    ItemStack itemStack = ItemStack.EMPTY;
    boolean add;
    int inventorySlotId = 0;
    public boolean warning;

    public AccessoryHolder(int x, int y, Dog dog, boolean add) {
        super(x, y, WIDGET_SIZE, WIDGET_SIZE, Component.empty());
        this.add = add;
        this.dog = dog;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
        this.active = !this.itemStack.isEmpty();
        if (!this.active) return;
        if (this.isHovered) {
            int bkg_col = this.add ? BKGCOL_ADD : BKGCOL_REM;
            graphics.fill(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, bkg_col);
        }

        graphics.item(itemStack, this.getX()+1, this.getY()+1);
        int iX = add ? ICON_ADD_X : ICON_REM_X;
        if (warning) iX = ICON_WARN_X;
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.STYLE_ADD_REMOVE, getX()+14, getY()+14, (float)iX, 0f, 9, 9, 256, 256);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean flag) {
        if (this.itemStack == null || this.itemStack.isEmpty())
            return;
        if (this.itemStack.is(Items.WOLF_ARMOR)) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(),
                new ChangeAccessoriesData(this.dog.getId(), add, inventorySlotId, true));
            return;
        }
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new ChangeAccessoriesData(this.dog.getId(), add, inventorySlotId));
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
