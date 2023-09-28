package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.SkinView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.client.screen.framework.widget.ScrollBar;
import doggytalents.client.screen.framework.widget.ScrollBar.Direction;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SkinView extends AbstractElement {

    Dog dog;
    List<DogSkin> textureList;
    EditBox filterBox;
    ScrollBar scrollBar;
    int activeSkinId;
    boolean skipReOffset;
    
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
        scrollBar = new ScrollBar(0, 0, 0, 0, Direction.HORIZONTAL, 0) {
            @Override
            public void onValueUpdated() {
                calulateAndSetActiveId(this.getProgressValue());
            }
        };
        this.activeSkinId = DogTextureManager.INSTANCE.getAll().indexOf(dog.getClientSkin());
    }

    @Override
    public AbstractElement init() {
        this.textureList = DogTextureManager.INSTANCE.getAll();
        var searchMsg = this.filterBox.getValue();
        if (!searchMsg.isEmpty())
            this.textureList = filterDogSkin(textureList, searchMsg);
        if (this.activeSkinId < 0) this.activeSkinId = 0;
        else if (this.activeSkinId >= this.textureList.size() ) 
            this.activeSkinId = 0;

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

        this.scrollBar.setX(this.getRealX());
        this.scrollBar.setY(this.getRealY() + this.getSizeY() - 8);
        this.scrollBar.setWidth(this.getSizeX());
        this.scrollBar.setHeight(8);
        int barsize = Mth.floor(scrollBar.getWidth()/this.textureList.size());
        barsize = Math.max(barsize, 20);
        this.scrollBar.setBarSize(barsize);
        if (skipReOffset) {
            skipReOffset = false;
        } else {
            moveBarToDog();
        }

        this.addChildren(scrollBar);
        

        return this;
    }

    private void moveBarToDog() {
        if (this.textureList.isEmpty())
            return;
        double progress = (double)this.activeSkinId/ (double) (textureList.size()-1);
        double barOffset = progress * this.scrollBar.getMaxOffsetValue();
        this.scrollBar.setBarOffset(barOffset);
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
        var tag_strs = s.split(Pattern.quote(" "));
        var ret = new ArrayList<DogSkin>();
        for (var x : locList) {
            if (StringUtils.containsIgnoreCase(x.getName(), s)) {
                ret.add(x);
            } else if (isTagMatch(x, tag_strs)) {
                ret.add(x);
            }
        }
        return ret;
    }

    private static boolean isTagMatch(DogSkin skin, String[] tag_strs) {
        for (var str : tag_strs) {
            if (!StringUtils.containsIgnoreCase(skin.getTags(), str)) {
                return false;
            }
        }
        return true;
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
    
    private void calulateAndSetActiveId(double progress) {
        int id = Mth.floor(progress * (this.textureList.size()-1));
        if (this.activeSkinId == id) return;
        this.activeSkinId = id;
        this.skipReOffset = true;
        this.children().clear();
        this.init();
    }
    
}
