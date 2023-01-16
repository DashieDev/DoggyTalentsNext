package doggytalents.client.screen.DogNewInfoScreen.element.view;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.screens.Screen;

public class AccessoriesView extends AbstractElement {

    Dog dog;

    public AccessoriesView(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        var font = getScreen().getMinecraft().font;
        font.draw(stack, "accessories", this.getRealX()+3, this.getRealY() + 40, 0xffffffff);
        
        
    }
    
}
