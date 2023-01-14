package doggytalents.client.screen.DogNewInfoScreen.element;

import java.util.ArrayList;

import doggytalents.ChopinLogger;
import doggytalents.client.screen.DogNewInfoScreen.store.ActiveTabSlice;
import doggytalents.client.screen.DogNewInfoScreen.widget.NavBarButton;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class DogInfoNavBarElement extends AbstractElement {

    private static final int BUTTON_SPACING = 10;

    public DogInfoNavBarElement(AbstractElement parent, int rX, int rY, int sizeX, int sizeY, Screen screen) {
        super(parent, rX, rY, sizeX, sizeY, screen);
        this.addNavButtons();  
    } 

    public void addNavButtons() {
        
        var tabsVal = ActiveTabSlice.Tab.values();
        var bLs = new ArrayList<NavBarButton>();
        var mc = Minecraft.getInstance();
        if (mc == null) return;
        var font = mc.font;
        int totalButtonWidth = 0;
        
        for (var tab : tabsVal) {
            int buttonWidth = font.width(tab.title);
            var title = Component.literal(tab.title);
            if (tab == ActiveTabSlice.activeTab) {
                title.withStyle(
                    Style.EMPTY
                    .withColor(0xa206c9)
                );
            }
            var button = new NavBarButton(
                0, this.getRealY(), 
                title, 
                b -> { 
                    ActiveTabSlice.activeTab = tab; 
                    ChopinLogger.l("clicked : " + tab.title); 
                    var s = this.getScreen();
                    //Refresh and re render.
                    s.init(mc, s.width, s.height);
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
    }


}
