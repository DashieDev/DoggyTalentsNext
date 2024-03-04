package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.DoubleDyableAccessory.DoubleDyableAccessoryInstance;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public abstract class DoubleDyableRenderEntry extends Entry {
 
    public void renderAccessory(RenderLayer<Dog, DogModel> layer, 
        PoseStack poseStack, MultiBufferSource buffer, int packedLight, 
        Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, 
        float ageInTicks, float netHeadYaw, float headPitch, AccessoryInstance inst) {
        
        var model = this.getModel();
        var dogModel = layer.getParentModel();
        dogModel.copyPropertiesTo(model);
        model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
        model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.sync(dogModel);
        
        if (!(inst instanceof DoubleDyableAccessoryInstance dInst))
            return;

        float[] bgColor = dInst.getBgColor();
        float[] fgColor = dInst.getFgColor();
        
        doRenderLayer(isTranslucent(), model, getBgResource(dInst), 
            poseStack, buffer, packedLight, dog, bgColor);
        doRenderLayer(isTranslucent(), model, getFgResource(dInst), 
            poseStack, buffer, packedLight, dog, fgColor);
    };

    private static void doRenderLayer(boolean isTranslucent, SyncedAccessoryModel model,
        ResourceLocation resource, PoseStack stack, MultiBufferSource buffer, int light, Dog dog,
            float[] color) {
        if (isTranslucent) {
            DefaultAccessoryRenderer.renderTranslucentModel(model, resource, 
            stack, buffer, light, dog, color[0], color[1], color[2], 1f);
        } else
        AccessoryModelManager.renderColoredCutoutModel(model, resource, 
            stack, buffer, light, dog, color[0], color[1], color[2]);
    }

    @Override
    public boolean isDyable() {
        return true;
    }

    protected abstract ResourceLocation getFgResource(AccessoryInstance inst);
    protected abstract ResourceLocation getBgResource(AccessoryInstance inst);

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return getBgResource(inst);
    }

}
