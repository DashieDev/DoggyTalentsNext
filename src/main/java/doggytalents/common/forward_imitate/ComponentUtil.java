package doggytalents.common.forward_imitate;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ComponentUtil {

    private static final TextComponent EMPTY = new TextComponent("");

    public static TextComponent literal(String s) {
        return new TextComponent(s);
    }

    public static TranslatableComponent translatable(String key, Object... args) {
        return new TranslatableComponent(key, args);
    }

    public static TextComponent empty() {
        return EMPTY;
    }
    
}
