package doggytalents.client.screen.DogNewInfoScreen.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyTalents;
import doggytalents.api.registry.Talent;
import doggytalents.client.screen.DogNewInfoScreen.store.Store;
import doggytalents.client.screen.DogNewInfoScreen.store.UIAction;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTalentDescSlice;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class TalentListEntryButton extends AbstractButton {
    
    static final int DEFAULT_COLOR = 0x485e5d5d;
    static final int DEFAULT_HLCOLOR = 0x835e5d5d;
    static final int DEFAULT_LEVEL_COLOR = 0x48a6a6a6;
    static final int DEFAULT_LEVEL_HLCOLOR = 0x83a6a6a6;
    static final int DEFAULT_MAXLEVEL_COLOR = 0x487500A5;
    static final int DEFAULT_MAXLEVEL_HLCOLOR = 0x837500A5;
    static final int DEFAULT_INVALID_COLOR = 0x48B20000;
    static final int DEFAULT_INVALID_HLCOLOR = 0x83B20000;

    Font font;
    Talent talent;
    Screen screen;
    Dog dog;

    public TalentListEntryButton(int x, int y, int width, int height, 
        Talent talent, Screen screen, Dog dog) {
        super(x, y, width, height, Component.translatable(talent.getTranslationKey()));
        //TODO Auto-generated constructor stub
        this.font = Minecraft.getInstance().font;
        this.talent = talent;
        this.screen = screen;
        this.dog = dog;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPress() {
        Store.get(screen).dispatch(ActiveTalentDescSlice.class, 
            new UIAction(UIActionTypes.Talents.OPEN_DESC, new ActiveTalentDescSlice(this.talent))
        );
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        int cl = this.isHovered ? DEFAULT_HLCOLOR : DEFAULT_COLOR;
        int lvlcl = this.isHovered ? DEFAULT_LEVEL_HLCOLOR : DEFAULT_LEVEL_COLOR;
        
        //Ex : disable talent (or mutual talent)
        int talentMaxLvl = 0;
        float talentLvlPercent = 0;
        if (!ConfigHandler.TALENT.getFlag(talent)) {
            lvlcl = 0;
            cl = this.isHovered ? DEFAULT_INVALID_HLCOLOR : DEFAULT_INVALID_COLOR;
        } else {
            talentMaxLvl = this.talent.getMaxLevel();
            talentLvlPercent = ((float)this.dog.getDogLevel(talent))/((float)talentMaxLvl);
            if (talentLvlPercent >= 1) {
                lvlcl = this.isHovered ? DEFAULT_MAXLEVEL_HLCOLOR : DEFAULT_MAXLEVEL_COLOR;
            }
        
        }
        
        fill(stack, this.x, this.y, this.x+this.width, this.y+this.height, cl);
        fill(stack, this.x, this.y, this.x+Mth.ceil(this.width*talentLvlPercent), this.y+this.height, lvlcl);
        
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
