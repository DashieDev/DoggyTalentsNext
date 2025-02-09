package doggytalents.client.backward_imitate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;

public abstract class BaseModel_20_3 {
 
    abstract public void renderToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_, int color_overlay);

    public static <T extends Model> VanillaWrapper<T> wrap(T model) {
        return new VanillaWrapper<>(model);
    }

    public static class VanillaWrapper<T extends Model> extends BaseModel_20_3 {
        private final T model;
        private VanillaWrapper(T model) {
            this.model = model;
        }
        @Override
        public void renderToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_,
                int color_overlay) {
            this.model.renderToBuffer(stack, p_103014_, p_103015_, p_103016_, color_overlay);
        }
    }

}
