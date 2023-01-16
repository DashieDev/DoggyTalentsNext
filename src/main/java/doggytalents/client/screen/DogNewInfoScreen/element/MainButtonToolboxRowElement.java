package doggytalents.client.screen.DogNewInfoScreen.element;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.ChopinLogger;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MainButtonToolboxRowElement extends AbstractElement {

    private final int BUTTON_SPACING = 4;

    public MainButtonToolboxRowElement(AbstractElement parent, Screen screen) {
        super(parent, screen);
    }

    @Override
    public AbstractElement init() {

        int totalWidth = 0;
        
        var modeButton = new Button(0, this.getRealY(), 70, this.getSizeY(),
            Component.literal("Switch Mode"), (b) -> {ChopinLogger.l("clicked editMode");}
        );
        totalWidth += modeButton.getWidth() + BUTTON_SPACING;
        var editInfoButton = new Button(0, this.getRealY(), 50, this.getSizeY(),
            Component.literal("Edit Info"), (b) -> {ChopinLogger.l("clicked editInfo");}
        );
        totalWidth += editInfoButton.getWidth() + BUTTON_SPACING;
        var changeSkinButton = new Button(0, this.getRealY(), 70, this.getSizeY(),
            Component.literal("Change Skin"), (b) -> {ChopinLogger.l("clicked editSkin");}
        );
        totalWidth += changeSkinButton.getWidth();

        int mX = this.getRealX() + this.getSizeX()/2;
        int pX = mX - totalWidth/2;
        modeButton.x = pX;
        pX += modeButton.getWidth() + BUTTON_SPACING;
        editInfoButton.x = pX;
        pX += editInfoButton.getWidth() + BUTTON_SPACING;
        changeSkinButton.x = pX;

        this.addChildren(modeButton);
        this.addChildren(editInfoButton);
        this.addChildren(changeSkinButton);
        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        
    }
    
}
