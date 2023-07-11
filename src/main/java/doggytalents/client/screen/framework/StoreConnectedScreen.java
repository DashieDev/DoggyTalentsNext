package doggytalents.client.screen.framework;

import java.util.List;

import javax.annotation.Nullable;

import doggytalents.ChopinLogger;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
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
        Store.get(this).dispatchAll(
            new UIAction(CommonUIActionTypes.RESIZE, new Object()),
            width, height
        );
    }

    
    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        if (guiEventListener instanceof AbstractElement) {
            //TODO Maybe AbstractElement::canFocus() in the future.
            return;
        } 
        ChopinLogger.l("set focus : " + guiEventListener);
        super.setFocused(guiEventListener);
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        //invalidate focus every click. Prefer focus to be null if the click is outside of any bound.
        this.setFocused(null);
        return super.mouseClicked(p_94695_, p_94696_, p_94697_);
    }

    public List<Class<? extends AbstractSlice>> getSlices() { return List.of(); }

}
