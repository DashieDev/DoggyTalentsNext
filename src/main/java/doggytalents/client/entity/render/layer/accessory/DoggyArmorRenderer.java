package doggytalents.client.entity.render.layer.accessory;

import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import doggytalents.DoggyTalents;
import doggytalents.api.registry.TalentInstance;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogArmorModel;
import doggytalents.client.entity.model.SyncedRenderFunctionWithHeadModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DoggyArmorMapping;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.client.entity.render.layer.DogArmorHelmetAltModel;
import doggytalents.common.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class DoggyArmorRenderer extends RenderLayer<DogRenderState, DogModel> {

    private DogArmorModel model;
    private DogArmorModel newModel;
    private DogArmorModel legacyModel;
    private SyncedRenderFunctionWithHeadModel alternativeModel;
    private boolean initAltModel = false;

    private DogArmorHelmetAltModel helmetAltModel;

    public DoggyArmorRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.newModel = new DogArmorModel(ctx.bakeLayer(ClientSetup.DOG_ARMOR));
        this.legacyModel = new DogArmorModel(ctx.bakeLayer(ClientSetup.DOG_ARMOR_LEGACY));
        this.alternativeModel = new SyncedRenderFunctionWithHeadModel(ctx.bakeLayer(ClientSetup.DOG_SYNCED_FUNCTION_WITH_HEAD));

        this.model = newModel;

        this.helmetAltModel = new DogArmorHelmetAltModel(ctx);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        var dog = renderState.dog;
        if (dog == null || !dog.isTame() || renderState.isInvisible) {
            return;
        }

        if (!ConfigHandler.CLIENT.RENDER_ARMOR.get() || dog.hideArmor())
            return;

        var skin = renderState.activeSkin;
        if (skin == null) return;
        if (skin.useCustomModel()) {
            var model = skin.getCustomModel().getValue();
            if (!model.armorShouldRender(dog))
                return;
        }

        Optional<TalentInstance> inst = dog.getTalent(DoggyTalents.DOGGY_ARMOR);
        if (!inst.isPresent()) return;

        if (ConfigHandler.CLIENT.USE_LEGACY_DOG_ARMOR_RENDER.get())
            this.model = this.legacyModel;
        else
            this.model = this.newModel;

        var parentModel = this.getParentModel();
        parentModel.copyPropertiesTo(this.model);
        this.model.sync(parentModel);
        initAltModel = false;

        // Get MultiBufferSource from Minecraft for VertexConsumer-based rendering
        var buffer = Minecraft.getInstance().renderBuffers().bufferSource();

        checkAndRenderSlot(dog, EquipmentSlot.HEAD, poseStack, buffer, packedLight);
        checkAndRenderSlot(dog, EquipmentSlot.CHEST, poseStack, buffer, packedLight);
        checkAndRenderSlot(dog, EquipmentSlot.LEGS, poseStack, buffer, packedLight);
        checkAndRenderSlot(dog, EquipmentSlot.FEET, poseStack, buffer, packedLight);
    }

    private void checkAndRenderSlot(doggytalents.common.entity.Dog dog, EquipmentSlot slot, PoseStack stack, MultiBufferSource buffer, int light) {
        var itemStack = dog.getItemBySlot(slot);

        // ArmorItem no longer exists in 26.1.2; use DataComponents.EQUIPPABLE instead
        var equippable = itemStack.getOrDefault(DataComponents.EQUIPPABLE, null);
        if (equippable == null) return;

        switch (slot) {
        case HEAD:
            this.model.setHelmet();
            break;
        case CHEST:
            this.model.setChestplate();
            break;
        case LEGS:
            this.model.setLeggings();
            break;
        case FEET:
            this.model.setBoot();
            break;
        default:
            return;
        }

        var altModel = getAlternativeArmorModel(dog, slot, stack, itemStack);
        if (altModel.isPresent()) {
            if (!initAltModel) {
                initAltModel = true;
                var parentModel = this.getParentModel();
                parentModel.copyPropertiesTo(this.alternativeModel);
                this.alternativeModel.sync(parentModel);
            }
            startRenderAlternativeModelFromRoot(altModel.get(), dog, stack, buffer, light, itemStack);
            return;
        }

        renderArmorCutout(this.model, DoggyArmorMapping.getMappedResource(itemStack.getItem(), dog, itemStack), stack, buffer, light);

        if (itemStack.hasFoil())
            renderGlint(stack, buffer, light, this.model);
    }

    private Optional<Model> getAlternativeArmorModel(doggytalents.common.entity.Dog dog, EquipmentSlot slot, PoseStack stack, ItemStack itemStack) {
        if (slot == EquipmentSlot.HEAD && ConfigHandler.CLIENT.USE_THIRD_PARTY_PLAYER_HELMET_MODEL.get()) {
            var dummy = this.helmetAltModel.getDummy();
            if (dummy != null) {
                // ClientHooks.getArmorModel no longer exists in 26.1.2; skip third-party model lookup
            }
        }
        if (slot == EquipmentSlot.HEAD && ConfigHandler.CLIENT.USE_PLAYER_HELMET_MODEL_BY_DEFAULT.get()) {
            var m = this.helmetAltModel.getModel();
            if (m != null) return Optional.of(m);
        }
        return Optional.empty();
    }

    private void startRenderAlternativeModelFromRoot(Model model, doggytalents.common.entity.Dog dog, PoseStack stack, MultiBufferSource buffer, int light, ItemStack itemStack) {
        this.alternativeModel.startRenderFromRoot(stack, stack1 -> {
            stack1.pushPose();
            stack1.scale(0.6f, 0.6f, 0.6f);
            stack1.translate(0, 0.15f, 0.07);
            renderAlternativeModel(model, stack1, buffer, light, itemStack);

            if (itemStack.hasFoil())
                renderGlint(stack, buffer, light, model);

            stack1.popPose();
        });
    }

    private void renderAlternativeModel(Model model, PoseStack stack, MultiBufferSource buffer, int light, ItemStack itemStack) {
        var texLoc = DoggyArmorMapping.getMappedResource(itemStack.getItem(), null, itemStack);
        VertexConsumer ivertexbuilder = buffer.getBuffer(RenderTypes.armorCutoutNoCull(texLoc));
        model.renderToBuffer(stack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }

    private void renderArmorCutout(DogArmorModel model, Identifier textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderTypes.armorCutoutNoCull(textureLocationIn));
        model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1, 1.0F, 1.0F, 1.0F));
    }

    private void renderGlint(PoseStack stack, MultiBufferSource buffer, int light, Model model) {
        model.renderToBuffer(stack, buffer.getBuffer(RenderTypes.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }

}
