package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.AccessoryView;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.widget.AccessoryHolder;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.AccessoryItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AccessoryEditElement extends AbstractElement {

    private static final int BUTTON_SPACING = 4;

    Player player;
    Dog dog;
    Inventory inventory;
    Minecraft mc;
    final ArrayList<AccessoryHolder> accessoryHolders = new ArrayList<AccessoryHolder>(5);

    TextOnlyButton lastPage;
    TextOnlyButton nextPage;

    //Local State that gets updated every frame.
    int startIndex = 0;

    public AccessoryEditElement(AbstractElement parent, Screen screen, Player player, Dog dog) {
        super(parent, screen);
        //TODO Auto-generated constructor stub
        mc = Minecraft.getInstance();
        this.player = player;
        this.dog = dog;
        inventory = player.getInventory();
    }

    @Override
    public AbstractElement init() {

        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;

        for (int i = 0; i < 5; ++i) {
            var accessoryHolder = new AccessoryHolder(
                0, 0,
                this.mc.getItemRenderer(), this.dog, true);
            this.accessoryHolders.add(accessoryHolder);
        }

        int accessoryHolderTotalsSize = 
            5 * AccessoryHolder.WIDGET_SIZE + 4 * BUTTON_SPACING;
        int startY = this.getRealY() + mY - AccessoryHolder.WIDGET_SIZE/2;
        int pX = this.getRealX() + mX - accessoryHolderTotalsSize/2;
        for (var holder : this.accessoryHolders) {
            holder.setX(pX);
            holder.setY(startY);
            this.addChildren(holder);
            pX += AccessoryHolder.WIDGET_SIZE + BUTTON_SPACING;
        }

        
        this.lastPage = new TextOnlyButton(
            this.getRealX() + mX - 80 - 9, 
            this.getRealY() + mY - 9, 18, 18,  
            Component.literal("<"), b -> {
                startIndex -= accessoryHolders.size();
            }, mc.font);
        this.addChildren(lastPage);

        this.nextPage = new TextOnlyButton(
            this.getRealX() + mX + 80 - 9, 
            this.getRealY() + mY - 9, 18, 18, 
            Component.literal(">"), b -> {
                startIndex += accessoryHolders.size();
            }, mc.font);
        this.addChildren(nextPage);

        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int holderIndx = 0;
        var items = this.inventory.items;
        this.lastPage.active = startIndex > 0;
        this.nextPage.active = false;
        this.startIndex = Math.max(0, startIndex);
        int tillStart = startIndex;
        var skin = dog.getClientSkin();
        for (int i = 0; i < items.size(); ++i) {
            var item = items.get(i);
            if (item == null || !this.eligibleAccessory(item)) continue;
            if (holderIndx >= this.accessoryHolders.size()) {
                this.nextPage.active = true;
                break;
            }
            if (--tillStart < 0) {
                var holder = this.accessoryHolders.get(holderIndx);
                holder.setStack(item);
                holder.setInventorySlotId(i);
                holder.warning = false;
                if (skin.useCustomModel() && (item.getItem() instanceof AccessoryItem accessory)) {
                    var model = skin.getCustomModel().getValue();
                    holder.warning = model.warnAccessory(dog, accessory.type.get());
                }
                ++holderIndx;
            }
        }
        if (holderIndx <= 0) {
            if (startIndex > 0) {
                this.startIndex = 0;
            } else {
                int mX = this.getSizeX()/2;
                int mY = this.getSizeY()/2;
                var txt = Component.translatable("doggui.style.accessories.no_accessories_in_inv");
                int tX = this.getRealX() + mX - mc.font.width(txt)/2;
                int tY = this.getRealY() + mY - mc.font.lineHeight/2;
                graphics.drawString(mc.font, txt, tX, tY, 0xffffffff);
            }
        } else {
            int tX = this.getRealX() + 6;
            int tY = this.getRealY() + 6;
            graphics.drawString(mc.font, I18n.get("doggui.style.accessories.your_accessories"), tX, tY, 0xffffffff);
        }
        while (holderIndx < this.accessoryHolders.size()) {
            this.accessoryHolders.get(holderIndx).setStack(ItemStack.EMPTY);
            ++holderIndx;
        }
    }

    private boolean eligibleAccessory(@Nonnull ItemStack stack) {
        var item = stack.getItem();
        return item instanceof AccessoryItem;
    }
    
}
