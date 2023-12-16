package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.CeramonyGarbModel;
import doggytalents.client.entity.model.LabCoatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class CeramonyGarbRenderEntry extends Entry{
    public static final ModelLayerLocation CERA_GARB = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ceramonial_garb"), "main");
    public CeramonyGarbModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new CeramonyGarbModel(ctx.bakeLayer(CERA_GARB));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CERA_GARB, CeramonyGarbModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.CERA_GARB;
    }
    @Override
    public boolean isDyable() {
        return true;
    }

    @Override
    public void renderAccessory(RenderLayer<Dog, DogModel> layer, PoseStack poseStack, MultiBufferSource buffer,
            int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch, AccessoryInstance inst) {
        
        var model = this.getModel();
            var dogModel = layer.getParentModel();
            dogModel.copyPropertiesTo(model);
            model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
            model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.sync(dogModel);
            
            float[] color = new float[]{1.0f, 1.0f, 1.0f};
            if (inst instanceof IColoredObject coloredObject)
                color = coloredObject.getColor();
            
            DefaultAccessoryRenderer.renderTranslucentModel(model, Resources.CERA_GARB, 
                poseStack, buffer, packedLight, dog, 1f, 1f, 1f, 1f);
            DefaultAccessoryRenderer.renderTranslucentModel(model, Resources.CERA_GARB_OVERLAY, 
                poseStack, buffer, packedLight, dog, color[0], color[1], color[2], 1f);
    }

    
}
