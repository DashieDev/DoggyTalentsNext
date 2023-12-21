package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.DogRandomNameRegistry;
import doggytalents.client.screen.ScreenUtil;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.GroupsListElement;
import doggytalents.client.screen.DogNewInfoScreen.widget.CombatReturnSwitch;
import doggytalents.client.screen.DogNewInfoScreen.widget.LowHealthStrategySwitch;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.ScrollView;
import doggytalents.client.screen.framework.element.ElementPosition.ChildDirection;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.CrossOriginTpData;
import doggytalents.common.network.packet.data.DogAutoMountData;
import doggytalents.common.network.packet.data.DogForceSitData;
import doggytalents.common.network.packet.data.DogNameData;
import doggytalents.common.network.packet.data.DogNoCuriousData;
import doggytalents.common.network.packet.data.DogObeyData;
import doggytalents.common.network.packet.data.DogRegardTeamPlayersData;
import doggytalents.common.network.packet.data.FriendlyFireData;
import doggytalents.common.network.packet.data.PatrolTargetLockData;
import doggytalents.common.network.packet.data.HideArmorData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.PacketDistributor;

public class EditInfoView extends AbstractElement {

    static final int PADDING_LEFT = 10;
    static final int PADDING_RIGHT = 30;
    static final int PADDING_TOP = 10;
    static final int LINE_SPACING = 3;

    Dog dog; 
    Font font;

    public EditInfoView(AbstractElement parent, Screen screen, Dog dog, Font font) {
        super(parent, screen);
        this.dog = dog;
        this.font = font;
    }

    @Override
    public AbstractElement init() {

        var scrollView = new ScrollView(this, getScreen());
        scrollView
            .setPosition(PosType.ABSOLUTE, 0, 0)
            .setSize(1f, 1f)
            .init();
        this.addChildren(scrollView);

        var scroll = scrollView.getContainer();

        scroll.addChildren(
            new NewnameEntry(scroll, getScreen(), dog)
                .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0, 
                    40, 20, ComponentUtil.literal("" + this.dog.canOwnerAttack()), 
                    b -> {
                        boolean newVal = !dog.canOwnerAttack();
                        b.setMessage(ComponentUtil.literal("" + newVal));
                        this.requestFriendlyFire(newVal);
                    }
                ),
                I18n.get("doggui.friendlyfire")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0,
                    40, 20, ComponentUtil.literal("" + this.dog.willObeyOthers()), 
                    b -> {
                        Boolean newVal = !this.dog.willObeyOthers();
                        b.setMessage(ComponentUtil.literal("" + newVal));
                        this.requestObeyOthers(newVal);
                    }     
                ),
                I18n.get("doggui.obeyothers")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0,
                    40, 20, ComponentUtil.literal("" + this.dog.regardTeamPlayers()), 
                    b -> {
                        Boolean newVal = !this.dog.regardTeamPlayers();
                        b.setMessage(ComponentUtil.literal("" + newVal));
                        this.requestRegardTeamPlayers(newVal);
                    }     
                ) {
                    @Override
                    public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                        super.render(stack, mouseX, mouseY, pTicks);
                        if (this.isHovered) {
                            ToolTipOverlayManager.get().setComponents(ScreenUtil.splitInto(I18n.get("doggui.regard_team_players.help"), 150, font));
                        }
                    }
                },
                I18n.get("doggui.regard_team_players")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0,
                    40, 20, ComponentUtil.literal("" + this.dog.forceSit()), 
                    b -> {
                        Boolean newVal = !this.dog.forceSit();
                        b.setMessage(ComponentUtil.literal("" + newVal));
                        this.requestForceSit(newVal);
                    }     
                ) {
                    @Override
                    public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                        super.render(stack, mouseX, mouseY, pTicks);
                        if (this.isHovered) {
                            ToolTipOverlayManager.get().setComponents(ScreenUtil.splitInto(I18n.get("doggui.force_sit.help"), 150, font));
                        }
                    }
                },
                I18n.get("doggui.force_sit")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0,
                    40, 20, ComponentUtil.literal("" + this.dog.crossOriginTp()), 
                    b -> {
                        Boolean newVal = !this.dog.crossOriginTp();
                        b.setMessage(ComponentUtil.literal("" + newVal));
                        this.requestCrossOriginTp(newVal);
                    }     
                ) {
                    @Override
                    public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                        super.render(stack, mouseX, mouseY, pTicks);
                        if (this.isHovered) {
                            ToolTipOverlayManager.get().setComponents(ScreenUtil.splitInto(I18n.get("doggui.cross_origin_tp.help"), 150, font));
                        }
                    }
                },
                I18n.get("doggui.cross_origin_tp")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0,
                    40, 20, ComponentUtil.literal("" + this.dog.patrolTargetLock()), 
                    b -> {
                        Boolean newVal = !this.dog.patrolTargetLock();
                        b.setMessage(ComponentUtil.literal("" + newVal));
                        this.requestPatrolTargetLock(newVal);
                    }     
                ) {
                    @Override
                    public void render(PoseStack graphics, int mouseX, int mouseY, float pTicks) {
                        super.render(graphics, mouseX, mouseY, pTicks);
                        if (this.isHovered) {
                            ToolTipOverlayManager.get().setComponents(ScreenUtil.splitInto(I18n.get("doggui.patrol_target_lock.help"), 150, font));
                        }
                    }
                },
                I18n.get("doggui.patrol_target_lock")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0,
                    40, 20, ComponentUtil.literal("" + this.dog.hideArmor()), 
                    b -> {
                        Boolean newVal = !this.dog.hideArmor();
                        b.setMessage(ComponentUtil.literal("" + newVal));
                        this.requestShowArmor(newVal);
                    }     
                ),
                I18n.get("doggui.hide_armor")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0,
                    40, 20, ComponentUtil.literal("" + this.dog.dogAutoMount()), 
                    b -> {
                        Boolean newVal = !this.dog.dogAutoMount();
                        b.setMessage(ComponentUtil.literal("" + newVal));
                        this.requestAutoMount(newVal);
                    }     
                ),
                I18n.get("doggui.auto_mount")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new FlatButton(
                    0, 0,
                    40, 20, Component.literal("" + this.dog.noDogCurious()), 
                    b -> {
                        Boolean newVal = !this.dog.noDogCurious();
                        b.setMessage(Component.literal("" + newVal));
                        this.requestNoDogCurious(newVal);
                    }     
                ) {
                    @Override
                    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                        super.render(graphics, mouseX, mouseY, pTicks);
                        if (this.isHovered) {
                            ToolTipOverlayManager.get().setComponents(ScreenUtil.splitInto(I18n.get("doggui.no_dog_curious.help"), 150, font));
                        }
                    }
                },
                I18n.get("doggui.no_dog_curious")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new LowHealthStrategySwitch(
                    0, 0, 
                    100, 20, dog, font, getScreen()
                ),
                I18n.get("dog.low_health_strategy")
            )
            .init()
        );

        scroll.addChildren(
            new ButtonOptionEntry(scroll, getScreen(), 
                new CombatReturnSwitch(
                    0, 0, 
                    100, 20, dog, font, getScreen()
                ),
                I18n.get("dog.combat_return_strategy")
            )
            .init()
        );

        scroll.addChildren(
            new GroupEntry(scroll, getScreen(), dog)
            .init()
        );

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
    }

    private static void requestNameChange(Dog dog, String value) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(),
             new DogNameData(dog.getId(), value));
    }

    private void requestFriendlyFire(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new FriendlyFireData(this.dog.getId(), val));
    }

    private void requestObeyOthers(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new DogObeyData(this.dog.getId(), val));
    }

    private void requestRegardTeamPlayers(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new DogRegardTeamPlayersData(this.dog.getId(), val));
    }

    private void requestForceSit(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new DogForceSitData(this.dog.getId(), val));
    }

    private void requestCrossOriginTp(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new CrossOriginTpData(this.dog.getId(), val));
    }

    private void requestPatrolTargetLock(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new PatrolTargetLockData(this.dog.getId(), val));
    }

    private void requestShowArmor(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new HideArmorData(this.dog.getId(), val));
    }

    private void requestAutoMount(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new DogAutoMountData(this.dog.getId(), val));
    }

    private void requestNoDogCurious(boolean val) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(), 
            new DogNoCuriousData(this.dog.getId(), val));
    }

    private static class NewnameEntry extends AbstractElement {

        private Font font;
        private Dog dog;
        private EditBox nameEdit;
        private FlatButton applyButton;
        private FlatButton randomButton;

        public NewnameEntry(AbstractElement parent, Screen screen, Dog dog) {
            super(parent, screen);
            this.font = Minecraft.getInstance().font;
            this.dog = dog;
        }

        @Override
        public AbstractElement init() {

            this.setPosition(PosType.RELATIVE, 0, 0);
            this.setSize(1f, 60);

            int startX = this.getRealX() + PADDING_LEFT;
            int pY = this.getRealY() + PADDING_TOP;
            
            pY += font.lineHeight + LINE_SPACING;

            this.addEditNameBox(startX, pY, 180, 20);
            this.applyButton = new FlatButton(
                startX + this.nameEdit.getWidth() + 15,
                pY, 40, 20, 
                ComponentUtil.translatable("doggui.common.apply"), b -> {
                    var newName = this.nameEdit.getValue();
                    requestNameChange(this.dog, newName);
                    b.active = false;
                });
            this.randomButton = new FlatButton(startX + this.nameEdit.getWidth() + 15,
            pY, 20, 20, ComponentUtil.empty(), b -> {
                var newName = DogRandomNameRegistry.getInstance().getRandomName(dog);
                requestNameChange(this.dog, newName);
                this.nameEdit.setValue(newName);
                this.applyButton.active = false;
                this.randomButton.active = true;
            }) {
                @Override
                public void renderButton(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                    if (!this.active)
                        return;
                    if (this.isHovered) {
                        fill(stack, this.x, this.y, this.x+this.width, this.y+this.height, 0x835e5d5d);
                    }
                    RenderSystem.setShaderTexture(0, Resources.HAMBURGER);
                    blit(stack, this.x, this.y, 20, 0, 20, 20);
                }
            };
            this.applyButton.active = false;
            this.randomButton.active = true;
            this.addChildren(randomButton);
            this.addChildren(applyButton);
        

            return this;
        }

        @Override
        public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
            int startX = this.getRealX() + PADDING_LEFT;
            int pY = this.getRealY() + PADDING_TOP;

            font.draw(stack, I18n.get("doggui.newname"), startX, pY, 0xffffffff);
            
        }

        private void addEditNameBox(int x, int y, int w, int h) {
            this.nameEdit = new EditBox(this.font, x, y, w, h, ComponentUtil.translatable("dogInfo.enterName"));
            nameEdit.setFocus(false);
            nameEdit.setMaxLength(32);
            nameEdit.setResponder(s -> {
                if (this.applyButton == null) return;
                if (this.applyButton.active) return;
                var dogName = this.dog.hasCustomName() ?
                    this.dog.getCustomName().getString()
                    : "";
                if (!s.equals(dogName)) {
                    this.applyButton.active = true;
                    this.randomButton.active = false;
                }
            });
    
            if (this.dog.hasCustomName()) {
                nameEdit.setValue(this.dog.getCustomName().getString());
            }
    
            this.addChildren(nameEdit);
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
            font.draw(stack, this.label, startX, pY, 0xffffffff);
        }
    }

    private static class GroupEntry extends AbstractElement {

        private Font font;
        private Dog dog;

        public GroupEntry(AbstractElement parent, Screen screen, Dog dog) {
            super(parent, screen);
            this.dog = dog;
            this.font = Minecraft.getInstance().font;
        }

        @Override
        public AbstractElement init() {
            this.setPosition(PosType.RELATIVE, 0, 0);
            this.setSize(1f, 60);
            this.addChildren(
                new GroupsListElement(this, getScreen(), dog)
                .setPosition(PosType.ABSOLUTE, PADDING_LEFT, 24)
                .setSize(this.getSizeX() - 2*PADDING_LEFT, 40)
                .init()
            );
            return this;
        }

        @Override
        public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
            int startX = this.getRealX() + PADDING_LEFT;
            int pY = this.getRealY() + 10;

            font.draw(stack, "Groups: ", startX, pY, 0xffffffff);
        }

    }

}
