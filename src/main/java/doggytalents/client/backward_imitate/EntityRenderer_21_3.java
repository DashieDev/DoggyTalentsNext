package doggytalents.client.backward_imitate;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public abstract class EntityRenderer_21_3<T extends Entity> extends EntityRenderer<T, WrappedEntityRenderState_21_3<T>> {

    protected EntityRenderer_21_3(Context p_174008_) {
        super(p_174008_);
        //TODO Auto-generated constructor stub
    }

    @Override
    public WrappedEntityRenderState_21_3<T> createRenderState() {
        return new WrappedEntityRenderState_21_3<T>(null);
    }

    @Override
    public void render(WrappedEntityRenderState_21_3<T> state, PoseStack stack, MultiBufferSource buffer,
            int light) {
        var entity = state.entity;
        var pticks = state.partialTick;
        var yRot = Mth.lerp(pticks, entity.yRotO, entity.getYRot());
        render(entity, yRot, pticks, stack, buffer, light);
        super.render(state, stack, buffer, light);
    }

    @Override
    public void extractRenderState(T entity, WrappedEntityRenderState_21_3<T> render_state, float pticks) {
        super.extractRenderState(entity, render_state, pticks);
        render_state.entity = entity;
    }
    
    public void render(T entity, float yrot, float pticks, PoseStack stack,
        MultiBufferSource bufferSource, int light) {}

    public abstract ResourceLocation getTextureLocation(T entity);
    

}
