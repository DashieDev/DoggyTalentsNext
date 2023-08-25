package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BeastarsUniformFemaleAugmentModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class BeastarsUniformFemaleEntry implements AccessoryModelManager.Entry {
    
    public static final ModelLayerLocation BEASTARS_UNIFORM_F_AUGMENT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "beastars_uniform_f_augment"), "main");

    private BeastarsUniformFemaleAugmentModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new BeastarsUniformFemaleAugmentModel(ctx.bakeLayer(BEASTARS_UNIFORM_F_AUGMENT));
    }

    @Override
    public ListModel<Dog> getModel() {
        return this.model;
    }

    @Override
    public void renderAccessory(RenderLayer<Dog, DogModel<Dog>> layer, PoseStack poseStack, MultiBufferSource buffer,
        int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
        float netHeadYaw, float headPitch, AccessoryInstance inst) {
        var dogModel = layer.getParentModel();
        dogModel.copyPropertiesTo(this.model);
        this.model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
        this.model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.model.pMane.xRot = dogModel.mane.xRot;
        this.model.pMane.yRot = dogModel.mane.yRot;
        this.model.pMane.zRot = dogModel.mane.zRot;

        this.model.pMane.x = dogModel.mane.x;
        this.model.pMane.y = dogModel.mane.y;
        this.model.pMane.z = dogModel.mane.z;

        this.model.pBody.xRot = dogModel.body.xRot;
        this.model.pBody.yRot = dogModel.body.yRot;
        this.model.pBody.zRot = dogModel.body.zRot;

        this.model.pBody.x = dogModel.body.x;
        this.model.pBody.y = dogModel.body.y;
        this.model.pBody.z = dogModel.body.z;

        this.model.pLFLeg.xRot = dogModel.legFrontLeft.xRot;
        this.model.pLFLeg.yRot = dogModel.legFrontLeft.yRot;
        this.model.pLFLeg.zRot = dogModel.legFrontLeft.zRot;

        this.model.pLFLeg.x = dogModel.legFrontLeft.x;
        this.model.pLFLeg.y = dogModel.legFrontLeft.y;
        this.model.pLFLeg.z = dogModel.legFrontLeft.z;

        this.model.pRFLeg.xRot = dogModel.legFrontRight.xRot;
        this.model.pRFLeg.yRot = dogModel.legFrontRight.yRot;
        this.model.pRFLeg.zRot = dogModel.legFrontRight.zRot;

        this.model.pRFLeg.x = dogModel.legFrontRight.x;
        this.model.pRFLeg.y = dogModel.legFrontRight.y;
        this.model.pRFLeg.z = dogModel.legFrontRight.z;

        this.model.root.copyFrom(dogModel.root);

        if (inst instanceof IColoredObject coloredObject) {
            float[] color = coloredObject.getColor();
            RenderLayer.renderColoredCutoutModel(this.model, Resources.BEASTARS_UNIFORM_FEMALE, poseStack, buffer, packedLight, dog, color[0], color[1], color[2]);
        } else
        RenderLayer.renderColoredCutoutModel(this.model, Resources.BEASTARS_UNIFORM_FEMALE, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BEASTARS_UNIFORM_F_AUGMENT, BeastarsUniformFemaleAugmentModel::createLayer);
    }


}
