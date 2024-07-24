package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.DoggyTalents;
import doggytalents.api.registry.TalentInstance;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogBackpackModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import java.util.Optional;

public class PackPuppyRenderer extends RenderLayer<Dog, DogModel> {

    private DogBackpackModel model;

    public PackPuppyRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new DogBackpackModel(ctx.bakeLayer(ClientSetup.DOG_BACKPACK));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dog.isInvisible()) {
            return;
        }

        if (!ConfigHandler.CLIENT.RENDER_CHEST.get())
            return;

        var dogSkin = dog.getClientSkin();
        if (dogSkin.useCustomModel()) {
            var model = dogSkin.getCustomModel().getValue();
            if (!model.armorShouldRender(dog))
                return;
        }

        Optional<PackPuppyTalent> inst = dog.getTalent(DoggyTalents.PACK_PUPPY)
            .map(x -> x.cast(PackPuppyTalent.class));
        if (inst.isPresent() && inst.get().renderChest()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.sync(getParentModel());

            RenderLayer.renderColoredCutoutModel(this.model, Resources.TALENT_CHEST, poseStack, buffer, packedLight, dog, 0xffffffff);
        }

    }
}
