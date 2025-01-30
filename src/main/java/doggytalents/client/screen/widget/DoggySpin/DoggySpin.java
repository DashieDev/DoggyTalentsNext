package doggytalents.client.screen.widget.DoggySpin;

import doggytalents.common.config.ConfigHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class DoggySpin extends AbstractWidget {

    private int size;
    private long accumulatedTime = 0;
    private long lastAccumulate = 0;

    public DoggySpin(int x, int y, int size) {
        super(x, y, size, size, Component.empty());
        this.size = size;
        this.lastAccumulate = System.currentTimeMillis();
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        updateAcculumlatedMillis();
        // graphics.fill(this.getX(), this.getY(), 
        //     this.getX() + this.getWidth(), 
        //     this.getY() + this.getHeight(), 0xff576f96);
        int mx = this.getX() + this.getWidth()/2;
        int my = this.getY() + this.getHeight()/2;
        DoggySpinModel.get().prepareRender(this.accumulatedTime);
        DoggySpinModel.get().renderGui(graphics, mx, my);
    }

    private void updateAcculumlatedMillis() {
        long current_time = System.currentTimeMillis();
        long time_since = current_time - this.lastAccumulate;
        if (time_since > 0) {
            this.accumulatedTime += Mth.clamp(time_since * 0.9, 0, 34);
            this.lastAccumulate = current_time;
        }
    }

    public void chooseStyle() {
        DoggySpinModel.get().configureRandomStyle();
        this.accumulatedTime = 0;
        this.lastAccumulate = System.currentTimeMillis();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }
    
    private static final DoggySpin spinWidget = new DoggySpin(0, 0, 128);

    public static void onScreenInit(final ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof LevelLoadingScreen) {
            spinWidget.chooseStyle();
        }
    }

    public static void onScreenRenderForeground(final ScreenEvent.Render.Post event) {
        if (!ConfigHandler.CLIENT.WORD_LOAD_ICON.get())
            return;
        if (!(event.getScreen() instanceof LevelLoadingScreen))
            return;
        spinWidget.setY(event.getScreen().height - spinWidget.getHeight());
        spinWidget.render(event.getGuiGraphics(), event.getMouseX(), event.getMouseY(), event.getPartialTick());
    }

}
