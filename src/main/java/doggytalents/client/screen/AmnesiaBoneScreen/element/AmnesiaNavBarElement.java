package doggytalents.client.screen.AmnesiaBoneScreen.element;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTabSlice;
import doggytalents.client.screen.AmnesiaBoneScreen.widget.NavBarButton;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class AmnesiaNavBarElement extends AbstractElement {

    private static final int BUTTON_SPACING = 10;
    private Dog dog;

    public AmnesiaNavBarElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
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
            var title = Component.translatable(tab.unlocalizedTitle);
            int buttonWidth = font.width(title);
            var activeTab = 
            getStateAndSubscribesTo(
                    ActiveTabSlice.class,
                    ActiveTabSlice.Tab.class, ActiveTabSlice.Tab.GENERAL);
            if (tab == activeTab) {
                title.withStyle(
                    Style.EMPTY
                    .withColor(0xff960606)
                );
            }
            var button = new NavBarButton(
                0, this.getRealY(), 
                title, 
                tab,
                font, getScreen(), dog
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
