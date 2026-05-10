package doggytalents.client.screen.framework.widget;

import doggytalents.DoggyTalents;
import doggytalents.api.registry.Talent;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.CommonUIActionTypes;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class TabPanelButton extends AbstractButton {
    static final int DEFAULT_COLOR = 0x485e5d5d;
    static final int DEFAULT_HLCOLOR = 0x835e5d5d;
    static final int DEFAULT_SEL_COLOR = 0x487500A5;
    static final int DEFAULT_SEL_HLCOLOR = 0x837500A5;

    Font font;
    Talent talent;
    Screen screen;
    Class<? extends AbstractSlice> slice;
    Object stateValue;
    Boolean selected;

    public TabPanelButton(int x, int y, int width, int height, 
        Screen screen, boolean selected, Component name,
        Class<? extends AbstractSlice> slice, Object stateValue) {
        super(x, y, width, height, name);
        this.font = Minecraft.getInstance().font;
        this.screen = screen;
        this.selected = selected;
        this.slice = slice;
        this.stateValue = stateValue;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        Store.get(screen).dispatch(slice,
            new UIAction(CommonUIActionTypes.CHANGE_PANEL_TAB, stateValue)
        );
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
        int cl = this.isHovered ? DEFAULT_HLCOLOR : DEFAULT_COLOR;
        int sel_cl = this.isHovered ? DEFAULT_SEL_HLCOLOR : DEFAULT_SEL_COLOR;
        
        graphics.fill( this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height,
            this.selected ? sel_cl : cl);
        
        //draw text
        int mX = this.getX() + this.width/2;
        int mY = this.getY() + this.height/2;
        var msg = truncateToWidth(font, this.getMessage(), this.width - 4);
        int tX = mX - font.width(msg)/2;
        int tY = mY - font.lineHeight/2;
        graphics.text(font, msg, tX, tY, 0xffffffff);
    }

    private static Component truncateToWidth(Font font, Component msg, int maxWidth) {
        if (font.width(msg) <= maxWidth) return msg;
        var s = font.plainSubstrByWidth(msg.getString(), Math.max(0, maxWidth - font.width("..")));
        return Component.literal(s + "..").withStyle(msg.getStyle());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }
}
