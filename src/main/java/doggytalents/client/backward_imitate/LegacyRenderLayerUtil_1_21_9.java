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

        private DogModel parentDogModel = null;

        public DogRenderLayer_1_21_9(RenderLayerParent<DogRenderState_21_3, DogModel> p_117346_) {
            super(p_117346_);
        }

        @Override
        public void submit(PoseStack stack, SubmitNodeCollector collector, int light,
            DogRenderState_21_3 renderState, float yrot, float xrot) {
            
            var submit = new DogLegacyLayerSubmit_1_21_9(stack.last().copy(), 
                renderState, light, yrot, xrot, super.getParentModel());
            submits.add(Pair.of(this, submit));
        }

        @Override
        public DogModel getParentModel() {
            return this.parentDogModel;
        }

        public void render(PoseStack stack, MultiBufferSource buffer, DogLegacyLayerSubmit_1_21_9 submit) {
            var render_state = submit.renderState;
            int light = submit.light;
            float yrot = submit.yRot;
            float xrot = submit.xRot;
            stack.pushPose();
            stack.last().set(submit.pose);
            this.parentDogModel = submit.parentModel;
            var parent_model = this.getParentModel();
            parent_model.setupAnim(render_state);
            render(stack, buffer, light, render_state, yrot, xrot);
            this.parentDogModel = null;
            stack.popPose();
        }

        public abstract void render(PoseStack stack, MultiBufferSource buffer, int light, 
            DogRenderState_21_3 render_state, float yrot, float xrot);
    }

    public static abstract class DogRenderLayerNew_1_21_9 extends RenderLayer<DogRenderState_21_3, DogModel> {

        public DogRenderLayerNew_1_21_9(RenderLayerParent<DogRenderState_21_3, DogModel> p_117346_) {
            super(p_117346_);
        }

        @Override
        public void submit(PoseStack psoeStack, SubmitNodeCollector nodeCollector_1_21_9, int light, DogRenderState_21_3 renderState_1_21_9, float netHeadYaw, float headPitch) {
            var ctx = new RenderContext_1_21_9(nodeCollector_1_21_9, renderState_1_21_9);
            var dog = renderState_1_21_9.dog;
            var walk_anim_time = renderState_1_21_9.walkAnimationPos;
            var walk_anim_speed = renderState_1_21_9.walkAnimationSpeed;
            var pticks = renderState_1_21_9.partialTick;
            var ticksp = renderState_1_21_9.ageInTicks;
            submit(psoeStack, ctx, light, dog, walk_anim_time, 
                walk_anim_speed, pticks, ticksp, netHeadYaw, headPitch);
        }

        public abstract void submit(PoseStack matrixStack, RenderContext_1_21_9 context_1_21_9, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch);

        public static record RenderContext_1_21_9(SubmitNodeCollector nodeCollector, DogRenderState_21_3 dogRenderState) {}


    }


    public static record DogLegacyLayerSubmit_1_21_9(PoseStack.Pose pose, 
        DogRenderState_21_3 renderState, int light, float yRot, float xRot, DogModel parentModel) {
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
