package doggytalents.client.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public class DebugGraph {
    public static final DebugGraph COMMON_INSTANCE = new DebugGraph(0, 0, 480, 120);

    public static DebugGraph shared() {
        return COMMON_INSTANCE;
    }

    public int x, y, width, height;

    public DebugGraph(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    private int maxHistory = 100;
    private final Map<String, RecordEntry> historyMap = new HashMap<>();
    
    public void reset(int maxHistory, EntriesBuilder builder) {
        this.maxHistory = maxHistory;
        historyMap.clear();
        if (maxHistory <= 0 || builder.entries.isEmpty()) {
            maxHistory = 0;
            return;
        }
        builder.entries.forEach(pair -> {
            historyMap.put(pair.getLeft(), pair.getRight());
        });
    }

    public void recordValue(String name, float val) {
        if (maxHistory <= 0)
            return;
        var entry = this.historyMap.get(name);
        if (entry == null)
            return;
        val = Mth.clamp(val, entry.minValue(), entry.maxValue());
        var history = entry.history();
        history.add(val);
        if (history.size() > maxHistory)
            history.removeFirst();
    }
    
    public void stop() {
        this.reset(0, EntriesBuilder.NULL);
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, float pticks) {

        //Background
        int cl = 0x005e5d5d | 0x48000000;
        graphics.fill(x, y, x+width, y+height, cl);
        
        for (var entry : this.historyMap.entrySet()) {
            renderEntry(graphics, x, y, width, height, entry.getValue());
        }
    }

    private void renderEntry(GuiGraphicsExtractor graphics, int x, int y, int w, int h, RecordEntry entry) {
        final float min_value = entry.minValue();
        final float max_value = entry.maxValue();
        final var history = entry.history();

        if (history.size() < 2)
            return;
        
        var bufferSource = net.minecraft.client.Minecraft.getInstance().renderBuffers().bufferSource();
        var vertexConsumer = bufferSource.getBuffer(net.minecraft.client.renderer.rendertype.RenderTypes.LINES);

        for (int i = 0; i < history.size() - 1; ++i) {
            float val = history.get(i);
            float val1 = history.get(i + 1);
            
            //normalize & clamp
            val = (val - min_value)/ (max_value - min_value);
            val1 = (val1 - min_value)/ (max_value - min_value);
            val = Mth.clamp(val, 0, 1);
            val1 = Mth.clamp(val1, 0, 1);

            float val_x = x + ((float)i / maxHistory) * w;
            float val1_x = x + ((float)(i + 1) / maxHistory) * w;

            float val_y = y + h - val * h;
            float val1_y = y + h - val1 * h;
            
            int r = ARGB.red(entry.color());
            int g = ARGB.green(entry.color());
            int b = ARGB.blue(entry.color());
            int a = ARGB.alpha(entry.color());
            vertexConsumer.addVertex(val_x, val_y, 0).setColor(r, g, b, a);
            vertexConsumer.addVertex(val1_x, val1_y, 0).setColor(r, g, b, a);
        }
    }

    // public static void afterGuiRender(RenderGuiEvent.Post event) {
    //     var shared = shared();
    //     if (shared.maxHistory <= 0)
    //         return;
    //     shared.render(event.getGuiGraphics(), event.getPartialTick().getGameTimeDeltaPartialTick(true));
    // }

    public static EntriesBuilder entriesBuilder() {
        return new EntriesBuilder();
    }

    public static class EntriesBuilder {
        private static final EntriesBuilder NULL = new EntriesBuilder();

        private final List<Pair<String, RecordEntry>> entries = new ArrayList<>();

        private EntriesBuilder() {}

        public EntriesBuilder entry(String id, float min, float max, int color) {
            if (min > max)
                throw new IllegalArgumentException("min cannot be greater than max");
            this.entries.add(Pair.of(id, new RecordEntry(new LinkedList<>(), min, max, color)));
            return this;
        }
    }

    private static record RecordEntry(
        LinkedList<Float> history,
        float minValue, float maxValue,
        int color
    ) {}

}
