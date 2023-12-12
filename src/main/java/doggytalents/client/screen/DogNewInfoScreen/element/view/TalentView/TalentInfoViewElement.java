package doggytalents.client.screen.DogNewInfoScreen.element.view.TalentView;

import java.security.cert.PKIXReason;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.ChopinLogger;
import doggytalents.DoggyTalents;
import doggytalents.api.registry.Talent;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTalentDescSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ScrollView;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.widget.DogInventoryButton;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogTalentData;
import doggytalents.common.network.packet.data.DoggyToolsPickFirstData;
import doggytalents.common.network.packet.data.DoggyTorchPlacingTorchData;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import doggytalents.common.talent.DoggyTorchTalent;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.network.PacketDistributor;

public class TalentInfoViewElement extends AbstractElement {

    Dog dog;
    Talent talent;
    Font font;

    static final int PADDING_LEFT = 5;
    static final int PADDING_RIGHT = 30;
    static final int PADDING_TOP = 5;
    static final int LINE_SPACING = 3;

    public TalentInfoViewElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;

        var mc = this.getScreen().getMinecraft();
        this.font = mc.font;
    }

    @Override
    public AbstractElement init() {
        var talent = Store.get(getScreen())
            .getStateOrDefault(ActiveTalentDescSlice.class, 
            ActiveTalentDescSlice.class, 
            new ActiveTalentDescSlice(null)).activeTalent;
        this.talent = talent;

        if (this.talent == null) {
            return this;
        }

        if (!ConfigHandler.TALENT.getFlag(talent) || !talent.isDogEligible(dog)) {
            return this;
        }

        this.addTrainButton(dog);

        addTitleAndDescriptionView(talent);

        return this;
    }

    private void addTitleAndDescriptionView(Talent talent) {
        var scrollView = new ScrollView(this, getScreen());
        scrollView
            .setPosition(PosType.ABSOLUTE, PADDING_LEFT, 0)
            .setSize(1f, this.getSizeY() - 65)
            .init();
        this.addChildren(scrollView);
        var container = scrollView.getContainer();
        container.addChildren(new TalentTitleAndDescEntry(container, getScreen(), talent).init());
        addTalentSpecificOptions(container, talent);
    }

    private void addTalentSpecificOptions(AbstractElement container, Talent talent) {
        /*
         * var dogInvButton = new DogInventoryButton(
            0, 0, getScreen(), (btn) -> {
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenDogScreenData());
                btn.active = false;
            });
        int dogInvButtonX = this.getRealX() + PADDING_LEFT;
        int dogInvButtonY = this.getRealY() + this.getSizeY() - 60;

        dogInvButton.setX(dogInvButtonX);
        dogInvButton.setY(dogInvButtonY);
        this.addChildren(dogInvButton);
         */

        if (talent == DoggyTalents.PACK_PUPPY.get()) {
            if (ConfigHandler.CLIENT.DOG_INV_BUTTON_IN_INV.get())
                return;
            var packPuppyButtonDiv = new DivElement(container, getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 20)
                .init();
            container.addChildren(packPuppyButtonDiv);
            var dogInvButton = new DogInventoryButton(
                packPuppyButtonDiv.getRealX() + PADDING_LEFT, 
                packPuppyButtonDiv.getRealY() + 5, getScreen(), (btn) -> {
                    PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenDogScreenData());
                    btn.active = false;
            });
            packPuppyButtonDiv.addChildren(dogInvButton);
        } else if (talent == DoggyTalents.DOGGY_TORCH.get()) {
            var talentInstOptional = dog.getTalent(DoggyTalents.DOGGY_TORCH);
            if (!talentInstOptional.isPresent())
                return;
            var talentInst = talentInstOptional.get();
            if (!(talentInst instanceof DoggyTorchTalent torchTalent))
                return;
            var torchButtonDiv = new DivElement(container, getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 30)
                .init();
            container.addChildren(torchButtonDiv);
            var torchButtonStr = Component.translatable(
                torchTalent.placingTorch() ?
                "talent.doggytalents.doggy_tools.placing_torch.unset"
                : "talent.doggytalents.doggy_tools.placing_torch.set"
            );
            var torchButton = new FlatButton(
                torchButtonDiv.getRealX() + PADDING_LEFT,
                torchButtonDiv.getRealY() + 5, 120, 20, torchButtonStr,
                b -> {
                    boolean newVal = !torchTalent.placingTorch();
                    b.setMessage(Component.translatable(
                        newVal ?
                        "talent.doggytalents.doggy_tools.placing_torch.unset"
                        : "talent.doggytalents.doggy_tools.placing_torch.set"
                    ));
                    torchTalent.setPlacingTorch(newVal);
                    PacketHandler.send(PacketDistributor.SERVER.noArg(), new DoggyTorchPlacingTorchData(
                        dog.getId(), newVal
                    ));
                }
            );
            torchButtonDiv.addChildren(torchButton);
        } else if (talent == DoggyTalents.DOGGY_TOOLS.get()) {
            var talentOptional = dog.getTalent(DoggyTalents.DOGGY_TOOLS);
            if (!talentOptional.isPresent())
                return;
            var talentInst = talentOptional.get();
            if (!(talentInst instanceof DoggyToolsTalent toolsTalent))
                return;

            container.addChildren(
                new ButtonOptionEntry(container, getScreen(), 
                    new FlatButton(
                        0, 0,
                        40, 20, Component.literal("" + toolsTalent.pickFirstTool()), 
                        b -> {
                            Boolean newVal = !toolsTalent.pickFirstTool();
                            b.setMessage(Component.literal("" + newVal));
                            toolsTalent.setPickFirstTool(newVal);
                            PacketHandler.send(PacketDistributor.SERVER.noArg(), new DoggyToolsPickFirstData(
                                dog.getId(), newVal
                            ));
                        }     
                    ),
                    I18n.get("talent.doggytalents.doggy_tools.pick_first_tool")
                )
                .init()
            );
            var toolsButtonDiv = new DivElement(container, getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 30)
                .init();
            container.addChildren(toolsButtonDiv);
            var toolButtonStr = Component.translatable(
                "talent.doggytalents.doggy_tools.open_tools"
            );
            var toolButton = new FlatButton(
                toolsButtonDiv.getRealX() + PADDING_LEFT,
                toolsButtonDiv.getRealY() + 5, 120, 20, toolButtonStr,
                b -> {
                    PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenDogScreenData(
                        OpenDogScreenData.ScreenType.TOOL,
                        dog.getId()
                    ));
                }
            );
            toolsButtonDiv.addChildren(toolButton);
        } else if (talent == DoggyTalents.DOGGY_ARMOR.get()) {
            if (dog.getDogLevel(DoggyTalents.DOGGY_ARMOR) <= 0)
                return;
            var armorButtonDiv = new DivElement(container, getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 30)
                .init();
            container.addChildren(armorButtonDiv);
            var armorButtonStr = Component.translatable(
                "talent.doggytalents.doggy_armor.open_armor"
            );
            var armorButton = new FlatButton(
                armorButtonDiv.getRealX() + PADDING_LEFT,
                armorButtonDiv.getRealY() + 5, 120, 20, armorButtonStr,
                b -> {
                    PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenDogScreenData(
                        OpenDogScreenData.ScreenType.ARMOR,
                        dog.getId()
                    ));
                }
            );
            armorButtonDiv.addChildren(armorButton);
        }
    }

    private void addTrainButton(Dog dog) {
        int dogLevel = dog.getDogLevel(talent);
        var trainButton = new Button(0, 0, 
            50, 20, Component.translatable("doggui.talents.train"), 
            b -> {
                //send training packet and dispatch here.
                requestTrain();
            }
        ) {
            @Override
            public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                // TODO Auto-generated method stub
                super.renderButton(stack, mouseX, mouseY, pTicks);
                int tX = this.x;
                int tY = this.y - LINE_SPACING - font.lineHeight;
                // var costStr = dogLevel < talent.getMaxLevel() ?
                //     "Cost : " + talent.getLevelCost(dogLevel + 1)
                //     : "Max Level Reached.";
                int dogLevel = dog.getDogLevel(talent);
                String costStr;
                int costStrColor;
                if (dogLevel >= talent.getMaxLevel()) {
                    costStr = I18n.get("doggui.talents.max_level");
                    costStrColor = 0xffF4FF00;
                } else {
                    costStr = I18n.get("doggui.talents.cost") + talent.getLevelCost(dogLevel + 1);
                    costStrColor = 0xffffffff;
                }
                font.draw(stack, costStr, tX, tY, costStrColor);
                this.active = 
                    dogLevel < talent.getMaxLevel()
                    && dog.canSpendPoints(talent.getLevelCost(dogLevel + 1));
            }

            @Override
            public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                MutableComponent c1;
                if (this.active) {
                    return;
                } else {
                    if (dog.getDogLevel(talent) < talent.getMaxLevel()) {
                        c1 = Component.translatable("doggui.talents.insufficent_points");
                        c1.setStyle(
                            Style.EMPTY
                            .withColor(0xffB20000)
                            .withBold(true)
                        );
                    } else {
                        return;
                    }
                }

                ToolTipOverlayManager.get().setComponents(List.of(c1));
            }
        };
        trainButton.active = 
            dogLevel < talent.getMaxLevel()
            && dog.canSpendPoints(talent.getLevelCost(dogLevel + 1));
        int trainButtonX = this.getRealX() + this.getSizeX() - trainButton.getWidth() - 35;
        int trainButtonY = this.getRealY() + this.getSizeY() - trainButton.getHeight() - 20;

        trainButton.x = trainButtonX;
        trainButton.y = trainButtonY;

        this.addChildren(trainButton);
    }

    private void requestTrain() {
        int level = dog.getDogLevel(talent);
        if (level < talent.getMaxLevel() && dog.canSpendPoints(talent.getLevelCost(level + 1))) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogTalentData(dog.getId(), talent));
        }
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.talent == null) {
            int mX = this.getSizeX()/2;
            int mY = this.getSizeY()/2;
            var txt = I18n.get("doggui.talents.no_talents_selected");
            int tX = this.getRealX() + mX - this.font.width(txt)/2;
            int tY = this.getRealY() + mY - this.font.lineHeight/2;
            this.font.draw(stack, txt, tX, tY, 0xffffffff);
            return;
        } else if (!ConfigHandler.TALENT.getFlag(talent)) {
            int mX = this.getSizeX()/2;
            int mY = this.getSizeY()/2;
            var txt = Component.translatable("doggui.talents.invalid.disabled");
            txt.setStyle(
                Style.EMPTY
                .withColor(0xffB20000)
                .withBold(true)
            );
            int tX = this.getRealX() + mX - this.font.width(txt)/2;
            int tY = this.getRealY() + mY - this.font.lineHeight/2;
            this.font.draw(stack, txt, tX, tY, 0xffffffff);
            return;
        } else if (!talent.isDogEligible(dog)) {
            int mX = this.getSizeX()/2;
            int mY = this.getSizeY()/2;
            var non_eligbleMsgOptional = talent.getNonEligibleTranslationKey(dog);
            var txt = Component.translatable(
                non_eligbleMsgOptional.orElse("doggui.talents.invalid.not_eligible")
            );
            txt.setStyle(
                Style.EMPTY
                .withColor(0x828282)
            );
            int tX = this.getRealX() + mX - this.font.width(txt)/2;
            int tY = this.getRealY() + mY - this.font.lineHeight/2;
            font.draw(stack, txt, tX, tY, 0xffffffff);
            return;
        }

        //Title and description
        int startX = this.getRealX() + PADDING_LEFT;
        int startY = this.getRealY() + PADDING_TOP;
        int pX = startX;
        int pY = startY;
        // var title = Component.translatable(this.talent.getTranslationKey())
        //     .withStyle(
        //         Style.EMPTY
        //         .withBold(true)
        //         .withColor(0xffF4FF00)
        //     );
        // graphics.drawString(font, title, pX, pY, 0xffffffff);
        // pY += 2*LINE_SPACING + this.font.lineHeight;
        // var desc = Component.translatable(this.talent.getInfoTranslationKey());
        // var desc_lines = this.font.split(desc, this.getSizeX() - (PADDING_LEFT + PADDING_RIGHT));
        // for (var line : desc_lines) {
        //     graphics.drawString(font, line, pX, pY, 0xffffffff);
        //     pY += font.lineHeight + LINE_SPACING;
        // }

        //Kanji
        startX = this.getRealX() + PADDING_LEFT;
        pY = this.getRealY() + this.getSizeY() - 60;
        this.drawDogLevelKanji(stack, startX, pY, 50);

        //Point left:
        startX = this.getRealX() + PADDING_LEFT + 40;
        pY = this.getRealY() + this.getSizeY() - 45;
        var currentLevelStr = I18n.get("doggui.pointsleft");
        var currentLevelStr1 = "" + this.dog.getSpendablePoints();
        font.draw(stack, currentLevelStr, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        font.draw(stack, currentLevelStr1, startX, pY, 0xffffffff);

        //Current level:
        startX = this.getRealX() + 80 + 40;
        pY = this.getRealY() + this.getSizeY() - 45;
        currentLevelStr = I18n.get("doggui.talents.current_talent_level");
        currentLevelStr1 = this.dog.getDogLevel(talent) 
            + "/" + this.talent.getMaxLevel();
        font.draw(stack, currentLevelStr, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        font.draw(stack, currentLevelStr1, startX, pY, 0xffffffff);

        
        
    }

    private void drawDogLevelKanji(PoseStack stack, int x, int y, int size) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DogStatusViewBoxElement.getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = size;
        blit(stack, x, 
            y, 0, 0, 0, imgeSize, imgeSize, imgeSize, imgeSize);
        RenderSystem.disableBlend();
    }

    public static class TalentTitleAndDescEntry extends AbstractElement {

        private Talent talent;
        
        private Component title;
        private List<FormattedCharSequence> descriptionLines;
        private Font font;

        public TalentTitleAndDescEntry(AbstractElement parent, Screen screen, Talent talent) {
            super(parent, screen);
            this.talent = talent;
            font = Minecraft.getInstance().font;
        }

        @Override
        public AbstractElement init() {
            this.setPosition(PosType.RELATIVE, 0, 0);
            title = Component.translatable(this.talent.getTranslationKey())
            .withStyle(
                Style.EMPTY
                .withBold(true)
                .withColor(0xffF4FF00)
            );
            var content = Component.translatable(this.talent.getInfoTranslationKey());
            descriptionLines = this.font.split(content, this.getParent().getSizeX() - 20);
            int totalH = LINE_SPACING + font.lineHeight + LINE_SPACING + descriptionLines.size()*(LINE_SPACING + font.lineHeight)
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
            for (var line : descriptionLines) {
                font.draw(stack, line, pX, pY, 0xffffffff);
                pY += font.lineHeight + LINE_SPACING;
            }
        }

    }

    private static class ButtonOptionEntry extends AbstractElement {

        private AbstractButton button;
        private String label;
        private Font font;

        private boolean newline = false;

        public ButtonOptionEntry(AbstractElement parent, Screen screen, AbstractButton button, String label) {
            super(parent, screen);
            this.font = Minecraft.getInstance().font;
            this.button = button;
            this.label = label;
        }

        @Override
        public AbstractElement init() {
            this.setPosition(PosType.RELATIVE, 0, 0);
            this.setSize(1f, 20 + LINE_SPACING);

            int buttonX_offset = PADDING_LEFT + 130;
            int buttonY_offset = this.getSizeY()/2
                - this.button.getHeight()/2 + 1;

            var p = this.getParent();
            if (
                p != null
                && buttonX_offset + this.button.getWidth() > p.getSizeX()
            ) {
                this.newline = true; 
                buttonX_offset = PADDING_LEFT; 
                buttonY_offset += 14;
            }

            if (newline)
            this.setSize(1f, 20 + LINE_SPACING + 14);

            this.button.x = (this.getRealX() + buttonX_offset);
            this.button.y = (this.getRealY() + buttonY_offset);

            this.addChildren(button);
            return this;
        }

        @Override
        public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
            if (newline) {
                int startX = this.getRealX() + PADDING_LEFT;
                int pY = this.getRealY() + 3;
                font.draw(stack, this.label, startX, pY, 0xffffffff);
                
                return;
            } 

            int startX = this.getRealX() + PADDING_LEFT;
            int pY = this.getRealY() + this.getSizeY()/2
                - font.lineHeight/2;
            font.draw(stack,  this.label, startX, pY, 0xffffffff);
        }
    }

    
}
