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

public class ArmorAccessoryRenderer extends RenderLayer<Dog, DogModel<Dog>> {

    private DogArmorModel model;

    public ArmorAccessoryRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new DogArmorModel(ctx.bakeLayer(ClientSetup.DOG_ARMOR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // Only show armour if dog is tamed or visible
        if (!dog.isTame() || dog.isInvisible()) {
            return;
        }

        Optional<TalentInstance> inst = dog.getTalent(DoggyTalents.DOGGY_ARMOR);
        if (!inst.isPresent()) return;

        if (this.model.modelNeedRefreshBeforeCurrentRender(dog)) {
            this.model.resetAllPose();
        }

        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTicks);
        this.model.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.model.setVisible(false);

        if (dog.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem) {
            var itemStack = dog.getItemBySlot(EquipmentSlot.HEAD);
            this.model.head.visible = true;
            this.renderArmorCutout(this.model, HelmetInteractHandler.getMappedResource(itemStack.getItem()).get().getModelTexture(), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, itemStack.isEnchanted());
        }

        this.model.setVisible(false);

        if (dog.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem) {
            var itemStack = dog.getItemBySlot(EquipmentSlot.CHEST);
            this.model.body.visible = true;
            this.model.mane.visible = true;
            this.model.tail.visible = true;
            this.renderArmorCutout(this.model, HelmetInteractHandler.getMappedResource(itemStack.getItem()).get().getModelTexture(), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, itemStack.isEnchanted());
        }

        this.model.setVisible(false);

        if (dog.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem) {
            var itemStack = dog.getItemBySlot(EquipmentSlot.LEGS);
            this.model.leggingFrontLeft.visible = true;
            this.model.leggingFrontRight.visible = true;
            this.model.leggingHindLeft.visible = true;
            this.model.leggingHindRight.visible = true;
            this.renderArmorCutout(this.model, HelmetInteractHandler.getMappedResource(itemStack.getItem()).get().getModelTexture(), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, itemStack.isEnchanted());
        }

        this.model.setVisible(false);

        //TODO : Currently somehow boots which are not enchanted are rendered with flash
        // If any other pieces is enchanted, but if the boot itself is enchanted, it
        // get rendered with another flash layer making it brighter than usual :v
        if (dog.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem) {
            var itemStack = dog.getItemBySlot(EquipmentSlot.FEET);
            this.model.bootFrontLeft.visible = true;
            this.model.bootFrontRight.visible = true;
            this.model.bootHindLeft.visible = true;
            this.model.bootHindRight.visible = true;
            this.renderArmorCutout(this.model, HelmetInteractHandler.getMappedResource(itemStack.getItem()).get().getModelTexture(), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, itemStack.isEnchanted());
        }
        if (this.model.modelNeedRefreshBeforeNextRender(dog)) {
            this.model.resetAllPose();
        }

        // for (AccessoryInstance accessoryInst : dog.getAccessories()) {
        //     if (accessoryInst instanceof ArmourAccessory.Instance armorInst) {

        //         this.model.setVisible(false);

        //         if (accessoryInst.ofType(DoggyAccessoryTypes.FEET)) {
        //             this.model.legBackLeft.visible = true;
        //             this.model.legBackRight.visible = true;
        //             this.model.legFrontLeft.visible = true;
        //             this.model.legFrontRight.visible = true;
        //         } else if (accessoryInst.ofType(DoggyAccessoryTypes.HEAD)) {
        //             
        //         } else if (accessoryInst.ofType(DoggyAccessoryTypes.CLOTHING)) {
        //             this.model.body.visible = true;
        //             this.model.mane.visible = true;
        //         } else if (accessoryInst.ofType(DoggyAccessoryTypes.TAIL)) {
        //             this.model.tail.visible = true;
        //         }

        //         if (accessoryInst instanceof IColoredObject) {
        //             float[] color = ((IColoredObject) armorInst).getColor();
        //             this.renderArmorCutout(this.model, armorInst.getModelTexture(dog), poseStack, buffer, packedLight, dog, color[0], color[1], color[2], armorInst.hasEffect());
        //         } else {
        //             this.renderArmorCutout(this.model, armorInst.getModelTexture(dog), poseStack, buffer, packedLight, dog, 1.0F, 1.0F, 1.0F, armorInst.hasEffect());
        //         }
        //     }
        // }
    }

    public void renderArmorPiece(PoseStack p_117119_, MultiBufferSource p_117120_, ItemStack p_117121_, EquipmentSlot p_117122_) {
        
    }

    public static <T extends LivingEntity> void renderArmorCutout(EntityModel<T> modelIn, ResourceLocation textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float red, float green, float blue, boolean enchanted) {
        VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(textureLocationIn), false, enchanted);
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }
}
