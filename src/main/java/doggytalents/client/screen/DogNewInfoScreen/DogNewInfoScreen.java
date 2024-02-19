package doggytalents.client.screen.DogNewInfoScreen;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.MainButtonToolboxRowElement;
import doggytalents.client.screen.DogNewInfoScreen.element.DogInfoNavBarElement;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogDescriptionViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.MainInfoView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.StatsView.StatsView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.StyleView;
import doggytalents.client.screen.DogNewInfoScreen.element.view.TalentView.TalentView;
import doggytalents.client.screen.DogNewInfoScreen.screen.DogCannotInteractWithScreen;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.*;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTabSlice.Tab;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.CommonUIActionTypes;
import doggytalents.client.screen.framework.DropdownMenuManager;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.StoreConnectedScreen;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DogNewInfoScreen extends StoreConnectedScreen {

    public final Dog dog;

    private TextOnlyButton rightTabButton;
    private TextOnlyButton lefTabButton;
    private boolean sideTabNavLocked = false;

    private DogNewInfoScreen(Dog dog) {
        super(Component.translatable("doggytalents.screen.dog.title"));
        this.dog = dog;
        
        this.rightTabButton = new TextOnlyButton(0, 0, 0, 0, Component.literal(">"), b -> {
            var selectedTab = Store.get(this).getStateOrDefault(
                ActiveTabSlice.class, ActiveTabSlice.Tab.class, 
                ActiveTabSlice.Tab.HOME
            );
            Store.get(this).dispatchAll(
                ActiveTabSlice.UIActionCreator(dog, ActiveTabSlice.getNextTab(selectedTab),
                    CommonUIActionTypes.SWITCH_TAB)
            );
        }, Minecraft.getInstance().font) {

            private boolean selected = false;

            @Override
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
                super.renderWidget(graphics, mouseX, mouseY, pticks);

                //draw key hint
                var mc = Minecraft.getInstance();
                var key_right = mc.options.keyRight;
                var key_str = key_right.getTranslatedKeyMessage();
                var bg_color = selected ?
                    0x487500A5 : 0x485e5d5d;

                var key_str_len = font.width(key_str);
                var tX = this.getX() + this.getWidth()/2
                    - key_str_len/2;
                var tY = this.getY() + this.getHeight()/2 + 20;
                graphics.drawString(font, key_str, tX, tY, 0xffffffff);
                
                graphics.fill(tX - 1, tY - 1, 
                    tX + key_str_len + 1, tY + font.lineHeight + 1, bg_color); 
            }

            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                var mc = Minecraft.getInstance();
                if (keyCode == mc.options.keyRight.getKey().getValue()) {
                    this.selected = true;
                }
                return false;
            }

            @Override
            public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
                this.selected = false;

                return false;
            }

        };

        this.lefTabButton = new TextOnlyButton(0, 0, 0, 0, Component.literal("<"), b -> {
            var selectedTab = Store.get(this).getStateOrDefault(
                ActiveTabSlice.class, ActiveTabSlice.Tab.class, 
                ActiveTabSlice.Tab.HOME
            );
            Store.get(this).dispatchAll(
                ActiveTabSlice.UIActionCreator(dog, ActiveTabSlice.getPrevTab(selectedTab),
                    CommonUIActionTypes.SWITCH_TAB)
            );
        }, Minecraft.getInstance().font) {

            private boolean selected;

            @Override
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pticks) {
                super.renderWidget(graphics, mouseX, mouseY, pticks);

                //draw key hint
                var mc = Minecraft.getInstance();
                var key_left = mc.options.keyLeft;
                var key_str = key_left.getTranslatedKeyMessage();
                var bg_color = selected ?
                    0x487500A5 : 0x485e5d5d;

                var key_str_len = font.width(key_str);
                var tX = this.getX() + this.getWidth()/2
                    - key_str_len/2;
                var tY = this.getY() + this.getHeight()/2 + 20;
                graphics.drawString(font, key_str, tX, tY, 0xffffffff);
                
                graphics.fill(tX - 1, tY - 1, 
                    tX + key_str_len + 1, tY + font.lineHeight + 1, bg_color); 
            }

            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                var mc = Minecraft.getInstance();
                if (keyCode == mc.options.keyLeft.getKey().getValue()) {
                    this.selected = true;
                }
                return false;
            }

            @Override
            public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
                this.selected = false;

                return false;
            }

        };

    }

    public static void open(Dog dog) { open(dog, ActiveTabSlice.Tab.HOME); }
    
    public static void open(Dog dog, ActiveTabSlice.Tab initTab) {
        var mc = Minecraft.getInstance();
        var screen = new DogNewInfoScreen(dog);
        Store.get(screen);
        mc.setScreen(screen);
        Store.get(screen).dispatchAll(
            ActiveTabSlice.UIActionCreator(dog, initTab, CommonUIActionTypes.CHANGE_TAB)
        );
    }

    @Override
    public void init() {
        super.init();

        this.setupDropdown();
        
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
        var selectedTab = getStateAndSubscribesTo(
            ActiveTabSlice.class, ActiveTabSlice.Tab.class, 
            null
        );
        if (selectedTab == null) return;
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

    public void setupDropdown() {
        var dropdownManager = DropdownMenuManager.get(this);
        dropdownManager.attach(this, dropdown -> this.addWidget(dropdown));
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
        
        this.lefTabButton.setX(CHANGE_TAB_BUTTON_MARGIN);
        this.lefTabButton.setWidth(30);
        this.lefTabButton.setHeight(100);
        this.lefTabButton.setY(mY - 50);

        this.rightTabButton.setX(this.width - 30 - CHANGE_TAB_BUTTON_MARGIN);
        this.rightTabButton.setWidth(30);
        this.rightTabButton.setHeight(100);
        this.rightTabButton.setY(mY - 50);

        this.addRenderableWidget(this.lefTabButton);
        this.addRenderableWidget(this.rightTabButton);

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {

        super.render(graphics, mouseX, mouseY, pTicks);

        //font.draw(stack, Component.literal("width : " + this.width ), 3 , 3, 0xffffffff);
        //font.draw(stack, Component.literal("height : " + this.height ), 3 , 11, 0xffffffff);
        //font.draw(stack, Component.literal("cursorX : " + mouseX ), 3 , 19, 0xffffffff);
        //font.draw(stack, Component.literal("cursorY : " + mouseY ), 3 , 27, 0xffffffff);
        //this.font.draw(p_96562_, ActiveTabSlice.activeTab.title, 0, 0, 0xffffff);

        //ToolTipManager
        var toolTipManager = ToolTipOverlayManager.get();
        if (toolTipManager.hasToolTip()) {
            toolTipManager.render(this, graphics, mouseX, mouseY);
            toolTipManager.reset();
        }

        //Dropdown manager
        var dropdownMananger = DropdownMenuManager.get(this);
        if (dropdownMananger.hasDropdownMenu()) {
            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 200);
            dropdownMananger.getDropdownMenu()
                .render(graphics, mouseX, mouseY, pTicks);
            graphics.pose().popPose();
        }

        if (!this.dog.isAlive()) {
            Minecraft.getInstance().setScreen(null);
        } else if (this.dog.isDefeated()) {
            DogCannotInteractWithScreen.open(dog);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.getFocused() instanceof EditBox) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        var mc = Minecraft.getInstance();
        var options = mc.options;
        
        if (!this.sideTabNavLocked) {
            this.sideTabNavLocked = true;
            if (keyCode == options.keyLeft.getKey().getValue()) {
                this.lefTabButton.playDownSound(mc.getSoundManager());
                this.lefTabButton.onClick(0, 0);
                this.lefTabButton.keyPressed(keyCode, scanCode, modifiers);
            } else if (keyCode == options.keyRight.getKey().getValue()) {
                this.rightTabButton.playDownSound(mc.getSoundManager());
                this.rightTabButton.onClick(0, 0);
                this.rightTabButton.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        
        DropdownMenuManager.get(this).keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.sideTabNavLocked = false;
        this.lefTabButton.keyReleased(keyCode, scanCode, modifiers);
        this.rightTabButton.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        if (guiEventListener == this.lefTabButton) 
            return;
        if (guiEventListener == this.rightTabButton)
            return;        
        super.setFocused(guiEventListener);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void resize(Minecraft p_96575_, int width, int height) {
        DropdownMenuManager.get(this).clearActiveDropdownMenu();
        super.resize(p_96575_, width, height);
    }

    @Override
    public void removed() {
        super.removed();
        DropdownMenuManager.finish();
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
            MainPanelSlice.class,
            TalentChangeHandlerSlice.class
        );
    }

}
