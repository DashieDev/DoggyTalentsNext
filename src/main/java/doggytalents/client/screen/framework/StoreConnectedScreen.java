package doggytalents.client.screen.framework;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.element.AbstractElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StoreConnectedScreen extends Screen {
    
    protected StoreConnectedScreen(Component title) {
        super(title);
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
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        Store.get(this).update();
        if (doRenderBackground())
            this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, pTicks);
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

}
