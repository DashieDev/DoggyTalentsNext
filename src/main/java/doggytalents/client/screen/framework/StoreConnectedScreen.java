package doggytalents.client.screen.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StoreConnectedScreen extends Screen implements IStoreSubscriber {

    private final ArrayList<Class<? extends AbstractSlice>> subscribedTo = new ArrayList<>();
    private boolean isResizing = false;
    protected AbstractElement rootView;
    
    protected StoreConnectedScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        this.reRenderRoot();
    }

    private AbstractElement createRootView() {
        return new RootView(null, this, this::renderRootView)
            .setPosition(PosType.ABSOLUTE, 0, 0)
            .setSize(this.width, this.height)
            .init();
    }

    public void renderRootView(AbstractElement rootView) {

    }

    @Override
    public void removed() {
        super.removed();
        Store.finish();
    }

    @Override
    public void resize(Minecraft p_96575_, int width, int height) {
        this.width = width;
        this.height = height;
        Store.get(this).dispatchAll(
            new UIAction(CommonUIActionTypes.RESIZE, new Object())
        );
        this.isResizing = true;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        Store.get(this).update();
        if (this.isResizing) {
            reRenderRoot();
            this.isResizing = false;
        }
        if (doRenderBackground())
            this.renderBackground(graphics, mouseX, mouseY, pTicks);
        super.render(graphics, mouseX, mouseY, pTicks);
    }

    
    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        if (guiEventListener instanceof AbstractElement) {
            //TODO Maybe AbstractElement::canFocus() in the future.
            return;
        }
        super.setFocused(guiEventListener);
    }

    @Override
    protected void renderBlurredBackground(float p_330683_) {
        
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        //invalidate focus every click. Prefer focus to be null if the click is outside of any bound.
        this.setFocused(null);
        return super.mouseClicked(p_94695_, p_94696_, p_94697_);
    }

    @Override
    public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
        for (var child : this.children()) {
            if (child instanceof AbstractElement e) {
                e.keyPressedRegardlessIfFocus(p_96552_, p_96553_, p_96554_);
            }
        }
        return super.keyPressed(p_96552_, p_96553_, p_96554_);
    }
    
    @Override
    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
        for (var child : this.children()) {
            if (child instanceof AbstractElement e) {
                e.KeyReleasedRegardlessIfFocus(p_94715_, p_94716_, p_94717_);
            }
        }
        return super.keyReleased(p_94715_, p_94716_, p_94717_);
    }

    public boolean doRenderBackground() { return true; }

    public List<Class<? extends AbstractSlice>> getSlices() { return List.of(); }

    @Override
    public void onStoreUpdated(List<Class<? extends AbstractSlice>> changedSlices) {
        if (this.isResizing)
            return;
        this.rootView.onStoreUpdated(changedSlices);
    }

    public void reRenderRoot() {
        this.setFocused(null);
        this.clearWidgets();
        this.rootView = createRootView();
        this.addRenderableWidget(this.rootView);
    }

    private static class RootView extends AbstractElement {

        private Consumer<AbstractElement> reRenderer;

        public RootView(AbstractElement parent, Screen screen, Consumer<AbstractElement> reRenderer) {
            super(parent, screen);
            this.reRenderer = reRenderer;
        }

        @Override
        public AbstractElement init() {
            this.reRenderer.accept(this);
            return this;
        }
        
    }
}
