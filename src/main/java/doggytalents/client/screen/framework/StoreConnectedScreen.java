package doggytalents.client.screen.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
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
        ToolTipOverlayManager.finish();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        Store.get(this).dispatchAll(
            new UIAction(CommonUIActionTypes.RESIZE, new Object())
        );
        this.isResizing = true;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
        Store.get(this).update();
        if (this.isResizing) {
            reRenderRoot();
            this.isResizing = false;
        }
        // Background is already extracted by Screen.extractRenderStateWithTooltipAndSubtitles
        // before extractRenderState is called; calling extractBackground here again would
        // invoke blurBeforeThisStratum() twice, which crashes in 26.1.2.

        for (var renderable : this.renderables) {
            renderable.extractRenderState(graphics, mouseX, mouseY, pTicks);
        }
    }

    
    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        if (guiEventListener instanceof AbstractElement) {
            // AbstractElement::canFocus() could be used here in the future.
            return;
        }
        super.setFocused(guiEventListener);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean flag) {
        //invalidate focus every click. Prefer focus to be null if the click is outside of any bound.
        this.setFocused(null);
        return super.mouseClicked(event, flag);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        for (var child : this.children()) {
            if (child instanceof AbstractElement e) {
                e.keyPressedRegardlessIfFocus(event.key(), event.scancode(), event.modifiers());
            }
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        for (var child : this.children()) {
            if (child instanceof AbstractElement e) {
                e.KeyReleasedRegardlessIfFocus(event.key(), event.scancode(), event.modifiers());
            }
        }
        return super.keyReleased(event);
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

    //1.21+ only
    private void renderDarkBackground_1_21_1_above(GuiGraphicsExtractor graphics) {
        graphics.fill(0, 0, this.width, this.height, 0x40000000);
    }
}
