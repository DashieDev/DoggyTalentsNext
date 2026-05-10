package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyTalents;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogRescueModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.lib.Resources;
import doggytalents.common.talent.RescueDogTalent;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

import java.util.Optional;

public class RescueDogRenderer extends RenderLayer<DogRenderState, DogModel> {

    private DogRescueModel model;

    public RescueDogRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new DogRescueModel(ctx.bakeLayer(ClientSetup.DOG_RESCUE_BOX));
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

        //dont render when bowtie is present
        var accessories = dog.getAccessories();
        for (var acc : accessories) {
            if (acc.getAccessory().getType() == DoggyAccessoryTypes.BOWTIE.get()) {
                return;
            }
        }

        Optional<RescueDogTalent> inst = dog.getTalent(DoggyTalents.RESCUE_DOG)
            .map(x -> x.cast(RescueDogTalent.class));;
        if (inst.isPresent() && inst.get().renderBox()) {
            var dogModel = this.getParentModel();
            dogModel.copyPropertiesTo(this.model);
            this.model.sync(dogModel);

            RenderLayer.renderColoredCutoutModel(this.model, Resources.TALENT_RESCUE, poseStack, submitNodeCollector, packedLight, renderState, OverlayTexture.NO_OVERLAY, 0xffffffff);
        }

    }
}
