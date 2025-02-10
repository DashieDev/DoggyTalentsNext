package doggytalents.client.backward_imitate;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public abstract class DogRenderLayerWithRenderState_21_3 extends RenderLayer<DogRenderState_21_3, DogModel> {

    public DogRenderLayerWithRenderState_21_3(RenderLayerParent<DogRenderState_21_3, DogModel> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int light, DogRenderState_21_3 render_state,
            float yrot, float xrot) {
        var dog = render_state.dog;
        var walk_anim_time = render_state.walkAnimationPos;
        var walk_anim_speed = render_state.walkAnimationSpeed;
        var pticks = render_state.partialTick;
        var ticksp = render_state.ageInTicks;
        render(stack, buffer, light, dog, walk_anim_time, walk_anim_speed, pticks, ticksp, yrot, xrot, render_state);
    }

    public abstract void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, DogRenderState_21_3 render_state);
    
}
