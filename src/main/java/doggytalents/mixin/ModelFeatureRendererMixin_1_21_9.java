package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.backward_imitate.LegacyRenderLayerUtil_1_21_9;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;

@Mixin(ModelFeatureRenderer.class)
public class ModelFeatureRendererMixin_1_21_9 {
    
    @Inject(at = @At("TAIL"),  method = "render")
    public void dtn__render(SubmitNodeCollection nodeCollection, MultiBufferSource.BufferSource buffer, OutlineBufferSource outlineBuffer, MultiBufferSource.BufferSource crumbling_buffer, CallbackInfo info) {
        
        LegacyRenderLayerUtil_1_21_9.afterModelFeatureRender(buffer);
    }

}
