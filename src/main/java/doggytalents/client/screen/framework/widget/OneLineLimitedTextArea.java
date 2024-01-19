package doggytalents.client.screen.framework.widget;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.ToolTipOverlayManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class OneLineLimitedTextArea extends AbstractWidget {

    private Font font;

    public OneLineLimitedTextArea(int x, int y, int w, Component msg) {
        super(x, y, w, 0, msg);
        font = Minecraft.getInstance().font;
        this.height = font.lineHeight + 2;
    }


    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        int acceptedWidth = this.width - 5;

        int tX = this.getX();
        int tY = this.getY();

        var msg = this.getMessage();
        if (msg == null)
            return;
        int msgWidth = font.width(msg);
        if (msgWidth > acceptedWidth) {
            var posfix = "..";
            var newStr = font.plainSubstrByWidth(msg.getString(), 
                acceptedWidth - font.width(posfix)) + posfix;
            msg = Component.literal(newStr).withStyle(msg.getStyle());
            //this.setTooltip(msgTooltip);
        }
        font.draw(stack, msg, tX, tY, 0xffffffff);
        if (!this.isHovered)
            return;
        ToolTipOverlayManager.get().setComponents(List.of(msg));
    }

    private int getX() {
        return this.x;
    }

    private int getY() {
        return this.y;
    }


    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
    }
}
