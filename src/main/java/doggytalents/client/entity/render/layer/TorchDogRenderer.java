package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyTalents;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.TorchDogModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.lib.Resources;
import doggytalents.common.talent.DoggyTorchTalent;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class TorchDogRenderer extends RenderLayer<DogRenderState, DogModel>  {

    private TorchDogModel model;

    public TorchDogRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new TorchDogModel(ctx.bakeLayer(ClientSetup.DOG_TORCHIE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        if (renderState.isInvisible) {
            return;
        }

        var dog = renderState.dog;
        if (dog == null) return;

        var inst = dog.getTalent(DoggyTalents.DOGGY_TORCH)
            .map(x -> x.cast(DoggyTorchTalent.class));
        if (!inst.isPresent())
            return;
        if (!inst.get().canRenderTorch())
            return;
        if (!inst.get().renderTorch())
            return;
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.setupAnim(renderState);

        Identifier res = Resources.TORCH_DOG;
        int renderLight = 15728880;
        if (dog.isDefeated()) {
            res = Resources.TORCH_DOG_UNLIT;
            renderLight = packedLight;
        }
        RenderLayer.renderColoredCutoutModel(this.model, res, poseStack, submitNodeCollector, renderLight, renderState, OverlayTexture.NO_OVERLAY, 0xffffffff);

    }

}
