package doggytalents.client.screen.framework.widget;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.types.TextType;
import doggytalents.client.screen.framework.types.TextType.Align;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;

public class MultiLineFlatButton extends FlatButton {

    private final int LINE_SPACING = 2;
    private final int PADDING_LEFT = 6;

    private List<Component> lines;
    private TextType.Align align = TextType.Align.MIDDLE;

    public MultiLineFlatButton(int x, int y, int width, int height, List<Component> lines, OnPress onPress) {
        super(x, y, width, height, ComponentUtil.empty(), onPress);
        this.lines = lines == null ? List.of() : lines;
    }

    public MultiLineFlatButton setTextAlign(TextType.Align align) {
        this.align = align;
        return this;
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        if (!this.active) return;

        int cl = this.isHovered ? DEFAULT_HLCOLOR : DEFAULT_COLOR;
        
        fill(stack, this.x, this.y, this.x+this.width, this.y+this.height, cl);
        
        if (this.align == TextType.Align.LEFT) {
            drawLeft(stack);
        } else if (this.align == TextType.Align.MIDDLE) {
            drawCentered(stack);
        }
    }

    private void drawCentered(PoseStack stack) {
        int mX = this.x + this.width/2;
        int mY = this.y + this.height/2;
        int lines_cnt = this.lines.size();
        int text_height = lines_cnt*font.lineHeight + (lines_cnt-1)*LINE_SPACING;
        int pTX;
        int pTY = mY - text_height/2;
        for (var line : lines) {
            pTX = mX - font.width(line)/2;
            font.draw(stack, line, pTX, pTY, 0xffffffff);
            pTY += font.lineHeight + LINE_SPACING; 
        }
    }

    private void drawLeft(PoseStack stack) {
        int mX = this.x + this.width/2;
        int mY = this.y + this.height/2;
        int lines_cnt = this.lines.size();
        int text_height = lines_cnt*font.lineHeight + (lines_cnt-1)*LINE_SPACING;
        int pTX = this.x + PADDING_LEFT;
        int pTY = mY - text_height/2;
        for (var line : lines) {
            font.draw(stack, line, pTX, pTY, 0xffffffff);
            pTY += font.lineHeight + LINE_SPACING; 
        }
    }

    private void drawRight(PoseStack stack) {
        //TODO
        
    }

}
