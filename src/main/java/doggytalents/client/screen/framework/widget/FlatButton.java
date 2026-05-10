package doggytalents.client.screen.framework.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class FlatButton extends AbstractButton {
    public static final int DEFAULT_COLOR = 0x005e5d5d;
    public static final int NON_HL_COLOR_MASK = 0x48000000;
    public static final int HL_COLOR_MASK = 0x83000000;
    Font font;

    protected final FlatButton.OnPress onPress;
    protected boolean visibleWhenNotActive = false;
    protected int buttonColor = DEFAULT_COLOR;

    public FlatButton(int x, int y, int width, int height,
        Component msg, FlatButton.OnPress onPress) {
        super(x, y, width, height, msg);
        this.font = Minecraft.getInstance().font;
        this.onPress = onPress;
    }
    
    @Override
    public void onPress(InputWithModifiers input) {
        this.onPress.onPress(this);
    }

    public FlatButton visibleWhenNotActive() {
        this.visibleWhenNotActive = true;
        return this;
    }

    public FlatButton withButtonColor(int color) {
        this.buttonColor = color;
        return this;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {

        if (!this.active && !visibleWhenNotActive) return;

        int cl = maskColor(buttonColor, this.isHovered && this.active);
        
        graphics.fill( this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, cl);
        
        //draw text
        int mX = this.getX() + this.width/2;
        int mY = this.getY() + this.height/2;
        var msg = modifyMessage(this.getMessage());
        msg = truncateToWidth(font, msg, this.width - 4);
        int tX = mX - font.width(msg)/2;
        int tY = mY - font.lineHeight/2;
        graphics.text(font, msg, tX, tY, 0xffffffff);
    }

    protected int maskColor(int color, boolean hightlight) {
        return color | (hightlight ? HL_COLOR_MASK : NON_HL_COLOR_MASK);
    }

    public Component modifyMessage(Component msg) {
        if (!this.active) {
            msg = msg.copy().withStyle(Style.EMPTY.withColor(0xff828282));
        }
        return msg;
    }

    public interface OnPress {
        void onPress(FlatButton p_93751_);
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
