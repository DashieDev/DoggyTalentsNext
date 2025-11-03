package doggytalents.client.backward_imitate;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.network.chat.Component;

public class GuiUtil_1_21_9 {
    
    public void buttonOnClickNull(AbstractWidget widget) {
        var dummy_event = new MouseButtonEvent(0, 0, new MouseButtonInfo(0, 0));
        widget.onClick(dummy_event, false);
    }


    public static abstract class AbstractWidget_1_21_9 extends AbstractWidget {

        public AbstractWidget_1_21_9(int x, int y, int w, int h, Component msg) {
            super(x, y, w, h, msg);
        }


        @Override
        public void onClick(MouseButtonEvent event, boolean doubleClick) {
            super.onClick(event, doubleClick);
            onClick(event.x(), event.y());
        }
        @Override
        public boolean keyPressed(KeyEvent keyEvent) {
            if (this.keyPressed(keyEvent.key(), keyEvent.scancode(), keyEvent.modifiers()))
                return true;
            return super.keyPressed(keyEvent);
        }
        @Override
        public boolean keyReleased(KeyEvent keyEvent) {
            if (this.keyReleased(keyEvent.key(), keyEvent.scancode(), keyEvent.modifiers()))
                return true;
            return super.keyReleased(keyEvent);
        }
        @Override
        public boolean mouseClicked(MouseButtonEvent mouseEvent, boolean doubleClick) {
            if (this.mouseClicked(mouseEvent.x(), mouseEvent.y(), mouseEvent.button()))
                return true;
            return super.mouseClicked(mouseEvent, doubleClick);
        }


        public void onClick(double mouseX, double mouseY) {}
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            return false;
        }
        public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
            return false;
        }
        public boolean mouseClicked(double x, double y, int mouseButton) {
            return false;
        }
    }

    public static abstract class AbstractButton_1_21_9 extends AbstractButton {

        public AbstractButton_1_21_9(int x, int y, int w, int h, Component msg) {
            super(x, y, w, h, msg);
        }

        @Override
        public void onPress(InputWithModifiers p_446730_) {
            onPress();
        }

        public abstract void onPress();

    }

}
