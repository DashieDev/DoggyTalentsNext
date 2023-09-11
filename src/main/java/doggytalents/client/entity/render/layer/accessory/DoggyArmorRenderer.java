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
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.HelmetInteractHandler;
import doggytalents.common.entity.accessory.ArmourAccessory;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class DoggyArmorRenderer extends RenderLayer<Dog, DogModel> {

    private DogArmorModel model;

    public DoggyArmorRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new DogArmorModel(ctx.bakeLayer(ClientSetup.DOG_ARMOR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // Only show armour if dog is tamed or visible
        if (!dog.isTame() || dog.isInvisible()) {
            return;
        }

        if (!ConfigHandler.CLIENT.RENDER_ARMOR.get())
            return;

        Optional<TalentInstance> inst = dog.getTalent(DoggyTalents.DOGGY_ARMOR);
        if (!inst.isPresent()) return;

        var parentModel = this.getParentModel();
        parentModel.copyPropertiesTo(this.model);
        this.model.sync(parentModel);

        if (dog.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem) {
            var itemStack = dog.getItemBySlot(EquipmentSlot.HEAD);
            this.model.setHelmet();
            this.renderArmorCutout(this.model, HelmetInteractHandler.getMappedResource(itemStack.getItem()).get().getModelTexture(), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, itemStack.isEnchanted());
        }

        if (dog.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem) {
            var itemStack = dog.getItemBySlot(EquipmentSlot.CHEST);
            this.model.setChestplate();
            this.renderArmorCutout(this.model, HelmetInteractHandler.getMappedResource(itemStack.getItem()).get().getModelTexture(), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, itemStack.isEnchanted());
        }

        if (dog.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem) {
            var itemStack = dog.getItemBySlot(EquipmentSlot.LEGS);
            this.model.setLeggings();
            this.renderArmorCutout(this.model, HelmetInteractHandler.getMappedResource(itemStack.getItem()).get().getModelTexture(), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, itemStack.isEnchanted());
        }

        if (dog.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem) {
            var itemStack = dog.getItemBySlot(EquipmentSlot.FEET);
            this.model.setBoot();
            this.renderArmorCutout(this.model, HelmetInteractHandler.getMappedResource(itemStack.getItem()).get().getModelTexture(), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, itemStack.isEnchanted());
        }
    }

    public static <T extends LivingEntity> void renderArmorCutout(EntityModel<T> modelIn, ResourceLocation textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float red, float green, float blue, boolean enchanted) {
        VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(textureLocationIn), false, enchanted);
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }
}
