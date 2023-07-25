package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView.widget.ArtifactHolder;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView.widget.ArtifactShowBox;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;

public class ArtifactSlotElement extends AbstractElement {

    private static final int BUTTON_SPACING = 4;

    Dog dog;
    Minecraft mc;
    final ArrayList<ArtifactShowBox> artifactBoxes = new ArrayList<ArtifactShowBox>(3);

    public ArtifactSlotElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        mc = Minecraft.getInstance();
    }

    @Override
    public AbstractElement init() {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;

        for (int i = 0; i < 3; ++i) {
            var artifactShowBox = new ArtifactShowBox(
                0, 0,
                this.mc.getItemRenderer(), this.dog, i);
            this.artifactBoxes.add(artifactShowBox);
        }

        // int totalSize = artifactBoxes.size() * ArtifactShowBox.WIDGET_SIZE 
        //     + artifactBoxes.size() - 1 * BUTTON_SPACING;
        // int startX = this.getRealX() + mX - ArtifactShowBox.WIDGET_SIZE/2;
        // int pY = this.getRealY() + mY - totalSize/2;
        // for (var holder : this.artifactBoxes) {
        //     holder.x = startX;
        //     holder.y = pY;
        //     this.addChildren(holder);
        //     pY += ArtifactHolder.WIDGET_SIZE + BUTTON_SPACING;
        // }

        int totalSize = artifactBoxes.size() * ArtifactShowBox.WIDGET_SIZE 
            + artifactBoxes.size() - 1 * BUTTON_SPACING;
        int startY = this.getRealY() + mY - ArtifactShowBox.WIDGET_SIZE/2;
        int pX = this.getRealX() + mX - totalSize/2;
        for (var holder : this.artifactBoxes) {
            holder.setX(pX);
            holder.setY(startY);
            this.addChildren(holder);
            pX += ArtifactShowBox.WIDGET_SIZE + BUTTON_SPACING;
        }
        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int holderIndx = 0;
        var artifacts = this.dog.getArtifactsList();
        for (int i = 0; i < artifacts.size(); ++i) {
            var artifact = artifacts.get(i); 
            if (holderIndx >= this.artifactBoxes.size()) break;
            var item = new ItemStack(artifact);

            var holder = this.artifactBoxes.get(holderIndx);
            holder.setStack(item);
            ++holderIndx;
        }
        while (holderIndx < this.artifactBoxes.size()) {
            this.artifactBoxes.get(holderIndx).setStack(ItemStack.EMPTY);
            ++holderIndx;
        }
    }
    
}
