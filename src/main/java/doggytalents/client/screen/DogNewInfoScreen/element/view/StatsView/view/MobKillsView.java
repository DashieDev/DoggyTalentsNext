package doggytalents.client.screen.DogNewInfoScreen.element.view.StatsView.view;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.stats.StatsTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

public class MobKillsView extends AbstractElement {

    static final int PADDING_LEFT = 5;
    static final int PADDING_RIGHT = 30;
    static final int PADDING_TOP = 5;
    static final int LINE_SPACING = 3;
    
    private StatsTracker stats;
    private Font font;
    private Dog dog;

    //Calculated from Props
    private int maxEntryFit;
    private int maxPageNum;
    
    //Local State
    private int startIndex = 0;
    private int pageIndex = 0;
    private TextOnlyButton lastPage;
    private TextOnlyButton nextPage;

    private boolean disabledKillStat = false;

    public MobKillsView(AbstractElement parent, Screen screen, Dog dog, StatsTracker stats, Font font) {
        super(parent, screen);
        this.stats = stats;
        this.font = font;
        this.dog = dog;
    }

    @Override
    public AbstractElement init() {
        this.maxEntryFit = this.getNumOfFitEntries(this.getSizeY());
        this.maxPageNum = this.calculatePagesNum(this.getSizeY(), this.stats.getAllKillCount().size());

        int mX = this.getSizeX()/2;

        this.lastPage = new TextOnlyButton(
            this.getRealX() + mX - 30 - 20, 
            this.getRealY() + this.getSizeY() - 20 - 5, 20, 20,  
            Component.literal("<"), b -> {
                startIndex -= this.maxEntryFit;
                --pageIndex;
                if (pageIndex < 0) pageIndex = 0;
            }, font);
        this.addChildren(lastPage);

        this.nextPage = new TextOnlyButton(
            this.getRealX() + mX + 30, 
            this.getRealY() + this.getSizeY() - 20 - 5, 20, 20, 
            Component.literal(">"), b -> {
                startIndex += this.maxEntryFit;
                ++pageIndex;
                if (pageIndex > this.maxPageNum) pageIndex = this.maxPageNum;
            }, font);
        this.addChildren(nextPage);
        this.disabledKillStat = ConfigHandler.SERVER.DISABLE_KILL_STATS.get();
        return this;
    }

    @Override
    public void renderElement(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        var mobKillMap = stats.getAllKillCount();
        int startX = this.getRealX() + PADDING_LEFT;
        int pY = this.getRealY() + PADDING_TOP;
        if (disabledKillStat) {
            String str = I18n.get("doggui.stats.mob_kills.disabled", dog.getName().getString() );
            graphics.text(font, str, startX, pY, 0xffffffff);
            this.lastPage.active = false;
            this.nextPage.active = false;
            return;
        }
        if (mobKillMap.isEmpty()) {
            String str = I18n.get("doggui.stats.mob_kills.no_kills", dog.getName().getString() );
            graphics.text(font, str, startX, pY, 0xffffffff);
            this.lastPage.active = false;
            this.nextPage.active = false;
            return;
        }
        this.lastPage.active = startIndex > 0;
        this.nextPage.active = false;
        this.startIndex = Math.max(0, startIndex);
        int tillStart = this.startIndex;
        int entryDrawm = 0;
        for (var entry : mobKillMap.entrySet()) {
            if (--tillStart >= 0) continue;
            if (entryDrawm >= this.maxEntryFit) {
                this.nextPage.active = true;
                break;
            } 
            var mobName = entry.getKey().getDescription().copy();
            mobName.withStyle(
                Style.EMPTY.withBold(true)
            );
            var kills = entry.getValue();
            graphics.text(font, mobName, startX, pY, 0xffffffff);
            pY += font.lineHeight + LINE_SPACING;
            var killWord = kills == 1 ? "kill" : "kills";
            var killSentence = dog.getName().getString() + " has " + kills + " " + killWord + " of " + mobName.getString();
            graphics.text(font, killSentence, startX, pY, 0xffffffff);
            pY += font.lineHeight + LINE_SPACING;
            ++entryDrawm;
        }
        if (entryDrawm <= 0 && this.startIndex > 0) {
            this.startIndex = 0;
        }

        int mX = this.getSizeX()/2;
        var txt = (this.pageIndex+1) + "/" + this.maxPageNum;
        int tX = this.getRealX() + mX - font.width(txt)/2;
        int tY = this.getRealY() + this.getSizeY() - 19;
        graphics.text(font, txt, tX, tY, 0xffffffff);

    }


    private int getOneEntrySize() {
        return font.lineHeight*2 + LINE_SPACING*2;
    }

    private int getNumOfFitEntries(int sizeY) {
        return sizeY/getOneEntrySize() - 1;
    }

    private int calculatePagesNum(int sizeY, int entriesNum) {
        return Mth.ceil(((float)entriesNum)/((float)getNumOfFitEntries(sizeY)));
    }
    

}
