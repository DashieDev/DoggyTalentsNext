package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.SkinView;

import java.util.List;
import java.util.function.Consumer;

import org.checkerframework.checker.units.qual.C;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveSkinSlice;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogTextureData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.PacketDistributor;

public class SkinButtonElement extends AbstractElement {

    Dog dog;
    Font font;
    List<DogSkin> locList;
    Button showHideInfoButton;
    Button applyButton;
    int activeSkinId;
    boolean showInfo;
    Consumer<GuiEventListener> leftAction;
    Consumer<GuiEventListener> rightAction;

    public SkinButtonElement(AbstractElement parent, Screen screen, Dog dog, List<DogSkin> locList, int active_id,
        Consumer<GuiEventListener> leftAction, Consumer<GuiEventListener> rightAction) {
        super(parent, screen);
        this.dog = dog;
        var mc = Minecraft.getInstance();
        this.font = mc.font;
        this.activeSkinId = active_id;
        this.locList = locList;
        this.leftAction = leftAction;
        this.rightAction = rightAction;
    }

    @Override
    public AbstractElement init() {
        showInfo = 
            Store.get(getScreen()).getStateOrDefault(
                ActiveSkinSlice.class, ActiveSkinSlice.class, 
                new ActiveSkinSlice()).showInfo;
        
        
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        
        if (locList == null) return this;
        if (locList.isEmpty()) return this;
        var prevSkinButton = new TextOnlyButton(
            this.getRealX() + 10, this.getRealY() + mY - 9,
            18, 18, Component.literal("<"), 
            b -> {
                this.leftAction.accept(b);
            }, this.font);
        prevSkinButton.active = activeSkinId > 0;
        
        

        var nextSkinButton = new TextOnlyButton(
            this.getRealX() + 75, this.getRealY() + mY - 9,
            18, 18, Component.literal(">"), 
            b -> {
                this.rightAction.accept(b);
            }, this.font);
            nextSkinButton.active = activeSkinId < this.locList.size() - 1;

        showHideInfoButton = applyButton = new CustomButton(
            this.getRealX() + this.getSizeX() - 30 - 30 - 62,
            this.getRealY() + mY - 10, 58, 20,
            Component.literal(!this.showInfo ? "Show Info" : "Hide Info"),
            b -> {
                Store.get(getScreen()).dispatch(ActiveSkinSlice.class, 
                    new UIAction(
                        !this.showInfo ?
                        UIActionTypes.Skins.SHOW_INFO : UIActionTypes.Skins.HIDE_INFO,
                        new ActiveSkinSlice()
                    ) 
                );
            }  
        );

        applyButton = new CustomButton(
            this.getRealX() + this.getSizeX() - 30 - 30,
            this.getRealY() + mY - 10, 40, 20,
            Component.literal("Apply"),
            b -> {
                applyAndRequestSkinChange(activeSkinId);
            }  
        );
        applyButton.active = (locList.get(activeSkinId))
            != dog.getClientSkin();

        this.addChildren(prevSkinButton);
        this.addChildren(nextSkinButton);
        this.addChildren(applyButton);
        this.addChildren(showHideInfoButton);
        
        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        
        int tX = this.getRealX() + 52;
        int tY = this.getRealY() + this.getSizeY()/2 - font.lineHeight/2;
        String str = (this.activeSkinId+1) + "/" + this.locList.size();
        tX -= font.width(str)/2;
        graphics.drawString(font, str, tX, tY, 0xffffffff);
    }

    public void applyAndRequestSkinChange(int id) {
        int size = locList.size();
        if (id >= size || id < 0) return;
        var selectedSkin = locList.get(id);
        String requestHash = null;
        if (selectedSkin == DogSkin.CLASSICAL 
            || selectedSkin == DogSkin.MISSING) 
            requestHash = "";
        else {
            requestHash = DogTextureManager.INSTANCE.getHash((locList.get(id)));
        }

        applyButton.active = false;

        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new DogTextureData(this.dog.getId(), requestHash));

    }
    
}
