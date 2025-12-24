package doggytalents.client.backward_imitate;

import org.apache.commons.lang3.NotImplementedException;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.network.chat.Component;

public class GuiUtil_1_21_9 {
    
    public static void buttonOnClickNull(AbstractWidget widget) {
        var dummy_event = new MouseButtonEvent(0, 0, new MouseButtonInfo(0, 0));
        widget.onClick(dummy_event, false);
    }

    public static void buttonTriggerKeyEvent(AbstractWidget widget, boolean press, int keyCode, int scanCode, int modifiers) {
        var dummy_event = new KeyEvent(keyCode, scanCode, modifiers);
        if (press)
            widget.keyPressed(dummy_event);
        else
            widget.keyReleased(dummy_event);
    }


    public static InputConstants.Key getInputKey(int keyCode, int scanCode) {
        var event = new KeyEvent(keyCode, scanCode, 0);
        return InputConstants.getKey(event);
    }

    public static boolean matchesMouse(KeyMapping key, int button) {
        var event = new MouseButtonEvent(0, 0, new MouseButtonInfo(button, 0));
        return key.matchesMouse(event);
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
        protected void onDrag(MouseButtonEvent event, double dY, double dX) {
            this.onDrag(event.x(), event.y(), dY, dX);
            super.onDrag(event, dY, dX);
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
        public void onDrag(double mouseX, double mouseY, double dY, double dX) {}
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

    public static abstract class AbstractButton_1_21_9 extends GuiUtil_1_21_11.AbstractButton_1_21_11 {

        public AbstractButton_1_21_9(int x, int y, int w, int h, Component msg) {
            super(x, y, w, h, msg);
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
        public void onPress(InputWithModifiers p_446730_) {
            onPress();
        }

        
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            return false;
        }
        public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
            return false;
        }
        public abstract void onPress();

    }

    public static abstract class Screen_1_21_9 extends Screen {

        protected Screen_1_21_9(Component title) {
            super(title);
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
        public boolean charTyped(CharacterEvent charEvent) {
            if (this.charTyped(charEvent.codepoint(), charEvent.modifiers()))
                return true;
            return super.charTyped(charEvent);
        }

        
        @Override
        public boolean mouseClicked(MouseButtonEvent mouseEvent, boolean doubleClick) {
            if (this.mouseClicked(mouseEvent.x(), mouseEvent.y(), mouseEvent.button()))
                return true;
            return super.mouseClicked(mouseEvent, doubleClick);
        }

        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            return false;
        }

        public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
            return false;
        }

        public boolean charTyped(int codepoint, int modifiers) {
            return false;
        }

        @Deprecated
        public final boolean charTyped(char codepoint, int modifiers) {
            throw new NotImplementedException("legacy placeholder");
        }

        public boolean mouseClicked(double x, double y, int mouseButton) {
            return false;
        }
    }

}
