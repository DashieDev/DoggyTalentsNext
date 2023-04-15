package doggytalents.client.screen.AmnesiaBoneScreen.element.view.GeneralView;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.client.screen.framework.types.TextType.Align;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.MultiLineFlatButton;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GeneralView extends AbstractElement {

    Dog dog;
    Font font;

    public GeneralView(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public AbstractElement init() {
        
        // var tab = Store.get(getScreen())
        //     .getStateOrDefault(MainPanelSlice.class, 
        //     MainTab.class, MainTab.MAIN);
        
        // switch (tab) {
        //     case EDIT_INFO:
        //         setupPanelView(tab);
        //         break;
        //     case DEBUG:
        //         setupPanelView(tab);
        //         break;
        //     default:
        //         setupMainView();
        //         break;
            
        // }
        setupMainView();

        return this;
    }

    private void setupMainView() {
        int mX = this.getScreen().width/2;
        int mY = this.getScreen().height/2;
        this.addChildren(
            new DogStatusViewBoxElement(this, this.getScreen(), this.dog)
            .setPosition(PosType.FIXED, mX - 105 - 10, mY - 105/2)
            .setSize(105)
            //.setBackgroundColor(0xffe39c02)
        );
        this.addChildren(
            new MultiLineFlatButton(mX + 20, mY - 40, 80, 30, List.of(
                Component.literal("Ownership"),
                Component.literal("change")
            ), b -> {

            }) {
                @Override
                public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
                    super.render(stack, mouseX, mouseY, partialTicks);
                    int tX = this.x + this.width - 10;
                    int tY = this.y + this.height/2 - font.lineHeight/2;
                    font.draw(stack, ">", tX, tY, 0xffffffff);
                }
            }.setTextAlign(Align.LEFT)
        );
        this.addChildren(
            new MultiLineFlatButton(mX + 20, mY + 10, 80, 20, List.of(
                Component.literal("Untame")
            ), b -> {

            }) {
                @Override
                public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
                    super.render(stack, mouseX, mouseY, partialTicks);
                    int tX = this.x + this.width - 10;
                    int tY = this.y + this.height/2 - font.lineHeight/2;
                    font.draw(stack, ">", tX, tY, 0xffffffff);
                }
            }.setTextAlign(Align.LEFT)
        );
    }

    // private void setupPanelView(MainTab tab) {
    //     int sizeX = this.getSizeX();
    //     int sizeY = this.getSizeY();

    //     int mX = sizeX/2;
    //     int mY = sizeY/2;

    //     int editInfoViewBoxSizeX = sizeX > 507 ? 448 : sizeX;
    //     int editInfoViewBoxSizeY = sizeY > 304 ? 320 : sizeY;
        
    //     var editInfoViewBoxDiv = new DivElement(this, getScreen())
    //         .setPosition(PosType.ABSOLUTE, mX - editInfoViewBoxSizeX/2, 
    //         mY - editInfoViewBoxSizeY/2 + (sizeY > 304 ? 10 : 0)) //+10 if detached to center it.
    //         .setSize(editInfoViewBoxSizeX, editInfoViewBoxSizeY);
    //         //.setBackgroundColor(0xffff05de);
    //     this.addChildren(editInfoViewBoxDiv);

    //     var editInfoListDiv = new MainViewListPanel(editInfoViewBoxDiv, getScreen())
    //         .setPosition(PosType.RELATIVE, 0, 0)
    //         .setSize(120, 1f)
    //         .setBackgroundColor(0x87363636)
    //         .init();

    //     editInfoViewBoxDiv.addChildren(editInfoListDiv);

    //     AbstractElement rightView;
    //     switch (tab) {
    //         case DEBUG:
    //             rightView = new DebugView(editInfoViewBoxDiv, getScreen(), dog);
    //             break;
    //         default:
    //             rightView = new EditInfoView(editInfoViewBoxDiv, getScreen(), dog, font);
    //             break;
    //     }
        
    //     rightView
    //         .setPosition(PosType.RELATIVE, 0, 0)
    //         .setSize(editInfoViewBoxDiv.getSizeX() - 120, 1f)
    //         .setBackgroundColor(0x57595858)
    //         .init();
    //     editInfoViewBoxDiv.addChildren(rightView);
        
    // }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

    }
    
}
