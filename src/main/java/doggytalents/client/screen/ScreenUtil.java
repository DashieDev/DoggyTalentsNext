package doggytalents.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import doggytalents.api.inferface.AbstractDog;

public class ScreenUtil {

    public static List<Component> splitInto(String text, int maxLength, Font font) {
        List<Component> list = new ArrayList<>();

        StringBuilder temp = new StringBuilder();
        String[] split = text.split(" ");

        for (int i = 0; i < split.length; ++i) {
            String str = split[i];
            int length = font.width(temp + str);

            if (length > maxLength) {
                list.add(Component.literal(temp.toString()));
                temp = new StringBuilder();
            }

            temp.append(str);
            temp.append(" ");

            if (i == split.length - 1) {
                list.add(Component.literal(temp.toString()));
            }
        }

        return list;
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics graphics, int dog_mX, int dog_mY, int size, float lookX, float lookY, AbstractDog dog) {
        dog_mY -= size/2;
        var inflated_size = size + 60;
        lookX = dog_mX - lookX;
        lookY = dog_mY - lookY;
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, dog_mX - inflated_size/2, dog_mY - inflated_size/2,
            dog_mX + inflated_size/2, dog_mY + inflated_size/2, size, 
            0.0625F, lookX, lookY, dog);
    }

    public static boolean shouldRemderSurvivalElement(Minecraft mc) {
        return mc.gameMode.canHurtPlayer();
    }



    //1.21.3+
    public static void blit_21_3(GuiGraphics graphics, ResourceLocation texture, int x, int y, int tex_from_x, int tex_from_y, int tex_to_x, int tex_to_y) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, tex_from_x, tex_from_y, tex_to_x, tex_to_y, 256, 256);
    }
    public static void blit_21_3(GuiGraphics graphics, ResourceLocation texture, int x, int y, int a, float tex_from_x, float tex_from_y, int tex_to_x, int tex_to_y, int tex_size_x, int tex_size_y) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, tex_from_x, tex_from_y, tex_to_x, tex_to_y, tex_size_x, tex_size_y);
    }

    //1.21.5+
    public static void renderComponentTooltip_21_5(GuiGraphics graphics, Font font, List<Component> list, int i, int j) {
        var client_components = list.stream()
            .map(Component::getVisualOrderText).collect(Collectors.toList());
        graphics.setTooltipForNextFrame(client_components, i, j);
    }

    //1.21.7+
    public static void blitColored_1_21_7(GuiGraphics graphics, ResourceLocation texture, int x, int y, float tex_from_x, float tex_from_y, int tex_to_x, int tex_to_y, int tex_size_x, int tex_size_y, float[] color) {
        int color_i = ARGB.colorFromFloat(color[3], color[0], color[1], color[2]);
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, tex_from_x, tex_from_y, tex_to_x, tex_to_y, tex_to_x, tex_to_y, tex_size_x, tex_size_y, color_i);
    }
    public static void blitColored_1_21_7(GuiGraphics graphics, ResourceLocation texture, int x, int y, float tex_from_x, float tex_from_y, int tex_to_x, int tex_to_y, float[] color) {
        int color_i = ARGB.colorFromFloat(color[3], color[0], color[1], color[2]);
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, tex_from_x, tex_from_y, tex_to_x, tex_to_y, tex_to_x, tex_to_y, 256, 256, color_i);
    }
}
