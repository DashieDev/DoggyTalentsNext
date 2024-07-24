package doggytalents.client.entity.render.layer;

import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyTalents;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogRescueModel;
import doggytalents.client.entity.model.FisherDogModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.talent.FisherDogTalent;
import doggytalents.common.talent.RescueDogTalent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class FisherDogRenderer extends RenderLayer<Dog, DogModel> {

    private FisherDogModel model;

    public FisherDogRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new FisherDogModel(ctx.bakeLayer(ClientSetup.DOG_FISHER_HAT));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dog.isInvisible()) {
            return;
        }

        var dogSkin = dog.getClientSkin();
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

            RenderLayer.renderColoredCutoutModel(this.model, Resources.FISHER_HAT, poseStack, buffer, packedLight, dog, 0xffffffff);
        }

    }
}
