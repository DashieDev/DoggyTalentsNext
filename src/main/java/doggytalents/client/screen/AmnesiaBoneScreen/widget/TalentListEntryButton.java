package doggytalents.client.screen.AmnesiaBoneScreen.widget;

import doggytalents.DoggyTalents;
import doggytalents.api.registry.Talent;
import doggytalents.client.screen.AmnesiaBoneScreen.store.UIActionTypes;
import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTalentDescSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.InputWithModifiers;
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
    boolean selected;

    public TalentListEntryButton(int x, int y, int width, int height, 
        Talent talent, Screen screen, Dog dog, boolean selected) {
        super(x, y, width, height, Component.translatable(talent.getTranslationKey()));
        this.font = Minecraft.getInstance().font;
        this.talent = talent;
        this.screen = screen;
        this.dog = dog;
        this.selected = selected;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        Store.get(screen).dispatch(ActiveTalentDescSlice.class,
            new UIAction(UIActionTypes.Talents.OPEN_DESC, new ActiveTalentDescSlice(this.talent))
        );
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
        int cl = this.isHovered ? DEFAULT_HLCOLOR : DEFAULT_COLOR;
        int lvlcl = this.isHovered ? DEFAULT_LEVEL_HLCOLOR : DEFAULT_LEVEL_COLOR;
        
        //Ex : disable talent (or mutual talent)
        int talentMaxLvl = 0;
        float talentLvlPercent = 0;
        if (!DogUtil.playerCanTrainTalent(Minecraft.getInstance().player, talent)) {
            lvlcl = 0;
            cl = this.isHovered ? DEFAULT_INVALID_HLCOLOR : DEFAULT_INVALID_COLOR;
        } else {
            talentMaxLvl = this.talent.getMaxLevel();
            talentLvlPercent = ((float)this.dog.getDogLevel(talent))/((float)talentMaxLvl);
            if (talentLvlPercent >= 1) {
                lvlcl = this.isHovered ? DEFAULT_MAXLEVEL_HLCOLOR : DEFAULT_MAXLEVEL_COLOR;
            }
        
        }
        
        graphics.fill( this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, cl);
        graphics.fill( this.getX(), this.getY(), this.getX()+Mth.ceil(this.width*talentLvlPercent), this.getY()+this.height, lvlcl);
        
        //draw text
        int mX = this.getX() + this.width/2;
        int mY = this.getY() + this.height/2;
        var msg = this.getMessage();
        if (this.selected) {
            msg = msg.copy().withStyle(
                msg.getStyle()
                .withUnderlined(true)
            );
        }
        msg = truncateToWidth(font, msg, this.width - 4);
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
