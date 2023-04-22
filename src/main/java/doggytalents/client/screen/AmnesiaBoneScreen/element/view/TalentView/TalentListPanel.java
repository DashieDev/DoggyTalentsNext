package doggytalents.client.screen.AmnesiaBoneScreen.element.view.TalentView;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.TalentListPageCounterSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TalentListPanel extends AbstractElement {

    Dog dog;
    Font font;

    //Local State
    int pageIndex = 0;
    int availableSpot = 0;

    public TalentListPanel(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        var mc = this.getScreen().getMinecraft();
        this.font = mc.font;
    }

    @Override
    public AbstractElement init() {
        this.getPosition().setChildDirection(ChildDirection.COL);
        pageIndex = 0;
        var talentListEntries =
            new TalentButtonEntryElement(this, getScreen(), dog, pageIndex);
        talentListEntries
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, this.getSizeY() - 50)
            .init();
        
        this.addChildren(talentListEntries);

        var talentPageButtons = 
            new TalentListPageButtonElement(this, getScreen(), 
                pageIndex, talentListEntries.calculateMaxPage(dog.getTalentMap().size())
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
        // var c1 = Component.literal("Pts: " + this.dog.getSpendablePoints());
        // int tX = this.getRealX() + mX - font.width(c1)/2;
        // int tY = this.getRealY() + this.getSizeY() -15;
        // font.draw(stack, c1, tX, tY, 0xffffffff);
    }
    
}
