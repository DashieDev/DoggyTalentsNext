package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BeastarsUniformMaleAugmentModel;
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

public class BeastarsUniformMaleEntry implements AccessoryModelManager.Entry  {
    public static final ModelLayerLocation BEASTARS_UNIFORM_M_AUGMENT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "beastars_tie"), "main");

    private BeastarsUniformMaleAugmentModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new BeastarsUniformMaleAugmentModel(ctx.bakeLayer(BEASTARS_UNIFORM_M_AUGMENT));
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
            
        this.model.root.copyFrom(dogModel.root);

        if (inst instanceof IColoredObject coloredObject) {
            float[] color = coloredObject.getColor();
            RenderLayer.renderColoredCutoutModel(this.model, Resources.BEASTARS_UNIFORM_MALE, poseStack, buffer, packedLight, dog, color[0], color[1], color[2]);
        } else
        RenderLayer.renderColoredCutoutModel(this.model, Resources.BEASTARS_UNIFORM_MALE, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BEASTARS_UNIFORM_M_AUGMENT, BeastarsUniformMaleAugmentModel::createLayer);
    }
}
