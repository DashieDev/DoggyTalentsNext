package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class DogModelTranslucentOverrideRenderer extends RenderLayer<Dog, DogModel> {
    
    public DogModelTranslucentOverrideRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float relativeHeadYRot, float headPitch) {
        if (dog.isInvisible())
            return;
        
        final var dog_model = this.getParentModel();
        final var translucent_model = dog_model.getTranslucentOverride();
        if (translucent_model == null)
            return;
        
        this.getParentModel().copyPropertiesTo(translucent_model);
        translucent_model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
        translucent_model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, relativeHeadYRot, headPitch);
        translucent_model.sync(getParentModel());
        DefaultAccessoryRenderer.renderTranslucentModel(translucent_model, this.getTextureLocation(dog), 
            poseStack, buffer, packedLight, dog, 1f, 1f, 1f, 1f);
    }

}
