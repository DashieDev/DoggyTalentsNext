package doggytalents.client.screen.AmnesiaBoneScreen;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.screen.AmnesiaBoneScreen.element.AmnesiaNavBarElement;
import doggytalents.client.screen.AmnesiaBoneScreen.element.view.GeneralView.GeneralView;
import doggytalents.client.screen.AmnesiaBoneScreen.element.view.TalentView.TalentView;
import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTabSlice;
import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTalentDescSlice;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.StoreConnectedScreen;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class AmneisaBoneScreen extends StoreConnectedScreen {

    private Dog dog;

    protected AmneisaBoneScreen(Dog dog) {
        super(ComponentUtil.translatable("doggytalents.screen.amnesia_bone.title"));
        this.dog = dog;
    }

    public static void open(Dog dog) {
        var mc = Minecraft.getInstance();
        var screen = new AmneisaBoneScreen(dog);
        Store.get(screen);
        mc.setScreen(screen);
    }

    @Override
    protected void init() {
        super.init();
        
        int mX = this.width/2;
        int mY = this.height/2;
        var navBar = new AmnesiaNavBarElement(null, this, this.dog)
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
            ActiveTabSlice.Tab.GENERAL
        );
        AbstractElement view;
        switch (selectedTab) {
            case GENERAL:
                view = new GeneralView(upperView, this, dog);
                break;
            case TALENTS:
                view = new TalentView(upperView, this, dog);
                break;
            default:
                view = new GeneralView(upperView, this, dog);
                break;
        }

        upperView.addChildren(
            view
            .setPosition(PosType.ABSOLUTE, 0, 0)
            .setSize(1f, 1f)
            //.setBackgroundColor(0xff32a852)
            .init()
        );
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        
        super.render(graphics, mouseX, mouseY, pTicks);

        //font.draw(stack, ComponentUtil.literal("width : " + this.width ), 3 , 3, 0xffffffff);
        //font.draw(stack, ComponentUtil.literal("height : " + this.height ), 3 , 11, 0xffffffff);
        //font.draw(stack, ComponentUtil.literal("cursorX : " + mouseX ), 3 , 19, 0xffffffff);
        //font.draw(stack, ComponentUtil.literal("cursorY : " + mouseY ), 3 , 27, 0xffffffff);
        //this.font.draw(p_96562_, ActiveTabSlice.activeTab.title, 0, 0, 0xffffff);

        //ToolTipManager
        var toolTipManager = ToolTipOverlayManager.get();
        if (toolTipManager.hasToolTip()) {
            toolTipManager.render(this, stack, mouseX, mouseY);
            toolTipManager.reset();
        }

        if (!this.dog.isDoingFine()) {
            Minecraft.getInstance().setScreen(null);
        }
        
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public List<Class<? extends AbstractSlice>> getSlices() {
        return List.of(
            ActiveTabSlice.class,
            ActiveTalentDescSlice.class
        );
    }
    
}
