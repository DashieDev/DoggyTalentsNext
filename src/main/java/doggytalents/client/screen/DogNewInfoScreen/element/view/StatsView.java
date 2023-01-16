package doggytalents.client.screen.DogNewInfoScreen.element.view;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.screens.Screen;

public class StatsView extends AbstractElement {

    Dog dog;

    public StatsView(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        var font = getScreen().getMinecraft().font;
        font.draw(stack, "stats", this.getRealX()+3, this.getRealY() + 40, 0xffffffff);
        
    }
    
}
