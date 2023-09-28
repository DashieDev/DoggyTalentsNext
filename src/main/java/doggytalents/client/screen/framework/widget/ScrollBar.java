package doggytalents.client.screen.framework.widget;

import doggytalents.client.screen.framework.element.AbstractElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ScrollBar extends AbstractWidget {

    private int barSize;
    private double barOffset;
    private Direction dir;

    public ScrollBar(int x, int y, int w, int h, Direction dir, int barsize) {
        super(x, y, w, h, Component.empty());
        this.barSize = dir == Direction.VERTICAL ? Math.min(h, barsize)
            : Math.min(w, barsize);
        this.dir = dir;
    }

    public static enum Direction { VERTICAL, HORIZONTAL }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        int barOffset = Mth.floor(this.barOffset);
        if (!this.isHovered) {
            final int thick = 3;
            if (this.dir == Direction.VERTICAL) 
                graphics.fill(this.getX() + this.width - thick, this.getY(), this.getX()+this.width, this.getY()+this.height, 0x87363636);
            else
                graphics.fill( this.getX(), this.getY() + this.height - thick, this.getX()+this.width, this.getY()+this.height, 0x87363636);
            if (this.dir == Direction.VERTICAL) {
                graphics.fill(this.getX() + this.getWidth() - thick, this.getY() + barOffset, 
                    this.getX()+this.getWidth(), this.getY() + barOffset+this.barSize, 0xffffffff);
            } else {
                graphics.fill(this.getX() + barOffset, this.getY() + this.getHeight() - thick, 
                    this.getX() + barOffset + this.barSize, this.getY() + this.getHeight(), 0xffffffff);
            }
            return;
        }
        graphics.fill( this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, 0x87363636);
        if (this.dir == Direction.VERTICAL) {
            graphics.fill(this.getX(), this.getY() + barOffset, 
                this.getX()+this.getWidth(), this.getY() + barOffset+this.barSize, 0xffffffff);
        } else {
            graphics.fill(this.getX() + barOffset, this.getY(), 
                this.getX() + barOffset + this.barSize, this.getY() + this.getHeight(), 0xffffffff);
        }
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dY, double dX) {
        double offset = this.dir == Direction.VERTICAL ?
            dX : dY;
        if (offset == 0) 
            return;
        offsetBar(offset);
        onValueUpdated();
    }

    private void offsetBar(double offset) {
        this.barOffset = Mth.clamp(barOffset + offset, 0, getMaxOffsetValue());
    }

    public void setBarOffset(double offset) {
        this.barOffset = Mth.clamp(offset, 0, getMaxOffsetValue());
    }

    public int getMaxOffsetValue() {
        return
            this.dir == Direction.VERTICAL ?
            this.height - barSize
            : this.width - barSize;
    }

    public double getProgressValue() {
        int maxOffset = getMaxOffsetValue();
        if (maxOffset <= 0)
            return 0;
        return barOffset/ (double)maxOffset;
    }

    public void onValueUpdated() {

    }

    public void setBarSize(int size) {
        this.barSize = size;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
        
    }
}
