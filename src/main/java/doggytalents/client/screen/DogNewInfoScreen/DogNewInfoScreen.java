package doggytalents.client.screen.DogNewInfoScreen;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.MainButtonToolboxRowElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DogDescriptionViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DogInfoNavBarElement;
import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DivElement;
import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.PosType;
import doggytalents.client.screen.DogNewInfoScreen.element.view.AccessoriesView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.StatsView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.TalentView.TalentView;
import doggytalents.client.screen.DogNewInfoScreen.store.UIAction;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTabSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.Store;
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
        var screen = new DogNewInfoScreen(dog);
        mc.setScreen(screen);
        Store.get(screen);
    }

    @Override
    public void init() {
        super.init();
        
        int mX = this.width/2;
        int mY = this.height/2;
        var navBar = new DogInfoNavBarElement(null, this)
            .setPosition(PosType.FIXED, mX, this.height - 12)
            .setSize(200, 10)
            .init();
        this.addRenderableWidget(navBar);
        var upperView = new DivElement(null, this)
            .setPosition(PosType.FIXED, 0, 0)
            .setSize(this.width, this.height - 20);
        this.addRenderableWidget(upperView);
        var selectedTab = Store.get(this).getStateOrDefault(
            ActiveTabSlice.class, ActiveTabSlice.Tab.class, 
            ActiveTabSlice.Tab.HOME
        );
        AbstractElement view;
        switch (selectedTab) {
            case ACCESSORIES:
                view = new AccessoriesView(upperView, this, dog);
                break;
            case STATS:
                view = new StatsView(upperView, this, dog);
                break;
            case TALENTS:
                view = new TalentView(upperView, this, dog);
                break;
            default:
                view = new MainInfoView(upperView, this, dog);
                break;
        }

        upperView.addChildren(
            view
            .setPosition(PosType.ABSOLUTE, 0, 0)
            .setSize(1f, 1f)
            //.setBackgroundColor(0xff32a852)
            .init()
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
        font.draw(stack, Component.literal("height : " + this.height ), 3 , 11, 0xffffffff);
        font.draw(stack, Component.literal("cursorX : " + p_96563_ ), 3 , 19, 0xffffffff);
        font.draw(stack, Component.literal("cursorY : " + p_96564_ ), 3 , 27, 0xffffffff);
        //this.font.draw(p_96562_, ActiveTabSlice.activeTab.title, 0, 0, 0xffffff);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        super.onClose();
        Store.finish();
    }

    @Override
    public void resize(Minecraft p_96575_, int width, int height) {
        Store.get(this).dispatchAll(
            new UIAction("resize", new Object()),
            width, height
        );
    }
    
}
