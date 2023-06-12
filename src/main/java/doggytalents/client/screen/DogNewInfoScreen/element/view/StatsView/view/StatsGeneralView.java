package doggytalents.client.screen.DogNewInfoScreen.element.view.StatsView.view;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.common.entity.stats.StatsTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;

public class StatsGeneralView extends AbstractElement {

    static final int PADDING_LEFT = 5;
    static final int PADDING_RIGHT = 30;
    static final int PADDING_TOP = 5;
    static final int LINE_SPACING = 3;
    
    StatsTracker stats;
    Font font;

    public StatsGeneralView(AbstractElement parent, Screen screen, StatsTracker stats, Font font) {
        super(parent, screen);
        this.stats = stats;
        this.font = font;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // TODO Add refresh button with interval lock ah no need.
        int startX = this.getRealX() + PADDING_LEFT;
        int pY = this.getRealY() + PADDING_TOP;
        String i18nPrefix = "doggui.stats.general.";
        String draw;
        draw = I18n.get(i18nPrefix + "damageDealt") + ": " + stats.getDamageDealt();
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceInWater") + ": " + stats.getDistanceInWater();
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceOnWater") + ": "+ stats.getDistanceOnWater();
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceRidden") + ": "+ stats.getDistanceRidden();
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceSneaking") + ": " + stats.getDistanceSneaking();
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceSprinting") + ": " + stats.getDistanceSprint();
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceWalking") + ": " + stats.getDistanceWalk();
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;

    }
    
}
