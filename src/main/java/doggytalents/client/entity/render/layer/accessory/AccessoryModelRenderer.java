package doggytalents.client.entity.render.layer.accessory;


import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.BowTieModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.BowTie;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class AccessoryModelRenderer extends RenderLayer<Dog, DogModel>  {

    public AccessoryModelRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        AccessoryModelManager.resolve(ctx);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        //TODO Temporary disable baby dog rendering accesory model
        if (dog.isInvisible()) {
            return;
        }

        for (var accessoryInst : dog.getAccessories()) {
            var accessory = accessoryInst.getAccessory();
            if (!(accessory instanceof IAccessoryHasModel)) continue;
            var hasModelAccessory = (IAccessoryHasModel) accessory;
            hasModelAccessory.getRenderEntry().renderAccessory(
                this, poseStack, buffer, packedLight, dog, limbSwing, 
                limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, accessoryInst);
        }

    }
    
}

