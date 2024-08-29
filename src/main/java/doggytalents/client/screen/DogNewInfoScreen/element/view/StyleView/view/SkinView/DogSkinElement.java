package doggytalents.client.screen.DogNewInfoScreen.element.view.StyleView.view.SkinView;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
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
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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

    private TextOnlyButton copy_sha1_button;

    public DogSkinElement(AbstractElement parent, Screen screen, Dog dog, List<DogSkin> locList, int active_id) {
        super(parent, screen);
        this.dog = dog;
        this.locList = locList;
        this.activeSkinId = active_id;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public AbstractElement init() {
        if (this.locList.isEmpty())
            return this;
        if (this.activeSkinId < 0)
            return this;
        if (this.activeSkinId >= this.locList.size())
            return this;
            
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
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), ComponentUtil.literal("Name: "), 
            ComponentUtil.literal(manifestSkin.getName())).init()); 
        if (!manifestSkin.getBasedOn().isEmpty())
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), ComponentUtil.literal("Based On: "), 
            ComponentUtil.literal(manifestSkin.getBasedOn())).init());
        if (!manifestSkin.getAuthor().isEmpty()) 
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), ComponentUtil.literal("Author: "), 
            ComponentUtil.literal(manifestSkin.getAuthor())).init());
        if (!manifestSkin.getDesc().isEmpty()) 
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), ComponentUtil.literal("Desciption: "), 
            ComponentUtil.literal(manifestSkin.getDesc())).init()); 
        if (!manifestSkin.getTags().isEmpty()) 
        scroll.addChildren(new SkinStrEntry(scroll, getScreen(), ComponentUtil.literal("Tags: "), 
        ComponentUtil.literal(manifestSkin.getTags())).init());
        if (manifestSkin.isCustom()) {
            this.initSha1Button();
            scroll.addChildren((new DivElement(scroll, getScreen()) {
                @Override
                public AbstractElement init() {
                    this.setPosition(PosType.RELATIVE, 0, 0);
                    this.setSize(1f, 20);
                    var button = DogSkinElement.this.copy_sha1_button;
                    button.setHeight(font.lineHeight + 5);
                    button.setX(this.getRealX() + 10);
                    button.setY(this.getRealY() + this.getSizeY() - button.getHeight());
                    this.addChildren(button);
                    return super.init();
                }
            }).init());
        }
        
        
        return this;
    }

    private void addAccStateHover() {
        var skin = this.locList.get(this.activeSkinId);
        var state = getStateFromSkin(skin);
        var button = new AccessoryStatusHover(0, 0, state);
        button.x = (this.getRealX() + 6);
        button.y = (this.getRealY() + this.getSizeY() - 25);
        this.addChildren(button);
    }

    private DogModel.AccessoryState getStateFromSkin(DogSkin skin) {
        if (!skin.useCustomModel())
            return AccessoryState.RECOMMENDED;
        var model = skin.getCustomModel();
        return model.getValue().getAccessoryState();
    }

    private void copySha1() {
        var skin = this.locList.get(this.activeSkinId);
        if (!skin.isCustom())
            return;
        var sha1 = DogTextureManager.INSTANCE.getHash(skin);
        if (sha1 == null || sha1.isEmpty())
            return;
        Minecraft.getInstance().keyboardHandler.setClipboard(sha1);
    }

    
    private void initSha1Button() {
        var sha1_txt = ComponentUtil.literal("Copy SHA-1").withStyle(
            Style.EMPTY.withColor(0x696868)
        );
        this.copy_sha1_button = new TextOnlyButton(0, 0, font.width(sha1_txt), 10, sha1_txt, b -> {
            this.copySha1();
            b.setMessage(ComponentUtil.literal("SHA-1 Copied!").withStyle(
                Style.EMPTY.withColor(0xff1fa800)
            ));
        }, font);
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
        public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
            int pX = this.getRealX();
            int pY = this.getRealY() + LINE_SPACING;
            font.draw(stack, title, pX, pY, 0xffffffff);
            
            pY += font.lineHeight + LINE_SPACING;
            for (var line : components) {
                font.draw(stack, line, pX, pY, 0xffffffff);
                pY += font.lineHeight + LINE_SPACING;
            }
        }

    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        if (this.locList == null) return;
        if (this.locList.isEmpty()) return;

        
        if (activeSkinId < 0) return;
        if (activeSkinId >= this.locList.size()) return;

        if (this.showInfo)
            renderShowInfo(stack, mouseX, mouseY, partialTicks);
        else
            renderNonShowInfo(stack, mouseX, mouseY, partialTicks);
         
    }

    private void renderNonShowInfo(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
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
            var c1 = ComponentUtil.literal(nameStr);
            c1.withStyle(Style.EMPTY.withBold(true));
            
            int nameX = this.getRealX() + mX - font.width(c1)/2;
            int nameY = this.getRealY() + this.getSizeY() - 13;
            font.draw(stack, c1, nameX, nameY, 0xffffffff);
        }

        
        this.renderSkinAndDogModel(activeSkinId, true, stack, 
            mouseX, mouseY, e_mX, e_mY + 36, 64, false);
        
        int prevId = this.activeSkinId - 1;
        int nextId = this.activeSkinId + 1;

        if (nextId < locList.size()) {
            this.renderSkinAndDogModel(nextId, false, stack, 
                mouseX, mouseY, e_mX + 32 + 25 + 25, e_mY + 32, 50, true);
        }

        if (prevId >= 0) {
            this.renderSkinAndDogModel(prevId, false, stack, 
                mouseX, mouseY, e_mX - 32 - 25 - 25, e_mY + 32, 50, true);
        }
    }

    private void renderShowInfo(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;
        int e_mX = this.getRealX() + mX;
        int e_mY = this.getRealY() + mY - 10;

        this.renderSkinAndDogModel(activeSkinId, true, stack, 
            mouseX, mouseY, this.getRealX() + 70, e_mY + 36, 64, true);

        var manifestSkin = this.locList.get(activeSkinId);
    
        if (!manifestSkin.hasExtraInfo()) {
            renderNoInfo(stack, mouseX, mouseY, partialTicks);
            return;
        }
    }

    private void renderNoInfo(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;

        var c1 = ComponentUtil.literal("No info to show.");

        int tX = this.getRealX() + mX - 10;
        int tY = this.getRealY() + mY - font.lineHeight/2;

        font.draw(stack, c1, tX, tY, 0xffffffff);
    }

    private void renderSkinAndDogModel(int indx, boolean followMouse, PoseStack stack, int mouseX, 
        int mouseY, int e_mX, int e_mY, int size, boolean useDummy) {
        var oldSkin = dog.getClientSkin();
        var manifestSkin = this.locList.get(indx);
        if (manifestSkin.mystery()) {
            manifestSkin = DogSkin.MYSTERY;
            renderMysteriousKanji(stack, e_mX, e_mY);
            ActiveSkinSlice.DUMMY_DOG_OBJ.setClientSkin(manifestSkin);
            DogStatusViewBoxElement.renderDogInside(stack, 
                ActiveSkinSlice.DUMMY_DOG_OBJ, e_mX, e_mY, size, 
                followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
        } else if (useDummy && ActiveSkinSlice.DUMMY_DOG_OBJ != null) {
            ActiveSkinSlice.DUMMY_DOG_OBJ.setClientSkin(manifestSkin);
            DogStatusViewBoxElement.renderDogInside(stack, 
                ActiveSkinSlice.DUMMY_DOG_OBJ, e_mX, e_mY, size, 
                followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
        } else {   
            dog.setClientSkin(manifestSkin);
            DogStatusViewBoxElement.renderDogInside(stack, dog, e_mX, e_mY, size, 
                followMouse ? e_mX - mouseX : -64, followMouse ? e_mY - mouseY : -64);
            dog.setClientSkin(oldSkin);
        }
       
        if (oldSkin == manifestSkin) {
            var font = Minecraft.getInstance().font;
            var c1 = ComponentUtil.translatable("doggui.style.skins.selected");
            c1.setStyle(
                Style.EMPTY
                    .withBold(true)
                    .withColor(0xff1fa800)   
            );
            
            var c1_len = font.width(c1);
            int tX = e_mX - c1_len/2;
            int tY = e_mY + 28;
            
            font.draw(stack, c1, tX, tY, 0xffffffff);
        }
    }
    
    private void renderMysteriousKanji(PoseStack stack, int x, int y) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 100;
        RenderSystem.setShaderTexture(0, Resources.KANJI_MYSTERY_BKG);
        blit(stack, x - imgeSize/2, 
            y - imgeSize/2 - 27, 0, 0, 0, imgeSize, imgeSize, imgeSize, imgeSize);
        stack.pushPose();
        stack.translate(0, 0, 400);
        RenderSystem.setShaderTexture(0, Resources.KANJI_MYSTERY);
        blit(stack, x - imgeSize/2, 
            y - imgeSize/2 - 27, 0, 0, 0, imgeSize, imgeSize, imgeSize, imgeSize);
        stack.popPose();
        RenderSystem.disableBlend();
    }
}
