package doggytalents.client.screen.DogNewInfoScreen.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.Talent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class TalentListEntryButton extends AbstractButton {
    
    int color = 0x48a6a6a6;
    int hlColor = 0x83a6a6a6;
    Font font;
    Talent talent;

    public TalentListEntryButton(int x, int y, int width, int height, 
        Talent talent) {
        super(x, y, width, height, Component.translatable(talent.getTranslationKey()));
        //TODO Auto-generated constructor stub
        this.font = Minecraft.getInstance().font;
        this.talent = talent;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPress() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        int cl = this.isHovered ? this.hlColor : this.color;
        fill(stack, this.x, this.y, this.x+this.width, this.y+this.height, cl);
        
        //draw text
        int mX = this.x + this.width/2;
        int mY = this.y + this.height/2;
        var msg = this.getMessage();
        int tX = mX - font.width(msg)/2;
        int tY = mY - font.lineHeight/2;
        //TODO if the name is too long, draw it cut off with a ..
        font.draw(stack, msg, tX, tY, 0xffffffff);
    }
    
}
