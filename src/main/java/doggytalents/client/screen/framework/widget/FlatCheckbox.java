package doggytalents.client.screen.framework.widget;

import doggytalents.client.entity.render.RenderUtil;
import doggytalents.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class FlatCheckbox extends AbstractButton {

    private static final int DEFAULT_COLOR = 0x5e5d5d;
    private static final int DEFAULT_ACTIVE_COLOR = 0x7500A5;
    private static final int HOVER_MASK = 0x83000000;
    private static final int NON_HOVER_MASK = 0x48000000;

    private Font font;

    protected final FlatCheckbox.OnChange onPress;
    private boolean value = false;
    private int activeColor;

    private float animTimeline = 0;
    private long millis0 = 0;
    

    public FlatCheckbox(int x, int y, int activeColor, FlatCheckbox.OnChange onPress) {
        super(x, y, 28, 14, Component.empty());
        this.font = Minecraft.getInstance().font;
        this.onPress = onPress;
        this.activeColor = activeColor;
        millis0 = System.currentTimeMillis();
    }

    public FlatCheckbox(int x, int y, FlatCheckbox.OnChange onPress) {
        this(x, y, DEFAULT_ACTIVE_COLOR, onPress);
    }
    
    @Override
    public void onPress(InputWithModifiers input) {
        this.onPress.onChange(this);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {

        if (!this.active) {
            this.animTimeline = 0;
            return;
        };

        updateAnim();


        int bg_color = maskColorHovered(this.colorLerp(DEFAULT_COLOR, activeColor, this.animTimeline));
        //background
        graphics.fill( 
            this.getX(), this.getY(), 
            this.getX()+this.width, 
            this.getY()+this.height, bg_color);
        //nob
        final int nob_spacing = 2;
        final int nob_size = this.getHeight() - 2 * nob_spacing;
        final int nob_x = getNobX(nob_spacing, nob_size);
        final int nob_y = this.getY() + nob_spacing;

        if (this.value) {
            graphics.fill( 
                nob_x, 
                nob_y, 
                nob_x + nob_size, 
                nob_y + nob_size, 0xffffffff);
        } else {
            graphics.fill( 
                nob_x, 
                nob_y, 
                nob_x + nob_size, 
                nob_y + nob_size, 0xffffffff);
        }
        
    }

    private void updateAnim() {
        long current_millis = System.currentTimeMillis();
        long passed_millis = current_millis - this.millis0;
        if (passed_millis <= 0) {
            return;
        }
        millis0 = current_millis;
        float anim_amount = passed_millis / ((float) getAnimMillis());
        this.animTimeline += this.value ? anim_amount : -anim_amount;
        this.animTimeline = Mth.clamp(this.animTimeline, 0, 1);
    }

    private int getNobX(int nob_spacing, int nob_size) {
        int nobX_true = this.getX() + this.getWidth() - nob_spacing - nob_size;
        int nobX_false = this.getX() + nob_spacing;
        if (nobX_false > nobX_true) {
            int sw = nobX_true; nobX_true = nobX_false; nobX_false = sw;
        } 
        int ret = (int) Mth.lerp(this.animTimeline, nobX_false, nobX_true);
        return Mth.clamp(ret, nobX_false, nobX_true);
    }

    private int colorLerp(int from_color, int to_color, float progress) {
        var acolor0 = Util.rgbIntToIntArray(from_color);
        var acolor1 = Util.rgbIntToIntArray(to_color);
        int red = (int) 
            Mth.clamp(acolor0[0] + (acolor1[0] - acolor0[0]) * progress, 
            0, 255);
        int green = (int) 
            Mth.clamp(acolor0[1] + (acolor1[1] - acolor0[1]) * progress, 
            0, 255);
        int blue = (int) 
            Mth.clamp(acolor0[2] + (acolor1[2] - acolor0[2]) * progress, 
            0, 255);
        return RenderUtil.rgbToInt(new int[] {red, green, blue});
    }

    private int maskColorHovered(int color) {
        if (this.isHovered)
            return color | HOVER_MASK;
        return color | NON_HOVER_MASK;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public FlatCheckbox initialValue(boolean value) {
        this.value = value;
        this.animTimeline = value ? 1 : 0;
        return this;
    }

    public boolean getValue() {
        return this.value;
    }

    private int getAnimMillis() {
        return 30;
    }

    public interface OnChange {
        void onChange(FlatCheckbox button);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }
}
