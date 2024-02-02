package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.MainPanelSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.MainPanelSlice.MainTab;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.TabPanelButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MainViewEntryElement extends AbstractElement {
    static final int BUTTON_HEIGHT = 20;
    static final int BUTTON_SPACING = 2;
    static final int PADDING_TOP = 0;
    static final int PADDING_LEFT = 0;

    public MainViewEntryElement(AbstractElement parent, Screen screen) {
        super(parent, screen);
    }

    @Override
    public AbstractElement init() {
        var activeTab = getStateAndSubscribesTo(MainPanelSlice.class, 
            MainTab.class, MainTab.MAIN);
        var tabs = MainTab.values();
        var tabButtons = new ArrayList<TabPanelButton>();
        for (var tab : tabs) {
            tabButtons.add(
                new TabPanelButton(
                    0, 0, getSizeX(), 
                    BUTTON_HEIGHT, getScreen(),
                    activeTab == tab, 
                    ComponentUtil.translatable(tab.unLocalisedTitle), 
                    MainPanelSlice.class, 
                    tab
                )
            );
            // --buttonsUntilFull;
            // if (buttonsUntilFull <= 0) break;
        }
        int startX = this.getRealX() + PADDING_LEFT;
        int pY = this.getRealY() + PADDING_TOP;
        for (var b : tabButtons) {
            b.x = startX;
            b.y = pY;
            pY += b.getHeight() + BUTTON_SPACING;
            this.addChildren(b);
        }

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        
    }
}
