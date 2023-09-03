package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.SkinView;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkinView extends AbstractElement {

    Dog dog;
    List<DogSkin> textureList;
    EditBox filterBox;
    int activeSkinId;
    
    public SkinView(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        filterBox = new EditBox(Minecraft.getInstance().font, 
        0, 0, 100, 16, Component.empty());
        filterBox.setResponder(str -> {
            this.children().clear();
            this.activeSkinId = 0;
            this.init();
        });
    }

    @Override
    public AbstractElement init() {
        this.textureList = DogTextureManager.INSTANCE.getAll();
        var searchMsg = this.filterBox.getValue();
        if (!searchMsg.isEmpty())
            this.textureList = filterDogSkin(textureList, searchMsg);
        this.getPosition().setChildDirection(ChildDirection.COL);

        var dogSkinPreview = new DogSkinElement(this, getScreen(), this.dog, textureList, this.activeSkinId);
        dogSkinPreview
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, 0.8f)
            .setBackgroundColor(0x87363636)
            .init();
        this.addChildren(dogSkinPreview);
        
        var skinSelect = new SkinButtonElement(this, getScreen(), this.dog, textureList, this.activeSkinId,
            b -> decreaseActiveId(b), b -> increaseActiveId(b));
        skinSelect
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, 0.2f)
            .setBackgroundColor(0xAA595858)
            .init();
        this.addChildren(skinSelect);
        
        filterBox.setX(this.getRealX() + 8);
        filterBox.setY(this.getRealY() + 8);
        this.addChildren(filterBox);
        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // int pX = this.getRealX() + 40;
        // int pY = this.getRealY() + 40;
        // InventoryScreen.renderEntityInInventory(pX, pY + 32, 50, 
        // pX - mouseX, pY - mouseY, this.dog);
        // var oldHash = dog.getSkinHash();
        // var manifestHash = DogTextureManager.INSTANCE.getTextureHash(this.textureList.get(2));
        // dog.setSkinHash(manifestHash);
        // pX += 60;
        // InventoryScreen.renderEntityInInventory(pX, pY + 32, 50, 
        // pX - mouseX, pY - mouseY, this.dog);
        // dog.setSkinHash(oldHash);
    }

     private static List<DogSkin> filterDogSkin(List<DogSkin> locList, String s) {
        var ret = new ArrayList<DogSkin>();
        for (var x : locList) {
            if (x.getName().contains(s)) {
                ret.add(x);
            }
        }
        return ret;
    }

    private void decreaseActiveId(GuiEventListener b) {
        this.activeSkinId = Math.max(0, this.activeSkinId - 1);
        this.children().clear();
        this.init();
    }

    private void increaseActiveId(GuiEventListener b) {
        this.activeSkinId = Math.min(textureList.size() - 1, this.activeSkinId + 1);
        this.children().clear();
        this.init();
    } 
    
}
