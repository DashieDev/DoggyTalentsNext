package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.dropdown.AddGroupMenu;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.ChopinLogger;
import doggytalents.client.screen.framework.DropdownMenuManager;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogGroupsManager;
import doggytalents.common.entity.DogGroupsManager.DogGroup;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogGroupsData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.PacketDistributor;

public class AddGroupMenu extends AbstractElement {

    Font font;
    EditBox addName;
    ColorSelectElement colorSelect;
    Dog dog;

    public AddGroupMenu(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.font = Minecraft.getInstance().font;
        this.dog = dog;
    }

    @Override
    public AbstractElement init() {
        
        int aX = 5;
        int pY = 22;

        addName = new EditBox(this.font, 
        this.getRealX() + aX, this.getRealY() + pY, this.getSizeX() - aX*2, 20, 
                Component.empty());
        addName.setMaxLength(DogGroupsManager.MAX_GROUP_STRLEN);
        this.addChildren(addName);

        pY += addName.getHeight() + 9;

        colorSelect = new ColorSelectElement(this, getScreen());
        colorSelect.setPosition(PosType.ABSOLUTE, 0, pY);
        colorSelect.setSize(this.getSizeX(), 30);
        colorSelect.init();
        this.addChildren(colorSelect);

        pY += colorSelect.getSizeY() + 7;

        var confirmButton = new FlatButton(this.getRealX() + aX, this.getRealY()+ pY, 40, 20, Component.literal("Add"), b -> {
            requestAddGroup();
            DropdownMenuManager.get(getScreen()).clearActiveDropdownMenu();
        });
        this.addChildren(confirmButton);

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        int aX = this.getRealX() + 5;
        int pY = this.getRealY() + 7;

        font.draw(stack, "Add Group: ", aX, pY, 0xffffffff);

    }

    private void requestAddGroup() {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new DogGroupsData.EDIT(this.dog.getId(), 
                new DogGroup(
                    this.addName.getValue(), 
                    this.colorSelect.selectedColor), true));
    }
    
    

}
