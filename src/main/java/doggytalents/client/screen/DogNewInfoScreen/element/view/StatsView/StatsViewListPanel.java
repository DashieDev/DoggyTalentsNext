package doggytalents.client.screen.DogNewInfoScreen.element.view.StatsView;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentListPageCounterSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentListSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentListSlice.TalentListData;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StatsViewListPanel extends AbstractElement {

    Font font;

    public StatsViewListPanel(AbstractElement parent, Screen screen) {
        super(parent, screen);
        var mc = Minecraft.getInstance();
        this.font = mc.font;
        //TODO Auto-generated constructor stub
    }

    @Override
    public AbstractElement init() {
        this.getPosition().setChildDirection(ChildDirection.COL);
        var tabListEntries =
            new StatsViewEntryElement(this, getScreen());
        tabListEntries
            .setPosition(PosType.RELATIVE, 0, 0)
            .setSize(1f, this.getSizeY() - 50)
            .init();
        
        this.addChildren(tabListEntries);

        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // int mX = this.getSizeX()/2;
        // var c1 = Component.literal("Pts: " + this.dog.getSpendablePoints());
        // int tX = this.getRealX() + mX - font.width(c1)/2;
        // int tY = this.getRealY() + this.getSizeY() -15;
        // font.draw(stack, c1, tX, tY, 0xffffffff);
    }
    
}

