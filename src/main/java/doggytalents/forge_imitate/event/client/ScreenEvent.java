package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;

public class ScreenEvent {
    
    public static class Render {
        
        public static class Post extends Event {
        
            private final Screen screen;
            private GuiGraphics graphics;
            private int mouseX;
            private int mouseY;
            private float pTicks;

            public Post(Screen screen, GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                this.screen = screen;
                this.graphics = graphics;
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                this.pTicks = pTicks;
            }

            public Screen getScreen() {
                return this.screen;
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

        
        
        public static class Post extends Event {
            
            private final Screen screen;

            public Post(Screen scr) {
                this.screen = scr;
            }
            
            public Screen getScreen() {
                return this.screen;
            }

            public void addListener(AbstractButton button) {
                Screens.getButtons(screen).add(button);
            }

        }

    }

}
