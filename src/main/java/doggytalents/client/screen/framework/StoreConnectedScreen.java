package doggytalents.client.screen.framework;

import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import net.minecraft.client.Minecraft;
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
            new UIAction(UIActionTypes.RESIZE, new Object()),
            width, height
        );
    }

}
