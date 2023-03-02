package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DivElement;
import doggytalents.client.screen.DogNewInfoScreen.element.ScrollView;
import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DebugView extends AbstractElement {

    Font font;

    public DebugView(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public AbstractElement init() {
        
        var scrollTest = new ScrollView(this, getScreen());
        scrollTest.setPosition(PosType.ABSOLUTE, 0, 0);
        scrollTest.getPosition().setChildDirection(ChildDirection.COL);
        scrollTest.setSize(1f, 1f);
        scrollTest.init();

        this.addChildren(scrollTest);

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff5687a6)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff615d9e)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff5687a6)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff615d9e)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff5687a6)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff615d9e)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff5687a6)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff615d9e)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff5687a6)
        );

        scrollTest.addScrollChildren(
            new DivElement(scrollTest.getContainer(), getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 40)
                .setBackgroundColor(0xff615d9e)
        );

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.font.draw(stack, Component.literal("Debug"), 
            this.getRealX(), this.getRealY(), 0xffffffff);
    }
    
}
