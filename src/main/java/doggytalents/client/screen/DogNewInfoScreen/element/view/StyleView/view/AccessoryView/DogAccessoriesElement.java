package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.AccessoryView;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.widget.AccessoryHolder;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public class DogAccessoriesElement extends AbstractElement {

    private static final int BUTTON_SPACING = 4;

    Dog dog;
    Minecraft mc;
    final ArrayList<AccessoryHolder> accessoryHolders = new ArrayList<AccessoryHolder>(5);

    //Divs
    DivElement dogDiv;
    DivElement accessoriesDiv;
    Font font;

    public DogAccessoriesElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        mc = Minecraft.getInstance();
        font = mc.font;
    }

    @Override
    public AbstractElement init() {

        dogDiv = new DivElement(this, getScreen());
        accessoriesDiv = new DivElement(this, getScreen());

        dogDiv
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(0.5f, 1f);
            //.setBackgroundColor(0xff53b06c);
        this.addChildren(dogDiv);

        accessoriesDiv
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(0.5f, 1f);
            //.setBackgroundColor(0x45680be0);

        //accessoriesDiv
        {
            int mX = accessoriesDiv.getSizeX()/2;
            int mY = accessoriesDiv.getSizeY()/2;

            for (int i = 0; i < 5; ++i) {
                var accessoryHolder = new AccessoryHolder(
                    0, 0,
                    this.mc.getItemRenderer(), this.dog, false);
                this.accessoryHolders.add(accessoryHolder);
            }

            int accessoryHolderTotalsSize = 5 * AccessoryHolder.WIDGET_SIZE 
                + 4 * BUTTON_SPACING;
            int startX = accessoriesDiv.getRealX() + mX - AccessoryHolder.WIDGET_SIZE/2;
            int pY = accessoriesDiv.getRealY() + mY - accessoryHolderTotalsSize/2;
            for (var holder : this.accessoryHolders) {
                holder.x = startX;
                holder.y = pY;
                accessoriesDiv.addChildren(holder);
                pY += AccessoryHolder.WIDGET_SIZE + BUTTON_SPACING;
            }
        }
        
        this.addChildren(accessoriesDiv);

        

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        //dog div
        {
            int mX = dogDiv.getSizeX()/2;
            int mY = dogDiv.getSizeY()/2;
            int e_mX = dogDiv.getRealX() + mX;
            int e_mY = dogDiv.getRealY() + mY;

            DogStatusViewBoxElement.renderDogInside(stack, this.dog, e_mX, e_mY + 32, 50, 
            e_mX - mouseX, e_mY - mouseY);
        }

        //accessory div
        {
            boolean noRenderAccessory = false;
            int holderIndx = 0;
            var accessories = this.dog.getAccessories();
            for (int i = 0; i < accessories.size(); ++i) {
                var accessory = accessories.get(i); 
                var skin = dog.getClientSkin();
                if (skin.useCustomModel()) {
                    var model = skin.getCustomModel().getValue();
                    if (!model.acessoryShouldRender(dog, accessory)) {
                        noRenderAccessory = true;
                    }
                }
                if (holderIndx >= this.accessoryHolders.size()) break;
                var item = accessory.getReturnItem();
                if (item != null) {
                    var holder = this.accessoryHolders.get(holderIndx);
                    holder.setStack(item);
                    holder.setInventorySlotId(i);
                    ++holderIndx;
                }
            }
            while (holderIndx < this.accessoryHolders.size()) {
                this.accessoryHolders.get(holderIndx).setStack(ItemStack.EMPTY);
                ++holderIndx;
            }

            if (noRenderAccessory) {
                drawNoRenderAccessoryWarning(graphics, mouseX, mouseY, partialTicks);
            }
        }
        
    }

    private void drawNoRenderAccessoryWarning(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)  {
        var txt = Component.translatable("doggui.invalid_dog.accessory_no_render_warn");
        var lines = font.split(txt, this.getSizeX() - 30);
        int pX = this.getRealX() + this.getSizeX()/2;
        int pY = this.getRealY() + this.getSizeY() - font.lineHeight - 3;
        for (int i = lines.size() - 1; i >= 0; --i) {
            pX = this.getRealX() + this.getSizeX()/2 
                - font.width(lines.get(i))/2;
            graphics.drawString(font, lines.get(i), pX, pY, 0xffcda700);
            pY -= font.lineHeight + 3;
        }
    }
    
}
