package doggytalents.client.screen.DogNewInfoScreen.element.view.TalentView;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.PosType;
import doggytalents.client.screen.DogNewInfoScreen.store.Store;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentListPageCounterSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentListSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentListSlice.TalentListData;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.screens.Screen;

public class talentListPanel extends AbstractElement {

    Dog dog;

    public talentListPanel(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        //TODO Auto-generated constructor stub
    }

    @Override
    public AbstractElement init() {
        this.getPosition().setChildDirection(ChildDirection.COL);
        var pageIndex = Store.get(getScreen()).getStateOrDefault(
            TalentListPageCounterSlice.class, 
            Integer.class, 0);
        var talentList = Store.get(getScreen())
            .getStateOrDefault(
                TalentListSlice.class, 
                TalentListData.class, 
                new TalentListData(List.of())
            );
        var talentListEntries =
            new TalentButtonEntryElement(this, getScreen(), dog, pageIndex, talentList);
        talentListEntries
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, this.getSizeY() - 50)
            .init();
        
        this.addChildren(talentListEntries);

        var talentPageButtons = 
            new TalentListPageButtonElement(this, getScreen(), 
                pageIndex, talentListEntries.calculateMaxPage(talentList.talents.size())
            );
        talentPageButtons
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, 40)
            .init();
        this.addChildren(talentPageButtons);

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        // TODO Auto-generated method stub
        
    }
    
}
