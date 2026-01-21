package doggytalents.client.debug;

import java.util.LinkedList;
import java.util.function.Supplier;

import org.checkerframework.checker.units.qual.min;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public enum DebugGraph {
    INSTANCE;

    private int maxHistory = 100;
    private final LinkedList<Float> history = new LinkedList<>();
    private float maxValue = 1;
    private float minValue = -1;

    public void reset(int maxHistory, float min, float max) {
        if (min > max)
            throw new IllegalArgumentException("min cannot be greater than max");
        this.maxHistory = maxHistory;
        this.history.clear();
        this.minValue = min;
        this.maxValue = max;
    }

    public void recordValue(float val) {
        if (maxHistory <= 0)
            return;
        val = Mth.clamp(val, minValue, maxValue);
        history.add(val);
        if (history.size() > maxHistory)
            history.removeFirst();
    }
    
    public void stop() {
        this.reset(0, 0, 0);
    }

    public void renderOverlay(GuiGraphics graphics, float pticks) {
        final int x = 0;
        final int y = 0;
        final int width = 480;
        final int height = 120;

        //Background
        int cl = 0x005e5d5d | 0x48000000;
        graphics.fill(x, y, x+width, y+height, cl);

        if (this.history.size() < 2)
            return;
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        
        var tessellator = Tesselator.getInstance();
        var buffer = tessellator.begin(Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        for (int i = 0; i < this.history.size() - 1; ++i) {
            float val = this.history.get(i);
            float val1 = this.history.get(i + 1);
            
            //normalize & clamp
            val = (val - minValue)/ (maxValue - minValue);
            val1 = (val1 - minValue)/ (maxValue - minValue);
            val = Mth.clamp(val, 0, 1);
            val1 = Mth.clamp(val1, 0, 1);

            float val_x = x + ((float)i / maxHistory) * width;
            float val1_x = x + ((float)(i + 1) / maxHistory) * width;

            float val_y = y + height - val * height;
            float val1_y = y + height - val1 * height;
            
            buffer.addVertex(val_x, val_y, 0).setColor(0, 255, 0, 255);
            buffer.addVertex(val1_x, val1_y, 0).setColor(0, 255, 0, 255);
        }

        BufferUploader.drawWithShader(buffer.buildOrThrow());

        RenderSystem.enableDepthTest();
    }

    @SubscribeEvent
    public void afterGuiRender(RenderGuiEvent.Post event) {
        if (this.maxHistory <= 0)
            return;
        renderOverlay(event.getGuiGraphics(), event.getPartialTick().getGameTimeDeltaPartialTick(true));
    }

}
