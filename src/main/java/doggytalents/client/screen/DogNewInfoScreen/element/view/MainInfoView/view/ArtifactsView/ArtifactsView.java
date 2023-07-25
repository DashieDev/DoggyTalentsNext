package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

public class ArtifactsView extends AbstractElement {

    private Dog dog;

    public ArtifactsView(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
    }

    @Override
    public AbstractElement init() {
        this.getPosition().setChildDirection(ChildDirection.COL);

        var dogAccessoriesEdit = new ArtifactSlotElement(this, getScreen(), dog);
        dogAccessoriesEdit
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, 0.65f)
            .setBackgroundColor(0x87363636)
            .init();
        this.addChildren(dogAccessoriesEdit);
        
        var accessoryEdit = new ArtifactEditElement(this, getScreen(),
            Minecraft.getInstance().player, dog);
        accessoryEdit
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, 0.35f)
            .setBackgroundColor(0xAA595858)
            .init();
        this.addChildren(accessoryEdit);

        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    }
    
}
