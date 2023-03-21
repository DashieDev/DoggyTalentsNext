package doggytalents.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class DefaultAccessoryRenderer extends RenderLayer<Dog, DogModel<Dog>> {

    public DefaultAccessoryRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // Only show armour if dog is tamed or visible
        if (!dog.isTame() || dog.isInvisible()) {
            return;
        }

        for (AccessoryInstance accessoryInst : dog.getAccessories()) {
            if (accessoryInst.usesRenderer(this.getClass())) {
                var dogModel = this.getParentModel();
                boolean isX64Model = dogModel.isX64Model();
                var texture_rl = isX64Model ?
                    accessoryInst.getModelTextureX64(dog)
                    : accessoryInst.getModelTexture(dog);
                if (texture_rl == null) return;
                if (accessoryInst instanceof IColoredObject coloredObject) {
                    float[] color = coloredObject.getColor();
                    RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, color[0], color[1], color[2]);
                } else {
                    RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}
