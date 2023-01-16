package doggytalents.client.screen.DogNewInfoScreen.element;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.store.AbstractUIAction;
import doggytalents.client.screen.DogNewInfoScreen.store.ActiveTabSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.Store;
import doggytalents.client.screen.DogNewInfoScreen.widget.NavBarButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class DogInfoNavBarElement extends AbstractElement {

    private static final int BUTTON_SPACING = 10;

    public DogInfoNavBarElement(AbstractElement parent, Screen screen) {
        super(parent, screen);
    } 

    @Override
    public AbstractElement init() {
        
        var tabsVal = ActiveTabSlice.Tab.values();
        var bLs = new ArrayList<NavBarButton>();
        var mc = Minecraft.getInstance();
        if (mc == null) return this;
        var font = mc.font;
        int totalButtonWidth = 0;
        
        for (var tab : tabsVal) {
            int buttonWidth = font.width(tab.title);
            var title = Component.literal(tab.title);
            var activeTab = 
                Store.get(getScreen())
                .getStateOrDefault(
                    ActiveTabSlice.class,
                    ActiveTabSlice.Tab.class, ActiveTabSlice.Tab.HOME);
            if (tab == activeTab) {
                title.withStyle(
                    Style.EMPTY
                    .withColor(0xa206c9)
                );
            }
            var button = new NavBarButton(
                0, this.getRealY(), 
                title, 
                b -> { 
                    Store.get(getScreen())
                    .dispatch(
                        ActiveTabSlice.class, 
                        new AbstractUIAction("ChangeTab", tab)
                    );
                }, 
                font
            );
            bLs.add(button);
            totalButtonWidth += buttonWidth;
        }
        var totalWidth = (tabsVal.length-1)*BUTTON_SPACING + totalButtonWidth;
        //int startRelativeXOff = (this.getSizeX() - totalWidth)/2;
        int pX = this.getRealX() - totalWidth/2;
        for (var b : bLs) {
            b.x = pX;
            this.addChildren(b);
            pX += b.getWidth() + BUTTON_SPACING;
        }
        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
    }


}
