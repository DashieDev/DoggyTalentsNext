package doggytalents.client.forward_imitate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

public class RenderUtil_1_20_under {
    
    public static void renderPart(ModelPart part, PoseStack stack, VertexConsumer vertexConsumer, int light, int overlay, int color_overlay) {

        float[] color_arr = ARGBUtil_1_20_under.srgbaArrayFromInt(color_overlay);
        float a = color_arr[0];
        float r = color_arr[1];
        float g = color_arr[2];
        float b = color_arr[3];

        part.render(stack, vertexConsumer, light, overlay, a, r, g, b);
    }

}
