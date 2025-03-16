package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;

public class ScreenEvent extends Event {

    private final Screen screen;

    private ScreenEvent(Screen screen) {
        this.screen = screen;
    }
    
    public Screen getScreen() {
        return this.screen;
    }

    public static class Render {
        
        public static class Post extends ScreenEvent {
        
            private GuiGraphics graphics;
            private int mouseX;
            private int mouseY;
            private float pTicks;

            public Post(Screen screen, GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                super(screen);
                this.graphics = graphics;
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                this.pTicks = pTicks;
            }

            public GuiGraphics getGuiGraphics() {
                return this.graphics;
            }

            public int getMouseX() {
                return this.mouseX;
            }

            public int getMouseY() {
                return this.mouseY;
            }

            public float getPartialTick() {
                return this.pTicks;
            }
        }

    }

    public static class Init {

        
        
        public static class Post extends ScreenEvent {

            public Post(Screen scr) {
                super(scr);
            }

            public void addListener(AbstractButton button) {
                Screens.getButtons(this.getScreen()).add(button);
            }

        }

    }

}
