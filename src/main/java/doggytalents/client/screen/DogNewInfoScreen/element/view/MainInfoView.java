package doggytalents.client.screen.DogNewInfoScreen.element.view;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DogDescriptionViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.MainButtonToolboxRowElement;
import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.screens.Screen;

public class MainInfoView extends AbstractElement {

    Dog dog;

    public MainInfoView(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
    }

    @Override
    public AbstractElement init() {
        int mX = this.getScreen().width/2;
        int mY = this.getScreen().height/2;
        this.addChildren(
            new DogStatusViewBoxElement(this, this.getScreen(), this.dog)
            .setPosition(PosType.FIXED, mX - 105 - 10, mY - 105/2)
            .setSize(105)
            .setBackgroundColor(0xffe39c02)
        );
        this.addChildren(
            new DogDescriptionViewBoxElement(this, this.getScreen(), this.dog)
            .setPosition(PosType.FIXED, mX + 10, mY - 105/2)
            .setSize(105)
            .setBackgroundColor(0xff00ffae)
        );
        this.addChildren(
            new MainButtonToolboxRowElement(this, this.getScreen())
            .setPosition(PosType.FIXED, mX - 50, mY+65)
            .setSize(100, 20)
            .setBackgroundColor(0xff36888a)
            .init()
        );

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

    }
    
}
