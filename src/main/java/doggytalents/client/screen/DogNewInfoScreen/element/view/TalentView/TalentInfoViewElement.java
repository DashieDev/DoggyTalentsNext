package doggytalents.client.screen.DogNewInfoScreen.element.view.TalentView;

import java.security.cert.PKIXReason;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyTalents;
import doggytalents.api.registry.Talent;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTalentDescSlice;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.widget.DogInventoryButton;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogTalentData;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
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

        if (!ConfigHandler.TALENT.getFlag(talent)) {
            return this;
        }

        this.addTrainButton(dog);

        if (
            talent == DoggyTalents.PACK_PUPPY.get()
            && !ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.DOG_INV_BUTTON_IN_INV) 
        ) {
            this.addDogInventoryButton();
        }

        

        return this;
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
            @Override // TODO 1.19.4 ???
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                // TODO Auto-generated method stub
                super.renderWidget(graphics, mouseX, mouseY, pTicks);
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
                graphics.drawString(font, costStr, tX, tY, costStrColor);
                this.active = 
                    dogLevel < talent.getMaxLevel()
                    && dog.canSpendPoints(talent.getLevelCost(dogLevel + 1));
            }

            @Override
            public void render(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                super.render(graphics, mouseX, mouseY, dogLevel);
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
                graphics.renderComponentTooltip(font, List.of(c1), mouseX, mouseY);
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
    }

    private void addDogInventoryButton() {
        var dogInvButton = new DogInventoryButton(
            0, 0, getScreen(), (btn) -> {
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenDogScreenData());
                btn.active = false;
            });
        int dogInvButtonX = this.getRealX() + PADDING_LEFT;
        int dogInvButtonY = this.getRealY() + this.getSizeY() - 60;

        dogInvButton.setX(dogInvButtonX);
        dogInvButton.setY(dogInvButtonY);
        this.addChildren(dogInvButton);
    }

    private void requestTrain() {
        int level = dog.getDogLevel(talent);
        if (level < talent.getMaxLevel() && dog.canSpendPoints(talent.getLevelCost(level + 1))) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogTalentData(dog.getId(), talent));
        }
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.talent == null) {
            int mX = this.getSizeX()/2;
            int mY = this.getSizeY()/2;
            var txt = I18n.get("doggui.talents.no_talents_selected");
            int tX = this.getRealX() + mX - this.font.width(txt)/2;
            int tY = this.getRealY() + mY - this.font.lineHeight/2;
            graphics.drawString(font, txt, tX, tY, 0xffffffff);
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
            graphics.drawString(font, txt, tX, tY, 0xffffffff);
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
        graphics.drawString(font, title, pX, pY, 0xffffffff);
        pY += 2*LINE_SPACING + this.font.lineHeight;
        var desc = Component.translatable(this.talent.getInfoTranslationKey());
        var desc_lines = this.font.split(desc, this.getSizeX() - (PADDING_LEFT + PADDING_RIGHT));
        for (var line : desc_lines) {
            graphics.drawString(font, line, pX, pY, 0xffffffff);
            pY += font.lineHeight + LINE_SPACING;
        }

        //Point left:
        startX = this.getRealX() + PADDING_LEFT;
        pY = this.getRealY() + this.getSizeY() - 45;
        var currentLevelStr = I18n.get("doggui.pointsleft");
        var currentLevelStr1 = "" + this.dog.getSpendablePoints();
        graphics.drawString(font, currentLevelStr, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        graphics.drawString(font, currentLevelStr1, startX, pY, 0xffffffff);

        //Current level:
        startX = this.getRealX() + 80;
        pY = this.getRealY() + this.getSizeY() - 45;
        currentLevelStr = I18n.get("doggui.talents.current_talent_level");
        currentLevelStr1 = this.dog.getDogLevel(talent) 
            + "/" + this.talent.getMaxLevel();
        graphics.drawString(font, currentLevelStr, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        graphics.drawString(font, currentLevelStr1, startX, pY, 0xffffffff);

        
        
    }
    
}
