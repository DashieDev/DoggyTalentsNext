package doggytalents.client.screen.AmnesiaBoneScreen.element.view.TalentView;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.Talent;
import doggytalents.client.screen.AmnesiaBoneScreen.widget.TalentListEntryButton;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentListSlice.TalentListData;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

public class TalentButtonEntryElement extends AbstractElement {

    Dog dog;
    int pageIndex;
    List<Talent> talentList;
    Talent selectedTalent;

    static final int BUTTON_HEIGHT = 20;
    static final int BUTTON_SPACING = 2;
    static final int PADDING_TOP = 0;
    static final int PADDING_LEFT = 0;

    
    public TalentButtonEntryElement(AbstractElement parent, Screen screen, 
        Dog dog, int pageIndex, List<Talent> talentList, Talent selectedTalent) {
        super(parent, screen);
        this.dog = dog;
        this.pageIndex = pageIndex;
        this.talentList = talentList;
        this.selectedTalent = selectedTalent;
    }

    @Override
    public AbstractElement init() {
        int buttonsUntilFull = calculateButtonCanFilled(this.getSizeY());
        var talentButtons = new ArrayList<TalentListEntryButton>();
        int startTalentIndex = getStartIndex(buttonsUntilFull, pageIndex);
        for (int i = startTalentIndex; i < this.talentList.size(); ++i) {
            var talent = this.talentList.get(i);
            talentButtons.add(
                new TalentListEntryButton(0, 0, 
                    this.getSizeX(), BUTTON_HEIGHT, talent, 
                    getScreen(), dog, talent == selectedTalent)
            );
            --buttonsUntilFull;
            if (buttonsUntilFull <= 0) break;
        }
        int startX = this.getRealX() + PADDING_LEFT;
        int pY = this.getRealY() + PADDING_TOP;
        for (var b : talentButtons) {
            b.x = startX;
            b.y = pY;
            pY += b.getHeight() + BUTTON_SPACING;
            this.addChildren(b);
        }

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        
    }

    public static int getStartIndex(int buttonCanBefilled, int pageIndex) {
        return buttonCanBefilled * (pageIndex -1);
    }

    public static int calculateButtonCanFilled(int height) {
        return Mth.floor( Math.max(
            (height - PADDING_TOP) / (BUTTON_SPACING + BUTTON_HEIGHT), 0
        ));
    }

    public static int calculateMaxPage(int talentSize, int sizeY) {
        return Math.max(1, calculateMaxPageInternal(talentSize, calculateButtonCanFilled(sizeY)));
    }

    private static int calculateMaxPageInternal(int talentSize, int buttonInOnePage) {
        return Mth.ceil(((float)talentSize)/((float)buttonInOnePage));
    }
    
}
