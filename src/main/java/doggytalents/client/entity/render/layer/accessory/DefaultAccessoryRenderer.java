package doggytalents.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

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

        for (AccessoryInstance accessoryInst : dog.getClientSortedAccessories()) {
            if (accessoryInst.usesRenderer(this.getClass())) {
                var dogModel = this.getParentModel();
                boolean isX64Model = dogModel.isX64Model();
                var texture_rl = isX64Model ?
                    accessoryInst.getModelTextureX64(dog)
                    : accessoryInst.getModelTexture(dog);
                boolean isTranslucent = accessoryInst.getAccessory().renderTranslucent();
                if (texture_rl == null) return;
                if (accessoryInst instanceof IColoredObject coloredObject) {
                    float[] color = coloredObject.getColor();
                    if (isTranslucent) 
                        renderTranslucentModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, color[0], color[1], color[2], 1);
                    else 
                        RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, color[0], color[1], color[2]);
                } else {
                    if (isTranslucent)
                        renderTranslucentModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, 1);
                    else
                        RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }

    public static <T extends LivingEntity> void renderTranslucentModel(EntityModel<T> p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, T p_117382_, float p_117383_, float p_117384_, float p_117385_, float opascity) {
        VertexConsumer vertexconsumer = p_117380_.getBuffer(RenderType.entityTranslucent(p_117378_));
        p_117377_.renderToBuffer(p_117379_, vertexconsumer, p_117381_, LivingEntityRenderer.getOverlayCoords(p_117382_, 0.0F), p_117383_, p_117384_, p_117385_, opascity);
    }
}
