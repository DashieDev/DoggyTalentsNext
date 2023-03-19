package doggytalents.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.SmartyGlassesModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.SmartyGlasses;
import doggytalents.common.entity.accessory.Wig;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class WigRenderer extends RenderLayer<Dog, DogModel<Dog>> {
    public WigModel model;

    public WigRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new WigModel(ctx.bakeLayer(ClientSetup.DOG_WIG));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dog.isInvisible()) {
            return;
        }

        for (var accessoryInst : dog.getAccessories()) {
            if (!(accessoryInst.getAccessory() instanceof Wig)) continue;
            var dogModel = this.getParentModel();
            dogModel.copyPropertiesTo(this.model);
            this.model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            this.model.pHead.xRot = dogModel.head.xRot;
            this.model.pHead.yRot = dogModel.head.yRot;
            this.model.pHead.zRot = dogModel.head.zRot;
            
            this.model.wig.xRot = dogModel.realHead.xRot;
            this.model.wig.yRot = dogModel.realHead.yRot;
            this.model.wig.zRot = dogModel.realHead.zRot;

            float pHeadX = dogModel.head.x;
            float pHeadY = dogModel.head.y;
            float pHeadZ = dogModel.head.z;
            this.model.pHead.setPos(pHeadX, pHeadY, pHeadZ);

            RenderLayer.renderColoredCutoutModel(this.model, Resources.WIG, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);
        }

    }
}
