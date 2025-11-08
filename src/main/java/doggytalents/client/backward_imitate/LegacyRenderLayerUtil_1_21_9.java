package doggytalents.client.backward_imitate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class LegacyRenderLayerUtil_1_21_9 {

    public static abstract class DogRenderLayer_1_21_9 extends RenderLayer<DogRenderState_21_3, DogModel> {

        public DogRenderLayer_1_21_9(RenderLayerParent<DogRenderState_21_3, DogModel> p_117346_) {
            super(p_117346_);
        }

        @Override
        public void submit(PoseStack stack, SubmitNodeCollector collector, int light,
            DogRenderState_21_3 renderState, float yrot, float xrot) {
            
            var submit = new DogLegacyLayerSubmit_1_21_9(stack.last().copy(), 
                renderState, light, yrot, xrot);
            submits.add(Pair.of(this, submit));
        }

        public void render(PoseStack stack, MultiBufferSource buffer, DogLegacyLayerSubmit_1_21_9 submit) {
            var render_state = submit.renderState;
            int light = submit.light;
            float yrot = submit.yRot;
            float xrot = submit.xRot;
            stack.pushPose();
            stack.last().set(submit.pose);
            render(stack, buffer, light, render_state, yrot, xrot);
            stack.popPose();
        }

        public abstract void render(PoseStack stack, MultiBufferSource buffer, int light, 
            DogRenderState_21_3 render_state, float yrot, float xrot);
    }


    public static record DogLegacyLayerSubmit_1_21_9(PoseStack.Pose pose, 
        DogRenderState_21_3 renderState, int light, float yRot, float xRot) {
    }
    
    private static final PoseStack poseStack = new PoseStack();
    private static final List<Pair<DogRenderLayer_1_21_9, DogLegacyLayerSubmit_1_21_9>> submits = new ArrayList<>();

    //Mixin Hook
    public static void afterModelFeatureRender(MultiBufferSource buffer) {
        if (submits.isEmpty())
            return;
        for (var entry : submits) {
            var renderer = entry.getLeft();
            var submit = entry.getRight(); 
            renderer.render(poseStack, buffer, submit);
        }
        submits.clear();
    }

    //Mixin Hook
    public static void onEndOfFeatureRender() {
        submits.clear();
    }
}
