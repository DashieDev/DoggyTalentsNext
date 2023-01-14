package doggytalents.client.screen.DogNewInfoScreen.element;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.Screen;

public class ViewElement extends AbstractElement {

    private int backgroundColor;

    public ViewElement(AbstractElement parent, int rX, int rY, int sizeX, int sizeY, Screen screen) {
        super(parent, rX, rY, sizeX, sizeY, screen);
        //TODO Auto-generated constructor stub
    }

    public ViewElement setBackgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.backgroundColor != 0) {
            int aX = this.getRealX();
            int aY = this.getRealY();
            ViewElement.fill(stack, aX, aY, aX + this.getSizeX(), 
                aY + this.getSizeY(), this.backgroundColor);
        }
    }
    
}
