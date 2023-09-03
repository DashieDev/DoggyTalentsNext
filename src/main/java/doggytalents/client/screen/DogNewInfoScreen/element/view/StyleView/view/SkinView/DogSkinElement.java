package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.SkinView;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveSkinSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DogSkinElement extends AbstractElement {

    private static int LINE_SPACING = 2;

    Dog dog;
    List<DogSkin> locList;
    int activeSkinId;
    Font font;
    boolean showInfo;

    public DogSkinElement(AbstractElement parent, Screen screen, Dog dog, List<DogSkin> locList, int active_id) {
        super(parent, screen);
        this.dog = dog;
        this.locList = locList;
        this.activeSkinId = active_id;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public AbstractElement init() {
        this.showInfo = 
            Store.get(getScreen()).getStateOrDefault(
                ActiveSkinSlice.class, ActiveSkinSlice.class, 
                new ActiveSkinSlice()).showInfo;
            
        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        if (this.locList == null) return;
        if (this.locList.isEmpty()) return;

        

        if (activeSkinId >= this.locList.size()) return;

        if (this.showInfo)
            renderShowInfo(graphics, mouseX, mouseY, partialTicks);
        else
            renderNonShowInfo(graphics, mouseX, mouseY, partialTicks);
         
    }

    private void renderNonShowInfo(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        int e_mX = this.getRealX() + mX;
        int e_mY = this.getRealY() + mY - 10;

        {
            var c1 = Component.literal(locList.get(activeSkinId).getName());
            c1.withStyle(Style.EMPTY.withBold(true));
            int nameX = this.getRealX() + mX - font.width(c1)/2;
            int nameY = this.getRealY() + this.getSizeY() - 13;
            graphics.drawString(font, c1, nameX, nameY, 0xffffffff);
        }

        
        this.renderSkinAndDogModel(activeSkinId, true, graphics, 
            mouseX, mouseY, e_mX, e_mY + 36, 64, false);
        
        int prevId = this.activeSkinId - 1;
        int nextId = this.activeSkinId + 1;

        if (nextId < locList.size()) {
            this.renderSkinAndDogModel(nextId, false, graphics, 
                mouseX, mouseY, e_mX + 32 + 25 + 25, e_mY + 32, 50, true);
        }

        if (prevId >= 0) {
            this.renderSkinAndDogModel(prevId, false, graphics, 
                mouseX, mouseY, e_mX - 32 - 25 - 25, e_mY + 32, 50, true);
        }
    }

    private void renderShowInfo(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        int e_mX = this.getRealX() + mX;
        int e_mY = this.getRealY() + mY - 10;

        this.renderSkinAndDogModel(activeSkinId, true, graphics, 
            mouseX, mouseY, this.getRealX() + 70, e_mY + 36, 64, true);

        var manifestSkin = this.locList.get(activeSkinId);
    
        if (!manifestSkin.hasExtraInfo()) {
            renderNoInfo(graphics, mouseX, mouseY, partialTicks);
            return;
        }
        
        //MockData
        Component basedOn = Component.literal(manifestSkin.getBasedOn());
        Component authors = Component.literal(manifestSkin.getAuthor());
        //END

        var split_basedOn = this.font.split(basedOn, 140);
        var split_authors = this.font.split(authors, 140);

        int lines = 6 + split_authors.size() + split_basedOn.size();
        int size = Mth.floor(lines * font.lineHeight + (lines - 1) * LINE_SPACING);

        int tX = this.getRealX() + mX - 20;
        int tY = this.getRealY() + mY - size/2;

        graphics.drawString(font, "Name: ", tX, tY, 0xffffffff);
        tY += font.lineHeight + LINE_SPACING;
        graphics.drawString(font, manifestSkin.getName(), tX, tY, 0xffffffff);
        tY += font.lineHeight + LINE_SPACING;


        tY += font.lineHeight + LINE_SPACING;
        
        graphics.drawString(font, "Based On: ", tX, tY, 0xffffffff);
        tY += font.lineHeight + LINE_SPACING;
        
        for (var c : split_basedOn) {
            graphics.drawString(font, c, tX, tY, 0xffffffff);
            tY += font.lineHeight + LINE_SPACING;
        }

        
        tY += font.lineHeight + LINE_SPACING;

        graphics.drawString(font, "Author: ", tX, tY, 0xffffffff);
        tY += font.lineHeight + LINE_SPACING;
        
        for (var c : split_authors) {
            graphics.drawString(font, c, tX, tY, 0xffffffff);
            tY += font.lineHeight + LINE_SPACING;
        }
        
    }

    private void renderNoInfo(GuiGraphics graphics,  int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;

        var c1 = Component.literal("No info to show.");

        int tX = this.getRealX() + mX - 10;
        int tY = this.getRealY() + mY - font.lineHeight/2;

        graphics.drawString(font, c1, tX, tY, 0xffffffff);
    }

    private void renderSkinAndDogModel(int indx, boolean followMouse, GuiGraphics graphics, int mouseX, 
        int mouseY, int e_mX, int e_mY, int size, boolean useDummy) {
        var oldSkin = dog.getClientSkin();
        var manifestSkin = this.locList.get(indx);
        if (useDummy && ActiveSkinSlice.DUMMY_DOG_OBJ != null) {
            ActiveSkinSlice.DUMMY_DOG_OBJ.setClientSkin(manifestSkin);
            DogStatusViewBoxElement.renderDogInside(graphics, 
                ActiveSkinSlice.DUMMY_DOG_OBJ, e_mX, e_mY, size, 
                followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
        } else {   
            dog.setClientSkin(manifestSkin);
            DogStatusViewBoxElement.renderDogInside(graphics, dog, e_mX, e_mY, size, 
                followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
            dog.setClientSkin(oldSkin);
        }
        if (oldSkin == manifestSkin) {
            var font = Minecraft.getInstance().font;
            var c1 = Component.translatable("doggui.style.skins.selected");
            c1.setStyle(
                Style.EMPTY
                    .withBold(true)
                    .withColor(0xff1fa800)   
            );
            
            var c1_len = font.width(c1);
            int tX = e_mX - c1_len/2;
            int tY = e_mY + 28;
            
            graphics.drawString(font, c1, tX, tY, 0xffffffff);
        }
    }
    
}
