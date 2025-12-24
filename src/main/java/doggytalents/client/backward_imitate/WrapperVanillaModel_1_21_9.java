package doggytalents.client.backward_imitate;

import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class WrapperVanillaModel_1_21_9 extends Model<WrapperVanillaModel_1_21_9.DefferedSetupContainer_1_21_9> {

    private final BaseModel_21_3 wrapped;

    public WrapperVanillaModel_1_21_9(BaseModel_21_3 wrapped) {
        super(new ModelPart(List.of(), Map.of()), RenderTypes::entityTranslucent);
        this.wrapped = wrapped;
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay,
            int color) {
        wrapped.renderToBuffer(stack, consumer, light, overlay, color);
    }

    @Override
    public void setupAnim(DefferedSetupContainer_1_21_9 state) {
        state.runDefferedSetupAndInvalidate_1_21_9();
        super.setupAnim(state);
    }
    
    @FunctionalInterface
    public static interface DefferedSetupContainer_1_21_9 {

        public void runDefferedSetupAndInvalidate_1_21_9();

    }
}