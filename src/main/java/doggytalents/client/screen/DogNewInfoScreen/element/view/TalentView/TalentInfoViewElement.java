package doggytalents.client.screen.DogNewInfoScreen.element.view.TalentView;

import java.security.cert.PKIXReason;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.TalentsOptions;
import doggytalents.DoggyTalents;
import doggytalents.api.registry.Talent;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.DogStatusViewBoxElement;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTalentDescSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentChangeHandlerSlice;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.DivElement;
import doggytalents.client.screen.framework.element.ScrollView;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.FlatCheckbox;
import doggytalents.client.screen.framework.widget.OneLineLimitedTextArea;
import doggytalents.client.screen.widget.DogInventoryButton;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogTalentData;
import doggytalents.common.network.packet.data.DogTalentOptionSetData;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import doggytalents.common.talent.CreeperSweeperTalent;
import doggytalents.common.talent.DoggyTorchTalent;
import doggytalents.common.talent.FisherDogTalent;
import doggytalents.common.talent.FlyingFurballTalent;
import doggytalents.common.talent.GatePasserTalent;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.talent.RescueDogTalent;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import doggytalents.common.util.DogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import doggytalents.common.network.PacketDistributor;

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
        getStateAndSubscribesTo(TalentChangeHandlerSlice.class, Object.class, null);
        var talent = getStateAndSubscribesTo(ActiveTalentDescSlice.class, 
            ActiveTalentDescSlice.class, 
            new ActiveTalentDescSlice(null)).activeTalent;
        this.talent = talent;

        if (this.talent == null) {
            return this;
        }

        if (!DogUtil.playerCanTrainTalent(Minecraft.getInstance().player, talent) || !talent.isDogEligible(dog)) {
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
        if (talent == DoggyTalents.PACK_PUPPY.get()) {
            addRenderPackPuppyButton(dog, container);

            var packPuppyButtonDiv = new PackPuppyButtonDiv(container, getScreen(), dog)
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 20)
                .init();
            container.addChildren(packPuppyButtonDiv);
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
                    var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.DOGGY_TORCH_ENABLE.get(), newVal);
                    PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                }
            );
            torchButtonDiv.addChildren(torchButton);

            if (torchTalent.canRenderTorch()) {
                container.addChildren(
                    new ButtonOptionEntry(container, getScreen(), 
                        new FlatCheckbox(0, 0,
                            b -> {
                                boolean newVal = !torchTalent.renderTorch();
                                b.setValue(newVal);
                                torchTalent.setRenderTorch(newVal);
                                var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.DOGGY_TORCH_RENDER.get(), newVal);
                                PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                            }     
                        ).initialValue(torchTalent.renderTorch()),
                        I18n.get("talent.doggytalents.doggy_torch.render_torch")
                    )
                    .init()
            );
            }
        } else if (talent == DoggyTalents.DOGGY_TOOLS.get()) {
            var talentOptional = dog.getTalent(DoggyTalents.DOGGY_TOOLS);
            if (!talentOptional.isPresent())
                return;
            var talentInst = talentOptional.get();
            if (!(talentInst instanceof DoggyToolsTalent toolsTalent))
                return;

            container.addChildren(
                new ButtonOptionEntry(container, getScreen(), 
                    new FlatCheckbox(0, 0,
                        b -> {
                            boolean newVal = !toolsTalent.pickFirstTool();
                            b.setValue(newVal);
                            toolsTalent.setPickFirstTool(newVal);
                            var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.DOGGY_TOOLS_EXC.get(), newVal);
                            PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                        }     
                    ).initialValue(toolsTalent.pickFirstTool()),
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
        } else if (talent == DoggyTalents.RESCUE_DOG.get()) {
            
            var talentInstOptional = dog.getTalent(DoggyTalents.RESCUE_DOG);
            if (!talentInstOptional.isPresent())
                return;
            var talentInst = talentInstOptional.get();
            if (!(talentInst instanceof RescueDogTalent rescue))
                return;
            container.addChildren(
                new ButtonOptionEntry(container, getScreen(), 
                    new FlatCheckbox(0, 0,
                        b -> {
                            boolean newVal = !rescue.renderBox();
                            b.setValue(newVal);
                            rescue.setRenderBox(newVal);
                            var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.RESCUE_DOG_RENDER.get(), newVal);
                            PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                        }     
                    ).initialValue(rescue.renderBox()),
                    I18n.get("talent.doggytalents.rescue_dog.render_box")
                )
                .init()
            );
        } else if (talent == DoggyTalents.GATE_PASSER.get()) {
            var talentInstOptional = dog.getTalent(DoggyTalents.GATE_PASSER);
            if (!talentInstOptional.isPresent())
                return;
            var talentInst = talentInstOptional.get();
            if (!(talentInst instanceof GatePasserTalent gateTalent))
                return;
            var gateButtonDiv = new DivElement(container, getScreen())
                .setPosition(PosType.RELATIVE, 0, 0)
                .setSize(1f, 30)
                .init();
            container.addChildren(gateButtonDiv);
            var gateButtonStr = Component.translatable(
                gateTalent.allowPassingGate() ?
                "talent.doggytalents.gate_passer.pass_gate.unset"
                : "talent.doggytalents.gate_passer.pass_gate.set"
            );
            var gateButton = new FlatButton(
                gateButtonDiv.getRealX() + PADDING_LEFT,
                gateButtonDiv.getRealY() + 5, 120, 20, gateButtonStr,
                b -> {
                    boolean newVal = !gateTalent.allowPassingGate();
                    b.setMessage(Component.translatable(
                        newVal ?
                        "talent.doggytalents.gate_passer.pass_gate.unset"
                        : "talent.doggytalents.gate_passer.pass_gate.set"
                    ));
                    gateTalent.setAllowPassingGate(newVal);
                    var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.GATE_PASSER_ENABLE.get(), newVal);
                    PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                }
            );
            gateButtonDiv.addChildren(gateButton);
        } else if (talent == DoggyTalents.CREEPER_SWEEPER.get()) {
            var talentInstOptional = dog.getTalent(DoggyTalents.CREEPER_SWEEPER);
            if (!talentInstOptional.isPresent())
                return;
            var talentInst = talentInstOptional.get();
            if (!(talentInst instanceof CreeperSweeperTalent sweep))
                return;
            if (!sweep.canAttackCreeper())
                return;
            container.addChildren(
                new ButtonOptionEntry(container, getScreen(), 
                    new FlatCheckbox(0, 0,
                        b -> {
                            boolean newVal = !sweep.onlyAttackCreeper();
                            b.setValue(newVal);
                            sweep.setOnlyAttackCreeper(newVal);
                            var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.CREEPER_SWEEPER_EXC.get(), newVal);
                            PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                        }     
                    ).initialValue(sweep.onlyAttackCreeper()),
                    I18n.get("talent.doggytalents.creeper_sweeper.only_attack_creeper")
                )
                .init()
            );
        } else if (talent == DoggyTalents.FISHER_DOG.get()) {
            var talentInstOptional = dog.getTalent(DoggyTalents.FISHER_DOG);
            if (!talentInstOptional.isPresent())
                return;
            var talentInst = talentInstOptional.get();
            if (!(talentInst instanceof FisherDogTalent fisher))
                return;
            container.addChildren(
                new ButtonOptionEntry(container, getScreen(), 
                    new FlatCheckbox(0, 0,
                        b -> {
                            boolean newVal = !fisher.renderHat();
                            b.setValue(newVal);
                            fisher.setRenderHat(newVal);
                            var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.FISHER_DOG_RENDER.get(), newVal);
                            PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                        }     
                    ).initialValue(fisher.renderHat()),
                    I18n.get("talent.doggytalents.fisher_dog.render_hat")
                )
                .init()
            );
        } else if (talent == DoggyTalents.FLYING_FURBALL.get()) {
            var talentInstOptional = dog.getTalent(DoggyTalents.FLYING_FURBALL);
            if (!talentInstOptional.isPresent())
                return;
            var talentInst = talentInstOptional.get();
            if (!(talentInst instanceof FlyingFurballTalent flying))
                return;
            container.addChildren(
                new ButtonOptionEntry(container, getScreen(), 
                    new FlatCheckbox(0, 0,
                        b -> {
                            boolean newVal = !flying.allowFlying();
                            b.setValue(newVal);
                            flying.setAllowFlying(newVal);
                            var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.FLYING_FURBALL_ALLOW.get(), newVal);
                            PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                        }     
                    ).initialValue(flying.allowFlying()),
                    I18n.get("talent.doggytalents.flying_furbal.allow")
                )
                .init()
            );
        }
    }

    private void addRenderPackPuppyButton(Dog dog, AbstractElement container) {
        var talentInstOptional = dog.getTalent(DoggyTalents.PACK_PUPPY);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof PackPuppyTalent packPup))
            return;
        container.addChildren(
            new ButtonOptionEntry(container, getScreen(), 
                new FlatCheckbox(0, 0,
                    b -> {
                        boolean newVal = !packPup.renderChest();
                        b.setValue(newVal);
                        packPup.setRenderChest(newVal);
                        var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.PACK_PUPPY_RENDER.get(), newVal);
                        PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                    }     
                ).initialValue(packPup.renderChest()),
                I18n.get("talent.doggytalents.pack_puppy.render_chest")
            )
            .init()
        );
        if (packPup.canCollectItems()) {
            container.addChildren(
                new ButtonOptionEntry(container, getScreen(), 
                    new FlatCheckbox(0, 0,
                        b -> {
                            boolean newVal = !packPup.pickupItems();
                            b.setValue(newVal);
                            packPup.setPickupItems(newVal);
                            var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.PACK_PUPPY_PICKUP.get(), newVal);
                            PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                        }     
                    ).initialValue(packPup.pickupItems()),
                    I18n.get("talent.doggytalents.pack_puppy.pickup_item")
                )
                .init()
            );
            // container.addChildren(
            //     new ButtonOptionEntry(container, getScreen(), 
            //         new FlatCheckbox(0, 0,
            //             b -> {
            //                 boolean newVal = !packPup.collectKillLoot();
            //                 b.setValue(newVal);
            //                 packPup.setCollectKillLoot(newVal);
            //                 var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.PACK_PUPPY_LOOT.get(), newVal);
            //                 PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
            //             }     
            //         ) {
            //             @Override
            //             public void renderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
            //                 super.renderWidget(graphics, mouseX, mouseY, pTicks);
            //                 if (!this.isHovered) return;
            //                 var c1 = Component.translatable("talent.doggytalents.pack_puppy.collect_kill_loot.desc");
            //                 ToolTipOverlayManager.get().setComponents(List.of(c1));
            //             }
            //         }.initialValue(packPup.collectKillLoot()),
            //         I18n.get("talent.doggytalents.pack_puppy.collect_kill_loot")
            //     )
            //     .init()
            // );
        }
        if (packPup.canOfferFood()) {
            container.addChildren(
                new ButtonOptionEntry(container, getScreen(), 
                    new FlatCheckbox(0, 0,
                        b -> {
                            boolean newVal = !packPup.offerFood();
                            b.setValue(newVal);
                            packPup.setOfferFood(newVal);
                            var data = DogTalentOptionSetData.of(dog, talent, TalentsOptions.PACK_PUPPY_FOOD.get(), newVal);
                            PacketHandler.send(PacketDistributor.SERVER.noArg(), data);
                        }     
                    ).initialValue(packPup.offerFood()),
                    I18n.get("talent.doggytalents.pack_puppy.offer_food")
                )
                .init()
            );
        }
    }

    private void addTrainButton(Dog dog) {
        int dogLevel = dog.getDogLevel(talent);
        var trainButton = new CustomButton(0, 0, 
            50, 20, Component.translatable("doggui.talents.train"), 
            b -> {
                //send training packet and dispatch here.
                requestTrain();
            }
        ) {
            //@Override
            public void renderWidgetMain(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
                //super.renderWidget(graphics, mouseX, mouseY, pTicks);
                int tX = this.getX();
                int tY = this.getY() - LINE_SPACING - font.lineHeight;
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
                graphics.text(font, costStr, tX, tY, costStrColor);
                this.active = 
                    dogLevel < talent.getMaxLevel()
                    && dog.canSpendPoints(talent.getLevelCost(dogLevel + 1));
            }

            @Override
            public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
                super.extractContents(graphics, mouseX, mouseY, dogLevel);
                renderWidgetMain(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
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

        trainButton.setX(trainButtonX);
        trainButton.setY(trainButtonY);

        this.addChildren(trainButton);

        var pointsLeftStr = new OneLineLimitedTextArea(0, 0, 75, Component.translatable("doggui.pointsleft"));
        pointsLeftStr.setX(this.getRealX() + PADDING_LEFT + 40);
        pointsLeftStr.setY(this.getRealY() + this.getSizeY() - 45);
        this.addChildren(pointsLeftStr);

    }

    private void requestTrain() {
        int level = dog.getDogLevel(talent);
        if (level < talent.getMaxLevel() && dog.canSpendPoints(talent.getLevelCost(level + 1))) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogTalentData(dog.getId(), talent));
        }
    }

    @Override
    public void renderElement(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.talent == null) {
            int mX = this.getSizeX()/2;
            int mY = this.getSizeY()/2;
            var txt = I18n.get("doggui.talents.no_talents_selected");
            int tX = this.getRealX() + mX - this.font.width(txt)/2;
            int tY = this.getRealY() + mY - this.font.lineHeight/2;
            graphics.text(font, txt, tX, tY, 0xffffffff);
            return;
        } else if (!DogUtil.playerCanTrainTalent(Minecraft.getInstance().player, talent)) {
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
            graphics.text(font, txt, tX, tY, 0xffffffff);
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
            graphics.text(font, txt, tX, tY, 0xffffffff);
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
        // graphics.text(font, title, pX, pY, 0xffffffff);
        // pY += 2*LINE_SPACING + this.font.lineHeight;
        // var desc = Component.translatable(this.talent.getInfoTranslationKey());
        // var desc_lines = this.font.split(desc, this.getSizeX() - (PADDING_LEFT + PADDING_RIGHT));
        // for (var line : desc_lines) {
        //     graphics.text(font, line, pX, pY, 0xffffffff);
        //     pY += font.lineHeight + LINE_SPACING;
        // }

        //Kanji
        startX = this.getRealX() + PADDING_LEFT;
        pY = this.getRealY() + this.getSizeY() - 60;
        this.drawDogLevelKanji(graphics, startX, pY, 50);

        //Point left:
        startX = this.getRealX() + PADDING_LEFT + 40;
        pY = this.getRealY() + this.getSizeY() - 45;
        //var currentLevelStr = I18n.get("doggui.pointsleft");
        var currentLevelStr = "";
        var currentLevelStr1 = "" + this.dog.getSpendablePoints();
        //graphics.text(font, currentLevelStr, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        graphics.text(font, currentLevelStr1, startX, pY, 0xffffffff);

        //Current level:
        startX = this.getRealX() + 80 + 40;
        pY = this.getRealY() + this.getSizeY() - 45;
        currentLevelStr = I18n.get("doggui.talents.current_talent_level");
        currentLevelStr1 = this.dog.getDogLevel(talent) 
            + "/" + this.talent.getMaxLevel();
        graphics.text(font, currentLevelStr, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        graphics.text(font, currentLevelStr1, startX, pY, 0xffffffff);

        
        
    }

    private void drawDogLevelKanji(GuiGraphicsExtractor graphics, int x, int y, int size) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        // RenderSystem.setShaderColor removed - no longer needed for white tint
        //RenderSystem.setShaderTexture(0, DogStatusViewBoxElement.getKanjiDogLevel(this.dog));
        int imgeSize = size;
        graphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, DogStatusViewBoxElement.getKanjiDogLevel(this.dog), x,
            y, 0.0F, 0.0F, imgeSize, imgeSize, imgeSize, imgeSize);
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
        public void renderElement(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
            int pX = this.getRealX();
            int pY = this.getRealY() + LINE_SPACING;
            graphics.text(font, title, pX, pY, 0xffffffff);
            
            pY += font.lineHeight + LINE_SPACING;
            for (var line : descriptionLines) {
                graphics.text(font, line, pX, pY, 0xffffffff);
                pY += font.lineHeight + LINE_SPACING;
            }
        }

    }

    private static class ButtonOptionEntry extends AbstractElement {

        private AbstractWidget button;
        private String label;
        private Font font;

        private boolean newline = false;

        public ButtonOptionEntry(AbstractElement parent, Screen screen, AbstractWidget button, String label) {
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

            this.button.setX(this.getRealX() + buttonX_offset);
            this.button.setY(this.getRealY() + buttonY_offset);

            this.addChildren(button);
            return this;
        }

        @Override
        public void renderElement(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
            if (newline) {
                int startX = this.getRealX() + PADDING_LEFT;
                int pY = this.getRealY() + 3;
                graphics.text(font, this.label, startX, pY, 0xffffffff);
                
                return;
            } 

            int startX = this.getRealX() + PADDING_LEFT;
            int pY = this.getRealY() + this.getSizeY()/2
                - font.lineHeight/2;
            graphics.text(font, this.label, startX, pY, 0xffffffff);
        }
    }

    private static class PackPuppyButtonDiv extends AbstractElement {

        private DogInventoryButton button;
        private Dog dog;

        public PackPuppyButtonDiv(AbstractElement parent, Screen screen, Dog dog) {
            super(parent, screen);
            this.dog = dog;
        }

        @Override
        public AbstractElement init() {
    
            this.button = new DogInventoryButton(
                this.getRealX() + PADDING_LEFT, 
                this.getRealY() + 5, getScreen(), dog);
            this.addChildren(button);

            return this;
        }

        @Override
        public void onGlobalKeyPress(int keyCode, int scanCode, int modifiers) {
            this.button.keyGlobalPressed(keyCode, scanCode, modifiers);
        }

        @Override
        public void onGlobalKeyRelease(int keyCode, int scanCode, int modifiers) {
            this.button.keyGlobalReleased(keyCode, scanCode, modifiers);
        }

    }

    
}
