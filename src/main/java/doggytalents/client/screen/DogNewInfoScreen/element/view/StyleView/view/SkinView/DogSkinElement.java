package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.SkinView;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveSkinSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class DogSkinElement extends AbstractElement {

    Dog dog;
    List<DogSkin> locList;
    int activeSkinId;
    Font font;

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
            
        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        if (this.locList == null) return;
        if (this.locList.isEmpty()) return;

        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        int e_mX = this.getRealX() + mX;
        int e_mY = this.getRealY() + mY - 10;

        if (activeSkinId >= this.locList.size()) return;

        {
            var c1 = Component.literal(locList.get(activeSkinId).getName());
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
            var c1 = Component.translatable("doggui.style.skins.selected");
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
