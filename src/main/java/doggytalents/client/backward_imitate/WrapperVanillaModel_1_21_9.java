package doggytalents.client.backward_imitate;

import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;

public class WrapperVanillaModel_1_21_9 extends Model<WrappedEntityRenderState_21_3<?>> {

    private final BaseModel_21_3 wrapped;

    public WrapperVanillaModel_1_21_9(BaseModel_21_3 wrapped) {
        super(new ModelPart(List.of(), Map.of()), RenderType::entityTranslucent);
        this.wrapped = wrapped;
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay,
            int color) {
        wrapped.renderToBuffer(stack, consumer, light, color, overlay);
    }

    @Override
    public void setupAnim(WrappedEntityRenderState_21_3<?> state) {
        state.runDefferedSetupAndInvalidate_1_21_9();
        super.setupAnim(state);
    }
}