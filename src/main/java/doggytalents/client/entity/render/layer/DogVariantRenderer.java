package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class DogVariantRenderer extends RenderLayer<DogRenderState, DogModel> {

    public DogVariantRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        var dog = renderState.dog;
        if (dog == null || renderState.isInvisible || !dog.isDogVariantRenderEffective())
            return;

        if (dog.isDefeated()) {
            var custom_injured_optional = dog.dogVariant().customInjuredTexture();

            if (!custom_injured_optional.isPresent())
                return;

            var custom_injured = custom_injured_optional.get();
            submitNodeCollector.submitModel(this.getParentModel(), renderState, poseStack,
                RenderTypes.entityTranslucent(custom_injured), packedLight, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
            return;
        }

        var glow_layer_optional = dog.dogVariant().glowingOverlay();

        if (!glow_layer_optional.isPresent())
            return;

        var glow_layer = glow_layer_optional.get();
        submitNodeCollector.submitModel(this.getParentModel(), renderState, poseStack,
            RenderTypes.entityTranslucent(glow_layer), 15728880, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
    }

}
