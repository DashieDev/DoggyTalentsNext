package doggytalents.client.screen.DogNewInfoScreen.element.view.StatsView.view;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.render.DogScreenOverlays;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.common.entity.stats.StatsTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.stats.StatFormatter;

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
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        // TODO Add refresh button with interval lock ah no need.
        int startX = this.getRealX() + PADDING_LEFT;
        int pY = this.getRealY() + PADDING_TOP;
        String i18nPrefix = "doggui.stats.general.";
        String draw;

        draw = I18n.get(i18nPrefix + "damageDealt") + ": " + formatHealth(stats.getDamageDealt()) + " ";
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, startX
            + font.width(draw), pY - 1, 16, 0 ,9, 9);
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, startX
            + font.width(draw), pY - 1, 16 + 45, 0 ,9, 9);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceInWater") + ": " + formatDistance(stats.getDistanceInWater());
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceOnWater") + ": "+ formatDistance(stats.getDistanceOnWater());
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceRidden") + ": "+ formatDistance(stats.getDistanceRidden());
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceSneaking") + ": " + formatDistance(stats.getDistanceSneaking());
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceSprinting") + ": " + formatDistance(stats.getDistanceSprint());
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;
        draw = I18n.get(i18nPrefix + "distanceWalking") + ": " + formatDistance(stats.getDistanceWalk());
        graphics.drawString(font, draw, startX, pY, 0xffffffff);
        pY += font.lineHeight + LINE_SPACING;

    }

    private String formatDistance(int x_cm) {
        return StatFormatter.DISTANCE.format(x_cm);
    }

    private String formatHealth(float h) {
        return StatFormatter.DECIMAL_FORMAT.format(h);
    }
    
}
