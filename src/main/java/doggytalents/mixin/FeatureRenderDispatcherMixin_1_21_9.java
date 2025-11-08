package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.backward_imitate.LegacyRenderLayerUtil_1_21_9;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;

@Mixin(FeatureRenderDispatcher.class)
public class FeatureRenderDispatcherMixin_1_21_9 {
    
    @Inject(at = @At("TAIL"),  method = "renderAllFeatures")
    public void dtn__renderAllFeatures(CallbackInfo info) {
        LegacyRenderLayerUtil_1_21_9.onEndOfFeatureRender();   
    }

}
