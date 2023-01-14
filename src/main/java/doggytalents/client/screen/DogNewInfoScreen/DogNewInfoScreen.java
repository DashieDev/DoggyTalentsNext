package doggytalents.client.screen.DogNewInfoScreen;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DogInfoNavBarElement;
import doggytalents.client.screen.DogNewInfoScreen.element.ViewElement;
import doggytalents.client.screen.DogNewInfoScreen.store.ActiveTabSlice;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DogNewInfoScreen extends Screen {

    private Dog dog;

    private DogNewInfoScreen(Dog dog) {
        super(Component.translatable("doggytalents.screen.dog.title"));
        this.dog = dog;
    }

    public static void open(Dog dog) {
        var mc = Minecraft.getInstance();
        mc.setScreen(new DogNewInfoScreen(dog));
    }

    @Override
    public void init() {
        super.init();
        
        int mX = this.width/2;
        int mY = this.height/2;
        var navBar = new DogInfoNavBarElement(null, 
            mX, this.height - 12, 200, 10, this);
        this.addRenderableWidget(navBar);
        var upperView = new ViewElement(null, 0, 0, 
            this.width, this.height - 20, this)
            .setBackgroundColor(0xff32a852);
        this.addRenderableWidget(upperView);
        upperView.addChildren(
            new DogStatusViewBoxElement(upperView, 10, 10, 100, this, this.dog)
        );
        //var button = new Button(0, 0, 40, 20, Component.literal(A ctiveTabSlice.activeTab.title), b -> {});
        //this.addRenderableWidget(button);
    }

    @Override
    public void render(PoseStack stack, int p_96563_, int p_96564_, float p_96565_) {
        // TODO Auto-generated method stub
        this.renderBackground(stack);
        super.render(stack, p_96563_, p_96564_, p_96565_);
        
        font.draw(stack, Component.literal("width : " + this.width ), 3 , 3, 0xffffffff);
        font.draw(stack, Component.literal("height : " + this.height ), 3 , 9, 0xffffffff);
        //this.font.draw(p_96562_, ActiveTabSlice.activeTab.title, 0, 0, 0xffffff);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
}
