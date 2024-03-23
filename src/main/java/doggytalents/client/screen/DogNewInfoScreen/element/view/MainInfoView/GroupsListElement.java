package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView;

import java.util.ArrayList;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.dropdown.AddGroupMenu.AddGroupMenu;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.GroupChangeHandlerSlice;
import doggytalents.client.screen.framework.DropdownMenuManager;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogGroupsManager;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogForceSitData;
import doggytalents.common.network.packet.data.DogGroupsData;
import doggytalents.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import doggytalents.forge_imitate.network.PacketDistributor;
import doggytalents.common.entity.DogGroupsManager.DogGroup;

public class GroupsListElement extends AbstractElement {

    Dog dog;
    Font font;
    public static int LINE_SPACING = 5;
    public static int GROUP_SPACING = 9;

    public GroupsListElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public AbstractElement init() {
        getStateAndSubscribesTo(GroupChangeHandlerSlice.class, Object.class, null);
        int pX = this.getRealX();
        int pY = this.getRealY();
        
        var dogGroups = dog.getGroups().getGroupsReadOnly();

        for (var group : dogGroups) {
            var groupButton = new GroupEntryButton(pX, pY, group, font, this.dog); 
            if (pX + groupButton.getWidth() > this.getRealX() + this.getSizeX()) {
                pX = this.getRealX();
                pY += LINE_SPACING + groupButton.getHeight();
                groupButton.setX(pX);
                groupButton.setY(pY);
            }
            this.addChildren(groupButton);
            pX += GROUP_SPACING + groupButton.getWidth();
        }
        if (dogGroups.size() >= DogGroupsManager.MAX_GROUP_SIZE)
            return this;
        int addButtonSize = font.lineHeight + GroupEntryButton.PADDING_VERT*2;
        var addButton = new FlatButton(pX, pY, addButtonSize, addButtonSize, Component.literal("+"), 
            b -> {
                displayGroupMenu(b.getX(), b.getY());
            }
        ) {

            static final int DEFAULT_COLOR = 0x485e5d5d;
            static final int DEFAULT_HLCOLOR = 0x835e5d5d;

            @Override
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                if (!this.active) return;

                int cl = this.isHovered ? DEFAULT_HLCOLOR : DEFAULT_COLOR;
                
                graphics.fill(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, cl);
                
                int mX = this.getX() + this.width/2;
                int mY = this.getY() + this.height/2;
                var msg = this.getMessage();
                int tX = mX - font.width(msg)/2 + 1;
                int tY = mY - font.lineHeight/2 + 1;
                graphics.drawString(font, msg, tX, tY, 0xffffffff);
            }
        };
        pX += addButton.getWidth();
        if (pX > this.getRealX() + this.getSizeX()) {
            pX = this.getRealX();
            pY += LINE_SPACING + addButton.getHeight();
            addButton.setX(pX);
            addButton.setY(pY);
        }
        this.addChildren(addButton);
        pX += GROUP_SPACING;


        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        
    }

    private void displayGroupMenu(int x, int y) {
        DropdownMenuManager.get(getScreen()).setActiveDropdownMenu(
            getScreen(),
            x + 5, y + 5,
            120, 120, 
            new AddGroupMenu(this, getScreen(),this.dog)
                .setBackgroundColor(0xff363636)
        );
    }

    

    private static class GroupEntryButton extends FlatButton {

        public static final int PADDING_HORZ = 2;
        public static final int PADDING_VERT = 1;
        public static final int ICON_REM_X = 0;

        private Font font;
        private DogGroup group;
        private Dog dog;

        private int textColor;

        public GroupEntryButton(int x, int y, DogGroup group, Font font, Dog dog) {
            super(x, y, 0, 0, Component.literal(group.name), null);
            this.font = font;
            this.setWidth(PADDING_HORZ*2 + font.width(this.getMessage()));
            this.setHeight(font.lineHeight + PADDING_VERT*2);
            this.group = group;
            this.dog = dog;
            this.textColor = Util.getTextBlackOrWhite(group.color);
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {

            if (!this.active) return;

            int cl = this.group.color;
            
            graphics.fill(this.getX(), this.getY(), this.getX()+this.width, this.getY()+this.height, cl);
            
            //draw text
            int mX = this.getX() + this.width/2;
            int mY = this.getY() + this.height/2;
            var msg = this.getMessage();
            int tX = mX - font.width(msg)/2;
            int tY = mY - font.lineHeight/2;
            graphics.drawString(font, msg, tX, tY, textColor);

            if (this.isHovered) drawRemoveIcon(graphics, mouseX, mouseY, pTicks);
        }

        @Override
        public void onPress() {
            requestRemoveGroup();
        }

        private void requestRemoveGroup() {
            PacketHandler
                .send(PacketDistributor.SERVER.noArg(), 
                new DogGroupsData.EDIT(this.dog.getId(), this.group, false));
        }

        private void drawRemoveIcon(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            int iX = ICON_REM_X;
            graphics.blit(Resources.STYLE_ADD_REMOVE, this.getX()+this.getWidth() - 4, getY()+this.getHeight() - 4, iX, 0, 9, 9);
        }

        
        
    }
    
}
