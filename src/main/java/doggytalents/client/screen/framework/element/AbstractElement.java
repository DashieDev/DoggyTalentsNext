package doggytalents.client.screen.framework.element;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;

//TODO: maybe implements LayoutElement too
public abstract class AbstractElement implements Renderable, ContainerEventHandler, NarratableEntry {

    @Nullable
    private GuiEventListener focused;
    private boolean isDragging;

    private final @Nullable AbstractElement parent;
    private final ArrayList<GuiEventListener> child = new ArrayList<>();
    private final Screen screen;

    private ElementPosition position;
    private ElementSize size;
    private int backgroundColor;

    public AbstractElement(AbstractElement parent, Screen screen) {
        if (this == parent) {
            this.parent = null;
        } else {
            this.parent = parent;
        }

        this.position = ElementPosition.getDefault(this);
        this.size = ElementSize.getDefault(this);
        this.screen = screen;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
    }

    public AbstractElement init() {
        return this;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return this.child;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.backgroundColor != 0) {
            int aX = this.getRealX();
            int aY = this.getRealY();
            graphics.fill(aX, aY, aX + this.getSizeX(), 
                aY + this.getSizeY(), this.backgroundColor);
        }
        this.renderElement(graphics, mouseX, mouseY, partialTicks);
        for (var c : this.child) {
            if (c instanceof Renderable wid)
            wid.render(graphics, mouseX, mouseY, partialTicks);
        }
    }

    /**
     * Never call render() here!!!
     */
    public void renderElement(GuiGraphics stack, int mouseX, int mouseY, float partialTicks) {};

    public AbstractElement setPosition(ElementPosition.PosType type, int left, int top) {
        this.position = new ElementPosition(this, left, top, type);
        return this;
    }
    
    public AbstractElement setSize(int width, int height) {
        this.size = new ElementSize(this, width, height);
        return this;
    }

    public AbstractElement setSize(float ratioX, float ratioY) {
        this.size = new ElementSize(this, ratioX, ratioY);
        return this;
    }

    public AbstractElement setSize(int width, float ratioY) {
        this.size = new ElementSize(this, width, ratioY);
        return this;
    }

    public AbstractElement setSize(float ratioX, int height) {
        this.size = new ElementSize(this, ratioX, height);
        return this;
    }

    public AbstractElement setSize(int size) {
        this.size = new ElementSize(this, size);
        return this;
    }

    public AbstractElement setSizeDynamicX(int sizeY) {
        this.size = ElementSize.createDynamicX(this, sizeY);
        return this;
    }

    public AbstractElement setSizeDynamicY(int sizeX) {
        this.size = ElementSize.createDynamicY(this, sizeX);
        return this;
    }
    
    public AbstractElement setBackgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    public boolean addChildren(GuiEventListener element) {
        
        //Prevent adding mutiple time the same element
        for (var c : this.children()) {
            if (c == element) {
                return false;
            }
        }

        //Should not exist in every parents to prevents loops.
        if (this.checkChildrenExistedInParents(element)) {
            this.child.add(element);
            var pos = this.getSize();
            if (pos.isDynamic()) {
                pos.updateSize();
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean checkChildrenExistedInParents(GuiEventListener component) {
        var p = this.getParent(); 
        while (p != null) {
            if (p == component) {
                return false;
            }
            p = p.getParent();
        }
        return true;
    }

    public @Nullable AbstractElement getParent() {
        return this.parent;
    }

    public ElementPosition getPosition() {
        return this.position;
    }

    public ElementSize getSize() {
        return this.size;
    }

    // public int getRelativeX() {
    //     return this.relativeX;
    // }

    // public int getRelativeY() {
    //     return this.relativeY;
    // }

    // public int getRealX() {
    //     var realX = this.relativeX;
    //     var p = this.getParent();
    //     while(p != null) {
    //         realX += p.getRelativeX();
    //         p = p.getParent();
    //     }
    //     return realX;
    // }

    // public int getRealY() {
    //     var realY = this.relativeY;
    //     var p = this.getParent();
    //     while(p != null) {
    //         realY += p.getRelativeY();
    //         p = p.getParent();
    //     }
    //     return realY;
    // }

    public int getRealX() {
        return this.position.getRealX();
    }

    public int getRealY() {
        return this.position.getRealY();
    }

    public int getSizeX() {
        return this.size.getWidth();
    }

    public int getSizeY() {
        return this.size.getHeight();
    }

    public Screen getScreen() {
        return this.screen;
    }

    public boolean isMouseOver(double x, double y) {
        return x >= (double)this.getRealX() && y >= (double)this.getRealY() && x < (double)(this.getRealX() + this.getSizeX()) && y < (double)(this.getRealY() + this.getSizeY());
    }

    @Override
    public final boolean isDragging() {
        return this.isDragging;
    }
    
    @Override
    public final void setDragging(boolean p_94681_) {
        this.isDragging = p_94681_;
    }
  
    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return getScreen().getFocused();
    }
    
    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        getScreen().setFocused(guiEventListener);
    }
    
}
