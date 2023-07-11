package doggytalents.client.screen.AmnesiaBoneScreen.element.view.TalentView;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.Talent;
import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTalentDescSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;

public class TalentListPanel extends AbstractElement {

    Dog dog;
    Font font;

    //Local State
    int pageIndex = 1;
    int availableSpot = 0;
    List<Talent> trainedTalents;

    public TalentListPanel(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        var mc = this.getScreen().getMinecraft();
        this.font = mc.font;
    }

    @Override
    public AbstractElement init() {
        this.children().clear();
        this.getPosition().setChildDirection(ChildDirection.COL);
        var selectedTalent = Store.get(getScreen())
        .getStateOrDefault(ActiveTalentDescSlice.class, 
        ActiveTalentDescSlice.class, 
        new ActiveTalentDescSlice(null)).activeTalent;
        
        trainedTalents = this.getTrainedTalent(dog);
        int talentButtonAreaSize = this.getSizeY() - 50;
        int startIndex = TalentButtonEntryElement.getStartIndex(
            TalentButtonEntryElement.calculateButtonCanFilled(talentButtonAreaSize)
        , pageIndex);
        if (startIndex >= trainedTalents.size() && pageIndex != 1) 
            pageIndex = 1;
        var talentListEntries =
            new TalentButtonEntryElement(this, getScreen(), dog, pageIndex, trainedTalents, selectedTalent);
        talentListEntries
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, talentButtonAreaSize)
            .init();
        
        this.addChildren(talentListEntries);

        var talentPageButtons = 
            new TalentListPageButtonElement(this, getScreen(), 
                pageIndex, TalentButtonEntryElement.calculateMaxPage(this.trainedTalents.size(), talentButtonAreaSize),
                () -> { --pageIndex; this.init(); }, () -> { ++pageIndex; this.init(); }
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
        // int mX = this.getSizeX()/2;
        // var c1 = ComponentUtil.literal("Pts: " + this.dog.getSpendablePoints());
        // int tX = this.getRealX() + mX - font.width(c1)/2;
        // int tY = this.getRealY() + this.getSizeY() -15;
        // font.draw(stack, c1, tX, tY, 0xffffffff);
    }

    private List<Talent> getTrainedTalent(Dog dog) {
        var talentInstMap = dog.getTalentMap();
        var talentList = new ArrayList<Talent>(talentInstMap.size());
        for (var talentInst : talentInstMap) {
            talentList.add(talentInst.getTalent());
        }
        return talentList;
    }
    
}
