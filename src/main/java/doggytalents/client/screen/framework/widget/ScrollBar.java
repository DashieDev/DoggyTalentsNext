package doggytalents.client.screen.framework.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.element.AbstractElement;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ScrollBar extends AbstractWidget {

    private int barSize;
    private double barOffset;
    private Direction dir;
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

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        if (!startedAnimate) {
            startedAnimate = true;
            prevAnimUpdateMillis = System.currentTimeMillis();
        }
        long currentTimeMillis = System.currentTimeMillis();
        long passedAnimMillis = currentTimeMillis - prevAnimUpdateMillis;
        if (passedAnimMillis > 0) {
            prevAnimUpdateMillis = currentTimeMillis;
        }
        int barOffset = Mth.floor(this.barOffset);
        if (this.holdInflate) {
            this.holdInflate = screen.isDragging();
        }
        final int BASE_THICK = 3;
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

        float inflateAnimProgress = ((float) this.inflateAnim) / MAX_INFLATE_ANIM;
        int maxBarThick = this.dir == Direction.VERTICAL ?
            this.getWidth() : this.getHeight();
        int barThickAfterAnim = BASE_THICK + Mth.ceil(inflateAnimProgress*(maxBarThick));
        if (this.dir == Direction.VERTICAL) 
            fill(stack, this.getX() + this.width - barThickAfterAnim, this.getY(), this.getX()+this.width, this.getY()+this.height, 0x87363636);
        else
            fill(stack,  this.getX(), this.getY() + this.height - barThickAfterAnim, this.getX()+this.width, this.getY()+this.height, 0x87363636);
        if (this.dir == Direction.VERTICAL) {
            fill(stack, this.getX() + this.getWidth() - barThickAfterAnim, this.getY() + barOffset, 
                this.getX()+this.getWidth(), this.getY() + barOffset+this.barSize, 0xffffffff);
        } else {
            fill(stack, this.getX() + barOffset, this.getY() + this.getHeight() - barThickAfterAnim, 
                this.getX() + barOffset + this.barSize, this.getY() + this.getHeight(), 0xffffffff);
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

    //Imitate
    private int getX() {
        return this.x;
    }

    private int getY() {
        return this.y;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
    }
}
