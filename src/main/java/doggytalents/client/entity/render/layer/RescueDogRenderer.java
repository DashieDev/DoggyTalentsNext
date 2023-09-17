package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyTalents;
import doggytalents.api.registry.TalentInstance;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogRescueModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import java.util.Optional;

public class RescueDogRenderer extends RenderLayer<Dog, DogModel> {

    private DogRescueModel model;

    public RescueDogRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new DogRescueModel(ctx.bakeLayer(ClientSetup.DOG_RESCUE_BOX));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dog.isInvisible()) {
            return;
        }

        //dont render when bowtie is present
        var accessories = dog.getAccessories();
        for (var acc : accessories) {
            if (acc.getAccessory().getType() == DoggyAccessoryTypes.BOWTIE.get()) {
                return;
            }
        }

        Optional<TalentInstance> inst = dog.getTalent(DoggyTalents.RESCUE_DOG);
        if (inst.isPresent() && inst.get().level() >= 5) {
            var dogModel = this.getParentModel();
            dogModel.copyPropertiesTo(this.model);
            this.model.sync(dogModel);

            RenderLayer.renderColoredCutoutModel(this.model, Resources.TALENT_RESCUE, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);
        }

    }
}
