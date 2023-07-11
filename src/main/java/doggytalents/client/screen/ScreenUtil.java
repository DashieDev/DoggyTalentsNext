package doggytalents.client.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;

import java.util.ArrayList;
import java.util.List;

public class ScreenUtil {

    public static List<Component> splitInto(String text, int maxLength, Font font) {
        List<Component> list = new ArrayList<>();

        StringBuilder temp = new StringBuilder();
        String[] split = text.split(" ");

        for (int i = 0; i < split.length; ++i) {
            String str = split[i];
            int length = font.width(temp + str);

            if (length > maxLength) {
                list.add(ComponentUtil.literal(temp.toString()));
                temp = new StringBuilder();
            }

            temp.append(str);
            temp.append(" ");

            if (i == split.length - 1) {
                list.add(ComponentUtil.literal(temp.toString()));
            }
        }

        return list;
    }


}
