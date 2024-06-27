package doggytalents.client.entity.render.layer.accessory;

import java.util.function.BiFunction;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.config.ConfigHandler.ClientConfig;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.lib.Resources;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class IncapacitatedRenderer extends RenderLayer<Dog, DogModel> {

    private DogModel defaultModel;

    public IncapacitatedRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.defaultModel = DogModelRegistry.getDogModelHolder("default").getValue();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // Only show armour if dog is tamed or visible
        if (!dog.isTame() || dog.isInvisible()) {
            return;
        }

        if (!dog.isDefeated()) return;

        var skin = dog.getClientSkin();
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
            renderTranslucentModel(dogModel, texture_rl, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, alpha);
        }
        //Bandaid layer
        var bandaid_state = sync_state.bandaid;
        ResourceLocation bandaid_texture_rl = null;
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
        renderTranslucentModel(dogModel, bandaid_texture_rl, poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, 1);
    }

    private ResourceLocation pickInjuredTexture(Dog dog, IncapacitatedSyncState state) {
        var variant_custom_overlay = dog.dogVariant().customInjuredTexture();
        if (variant_custom_overlay.isPresent())
            return null;
        
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

    private float getInjureOpascity(Dog dog) {
        var default_val = dog.getDefaultInitIncapVal();
        var dog_val = dog.getDogIncapValue();
        if (default_val <= 0f)
            return 0f;
        if (dog_val >= default_val)
            return 1f;
        var ret = ((float)dog_val)/((float)default_val);
        return Mth.clamp(ret, 0, 1);
    }

    public static <T extends LivingEntity> void renderTranslucentModel(EntityModel<T> p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, T p_117382_, float p_117383_, float p_117384_, float p_117385_, float opascity) {
        VertexConsumer vertexconsumer = p_117380_.getBuffer(RenderType.entityTranslucent(p_117378_));
        p_117377_.renderToBuffer(p_117379_, vertexconsumer, p_117381_, LivingEntityRenderer.getOverlayCoords(p_117382_, 0.0F), p_117383_, p_117384_, p_117385_, opascity);
    }
}
