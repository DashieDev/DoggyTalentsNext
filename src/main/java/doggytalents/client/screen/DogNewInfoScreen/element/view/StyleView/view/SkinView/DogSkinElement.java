package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.SkinView;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveSkinSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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

    public DogSkinElement(AbstractElement parent, Screen screen, Dog dog, List<DogSkin> locList) {
        super(parent, screen);
        this.dog = dog;
        this.locList = locList;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public AbstractElement init() {
        this.activeSkinId = 
            Store.get(getScreen()).getStateOrDefault(
                ActiveSkinSlice.class, ActiveSkinSlice.class, 
                new ActiveSkinSlice()).activeSkinId;
        this.showInfo = 
            Store.get(getScreen()).getStateOrDefault(
                ActiveSkinSlice.class, ActiveSkinSlice.class, 
                new ActiveSkinSlice()).showInfo;
            
        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        if (this.locList == null) return;
        if (this.locList.isEmpty()) return;

        

        if (activeSkinId >= this.locList.size()) return;

        if (this.showInfo)
            renderShowInfo(stack, mouseX, mouseY, partialTicks);
        else
            renderNonShowInfo(stack, mouseX, mouseY, partialTicks);
         
    }

    private void renderNonShowInfo(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        int e_mX = this.getRealX() + mX;
        int e_mY = this.getRealY() + mY - 10;

        {
            var c1 = ComponentUtil.literal(locList.get(activeSkinId).getName());
            c1.withStyle(Style.EMPTY.withBold(true));
            int nameX = this.getRealX() + mX - font.width(c1)/2;
            int nameY = this.getRealY() + this.getSizeY() - 13;
            font.draw(stack, c1, nameX, nameY, 0xffffffff);
        }

        
        this.renderSkinAndDogModel(activeSkinId, true, stack, 
            mouseX, mouseY, e_mX, e_mY + 36, 64);
        
        int prevId = this.activeSkinId - 1;
        int nextId = this.activeSkinId + 1;

        if (nextId < locList.size()) {
            this.renderSkinAndDogModel(nextId, false, stack, 
                mouseX, mouseY, e_mX + 32 + 25 + 25, e_mY + 32, 50);
        }

        if (prevId >= 0) {
            this.renderSkinAndDogModel(prevId, false, stack, 
                mouseX, mouseY, e_mX - 32 - 25 - 25, e_mY + 32, 50);
        }
    }

    private void renderShowInfo(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        int e_mX = this.getRealX() + mX;
        int e_mY = this.getRealY() + mY - 10;

        this.renderSkinAndDogModel(activeSkinId, true, stack, 
            mouseX, mouseY, this.getRealX() + 70, e_mY + 36, 64);

        var manifestSkin = this.locList.get(activeSkinId);
    
        if (!manifestSkin.hasExtraInfo()) {
            renderNoInfo(stack, mouseX, mouseY, partialTicks);
            return;
        }
        
        //MockData
        Component basedOn = ComponentUtil.literal(manifestSkin.getBasedOn());
        Component authors = ComponentUtil.literal(manifestSkin.getAuthor());
        //END

        var split_basedOn = this.font.split(basedOn, 140);
        var split_authors = this.font.split(authors, 140);

        int lines = 6 + split_authors.size() + split_basedOn.size();
        int size = Mth.floor(lines * font.lineHeight + (lines - 1) * LINE_SPACING);

        int tX = this.getRealX() + mX - 20;
        int tY = this.getRealY() + mY - size/2;

        font.draw(stack, "Name: ", tX, tY, 0xffffffff);
        tY += font.lineHeight + LINE_SPACING;
        font.draw(stack, manifestSkin.getName(), tX, tY, 0xffffffff);
        tY += font.lineHeight + LINE_SPACING;


        tY += font.lineHeight + LINE_SPACING;
        
        font.draw(stack, "Based On: ", tX, tY, 0xffffffff);
        tY += font.lineHeight + LINE_SPACING;
        
        for (var c : split_basedOn) {
            font.draw(stack, c, tX, tY, 0xffffffff);
            tY += font.lineHeight + LINE_SPACING;
        }

        
        tY += font.lineHeight + LINE_SPACING;

        font.draw(stack, "Author: ", tX, tY, 0xffffffff);
        tY += font.lineHeight + LINE_SPACING;
        
        for (var c : split_authors) {
            font.draw(stack, c, tX, tY, 0xffffffff);
            tY += font.lineHeight + LINE_SPACING;
        }
        
    }

    private void renderNoInfo(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;

        var c1 = ComponentUtil.literal("No info to show.");

        int tX = this.getRealX() + mX - 10;
        int tY = this.getRealY() + mY - font.lineHeight/2;

        font.draw(stack, c1, tX, tY, 0xffffffff);
    }

    private void renderSkinAndDogModel(int indx, boolean followMouse, PoseStack stack, int mouseX, 
        int mouseY, int e_mX, int e_mY, int size) {
        var oldSkin = dog.getClientSkin();
        var manifestSkin = this.locList.get(indx);
        dog.setClientSkin(manifestSkin);
        DogStatusViewBoxElement.renderDogInside(stack, dog, e_mX, e_mY, size, 
            followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
        
        dog.setClientSkin(oldSkin);
        if (oldSkin == manifestSkin) {
            var font = Minecraft.getInstance().font;
            var c1 = ComponentUtil.translatable("doggui.style.skins.selected");
            c1.setStyle(
                Style.EMPTY
                    .withBold(true)
                    .withColor(0xff1fa800)   
            );
            
            var c1_len = font.width(c1);
            int tX = e_mX - c1_len/2;
            int tY = e_mY + 28;
            
            font.draw(stack, c1, tX, tY, 0xffffffff);
        }
    }
    
}
