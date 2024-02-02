package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.SkinView;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.DogModel.AccessoryState;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveSkinSlice;
import doggytalents.client.screen.DogNewInfoScreen.widget.AccessoryStatusHover;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ScrollView;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class DogSkinElement extends AbstractElement {

    private static int LINE_SPACING = 2;

    Dog dog;
    List<DogSkin> locList;
    int activeSkinId;
    Font font;
    boolean showInfo;

    public DogSkinElement(AbstractElement parent, Screen screen, Dog dog, List<DogSkin> locList, int active_id) {
        super(parent, screen);
        this.dog = dog;
        this.locList = locList;
        this.activeSkinId = active_id;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public AbstractElement init() {
        addAccStateHover();

        
        this.showInfo = 
            getStateAndSubscribesTo(
                ActiveSkinSlice.class, ActiveSkinSlice.class, 
                new ActiveSkinSlice()).showInfo;
        if (!this.showInfo) 
            return this;
        var manifestSkin = this.locList.get(activeSkinId);

        if (!manifestSkin.hasExtraInfo()) {
            return this;
        }
        var scrollView = new ScrollView(this, getScreen());
        scrollView
            .setPosition(PosType.ABSOLUTE, Mth.floor(getParent().getSizeX()*0.45f), 0)
            .setSize(0.55f, 1f)
            .init();
        this.addChildren(scrollView);
        
        var scroll = scrollView.getContainer();
        scroll.addChildren(new DivElement(scroll, getScreen()).setSize(1f, Math.max(10, this.getSizeY()/2 - 40)).init()); 
        if (!manifestSkin.getName().isEmpty())
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), Component.literal("Name: "), 
            Component.literal(manifestSkin.getName())).init()); 
        if (!manifestSkin.getBasedOn().isEmpty())
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), Component.literal("Based On: "), 
            Component.literal(manifestSkin.getBasedOn())).init());
        if (!manifestSkin.getAuthor().isEmpty()) 
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), Component.literal("Author: "), 
            Component.literal(manifestSkin.getAuthor())).init());
        if (!manifestSkin.getDesc().isEmpty()) 
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), Component.literal("Desciption: "), 
            Component.literal(manifestSkin.getDesc())).init()); 
        if (!manifestSkin.getTags().isEmpty()) 
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), Component.literal("Tags: "), 
            Component.literal(manifestSkin.getTags())).init());
        
        return this;
    }

    private void addAccStateHover() {
        if (this.locList.isEmpty())
            return;
        if (this.activeSkinId < 0)
            return;
        if (this.activeSkinId >= this.locList.size())
            return;
        var skin = this.locList.get(this.activeSkinId);
        var state = getStateFromSkin(skin);
        var button = new AccessoryStatusHover(0, 0, state);
        button.setX(this.getRealX() + 6);
        button.setY(this.getRealY() + this.getSizeY() - 25);
        this.addChildren(button);
    }

    private DogModel.AccessoryState getStateFromSkin(DogSkin skin) {
        if (!skin.useCustomModel())
            return AccessoryState.RECOMMENDED;
        var model = skin.getCustomModel();
        return model.getValue().getAccessoryState();
    }

    public static class SkinStrEntry extends AbstractElement {
        
        private Component title, content;
        private List<FormattedCharSequence> components;
        private Font font;

        public SkinStrEntry(AbstractElement parent, Screen screen, Component title, Component content) {
            super(parent, screen);
            this.title = title;
            this.content = content;
            font = Minecraft.getInstance().font;
        }

        @Override
        public AbstractElement init() {
            this.setPosition(PosType.RELATIVE, 0, 0);
            title = title.copy().withStyle(Style.EMPTY.withBold(true));
            components = this.font.split(content, this.getParent().getSizeX() - 20);
            int totalH = LINE_SPACING + font.lineHeight + LINE_SPACING + components.size()*(LINE_SPACING + font.lineHeight)
                + LINE_SPACING;
            this.setSize(1f, totalH);

            return this;
        }

        @Override
        public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            int pX = this.getRealX();
            int pY = this.getRealY() + LINE_SPACING;
            graphics.drawString(font, title, pX, pY, 0xffffffff);
            
            pY += font.lineHeight + LINE_SPACING;
            for (var line : components) {
                graphics.drawString(font, line, pX, pY, 0xffffffff);
                pY += font.lineHeight + LINE_SPACING;
            }
        }

    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        if (this.locList == null) return;
        if (this.locList.isEmpty()) return;

        
        if (activeSkinId < 0) return;
        if (activeSkinId >= this.locList.size()) return;

        if (this.showInfo)
            renderShowInfo(graphics, mouseX, mouseY, partialTicks);
        else
            renderNonShowInfo(graphics, mouseX, mouseY, partialTicks);
         
    }

    private void renderNonShowInfo(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        int e_mX = this.getRealX() + mX;
        int e_mY = this.getRealY() + mY - 10;

        {
            int maxLen = this.getSizeX() - 120;
            var nameStr = locList.get(activeSkinId).getName();
            var nameStrLen = font.width(nameStr);
            if (nameStrLen > maxLen) {
                int cutLen = Math.max(0, maxLen - font.width(".."));
                nameStr = font.plainSubstrByWidth(nameStr, cutLen) + "..";
            }
            var c1 = Component.literal(nameStr);
            c1.withStyle(Style.EMPTY.withBold(true));
            
            int nameX = this.getRealX() + mX - font.width(c1)/2;
            int nameY = this.getRealY() + this.getSizeY() - 13;
            graphics.drawString(font, c1, nameX, nameY, 0xffffffff);
        }

        
        this.renderSkinAndDogModel(activeSkinId, true, graphics, 
            mouseX, mouseY, e_mX, e_mY + 36, 64, false);
        
        int prevId = this.activeSkinId - 1;
        int nextId = this.activeSkinId + 1;

        if (nextId < locList.size()) {
            this.renderSkinAndDogModel(nextId, false, graphics, 
                mouseX, mouseY, e_mX + 32 + 25 + 25, e_mY + 32, 50, true);
        }

        if (prevId >= 0) {
            this.renderSkinAndDogModel(prevId, false, graphics, 
                mouseX, mouseY, e_mX - 32 - 25 - 25, e_mY + 32, 50, true);
        }
    }

    private void renderShowInfo(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        int e_mX = this.getRealX() + mX;
        int e_mY = this.getRealY() + mY - 10;

        this.renderSkinAndDogModel(activeSkinId, true, graphics, 
            mouseX, mouseY, this.getRealX() + 70, e_mY + 36, 64, true);

        var manifestSkin = this.locList.get(activeSkinId);
    
        if (!manifestSkin.hasExtraInfo()) {
            renderNoInfo(graphics, mouseX, mouseY, partialTicks);
            return;
        }
        
    }

    private void renderNoInfo(GuiGraphics graphics,  int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;

        var c1 = Component.literal("No info to show.");

        int tX = this.getRealX() + mX - 10;
        int tY = this.getRealY() + mY - font.lineHeight/2;

        graphics.drawString(font, c1, tX, tY, 0xffffffff);
    }

    private void renderSkinAndDogModel(int indx, boolean followMouse, GuiGraphics graphics, int mouseX, 
        int mouseY, int e_mX, int e_mY, int size, boolean useDummy) {
        var oldSkin = dog.getClientSkin();
        var manifestSkin = this.locList.get(indx);
        if (manifestSkin.mystery()) {
            manifestSkin = DogSkin.MYSTERY;
            renderMysteriousKanji(graphics, e_mX, e_mY);
            ActiveSkinSlice.DUMMY_DOG_OBJ.setClientSkin(manifestSkin);
            DogStatusViewBoxElement.renderDogInside(graphics, 
                ActiveSkinSlice.DUMMY_DOG_OBJ, e_mX, e_mY, size, 
                followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
        } else if (useDummy && ActiveSkinSlice.DUMMY_DOG_OBJ != null) {
            ActiveSkinSlice.DUMMY_DOG_OBJ.setClientSkin(manifestSkin);
            DogStatusViewBoxElement.renderDogInside(graphics, 
                ActiveSkinSlice.DUMMY_DOG_OBJ, e_mX, e_mY, size, 
                followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
        } else {   
            dog.setClientSkin(manifestSkin);
            DogStatusViewBoxElement.renderDogInside(graphics, dog, e_mX, e_mY, size, 
                followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
            dog.setClientSkin(oldSkin);
        }
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
            
            graphics.drawString(font, c1, tX, tY, 0xffffffff);
        }
    }
    
    private void renderMysteriousKanji(GuiGraphics graphics, int x, int y) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 100;
        graphics.blit(Resources.KANJI_MYSTERY_BKG, x - imgeSize/2, 
            y - imgeSize/2 - 27, 0, 0, 0, imgeSize, imgeSize, imgeSize, imgeSize);
        var stack = graphics.pose();
        stack.pushPose();
        stack.translate(0, 0, 400);
        graphics.blit(Resources.KANJI_MYSTERY, x - imgeSize/2, 
            y - imgeSize/2 - 27, 0, 0, 0, imgeSize, imgeSize, imgeSize, imgeSize);
        stack.popPose();
        RenderSystem.disableBlend();
    }
}
