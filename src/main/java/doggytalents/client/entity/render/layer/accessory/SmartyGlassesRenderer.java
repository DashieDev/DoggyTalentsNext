package doggytalents.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.SmartyGlassesModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.SmartyGlasses;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class SmartyGlassesRenderer extends RenderLayer<Dog, DogModel<Dog>> {
    public SmartyGlassesModel model;

    public SmartyGlassesRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new SmartyGlassesModel(ctx.bakeLayer(ClientSetup.DOG_SMARTY_GLASSES));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dog.isInvisible()) {
            return;
        }

        for (var accessoryInst : dog.getAccessories()) {
            if (!(accessoryInst.getAccessory() instanceof SmartyGlasses)) continue;
            var dogModel = this.getParentModel();
            dogModel.copyPropertiesTo(this.model);
            this.model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.glasses.xRot = dogModel.head.xRot;
            this.model.glasses.yRot = dogModel.head.yRot;
            this.model.glasses.zRot = dogModel.head.zRot;
            this.model.realGlasses.zRot = dogModel.realHead.zRot;

            RenderLayer.renderColoredCutoutModel(this.model, Resources.SMARTY_GLASSES, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);
        }

    }
}
