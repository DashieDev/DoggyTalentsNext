package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyTalents;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.TorchDogModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.talent.DoggyTorchTalent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class TorchDogRenderer extends RenderLayer<Dog, DogModel>  {
    
    private TorchDogModel model;

    public TorchDogRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new TorchDogModel(ctx.bakeLayer(ClientSetup.DOG_TORCHIE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dog.isInvisible()) {
            return;
        }

        var inst = dog.getTalent(DoggyTalents.DOGGY_TORCH)
            .map(x -> x.cast(DoggyTorchTalent.class));
        if (!inst.isPresent())
            return;
        if (!inst.get().canRenderTorch())
            return;
        if (!inst.get().renderTorch())
            return;
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
        this.model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        RenderLayer.renderColoredCutoutModel(this.model, Resources.TORCH_DOG, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);

    }

}
