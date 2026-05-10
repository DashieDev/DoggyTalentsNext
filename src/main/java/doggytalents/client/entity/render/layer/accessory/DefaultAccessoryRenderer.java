package doggytalents.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.Accessory.AccessoryRenderType;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogFrontLegsSeperate;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.config.ConfigHandler;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class DefaultAccessoryRenderer extends RenderLayer<DogRenderState, DogModel> {

    private DogModel defaultModel;
    private DogFrontLegsSeperate hindLegDiffTextModel;

    public DefaultAccessoryRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);

        this.defaultModel = DogModelRegistry.getDogModelHolder("default").getValue();
        this.hindLegDiffTextModel = new DogFrontLegsSeperate(ctx.bakeLayer(ClientSetup.DOG_FRONT_LEGS_SEPERATE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        var dog = renderState.dog;
        if (dog == null || !dog.isTame() || renderState.isInvisible) {
            return;
        }

        var activeSkin = renderState.activeSkin;
        for (AccessoryInstance accessoryInst : dog.getClientSortedAccessories()) {
            var skin = activeSkin;
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
                this.renderHindLegDifferentAccessory(poseStack, submitNodeCollector, packedLight, renderState, accessoryInst);
            } else {
                this.renderNormalAccessory(poseStack, submitNodeCollector, packedLight, renderState, accessoryInst);
            }
        }
    }

    private void renderNormalAccessory(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, DogRenderState renderState, AccessoryInstance accessoryInst) {
        var dog = renderState.dog;
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
        if (renderState.activeSkin != null && renderState.activeSkin.useCustomModel())
            dogModel.tail.visible = false;
        if (accessoryInst instanceof IColoredObject coloredObject) {
            float[] color = coloredObject.getColor();
            if (isTranslucent)
                renderTranslucentModel(dogModel, texture_rl, poseStack, collector, packedLight, renderState, color[0], color[1], color[2], 1);
            else
                RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, collector, packedLight, renderState, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1, color[0], color[1], color[2]));
        } else {
            if (isTranslucent)
                renderTranslucentModel(dogModel, texture_rl, poseStack, collector, packedLight, renderState, 1.0F, 1.0F, 1.0F, 1);
            else
                RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, collector, packedLight, renderState, OverlayTexture.NO_OVERLAY, 0xffffffff);
        }
        dogModel.tail.visible = tailVisible0;
    }

    private void renderHindLegDifferentAccessory(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, DogRenderState renderState, AccessoryInstance accessoryInst) {
        var dog = renderState.dog;
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
        if (renderState.activeSkin != null && renderState.activeSkin.useCustomModel())
            dogModel.tail.visible = false;

        //Render the parent model overlay without the front legs.
        boolean rightFrontLegVisible0 = dogModel.legFrontRight.visible;
        boolean leftFrontLegVisible0 = dogModel.legFrontLeft.visible;
        dogModel.legFrontLeft.visible = false;
        dogModel.legFrontRight.visible = false;
        if (accessoryInst instanceof IColoredObject coloredObject) {
            float[] color = coloredObject.getColor();
            if (isTranslucent)
                renderTranslucentModel(dogModel, texture_rl, poseStack, collector, packedLight, renderState, color[0], color[1], color[2], 1);
            else
                RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, collector, packedLight, renderState, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1, color[0], color[1], color[2]));
        } else {
            if (isTranslucent)
                renderTranslucentModel(dogModel, texture_rl, poseStack, collector, packedLight, renderState, 1.0F, 1.0F, 1.0F, 1);
            else
                RenderLayer.renderColoredCutoutModel(dogModel, texture_rl, poseStack, collector, packedLight, renderState, OverlayTexture.NO_OVERLAY, 0xffffffff);
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
                renderTranslucentModel(hindLegDiffTextModel, texture_rl, poseStack, collector, packedLight, renderState, color[0], color[1], color[2], 1);
            else
                RenderLayer.renderColoredCutoutModel(hindLegDiffTextModel, texture_rl, poseStack, collector, packedLight, renderState, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1, color[0], color[1], color[2]));
        } else {
            if (isTranslucent)
                renderTranslucentModel(hindLegDiffTextModel, texture_rl, poseStack, collector, packedLight, renderState, 1.0F, 1.0F, 1.0F, 1);
            else
                RenderLayer.renderColoredCutoutModel(hindLegDiffTextModel, texture_rl, poseStack, collector, packedLight, renderState, OverlayTexture.NO_OVERLAY, 0xffffffff);
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

    public static void renderTranslucentModel(EntityModel<DogRenderState> model, Identifier texture,
            PoseStack poseStack, SubmitNodeCollector collector, int light,
            DogRenderState renderState, float r, float g, float b, float opacity) {
        collector.submitModel(model, renderState, poseStack,
            RenderTypes.entityTranslucent(texture), light, OverlayTexture.NO_OVERLAY,
            ARGB.colorFromFloat(opacity, r, g, b), null);
    }
}
