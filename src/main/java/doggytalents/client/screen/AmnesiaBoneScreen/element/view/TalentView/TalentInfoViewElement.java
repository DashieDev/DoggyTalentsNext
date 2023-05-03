package doggytalents.client.screen.AmnesiaBoneScreen.element.view.TalentView;
import java.security.cert.PKIXReason;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyTalents;
import doggytalents.api.registry.Talent;
import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTalentDescSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.client.screen.widget.DogInventoryButton;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogDeTrainData;
import doggytalents.common.network.packet.data.DogTalentData;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
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

        this.addDetrainButton(dog);
        

        return this;
    }

    private void addDetrainButton(Dog dog) {
        int dogLevel = dog.getDogLevel(talent);
        var trainButton = new CustomButton(0, 0, 
            50, 20, Component.translatable("doggui.detrain.talents.detrain"), 
            b -> {
                //send training packet and dispatch here.
                requestDeTrain();
            }
        ) {
            @Override
            public void renderWidget(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                // TODO Auto-generated method stub
                super.renderWidget(stack, mouseX, mouseY, pTicks);
                int tX = this.getX();
                int tY = this.getY() - LINE_SPACING - font.lineHeight;
                // var costStr = dogLevel < talent.getMaxLevel() ?
                //     "Cost : " + talent.getLevelCost(dogLevel + 1)
                //     : "Max Level Reached.";
                int dogLevel = dog.getDogLevel(talent);
                String costStr;
                int costStrColor;
                if (dogLevel <= 0) {
                    costStr = I18n.get("doggui.detrain.talents.no_level");
                    costStrColor = 0xffF4FF00;
                } else {
                    costStr = I18n.get("doggui.talents.cost") + talent.getDeTrainXPCost(dogLevel);
                    costStrColor = 0xffffffff;
                }
                font.draw(stack, costStr, tX, tY, costStrColor);
                var player = Minecraft.getInstance().player;
                this.active = 
                    dogLevel > 0
                    && (player != null && player.experienceLevel >= talent.getDeTrainXPCost(dogLevel));
            }

            @Override
            public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                super.render(stack, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                MutableComponent c1;
                var player = Minecraft.getInstance().player;
                if (this.active) {
                    return;
                } else {
                    if (player != null && player.experienceLevel >= talent.getDeTrainXPCost(dogLevel)) {
                        return;
                    } else {
                        c1 = Component.translatable("doggui.detrain.talents.insufficent_xp");
                        c1.setStyle(
                            Style.EMPTY
                            .withColor(0xffB20000)
                            .withBold(true)
                        );
                    }
                }
                getScreen().renderComponentTooltip(stack, List.of(c1), mouseX, mouseY);
            }
        };
        var player = Minecraft.getInstance().player;
        trainButton.active = 
            dogLevel > 0
            && (player != null && player.experienceLevel >= talent.getDeTrainXPCost(dogLevel));
        int trainButtonX = this.getRealX() + this.getSizeX() - trainButton.getWidth() - 35;
        int trainButtonY = this.getRealY() + this.getSizeY() - trainButton.getHeight() - 20;

        trainButton.setX(trainButtonX);
        trainButton.setY(trainButtonY);

        this.addChildren(trainButton);
    }

    private void requestDeTrain() {
        int level = dog.getDogLevel(talent);
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        var xp = player.experienceLevel;
        if (level > 0 && xp >= talent.getDeTrainXPCost(level)) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogDeTrainData(dog.getId(), talent));
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
        } 

        //Title and description
        int startX = this.getRealX() + PADDING_LEFT;
        int startY = this.getRealY() + PADDING_TOP;
        int pX = startX;
        int pY = startY;
        var title = Component.translatable(this.talent.getTranslationKey())
            .withStyle(
                Style.EMPTY
                .withBold(true)
                .withColor(0xffF4FF00)
            );
        font.draw(stack, title, pX, pY, 0xffffffff);
        pY += 2*LINE_SPACING + this.font.lineHeight;
        var desc = Component.translatable(this.talent.getInfoTranslationKey());
        var desc_lines = this.font.split(desc, this.getSizeX() - (PADDING_LEFT + PADDING_RIGHT));
        for (var line : desc_lines) {
            font.draw(stack, line, pX, pY, 0xffffffff);
            pY += font.lineHeight + LINE_SPACING;
        }

        //Point left:
        startX = this.getRealX() + PADDING_LEFT;
        pY = this.getRealY() + this.getSizeY() - 45;
        var currentLevelStr = I18n.get("doggui.pointsleft");
        var currentLevelStr1 = "" + this.dog.getSpendablePoints();
        font.draw(stack, currentLevelStr, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        font.draw(stack, currentLevelStr1, startX, pY, 0xffffffff);

        //Current level:
        startX = this.getRealX() + 80;
        pY = this.getRealY() + this.getSizeY() - 45;
        currentLevelStr = I18n.get("doggui.talents.current_talent_level");
        currentLevelStr1 = this.dog.getDogLevel(talent) 
            + "/" + this.talent.getMaxLevel();
        font.draw(stack, currentLevelStr, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        font.draw(stack, currentLevelStr1, startX, pY, 0xffffffff);
        
    }
    
}
