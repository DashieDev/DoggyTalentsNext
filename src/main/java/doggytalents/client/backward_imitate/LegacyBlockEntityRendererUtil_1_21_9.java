package doggytalents.client.backward_imitate;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.backward_imitate.WrapperVanillaModel_1_21_9.DefferedSetupContainer_1_21_9;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class LegacyBlockEntityRendererUtil_1_21_9 {

    public static abstract class BlockEntityRenderer_1_21_9<T extends BlockEntity> 
        implements BlockEntityRenderer<T, WrappedBlockEntityRenderState<T>> {

        
        @Override
        public WrappedBlockEntityRenderState<T> createRenderState() {
            return new WrappedBlockEntityRenderState<>();
        }

        @Override
        public void extractRenderState(T blockEntity, 
            WrappedBlockEntityRenderState<T> renderState, float pticks,
            Vec3 cameraPos, @Nullable CrumblingOverlay crumbleOverlay) {
            
            BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, pticks, cameraPos, crumbleOverlay);
            renderState.blockEntity = blockEntity;
            renderState.pticks = pticks;
        }

        @Override
        public void submit(WrappedBlockEntityRenderState<T> renderState, PoseStack stack,
            SubmitNodeCollector collector, CameraRenderState cameraState) {
                
            var block_entity = renderState.blockEntity;
            var ctx = new RenderContext_1_21_9<>(collector, renderState, cameraState);
            float pticks = renderState.pticks;
            int light = renderState.lightCoords;
            int overlay = OverlayTexture.NO_OVERLAY;
            this.submit(block_entity, pticks, stack, ctx, light, overlay);
        }

        public abstract void submit(T blockEntity, float pTicks, PoseStack stack, 
            RenderContext_1_21_9<T> context_1_21_9, int light, int overlay);

        protected void submitModel(SubmitNodeCollector collector_1_21_9, 
            BaseModel_21_3 model, WrappedBlockEntityRenderState<?> renderState, RenderType renderType, 
            PoseStack stack, int light, int overlay, int color) {
            
            collector_1_21_9.submitModel(model.getWrappedVanilla_1_21_9(), 
                renderState, stack, renderType, 
                light, overlay, color, null, 0, null);
        }

        protected void submitModel(RenderContext_1_21_9<?> ctx,
            BaseModel_21_3 model, RenderType renderType, 
            PoseStack stack, int light, int overlay, int color) {

            submitModel(ctx.collector(), model, ctx.renderState(), renderType, 
                stack, light, overlay, color);
        }

    }

    public static record RenderContext_1_21_9<T extends BlockEntity>(
        SubmitNodeCollector collector, WrappedBlockEntityRenderState<T> renderState, CameraRenderState cameraState) {}

    public static class WrappedBlockEntityRenderState<T extends BlockEntity> 
        extends BlockEntityRenderState implements DefferedSetupContainer_1_21_9 {

        public T blockEntity = null;
        public float pticks = 0;
        public Runnable defferedSetup_1_21_9 = null;

        @Override
        public void runDefferedSetupAndInvalidate_1_21_9() {
            if (defferedSetup_1_21_9 == null)
                return;
            defferedSetup_1_21_9.run();
            defferedSetup_1_21_9 = null;
        }

    }

}
