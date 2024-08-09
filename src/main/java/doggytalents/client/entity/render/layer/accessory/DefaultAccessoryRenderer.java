package doggytalents.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.Accessory.AccessoryRenderType;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogFrontLegsSeperate;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;

public class DefaultAccessoryRenderer extends RenderLayer<Dog, DogModel> {

    private DogModel defaultModel;
    private DogFrontLegsSeperate hindLegDiffTextModel;

    public DefaultAccessoryRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);

        this.defaultModel = DogModelRegistry.getDogModelHolder("default").getValue();
        this.hindLegDiffTextModel = new DogFrontLegsSeperate(ctx.bakeLayer(ClientSetup.DOG_FRONT_LEGS_SEPERATE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // Only show armour if dog is tamed or visible
        if (!dog.isTame() || dog.isInvisible()) {
            return;
        }

        for (AccessoryInstance accessoryInst : dog.getClientSortedAccessories()) {
            var skin = dog.getClientSkin();
            if (skin.useCustomModel()) {
                var model = skin.getCustomModel().getValue();
                if (!model.acessoryShouldRender(dog, accessoryInst)) {
                    continue;
                }
            }
            var accessory = accessoryInst.getAccessory();
            if (!accessory.shouldRender())
                continue;
            if (!isOverlay(accessory)) continue;               
            if (accessory.hasHindLegDiffTex()) {
                this.renderHindLegDifferentAccessory(poseStack, buffer, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, accessoryInst);
            } else {
                this.renderNormalAccessory(poseStack, buffer, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, accessoryInst);
            }
        }
    }

    private void renderNormalAccessory(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, AccessoryInstance accessoryInst) {
        var parentModel = this.getParentModel();

        DogModel dogModel;
        if (parentModel.useDefaultModelForAccessories()) {
            dogModel = this.defaultModel;
            parentModel.copyPropertiesTo(dogModel);
            dogModel.copyFrom(parentModel);
        } else {
            dogModel = parentModel;
        }
        var texture_rl = accessoryInst.getModelTexture(dog);
        boolean isTranslucent = isAcceossryRenderTranslucent(accessoryInst.getAccessory());
        if (texture_rl == null) return;
        boolean tailVisible0 = dogModel.tail.visible;
        if (dog.getClientSkin().useCustomModel())
            dogModel.tail.visible = false;
        if (accessoryInst instanceof IColoredObject coloredObject) {
            float[] color = coloredObject.getColor();
            if (isTranslucent) 
                renderTranslucentModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, color[0], color[1], color[2], 1);
            else 
                RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, FastColor.ARGB32.colorFromFloat(1, color[0], color[1], color[2]));
        } else {
            if (isTranslucent)
                renderTranslucentModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, 1);
            else
                RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, 0xffffffff);
        }
        dogModel.tail.visible = tailVisible0;
    }

    private void renderHindLegDifferentAccessory(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, AccessoryInstance accessoryInst) {
        var parentModel = this.getParentModel();

        DogModel dogModel;
        if (parentModel.useDefaultModelForAccessories()) {
            dogModel = this.defaultModel;
        } else {
            dogModel = parentModel;
        }
        if (dogModel != parentModel) {
            this.getParentModel().copyPropertiesTo(dogModel);
            dogModel.copyFrom(parentModel);
        }
        var texture_rl = accessoryInst.getModelTexture(dog);
        boolean isTranslucent = isAcceossryRenderTranslucent(accessoryInst.getAccessory());
        if (texture_rl == null) return;
        boolean tailVisible0 = dogModel.tail.visible;
        if (dog.getClientSkin().useCustomModel())
            dogModel.tail.visible = false;

        //Render the parent model overlay without the front legs.
        boolean rightFrontLegVisible0 = dogModel.legFrontRight.visible;
        boolean leftFrontLegVisible0 = dogModel.legFrontLeft.visible; 
        dogModel.legFrontLeft.visible = false;
        dogModel.legFrontRight.visible = false;
        if (accessoryInst instanceof IColoredObject coloredObject) {
            float[] color = coloredObject.getColor();
            if (isTranslucent) 
                renderTranslucentModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, color[0], color[1], color[2], 1);
            else 
                RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, FastColor.ARGB32.colorFromFloat(1, color[0], color[1], color[2]));
        } else {
            if (isTranslucent)
                renderTranslucentModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, 1);
            else
                RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, 0xffffffff);
        }
        dogModel.tail.visible = tailVisible0;
        dogModel.legFrontRight.visible = rightFrontLegVisible0;
        dogModel.legFrontLeft.visible = leftFrontLegVisible0;

        //Render the front leg overlay afterwards
        dogModel.copyPropertiesTo(hindLegDiffTextModel);
        hindLegDiffTextModel.sync(dogModel);
        if (accessoryInst instanceof IColoredObject coloredObject) {
            float[] color = coloredObject.getColor();
            if (isTranslucent) 
                renderTranslucentModel(hindLegDiffTextModel, texture_rl, poseStack, buffer, packedLight, dog, color[0], color[1], color[2], 1);
            else 
                RenderLayer.renderColoredCutoutModel(hindLegDiffTextModel, texture_rl, poseStack, buffer, packedLight, dog, FastColor.ARGB32.colorFromFloat(1, color[0], color[1], color[2]));
        } else {
            if (isTranslucent)
                renderTranslucentModel(hindLegDiffTextModel, texture_rl, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, 1);
            else
                RenderLayer.renderColoredCutoutModel(hindLegDiffTextModel, texture_rl, poseStack, buffer, packedLight, dog, 0xffffffff);
        }
    }

    public boolean isOverlay(Accessory accessory) {
        var type = accessory.getAccessoryRenderType();
        return
            type == AccessoryRenderType.OVERLAY
            || type == AccessoryRenderType.OVERLAY_AND_MODEL;
    }

    public boolean isAcceossryRenderTranslucent(Accessory accessory) {
        if (ConfigHandler.CLIENT.TRANSLUCENT_ALL_OVERLAY.get()) {
            return true;
        }
        return accessory.renderTranslucent();
    }

    public static <T extends LivingEntity> void renderTranslucentModel(EntityModel<T> p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, T p_117382_, float p_117383_, float p_117384_, float p_117385_, float opascity) {
        VertexConsumer vertexconsumer = p_117380_.getBuffer(RenderType.entityTranslucent(p_117378_));
        p_117377_.renderToBuffer(p_117379_, vertexconsumer, p_117381_, LivingEntityRenderer.getOverlayCoords(p_117382_, 0.0F), FastColor.ARGB32.colorFromFloat(opascity, p_117383_, p_117384_, p_117385_));
    }
}
