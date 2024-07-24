package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.KitsuneMaskModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.KitsuneMask;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import doggytalents.forge_imitate.event.client.EntityRenderersEvent.RegisterLayerDefinitions;


public class KitsuneMaskRenderEntry extends Entry {
    
    public static final ModelLayerLocation DOG_KITSUNE_MASK = new ModelLayerLocation(Util.getResource("dog_kitsune_mask"), "main");
    
    public KitsuneMaskModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new KitsuneMaskModel(ctx.bakeLayer(DOG_KITSUNE_MASK));
    }   

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_KITSUNE_MASK, KitsuneMaskModel::createBodyLayer);
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }
    
    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.KITSUNE_MASK;
    }

    @Override
    public void renderAccessory(RenderLayer<Dog, DogModel> layer, PoseStack poseStack, MultiBufferSource buffer,
            int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch, AccessoryInstance inst) {
        this.model.wear();
        if (inst instanceof KitsuneMask.Inst kitsuneInst && kitsuneInst.unwear) {
            this.model.unWear();
        }
        super.renderAccessory(layer, poseStack, buffer, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks,
                netHeadYaw, headPitch, inst);
    }
    
}
