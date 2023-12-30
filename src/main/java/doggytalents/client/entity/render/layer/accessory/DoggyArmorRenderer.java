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
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DoggyArmorMapping;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.EntityModel;
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
import net.minecraft.resources.ResourceLocation;
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

    private final TextureAtlas dogArmorTrimAtlas;

    public DoggyArmorRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.newModel = new DogArmorModel(ctx.bakeLayer(ClientSetup.DOG_ARMOR));
        this.legacyModel = new DogArmorModel(ctx.bakeLayer(ClientSetup.DOG_ARMOR_LEGACY));
        this.model = newModel;

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

        renderArmorCutout(this.model, DoggyArmorMapping.getMappedResource(itemStack.getItem(), dog, itemStack), stack, buffer, light, dog, 1.0F, 1.0F, 1.0F);
        
        var trim = ArmorTrim.getTrim(dog.level().registryAccess(), itemStack);
        if (trim.isPresent()) {
            renderTrim(armor.getMaterial(), stack, buffer, light, trim.get(), this.model);
        }

        if (itemStack.hasFoil())
            renderGlint(stack, buffer, light, this.model);
    }

    private void renderArmorCutout(DogArmorModel model, ResourceLocation textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Dog entityIn, float red, float green, float blue) {
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.armorCutoutNoCull(textureLocationIn));
        model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    private void renderTrim(ArmorMaterial material, PoseStack stack, MultiBufferSource buffer, int light, ArmorTrim trim, DogArmorModel model) {
        var textureatlassprite = this.dogArmorTrimAtlas.getSprite(trim.outerTexture(material));
        var vertexconsumer = textureatlassprite.wrap(buffer.getBuffer(Sheets.armorTrimsSheet()));
        model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderGlint(PoseStack stack, MultiBufferSource buffer, int light, DogArmorModel model) {
        model.renderToBuffer(stack, buffer.getBuffer(RenderType.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
  
}
