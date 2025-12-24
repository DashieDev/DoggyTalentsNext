package doggytalents.client.backward_imitate;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class GuiUtil_1_21_11 {
    
    public static abstract class AbstractButton_1_21_11 extends AbstractWidget {

        public AbstractButton_1_21_11(int x, int y, int w, int h, Component msg) {
            super(x, y, w, h, msg);
        }

        public abstract void onPress(InputWithModifiers clickInput);

        @Override
        public void onClick(MouseButtonEvent clickInput, boolean doubleClick_1_21_9) {
            this.onPress(clickInput);
        }
        
    }

}
