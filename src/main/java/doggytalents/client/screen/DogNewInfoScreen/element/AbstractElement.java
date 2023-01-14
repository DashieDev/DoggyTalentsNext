package doggytalents.client.screen.DogNewInfoScreen.element;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AbstractElement extends GuiComponent implements Widget, ContainerEventHandler, NarratableEntry {

    @Nullable
    private GuiEventListener focused;
    private boolean isDragging;

    private final @Nullable AbstractElement parent;
    private final ArrayList<GuiEventListener> child = new ArrayList<>();
    private final Screen screen;
    private int relativeX;
    private int relativeY;
    private int sizeX;
    private int sizeY;

    public AbstractElement(AbstractElement parent, int rX, int rY, int sizeX, int sizeY, Screen screen) {
        if (this == parent) {
            this.parent = null;
        } else {
            this.parent = parent;
        }
        this.relativeX = rX;
        this.relativeY = rY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.screen = screen;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
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
    public final void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderElement(stack, mouseX, mouseY, partialTicks);
        for (var c : this.child) {
            if (c instanceof Widget wid)
            wid.render(stack, mouseX, mouseY, partialTicks);
        }
    }

    /**
     * Never call render() here!!!
     */
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {}

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

    public AbstractElement getParent() {
        return this.parent;
    }

    public int getRelativeX() {
        return this.relativeX;
    }

    public int getRelativeY() {
        return this.relativeY;
    }

    public int getRealX() {
        var realX = this.relativeX;
        var p = this.getParent();
        while(p != null) {
            realX += p.getRelativeX();
            p = p.getParent();
        }
        return realX;
    }

    public int getRealY() {
        var realY = this.relativeY;
        var p = this.getParent();
        while(p != null) {
            realY += p.getRelativeY();
            p = p.getParent();
        }
        return realY;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public Screen getScreen() {
        return this.screen;
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
        return this.focused;
    }
    
    @Override
    public void setFocused(@Nullable GuiEventListener p_94677_) {
        this.focused = p_94677_;
    }
    
}
