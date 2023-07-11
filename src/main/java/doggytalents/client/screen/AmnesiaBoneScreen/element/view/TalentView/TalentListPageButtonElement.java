package doggytalents.client.screen.AmnesiaBoneScreen.element.view.TalentView;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;

public class TalentListPageButtonElement extends AbstractElement {

    static final int BUTTON_SIZE = 20;
    static final int BUTTON_MIDDLE_SPACING = 40;

    int curPage;
    int maxPage;

    Font font;

    PageChange changeLeft;
    PageChange changeRight;

    public TalentListPageButtonElement(AbstractElement parent, Screen screen,
        int curPage, int maxPage, PageChange changeLeft, PageChange changeRight) {
        super(parent, screen);
        this.curPage = curPage;
        this.maxPage = maxPage;
        this.changeLeft = changeLeft;
        this.changeRight = changeRight;
        var mc = this.getScreen().getMinecraft();
        this.font = mc.font;
    }

    @Override
    public AbstractElement init() {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        var nextButton = new TextOnlyButton(0, 0, BUTTON_SIZE, BUTTON_SIZE, 
            ComponentUtil.literal(">"), b -> {
                this.changeRight.change();
            }, font
        ); 
        nextButton.active = curPage < maxPage;
        var backButton = new TextOnlyButton(0, 0, BUTTON_SIZE, BUTTON_SIZE, 
            ComponentUtil.literal("<"), b -> {
                this.changeLeft.change();
            }, font
        ); 
        backButton.active = 1 < curPage;
        
        int aX = mX - BUTTON_MIDDLE_SPACING/2 - backButton.getWidth();
        int aY = mY - backButton.getHeight()/2;
        backButton.x = this.getRealX() + aX;
        backButton.y = this.getRealY() + aY;
        aX = mX + BUTTON_MIDDLE_SPACING/2;
        nextButton.x = this.getRealX() + aX;
        nextButton.y = this.getRealY() + aY;
        this.addChildren(backButton);
        this.addChildren(nextButton);
        

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        var c0 = ComponentUtil.literal(this.curPage + "/" + this.maxPage);
        int tX = this.getRealX() + mX - font.width(c0)/2;
        int tY = this.getRealY() + mY - font.lineHeight/2;
        font.draw(stack, c0, tX, tY, 0xffffffff);
        
    }

    @FunctionalInterface
    public static interface PageChange {

        public void change();
        
    }
    
}
