package doggytalents.client.screen.framework.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class OneLineLimitedTextArea extends AbstractWidget {

    private Font font;
    private Tooltip msgTooltip;

    public OneLineLimitedTextArea(int x, int y, int w, Component msg) {
        super(x, y, w, 0, msg);
        font = Minecraft.getInstance().font;
        msgTooltip = (Tooltip.create(msg));
        this.height = font.lineHeight + 2;
    }


    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
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
            this.setTooltip(msgTooltip);
        }
        graphics.drawString(font, msg, tX, tY, 0xffffffff);
        
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
        
    }
    
    

}
