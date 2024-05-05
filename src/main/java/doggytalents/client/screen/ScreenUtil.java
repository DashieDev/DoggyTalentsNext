package doggytalents.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

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

}