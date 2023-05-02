package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.ScreenUtil;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogForceSitData;
import doggytalents.common.network.packet.data.DogNameData;
import doggytalents.common.network.packet.data.DogObeyData;
import doggytalents.common.network.packet.data.DogRegardTeamPlayersData;
import doggytalents.common.network.packet.data.FriendlyFireData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
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

    //local state
    EditBox nameEdit;
    FlatButton applyButton;
    

    public EditInfoView(AbstractElement parent, Screen screen, Dog dog, Font font) {
        super(parent, screen);
        this.dog = dog;
        this.font = font;
    }

    @Override
    public AbstractElement init() {
        int startX = this.getRealX() + PADDING_LEFT;
        int endX = this.getRealX() + this.getSizeX() - PADDING_RIGHT;
        int pY = this.getRealY() + PADDING_TOP;

        //<Text> Dog name: </Text>
        
        pY += font.lineHeight + LINE_SPACING;

        this.addEditNameBox(startX, pY, 180, 20);
        this.applyButton = new FlatButton(
            startX + this.nameEdit.getWidth() + 15,
            pY, 40, 20, 
            Component.literal("Apply"), b -> {
                var newName = this.nameEdit.getValue();
                this.requestNameChange(newName);
                b.active = false;
            });
        this.applyButton.active = false;
        this.addChildren(applyButton);

        pY += 20 + LINE_SPACING + 2 * (font.lineHeight + LINE_SPACING);

        //<Text> Friendly fire?: </Text>
        var friendlyFireButton = new FlatButton(
            startX + 130, pY, 
            40, 20, Component.literal("" + this.dog.canOwnerAttack()), 
            b -> {
                boolean newVal = !dog.canOwnerAttack();
                b.setMessage(Component.literal("" + newVal));
                this.requestFriendlyFire(newVal);
            }
        );
        this.addChildren(friendlyFireButton);

        pY += 20 + LINE_SPACING;
        //<Text> Obey Others?: </Text>
        var obeyOthersButton = new FlatButton(
            startX + 130, pY, 
            40, 20, Component.literal("" + this.dog.willObeyOthers()), 
            b -> {
                Boolean newVal = !this.dog.willObeyOthers();
                b.setMessage(Component.literal("" + newVal));
                this.requestObeyOthers(newVal);
            }     
        );
        this.addChildren(obeyOthersButton);
        
        pY += 20 + LINE_SPACING;
        //<Text> Regard Team Players?: </Text>
        var regardTeamPlayersButton = new FlatButton(
            startX + 130, pY, 
            40, 20, Component.literal("" + this.dog.regardTeamPlayers()), 
            b -> {
                Boolean newVal = !this.dog.regardTeamPlayers();
                b.setMessage(Component.literal("" + newVal));
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
        };
        this.addChildren(regardTeamPlayersButton);

        pY += 20 + LINE_SPACING;
        //<Text> Regard Team Players?: </Text>
        var forceSit = new FlatButton(
            startX + 130, pY, 
            40, 20, Component.literal("" + this.dog.forceSit()), 
            b -> {
                Boolean newVal = !this.dog.forceSit();
                b.setMessage(Component.literal("" + newVal));
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
        };
        this.addChildren(forceSit);


        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int startX = this.getRealX() + PADDING_LEFT;
        int endX = this.getRealX() + this.getSizeX() - PADDING_RIGHT;
        int pY = this.getRealY() + PADDING_TOP;

        font.draw(stack, I18n.get("doggui.newname"), startX, pY, 0xffffffff);
        
        pY += font.lineHeight + LINE_SPACING;

        //<TextFieldAndApplyButton/>

        pY += 20 + LINE_SPACING + 2 * (font.lineHeight + LINE_SPACING);

        font.draw(stack, I18n.get("doggui.friendlyfire"), startX, pY + 6, 0xffffffff);
        //<Checkbox/>

        pY += 20 + LINE_SPACING;

        font.draw(stack, I18n.get("doggui.obeyothers"), startX, pY + 6, 0xffffffff);
        //<Checkbox/>

        pY += 20 + LINE_SPACING;

        font.draw(stack, I18n.get("doggui.regard_team_players"), startX, pY + 6, 0xffffffff);
        //<Checkbox/>

        pY += 20 + LINE_SPACING;

        font.draw(stack, I18n.get("doggui.force_sit"), startX, pY + 6, 0xffffffff);
        //<Checkbox/>


    }

    private void addEditNameBox(int x, int y, int w, int h) {
        this.nameEdit = new EditBox(this.font, x, y, w, h, Component.translatable("dogInfo.enterName"));
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
            }
        });

        if (this.dog.hasCustomName()) {
            nameEdit.setValue(this.dog.getCustomName().getString());
        }

        this.addChildren(nameEdit);
    }

    private void requestNameChange(String value) {
        PacketHandler
            .send(PacketDistributor.SERVER.noArg(),
             new DogNameData(this.dog.getId(), value));
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

}
