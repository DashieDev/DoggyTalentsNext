package doggytalents.client.screen.framework.element;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.util.Mth;

public class ElementLayout {

    private final AbstractElement owner;
    private ElementSize size;
    private ElementPosition position;

    //Root element will bind a callback that set a boolean field in the parent screen
    //that trigger relayout traversal, so we don't have to tranverse the tree every frame.
    private Runnable relayoutNotifier = null;
    private boolean isDirty = false;
    private boolean hasDirtyBranch = false;
    
    private int realX, realY, realW, realH; 

    private int totalChildrenW, totalChildrenH;

    public ElementLayout(AbstractElement owner, ElementSize size, ElementPosition position) {
        this.owner = owner;
        this.size = size;
        this.position = position;
    }

    public void setPosition(ElementPosition position) {
        this.position = position;
        this.markDirty();
    }

    public void setSize(ElementSize size) {
        this.size = size;
        this.markDirty();
    }

    public void calculateLayout(int parentX, int parentY, int parentW, int parentH) {

        if (!this.isDirty && !this.hasDirtyBranch)
            return;

        final var children = getLayoutFromChildren();
        
        if (this.isDirty) {
            if (size instanceof ElementAbsoluteSize absoluteSize) {
                this.realW = absoluteSize.x();
                this.realH = absoluteSize.y();
            } else if (size instanceof ElementRelativeSize relativeSize) {
                this.realW = Mth.floor(parentW * relativeSize.x());
                this.realH = Mth.floor(parentH * relativeSize.y());
            }

            switch (this.position.posType()) {
            case ABSOLUTE:
                this.realX = parentX + this.position.x();
                this.realY = parentY + this.position.y();
                break;
            case FIXED:
                this.realX = this.position.x();
                this.realY = this.position.y();
                break;
            default:
                break;
            }
        }
        
        int pX = 0;
        int pY = 0;

        for (var child_layout : children) {
            var child_pos = child_layout.position;

            boolean calculate_relative = 
                this.isDirty && child_pos.posType() == PosType.RELATIVE;

            if (calculate_relative) {
                child_layout.realX = this.realX + pX + child_pos.x();
                child_layout.realY = this.realY + pY + child_pos.y();
            }

            child_layout.calculateLayout(this.realX, this.realY, this.realW, this.realH);

            if (calculate_relative) {
                if (this.position.childDir() == ChildDirection.COL) {
                    pY += child_layout.realH;
                } else {
                    pX += child_layout.realW;
                }
            }
        }

        if (this.isDirty) {
            this.totalChildrenW = children.stream().map(x -> x.realW).reduce(0, (a, b) -> a + b);
            this.totalChildrenH = children.stream().map(x -> x.realH).reduce(0, (a, b) -> a + b);
        }

        this.isDirty = false;
        this.hasDirtyBranch = false;
    }

    private List<ElementLayout> getLayoutFromChildren() {
        var children = this.owner.children();
        if (children.isEmpty())
            return List.of();
        var ret = new ArrayList<ElementLayout>();
        for (var widget : children) {
            if (!(widget instanceof AbstractElement element))
                continue;
            ret.add(element.getLayout());
        }
        return ret;
    }

    private List<AbstractWidget> getVanillaWidgets() {
        
    }

    private ElementLayout getParentLayout() {
        var parent = this.owner.getParent();
        if (parent == null)
            return null;
        return parent.getLayout();
    }

    public void setReLayoutNotify(Runnable notifier) {
        this.relayoutNotifier = notifier;
    }

    private void notifyRelayout() {
        var target = this.owner;
        var notifier = target.getLayout().relayoutNotifier;
        while (
            notifier == null && target != null 
        ) {
            target = target.getParent();
            notifier = target == null ? null : target.getLayout().relayoutNotifier;
        }

        if (notifier != null)
            notifier.run();
    }

    public void markDirty() {
        this.markDirty(true);
    }

    private void markDirty(boolean notify) {
        this.isDirty = true;
        var parent = getParentLayout();
        if (parent != null) {
            if (this.position.posType() == PosType.RELATIVE) {
                parent.markDirty(false);
            } else {
                parent.markBranchDirty();
            }
        }
        
        for (var children : this.getLayoutFromChildren()) {
            children.markDirty(false);
        }
        
        if (notify)
            this.notifyRelayout();
    }

    private void markBranchDirty() {
        this.hasDirtyBranch = true;
        var parent = getParentLayout();
        if (parent != null) {
            parent.markBranchDirty();
        }
    }

    public void componentDidMount() {
        this.markDirty();
    }

    public void componentAboutToUnmount() {
        var parent = getParentLayout();
        if (parent != null)
            parent.markDirty();
    }

    public int realX() { return this.realX; }
    public int realY() { return this.realY; }
    public int realW() { return this.realW; }
    public int realH() { return this.realH; }
    public int totalChildrenW() { return this.totalChildrenW; }
    public int totalChildrenH() { return this.totalChildrenH; }

    public static sealed interface ElementSize
        permits ElementAbsoluteSize, ElementRelativeSize {}

    public static record ElementAbsoluteSize(int x, int y)
        implements ElementSize {}

    public static record ElementRelativeSize(float x, float y)
        implements ElementSize {}

    public static record ElementPosition(int x, int y, PosType posType, ChildDirection childDir) {}

}