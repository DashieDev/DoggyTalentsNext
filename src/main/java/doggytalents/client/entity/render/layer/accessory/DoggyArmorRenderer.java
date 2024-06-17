package doggytalents.client.entity.render.layer.accessory;

import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.TalentInstance;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogArmorModel;
import doggytalents.client.entity.model.SyncedRenderFunctionWithHeadModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DoggyArmorMapping;
import doggytalents.client.entity.render.layer.DogArmorHelmetAltModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.ItemUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;

public class DoggyArmorRenderer extends RenderLayer<Dog, DogModel> {

    private DogArmorModel model;
    private DogArmorModel newModel;
    private DogArmorModel legacyModel;
    private SyncedRenderFunctionWithHeadModel alternativeModel;
    private boolean initAltModel = false;    

    private DogArmorHelmetAltModel helmetAltModel;
    private final TextureAtlas dogArmorTrimAtlas;

    public DoggyArmorRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.newModel = new DogArmorModel(ctx.bakeLayer(ClientSetup.DOG_ARMOR));
        this.legacyModel = new DogArmorModel(ctx.bakeLayer(ClientSetup.DOG_ARMOR_LEGACY));
        this.alternativeModel = new SyncedRenderFunctionWithHeadModel(ctx.bakeLayer(ClientSetup.DOG_SYNCED_FUNCTION_WITH_HEAD));
        
        this.model = newModel;

        this.helmetAltModel = new DogArmorHelmetAltModel(ctx);
        this.dogArmorTrimAtlas = ctx.getModelManager().getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!dog.isTame() || dog.isInvisible()) {
            return;
        }

        if (!ConfigHandler.CLIENT.RENDER_ARMOR.get() || dog.hideArmor())
            return;

        var skin = dog.getClientSkin();
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

        checkAndRenderSlot(dog, EquipmentSlot.HEAD, poseStack, buffer, packedLight);
        checkAndRenderSlot(dog, EquipmentSlot.CHEST, poseStack, buffer, packedLight);
        checkAndRenderSlot(dog, EquipmentSlot.LEGS, poseStack, buffer, packedLight);
        checkAndRenderSlot(dog, EquipmentSlot.FEET, poseStack, buffer, packedLight);
    }

    private void checkAndRenderSlot(Dog dog, EquipmentSlot slot, PoseStack stack, MultiBufferSource buffer, int light) {
        var itemStack = dog.getItemBySlot(slot);
        var item = itemStack.getItem();
        if (!(item instanceof ArmorItem armor))
            return;
        
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
            startRenderAlternativeModelFromRoot(altModel.get(), dog, stack, buffer, light, itemStack, armor);
            return;
        } 

        renderArmorCutout(this.model, DoggyArmorMapping.getMappedResource(itemStack.getItem(), dog, itemStack), stack, buffer, light, dog, 1.0F, 1.0F, 1.0F);
        
        var trim = ItemUtil.getTrim(itemStack);
        if (trim.isPresent()) {
            renderTrim(armor.getMaterial(), stack, buffer, light, trim.get(), this.model);
        }

        if (itemStack.hasFoil())
            renderGlint(stack, buffer, light, this.model);
    }

    private Optional<Model> getAlternativeArmorModel(Dog dog, EquipmentSlot slot, PoseStack stack, ItemStack itemStack) {
        if (slot == EquipmentSlot.HEAD && ConfigHandler.CLIENT.USE_THIRD_PARTY_PLAYER_HELMET_MODEL.get()) {
            var dummy = this.helmetAltModel.getDummy();
            var customHeadModel = net.neoforged.neoforge.client.ClientHooks
                .getArmorModel(dog, itemStack, slot, dummy);
            if (customHeadModel != dummy && customHeadModel != null)
                return Optional.of(customHeadModel);
        }
        if (slot == EquipmentSlot.HEAD && ConfigHandler.CLIENT.USE_PLAYER_HELMET_MODEL_BY_DEFAULT.get()) {
            return Optional.of(this.helmetAltModel.getModel());
        }
        return Optional.empty();
    }

    private void startRenderAlternativeModelFromRoot(Model model, Dog dog, PoseStack stack, MultiBufferSource buffer, int light, ItemStack itemStack, ArmorItem armor) {
        this.alternativeModel.startRenderFromRoot(stack, stack1 -> {
            stack1.pushPose();
            stack1.scale(0.6f, 0.6f, 0.6f);
            stack1.translate(0, 0.15f, 0.07);
            renderAlternativeModel(model, dog, stack1, buffer, light, itemStack);

            var trim = ItemUtil.getTrim(itemStack);
            if (trim.isPresent()) {
                renderTrim(armor.getMaterial(), stack, buffer, light, trim.get(), model);
            }

            if (itemStack.hasFoil())
                renderGlint(stack, buffer, light, model);

            stack1.popPose();
        });
    }

    private void renderAlternativeModel(Model model, Dog dog, PoseStack stack, MultiBufferSource buffer, int light, ItemStack itemStack) {
        var texLoc = DoggyArmorMapping.getMappedResource(itemStack.getItem(), dog, itemStack);
        VertexConsumer ivertexbuilder = buffer.getBuffer(RenderType.armorCutoutNoCull(texLoc));
        model.renderToBuffer(stack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }

    private void renderArmorCutout(DogArmorModel model, ResourceLocation textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Dog entityIn, float red, float green, float blue) {
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.armorCutoutNoCull(textureLocationIn));
        model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1, red, green, blue));
    }

    private void renderTrim(Holder<ArmorMaterial> material, PoseStack stack, MultiBufferSource buffer, int light, ArmorTrim trim, Model model) {
        var textureatlassprite = this.dogArmorTrimAtlas.getSprite(trim.outerTexture(material));
        var vertexconsumer = textureatlassprite.wrap(buffer.getBuffer(Sheets.armorTrimsSheet(trim.pattern().value().decal())));
        model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }

    private void renderGlint(PoseStack stack, MultiBufferSource buffer, int light, Model model) {
        model.renderToBuffer(stack, buffer.getBuffer(RenderType.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }
  
}
