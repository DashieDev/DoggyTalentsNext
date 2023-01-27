package doggytalents.client.screen.DogNewInfoScreen.element.view.TalentView;

import java.security.cert.PKIXReason;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.Talent;
import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.client.screen.DogNewInfoScreen.store.ActiveTalentDescSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.Store;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class TalentInfoViewElement extends AbstractElement {

    Dog dog;
    Talent talent;
    Font font;

    static final int PADDING_LEFT = 5;
    static final int PADDING_RIGHT = 30;
    static final int PADDING_TOP = 5;
    static final int LINE_SPACING = 3;

    public TalentInfoViewElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;

        var mc = this.getScreen().getMinecraft();
        this.font = mc.font;
    }

    @Override
    public AbstractElement init() {
        var talent = Store.get(getScreen())
            .getStateOrDefault(ActiveTalentDescSlice.class, 
            ActiveTalentDescSlice.class, 
            new ActiveTalentDescSlice(null)).activeTalent;
        this.talent = talent;

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.talent == null) {
            int mX = this.getSizeX()/2;
            int mY = this.getSizeY()/2;
            var txt = "Select a Talent to start.";
            int tX = this.getRealX() + mX - this.font.width(txt)/2;
            int tY = this.getRealY() + mY - this.font.lineHeight/2;
            this.font.draw(stack, txt, tX, tY, 0xffffffff);
            return;
        }

        int startX = this.getRealX() + PADDING_LEFT;
        int startY = this.getRealY() + PADDING_TOP;
        int pX = startX;
        int pY = startY;
        var title = Component.translatable(this.talent.getTranslationKey())
            .withStyle(
                Style.EMPTY
                .withBold(true)
                .withColor(0xffdd05fa)
            );
        font.draw(stack, title, pX, pY, 0xffffffff);
        pY += 2*LINE_SPACING + this.font.lineHeight;
        var desc = Component.translatable(this.talent.getInfoTranslationKey());
        var desc_lines = this.font.split(desc, this.getSizeX() - (PADDING_LEFT + PADDING_RIGHT));
        for (var line : desc_lines) {
            font.draw(stack, line, pX, pY, 0xffffffff);
            pY += font.lineHeight + LINE_SPACING;
        }
        
    }
    
}
