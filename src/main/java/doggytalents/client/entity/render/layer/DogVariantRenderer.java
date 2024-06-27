package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class DogVariantRenderer extends RenderLayer<Dog, DogModel> {
    
    public DogVariantRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dog.isInvisible())
            return;
        
        var glow_layer_optional = dog.dogVariant().glowingOverlay();

        if (!glow_layer_optional.isPresent())
            return;
        
        var glow_layer = glow_layer_optional.get();
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(glow_layer));
        this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

}
