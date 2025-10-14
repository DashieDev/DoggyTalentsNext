package doggytalents.client.forward_imitate;

import net.minecraft.util.FastColor.ARGB32;

public class ARGBUtil_1_20_under {
    
    public static int colorFromFloat(float a, float r, float g, float b) {
        return ARGB32.color((int) (a * 255), (int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static float[] srgbaArrayFromInt(int color) {
        return new float[] { 
            ARGB32.red(color) / 255.0f,   
            ARGB32.green(color) / 255.0f, 
            ARGB32.blue(color) / 255.0f,
            ARGB32.alpha(color) / 255.0f 
        };
    }

}
