package doggytalents.client.screen.DogNewInfoScreen.element.view;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DivElement;
import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.screens.Screen;

public class TalentView extends AbstractElement {

    Dog dog;

    public TalentView(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
    }

    @Override
    public AbstractElement init() {
        var talentListDiv = new DivElement(this, getScreen())
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(120, 1f)
            .setBackgroundColor(0xff5e10e6);
        this.addChildren(talentListDiv);
        var talentLisDivPos = talentListDiv.getPosition();
        var talentLisDivSize = talentListDiv.getSize();
        var talentInfoDiv = new DivElement(this, getScreen())
            .setPosition(PosType.RELATIVE, 
            talentLisDivPos.getX() + talentLisDivSize.getWidth(),
            0)
            .setSize(this.getSizeX() - 120, 1f)
            .setBackgroundColor(0xffff05de);
        this.addChildren(talentInfoDiv);
        var talentInfoCenterDiv = new DivElement(talentInfoDiv, getScreen())
            .setPosition(
                PosType.RELATIVE, 
                talentInfoDiv.getSizeX()/2, talentInfoDiv.getSizeY()/2
            )
            .setSize(100 , getBlitOffset())

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        var font = getScreen().getMinecraft().font;
        font.draw(stack, "talents", this.getRealX()+3, this.getRealY() + 40, 0xffffffff);
        
    }
    
}
