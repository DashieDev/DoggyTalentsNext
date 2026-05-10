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

public class DogCustomGlowingOverlayRenderer extends RenderLayer<DogRenderState, DogModel> {

    public DogCustomGlowingOverlayRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        if (renderState.isInvisible)
            return;

        var dog = renderState.dog;
        if (dog == null) return;

        var skin = renderState.activeSkin;
        if (skin == null) return;
        if (!skin.isCustom() || !skin.hasGlowingOverlay())
            return;

        var glow_layer = skin.getGlowingOverlay().get();
        submitNodeCollector.submitModel(this.getParentModel(), renderState, poseStack,
            RenderTypes.entityTranslucent(glow_layer), 15728880, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
    }

}
