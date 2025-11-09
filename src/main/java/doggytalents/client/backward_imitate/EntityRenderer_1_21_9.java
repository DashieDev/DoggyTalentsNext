package doggytalents.client.backward_imitate;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public abstract class EntityRenderer_1_21_9<T extends Entity> extends EntityRenderer<T, WrappedEntityRenderState_21_3<T>> {

    private final ItemModelResolver itemResolver;

    protected EntityRenderer_1_21_9(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.itemResolver = ctx.getItemModelResolver();
    }

    @Override
    public WrappedEntityRenderState_21_3<T> createRenderState() {
        return new WrappedEntityRenderState_21_3<T>(null);
    }

    @Override
    public void submit(WrappedEntityRenderState_21_3<T> renderState_1_21_9, PoseStack stack, SubmitNodeCollector collector_1_21_9, CameraRenderState cameraState_1_21_9) {
        var entity = renderState_1_21_9.entity;
        float yrot = entity.getYRot();
        float pticks = renderState_1_21_9.partialTick;
        int light = renderState_1_21_9.lightCoords;
        var ctx = new RenderContext_1_21_9<>(collector_1_21_9, renderState_1_21_9, cameraState_1_21_9);
        submit(entity, yrot, pticks, stack, ctx, light);
    }

    public abstract void submit(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, RenderContext_1_21_9<T> render_ctx_1_21_9, int packedLightIn);

    public static record RenderContext_1_21_9<T extends Entity>(
        SubmitNodeCollector collector, WrappedEntityRenderState_21_3<T> renderState, CameraRenderState cameraState) {}

    @Override
    public void extractRenderState(T entity, WrappedEntityRenderState_21_3<T> render_state, float pticks) {
        super.extractRenderState(entity, render_state, pticks);
        render_state.entity = entity;
    }
    
    public abstract ResourceLocation getTextureLocation(T entity);
    
    protected void submitModel(SubmitNodeCollector collector_1_21_9, 
        BaseEntityModel_21_3<?> model, WrappedEntityRenderState_21_3<?> renderState, RenderType renderType, 
        PoseStack stack, int light, int overlay, int color) {

        collector_1_21_9.submitModel(model.getWrappedVanilla_1_21_9(), 
            renderState, stack, renderType, 
            light, overlay, color, null, renderState.outlineColor, null);
    }

    protected void submitModel(RenderContext_1_21_9<?> ctx,
        BaseEntityModel_21_3<?> model, RenderType renderType, 
        PoseStack stack, int light, int overlay, int color) {

        submitModel(ctx.collector(), model, ctx.renderState(), renderType, 
            stack, light, overlay, color);
    }

    protected void submitItemStack(ItemStack itemStack, ItemDisplayContext itemDisplayContext,
        int light, int overlay, PoseStack stack, RenderContext_1_21_9<?> ctx) {

        var item_state = new ItemStackRenderState();
        this.itemResolver.updateForNonLiving(item_state, itemStack, 
            itemDisplayContext, ctx.renderState.entity);
        item_state.submit(stack, ctx.collector(), light, overlay, 0);

    }
}
