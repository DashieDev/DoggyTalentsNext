package doggytalents.client.backward_imitate.fabric_util;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.input.KeyEvent;

public class FabricGuiUtil_1_21_10 {
    
    public static boolean matchesKey(KeyMapping key, int keyCode, int scanCode) {
        var keyEvent = new KeyEvent(keyCode, scanCode, 0);
        return key.matches(keyEvent);
    }

}
