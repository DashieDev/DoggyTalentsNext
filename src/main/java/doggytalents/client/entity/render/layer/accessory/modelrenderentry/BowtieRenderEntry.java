package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.BowTieModel;
import doggytalents.client.entity.model.SmartyGlassesModel;
import doggytalents.client.entity.model.WigModel;
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

public class BowtieRenderEntry implements AccessoryModelManager.Entry {

    public static final ModelLayerLocation DOG_BOWTIE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_bowtie"), "main");

    BowTieModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new BowTieModel(ctx.bakeLayer(DOG_BOWTIE));
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

        float pManeX = dogModel.mane.x;
        float pManeY = dogModel.mane.y;
        float pManeZ = dogModel.mane.z;
        this.model.pMane.setPos(pManeX, pManeY, pManeZ);

        if (inst instanceof IColoredObject coloredObject) {
            float[] color = coloredObject.getColor();
            RenderLayer.renderColoredCutoutModel(this.model, Resources.DYABLE_BOW_TIE, poseStack, buffer, packedLight, dog, color[0], color[1], color[2]);
        } else
        RenderLayer.renderColoredCutoutModel(this.model, Resources.BOW_TIE, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_BOWTIE, BowTieModel::createBowtieLayer);
    }
    
}
