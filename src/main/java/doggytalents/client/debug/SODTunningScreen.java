package doggytalents.client.debug;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.common.collect.Maps;

import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.ScrollBar;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.client.screen.framework.widget.ScrollBar.Direction;
import doggytalents.common.entity.anim.SecondOrderDynamics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.StatFormatter;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.client.input.KeyEvent;

public class SODTunningScreen extends Screen {

    private static final float MIN_VAL = 0f;
    private static final float MAX_VAL = 1f;

    private final RandomSource random = RandomSource.create();
    private DebugGraph sampleGraph = new DebugGraph(0, 0, 480, 120);
    private boolean simpleMode = false;

    //Persistent state
    private static float[] xArr = null;
    private static final Map<String, TunningEntry> persistentData = Maps.newHashMap();

    public SODTunningScreen() {
        super(Component.empty());
        resetGraph(xArr == null);
    }

    private void resetGraph(boolean new_graph) {
        final int simulate_ticks = 200;
        sampleGraph.reset(simulate_ticks, 
            DebugGraph.entriesBuilder()
                .entry("x", -0.2f, 1.2f, 0xffff0000)
                .entry("y", -0.2f, 1.2f, 0xff0000ff)
        );
        if (new_graph || xArr == null) {
            xArr = simpleMode ? generateSimpleData(simulate_ticks) 
            : generateRandomData(simulate_ticks);
        }
        float f = persistentData.computeIfAbsent("f", k -> new TunningEntry())
            .value;
        float z = persistentData.computeIfAbsent("z", k -> new TunningEntry())
            .value;
        float r = persistentData.computeIfAbsent("r", k -> new TunningEntry())
            .value;
        float[] y = f > 0 ?
            calculateRespond(f, z, r, xArr)
            : xArr;
        for (int i = 0; i < simulate_ticks; ++i) {
            sampleGraph.recordValue("x", xArr[i]);
            sampleGraph.recordValue("y", y[i]);
        }
    }

    private float[] generateRandomData(int ticks) {
        float value = MIN_VAL + random.nextFloat() * (MAX_VAL - MIN_VAL);
        int duration = random.nextInt(40);
        var buffer = new float[ticks];
        for (int i = 0; i < ticks; ++i) {
            if (duration <= 0) {
                value = MIN_VAL + random.nextFloat() * (MAX_VAL - MIN_VAL);
                duration = random.nextInt(20);
            }
            --duration;
            buffer[i] = value;
        }
        return buffer;
    }

    private float[] generateSimpleData(int ticks) {
        var buffer = new float[ticks];
        int change_tick = Mth.floor(0.3f * ticks);
        for (int i = 0; i < ticks; ++i) {
            buffer[i] = i < change_tick ? 0.1f : 0.8f;
        }
        return buffer;
    }

    private float[] calculateRespond(float f, float z, float r, float[] raw_data) {
        var respond = new float[raw_data.length];
        respond[0] = raw_data[0];
        var dynamics = SecondOrderDynamics.single(f, z, r, respond[0]);
        for (int i = 1; i < raw_data.length; ++i) {
            respond[i] = dynamics.update(1/20f, raw_data[i]);
        }
        return respond;
    }

    @Override
    protected void init() {
        int width1 = Mth.clamp(this.width, 0, 500);
        int mX = this.width/2;
        int mY = this.height/2;
        this.sampleGraph.width = width1;
        this.sampleGraph.height = Mth.floor(this.height * 0.4f);
        int pY = Mth.floor(this.height * 0.4f) + 30;
        var reset_button = new FlatButton(
            width1 - 70, pY, 60, 30, Component.literal("New sample"), b -> {
                this.resetGraph(true);
            });
        this.addRenderableWidget(reset_button);

        int pX = 30;
        createAndAddTunningEntry("f", pX, pY, new_val -> {
            resetGraph(false);
        });
        pY += 25;
        createAndAddTunningEntry("z", pX, pY, new_val -> {
            resetGraph(false);
        });
        pY += 25;
        createAndAddTunningEntry("r", pX, pY, new_val -> {
            resetGraph(false);
        });

    }

    private TunningEntry createAndAddTunningEntry(String id, int x, int y, Consumer<Float> responder) {
        int pX = x;
        var entry = persistentData.computeIfAbsent(id, k -> new TunningEntry());
        final Function<Float, Component> value_show_to_str =
            val -> Component.literal(id + ": " + StatFormatter.DECIMAL_FORMAT.format(val));
        final var value_show = new TextOnlyButton(pX, y, 60, 20, 
            value_show_to_str.apply(entry.value), b -> {
                try {
                    var value = StatFormatter.DECIMAL_FORMAT.format(entry.value);
                    Minecraft.getInstance().keyboardHandler.setClipboard(value);
                } catch (NumberFormatException e) {

                }
            }, font);

        final var clamp_field = new EditBox(font, pX, y, 60, 20, Component.empty());
        clamp_field.setValue("" 
            + StatFormatter.DECIMAL_FORMAT.format(entry.min) + ":" 
            + StatFormatter.DECIMAL_FORMAT.format(entry.max));
        clamp_field.setResponder(s -> {
            var splitted = s.split(":");
            if (splitted.length == 1) {
                float value = 0;
                try {
                    value = Float.parseFloat(splitted[0].strip());
                } catch (NumberFormatException e) {
                    
                }
                entry.min = value;
                entry.max = value;
                entry.value = 0;
                return;
            }
            if (splitted.length != 2)
                return;
            float min = 0;
            float max = 0;
            try {
                min = Float.parseFloat(splitted[0].strip());
                max = Float.parseFloat(splitted[1].strip());
            } catch (NumberFormatException e) {
                
            }
            if (max < min)
                return;
            entry.min = min;
            entry.max = max;
            entry.value = 0;
        });
        this.addRenderableWidget(clamp_field);
        pX += clamp_field.getWidth() + 20;
        final var slider = new ScrollBar(pX, y + 5, 100, 10, Direction.HORIZONTAL, 10, this) {
            @Override
            public void onValueUpdated() {
                entry.value = (float) (entry.min + this.getProgressValue() * (entry.max - entry.min));
                entry.value = Mth.clamp(entry.value, entry.min, entry.max);
                entry.responder.accept(entry.value);
                value_show.setMessage(value_show_to_str.apply(entry.value));
            }
        }.alignMode(ScrollBar.AlignMode.CENTER);
        double progress = entry.max - entry.min <= 0 ? 0 :
            (entry.value - entry.min) / (entry.max - entry.min); 
        double slider_off = progress * slider.getMaxOffsetValue();
        slider.setBarOffset(slider_off);
        this.addRenderableWidget(slider);
        pX += slider.getWidth() + 20;
        value_show.setX(pX);
        value_show.setY(y);
        this.addRenderableWidget(value_show);
        
        entry.responder = responder;
        return entry;
    }
    
    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pticks) {
        graphics.fill(0, 0, this.width, this.height, 0x40000000);
        super.extractRenderState(graphics, mouseX, mouseY, pticks);
        this.sampleGraph.extractRenderState(graphics, pticks);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        var sneakKey = this.minecraft.options.keyShift;
        if (event.key() == sneakKey.getKey().getValue()) {
            this.simpleMode = true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        var sneakKey = this.minecraft.options.keyShift;
        if (event.key() == sneakKey.getKey().getValue()) {
            this.simpleMode = false;
        }
        return super.keyReleased(event);
    }

    private static class TunningEntry {
        public float min, max;
        public float value;
        public Consumer<Float> responder;
        public TunningEntry() {} 
    }

}
