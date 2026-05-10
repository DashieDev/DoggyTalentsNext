package doggytalents.client.entity.render.layer;

import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyTalents;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.FisherDogModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.lib.Resources;
import doggytalents.common.talent.FisherDogTalent;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class FisherDogRenderer extends RenderLayer<DogRenderState, DogModel> {

    private FisherDogModel model;

    public FisherDogRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new FisherDogModel(ctx.bakeLayer(ClientSetup.DOG_FISHER_HAT));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        if (renderState.isInvisible) {
            return;
        }

        var dog = renderState.dog;
        if (dog == null) return;

        var dogSkin = renderState.activeSkin;
        if (dogSkin == null) return;
        if (dogSkin.useCustomModel()) {
            var model = dogSkin.getCustomModel().getValue();
            if (!model.armorShouldRender(dog))
                return;
        }

        //dont render when hat is present
        var accessories = dog.getAccessories();
        for (var acc : accessories) {
            if (acc.getAccessory().getType() == DoggyAccessoryTypes.HEAD.get()) {
                return;
            }
        }

        Optional<FisherDogTalent> inst = dog.getTalent(DoggyTalents.FISHER_DOG)
            .map(x -> x.cast(FisherDogTalent.class));
        if (inst.isPresent() && inst.get().canRenderHat() && inst.get().renderHat()) {
            var dogModel = this.getParentModel();
            dogModel.copyPropertiesTo(this.model);
            this.model.sync(dogModel);

            RenderLayer.renderColoredCutoutModel(this.model, Resources.FISHER_HAT, poseStack, submitNodeCollector, packedLight, renderState, OverlayTexture.NO_OVERLAY, 0xffffffff);
        }

    }
}
