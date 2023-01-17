package doggytalents.client.screen.DogNewInfoScreen.element;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.store.UIAction;
import doggytalents.client.screen.DogNewInfoScreen.store.Store;
import doggytalents.client.screen.DogNewInfoScreen.store.TalentListPageCounterSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.TalentListSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.TalentListSlice.TalentListData;
import doggytalents.client.screen.DogNewInfoScreen.widget.TalentListEntryButton;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

public class TalentButtonEntryElement extends AbstractElement {

    Dog dog;
    TalentListData talentList;
    static final int BUTTON_HEIGHT = 20;
    static final int BUTTON_SPACING = 2;
    static final int PADDING_TOP = 0;
    static final int PADDING_LEFT = 0;

    public TalentButtonEntryElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
    }

    @Override
    public AbstractElement init() {
        this.talentList = Store.get(getScreen())
            .getStateOrDefault(
                TalentListSlice.class, 
                TalentListData.class, 
                new TalentListData(List.of())
            );
        int buttonsUntilFull = Mth.floor(
            (this.getSizeY() - PADDING_TOP) / (BUTTON_SPACING + BUTTON_HEIGHT)
        );
        var talentButtons = new ArrayList<TalentListEntryButton>();
        for (var talent : this.talentList.talents) {
            talentButtons.add(
                new TalentListEntryButton(0, 0, 
                    this.getSizeX(), BUTTON_HEIGHT, talent)
            );
            --buttonsUntilFull;
            if (buttonsUntilFull == 0) break;
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
    
}
