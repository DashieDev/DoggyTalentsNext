package doggytalents.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.config.ConfigHandler.ClientConfig;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

public class IncapacitatedRenderer extends RenderLayer<DogRenderState, DogModel> {

    private DogModel defaultModel;

    public IncapacitatedRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.defaultModel = DogModelRegistry.getDogModelHolder("default").getValue();
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        var dog = renderState.dog;
        if (dog == null || !dog.isTame() || renderState.isInvisible) {
            return;
        }

        if (!dog.isDefeated()) return;

        var skin = renderState.activeSkin;
        if (skin == null) return;
        if (skin.useCustomModel()) {
            var model = skin.getCustomModel().getValue();
            if (!model.incapShouldRender(dog)) {
                return;
            }
        }

        if (!ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_INCAPACITATED_TEXTURE)) return;

        var dogModel = this.getParentModel();
        if (dogModel.useDefaultModelForAccessories()) {
            dogModel.copyPropertiesTo(defaultModel);
            defaultModel.copyFrom(dogModel);
            dogModel = defaultModel;
        }
        var sync_state = dog.getIncapSyncState();
        var texture_rl = pickInjuredTexture(dog, sync_state);
        if (texture_rl != null) {
            var alpha = getInjureOpascity(dog);
            renderTranslucentModel(dogModel, texture_rl, poseStack, submitNodeCollector, packedLight, renderState, 1.0F, 1.0F, 1.0F, alpha);
        }
        //Bandaid layer
        var bandaid_state = sync_state.bandaid;
        Identifier bandaid_texture_rl = null;
        switch (bandaid_state) {
        case FULL:
            bandaid_texture_rl = Resources.BANDAID_OVERLAY_FULL;
            break;
        case HALF:
            bandaid_texture_rl = Resources.BANDAID_OVERLAY_HALF;
            break;
        default:
            break;
        }

        if (bandaid_texture_rl != null)
            renderTranslucentModel(dogModel, bandaid_texture_rl, poseStack, submitNodeCollector, packedLight, renderState, 1.0F, 1.0F, 1.0F, 1);
    }

    private Identifier pickInjuredTexture(doggytalents.common.entity.Dog dog, IncapacitatedSyncState state) {
        if (dog.isDogVariantRenderEffective()) {
            var variant_custom_overlay = dog.dogVariant().customInjuredTexture();
            if (variant_custom_overlay.isPresent())
                return null;
        }

        boolean isLowGraphic =
            ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_INCAP_TXT_LESS_GRAPHIC);
        if (isLowGraphic) {
            return Resources.INCAPACITATED_LESS_GRAPHIC;
        }

        var type = state.type;
        var ret = Resources.INCAPACITATED_BLOOD;
        switch (type) {
        case BLOOD:
            ret = Resources.INCAPACITATED_BLOOD;
            break;
        case BURN:
            ret = Resources.INCAPACITATED_BURN;
            break;
        case POISON:
            ret = Resources.INCAPACITATED_POISON;
            break;
        case DROWN:
            ret = Resources.INCAPACITATED_DROWN;
            break;
        case STARVE:
            ret = null;
            break;
        default:
            break;
        }

        return ret;
    }

    private float getInjureOpascity(doggytalents.common.entity.Dog dog) {
        var default_val = dog.getDefaultInitIncapVal();
        var dog_val = dog.getDogIncapValue();
        if (default_val <= 0f)
            return 0f;
        if (dog_val >= default_val)
            return 1f;
        var ret = ((float)dog_val)/((float)default_val);
        return Mth.clamp(ret, 0, 1);
    }

    public static void renderTranslucentModel(EntityModel<DogRenderState> model, Identifier texture,
            PoseStack poseStack, SubmitNodeCollector collector, int light,
            DogRenderState renderState, float r, float g, float b, float opacity) {
        collector.submitModel(model, renderState, poseStack,
            RenderTypes.entityTranslucent(texture), light, OverlayTexture.NO_OVERLAY,
            ARGB.colorFromFloat(opacity, r, g, b), null);
    }
}
