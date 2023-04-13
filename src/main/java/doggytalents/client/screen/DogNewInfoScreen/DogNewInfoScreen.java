package doggytalents.client.screen.DogNewInfoScreen;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.MainButtonToolboxRowElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DogInfoNavBarElement;
import doggytalents.client.screen.DogNewInfoScreen.element.AbstractElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DivElement;
import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.PosType;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogDescriptionViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.MainInfoView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.StatsView.StatsView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.StyleView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.TalentView.TalentView;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.*;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTabSlice.Tab;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.StoreConnectedScreen;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DogNewInfoScreen extends StoreConnectedScreen {

    private Dog dog;

    private TextOnlyButton rightTabButton;
    private TextOnlyButton lefTabButton;

    private DogNewInfoScreen(Dog dog) {
        super(Component.translatable("doggytalents.screen.dog.title"));
        this.dog = dog;
        
        this.rightTabButton = new TextOnlyButton(0, 0, 0, 0, Component.literal(">"), b -> {
            Store.get(this).dispatch(ActiveTabSlice.class, 
                new UIAction(UIActionTypes.CHANGE_TAB_NEXT, null)
            );
        }, Minecraft.getInstance().font);

        this.lefTabButton = new TextOnlyButton(0, 0, 0, 0, Component.literal("<"), b -> {
            Store.get(this).dispatch(ActiveTabSlice.class, 
                new UIAction(UIActionTypes.CHANGE_TAB_PREV, null)
            );
        }, Minecraft.getInstance().font);

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
        var navBar = new DogInfoNavBarElement(null, this, this.dog)
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
            case STYLE:
                view = new StyleView(upperView, this, dog);
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
        
        this.addSwitchTabButtons(selectedTab);

        //var button = new Button(0, 0, 40, 20, Component.literal(A ctiveTabSlice.activeTab.title), b -> {});
        //this.addRenderableWidget(button);
    }

    private void addSwitchTabButtons(Tab activeTab) {
        int mY = this.height/2;

        int requiredX;
        switch (activeTab) {
            case STATS:
                requiredX = 600;
                break;
            case STYLE:
                requiredX = 600;
                break;
            case TALENTS:
                requiredX = 700;
                break;
            default:
                requiredX = 600;
                break;
        }
        if (this.width < requiredX) return;

        final int CHANGE_TAB_BUTTON_MARGIN = 15;
        
        this.lefTabButton.x = CHANGE_TAB_BUTTON_MARGIN;
        this.lefTabButton.setWidth(30);
        this.lefTabButton.setHeight(100);
        this.lefTabButton.y = mY - 50;

        this.rightTabButton.x = this.width - 30 - CHANGE_TAB_BUTTON_MARGIN;
        this.rightTabButton.setWidth(30);
        this.rightTabButton.setHeight(100);
        this.rightTabButton.y = mY - 50;

        this.addRenderableWidget(this.lefTabButton);
        this.addRenderableWidget(this.rightTabButton);

    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {

        
        
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, pTicks);

        //font.draw(stack, Component.literal("width : " + this.width ), 3 , 3, 0xffffffff);
        //font.draw(stack, Component.literal("height : " + this.height ), 3 , 11, 0xffffffff);
        //font.draw(stack, Component.literal("cursorX : " + mouseX ), 3 , 19, 0xffffffff);
        //font.draw(stack, Component.literal("cursorY : " + mouseY ), 3 , 27, 0xffffffff);
        //this.font.draw(p_96562_, ActiveTabSlice.activeTab.title, 0, 0, 0xffffff);

        //ToolTipManager
        var toolTipManager = ToolTipOverlayManager.get();
        if (toolTipManager.hasToolTip()) {
            toolTipManager.render(this, stack, mouseX, mouseY);
            toolTipManager.reset();
        }
        
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.getFocused() instanceof EditBox) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        var mc = Minecraft.getInstance();
        var options = mc.options;
        
        if (keyCode == options.keyLeft.getKey().getValue()) {
            this.lefTabButton.playDownSound(mc.getSoundManager());
            this.lefTabButton.onClick(0, 0);
        } else if (keyCode == options.keyRight.getKey().getValue()) {
            this.rightTabButton.playDownSound(mc.getSoundManager());
            this.rightTabButton.onClick(0, 0);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public List<Class<? extends AbstractSlice>> getSlices() {
        return List.of(
            ActiveTabSlice.class,
            TalentListSlice.class,
            TalentListPageCounterSlice.class,
            ActiveTalentDescSlice.class,
            StyleViewPanelSlice.class,
            ActiveSkinSlice.class,
            StatsViewPanelSlice.class,
            MainPanelSlice.class
        );
    }

}
