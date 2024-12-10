package doggytalents.client.screen.framework.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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
        //TODO Auto-generated constructor stub
        this.font = Minecraft.getInstance().font;
        this.onPress = onPress;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPress() {
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

    @Override //TODO 1.19.4 ?? 
    public void renderButton(PoseStack graphics, int mouseX, int mouseY, float pTicks) {

        if (!this.active && !visibleWhenNotActive) return;

        int cl = maskColor(buttonColor, this.isHovered && this.active);
        
        fill(graphics, this.x, this.y, this.x+this.width, this.y+this.height, cl);
        
        //draw text
        int mX = this.x + this.width/2;
        int mY = this.y + this.height/2;
        var msg = this.getMessage();
        int tX = mX - font.width(msg)/2;
        int tY = mY - font.lineHeight/2;
        msg = modifyMessage(msg);
        //TODO if the name is too long, draw it cut off with a ..
        font.draw(graphics, msg, tX, tY, 0xffffffff);
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
    

    //1.19.2-
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public void setX(int val) { this.x = val; }
    public void setY(int val) { this.y = val; }

}
