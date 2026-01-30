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
    private AlignMode alignMode = AlignMode.RIGHT_BOTTOM;
    private Screen screen;
    private boolean holdInflate;
    private long inflateAnim = 0;
    private final long MAX_INFLATE_ANIM = 50;
    private long prevAnimUpdateMillis = 0;
    private boolean startedAnimate = false;

    public ScrollBar(int x, int y, int w, int h, Direction dir, int barsize, Screen screen) {
        super(x, y, w, h, Component.empty());
        this.barSize = dir == Direction.VERTICAL ? Math.min(h, barsize)
            : Math.min(w, barsize);
        this.dir = dir;
        this.screen = screen;
        prevAnimUpdateMillis = System.currentTimeMillis();
    }

    public static enum Direction { VERTICAL, HORIZONTAL }
    public static enum AlignMode { CENTER, LEFT_TOP, RIGHT_BOTTOM }

    public ScrollBar alignMode(AlignMode mode) {
        this.alignMode = mode;
        return this;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        if (!startedAnimate) {
            startedAnimate = true;
            prevAnimUpdateMillis = System.currentTimeMillis();
        }
        long currentTimeMillis = System.currentTimeMillis();
        long passedAnimMillis = currentTimeMillis - prevAnimUpdateMillis;
        if (passedAnimMillis > 0) {
            prevAnimUpdateMillis = currentTimeMillis;
        }
        if (this.holdInflate) {
            this.holdInflate = screen.isDragging();
        }
        if (passedAnimMillis > 0) {
            if (!this.isHovered && !holdInflate) {
                this.inflateAnim -= passedAnimMillis;
            } else {
                this.inflateAnim += passedAnimMillis;
            }
        }
        if (this.inflateAnim < 0) {
            this.inflateAnim = 0;
        }
        if (this.inflateAnim > MAX_INFLATE_ANIM) {
            this.inflateAnim = MAX_INFLATE_ANIM;
        }

        final int BASE_THICK = 3;
        float inflateAnimProgress = ((float) this.inflateAnim) / MAX_INFLATE_ANIM;
        
        int maxThickness = (this.dir == Direction.VERTICAL) ? this.getWidth() : this.getHeight();
        
        int currentThickness = (int) Mth.lerp(inflateAnimProgress, BASE_THICK, maxThickness);

        int fill_x = this.getX();
        int fill_y = this.getY();
        int fill_w = (this.dir == Direction.VERTICAL) ? currentThickness : this.getWidth();
        int fill_h = (this.dir == Direction.VERTICAL) ? this.getHeight() : currentThickness;

        if (this.dir == Direction.VERTICAL) {
            switch (this.alignMode) {
                case RIGHT_BOTTOM:
                    fill_x = this.getX() + this.getWidth() - currentThickness;
                    break;
                case CENTER:
                    fill_x = this.getX() + this.getWidth()/2 - currentThickness/2;
                    break;   
                default:
                    fill_x = this.getX();
                    break;
            }
        } else {
            switch (this.alignMode) {
                case RIGHT_BOTTOM:
                    fill_y = this.getY() + this.getHeight() - currentThickness;
                    break;
                case CENTER:
                    fill_y = this.getY() + (this.getHeight() - currentThickness) / 2;
                    break;
                default:
                    fill_y = this.getY();
                    break;
            }
        }

        int barOffset = Mth.floor(this.barOffset);

        //Track
        graphics.fill(fill_x, fill_y, fill_x + fill_w, fill_y + fill_h, 0x87363636);

        //Handle
        if (this.dir == Direction.VERTICAL) {
            graphics.fill(fill_x, fill_y + barOffset, 
                fill_x + fill_w, fill_y + barOffset + this.barSize, 0xffffffff);
        } else {
            graphics.fill(fill_x + barOffset, fill_y, 
                fill_x + barOffset + this.barSize, fill_y + fill_h, 0xffffffff);
        }
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dY, double dX) {
        holdInflate = true;
        if (!checkAndHandleMouseOutBound(mouseX, mouseY))
            return;
        double offset = this.dir == Direction.VERTICAL ?
            dX : dY;
        if (offset == 0) 
            return;
        offsetBar(offset);
        onValueUpdated();
    }

    private boolean checkAndHandleMouseOutBound(double mouseX, double mouseY) {
        if (mouseX < this.getX()) {
            if (this.barOffset > 0) {
                setBarOffset(0);
                onValueUpdated();
            }
            return false;
        }   
        if (mouseX > this.getX() + this.width) {
            if (this.barOffset < this.getMaxOffsetValue()) {
                setBarOffset(getMaxOffsetValue());
                onValueUpdated();
            }
            return false;
        }
        return true;
    }

    private void offsetBar(double offset) {
        this.barOffset = Mth.clamp(barOffset + offset, 0, getMaxOffsetValue());
    }

    public void setBarOffset(double offset) {
        this.barOffset = Mth.clamp(offset, 0, getMaxOffsetValue());
    }

    public int getMaxOffsetValue() {
        var ret = 
            this.dir == Direction.VERTICAL ?
            this.height - barSize
            : this.width - barSize;
        if (ret < 0) ret = 0;
        return ret;
    }

    public double getProgressValue() {
        int maxOffset = getMaxOffsetValue();
        if (maxOffset <= 0)
            return 0;
        return barOffset/ (double)maxOffset;
    }

    public void setProgressValue(float progress) {
        progress = Mth.clamp(progress, 0.0f, 1.0f);
        this.setBarOffset(progress * this.getMaxOffsetValue());
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
