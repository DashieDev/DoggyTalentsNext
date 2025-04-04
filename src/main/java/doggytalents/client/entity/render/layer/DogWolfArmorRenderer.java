package doggytalents.client.entity.render.layer;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.backward_imitate.ItemUtil_1_21_5;
import doggytalents.client.backward_imitate.DogRenderLayer_21_3;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WolfArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;

public class DogWolfArmorRenderer extends DogRenderLayer_21_3 {

    private static final Map<Crackiness.Level, ResourceLocation> ARMOR_CRACK_LOCATIONS = Map.of(
        Crackiness.Level.LOW,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_low.png"),
        Crackiness.Level.MEDIUM,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_medium.png"),
        Crackiness.Level.HIGH,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_high.png")
    );

    private DogModel defaultModel;

    public DogWolfArmorRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.defaultModel = DogModelRegistry.getDogModelHolder("default").getValue();
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

        if (dog.wolfArmor().isEmpty())
            return;

        var wolfArmorOptional = getWolfArmorItem(dog);
        if (!wolfArmorOptional.isPresent())
            return;
        var wolfArmorPair = wolfArmorOptional.get();

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

        renderWolfArmorLayerMain(dogModel, poseStack, buffer, packedLight, wolfArmorPair.getRight());
        renderWolfArmorLayerDyed(dogModel, poseStack, buffer, packedLight, wolfArmorPair.getLeft(), wolfArmorPair.getRight());
        renderWolfArmorLayerCracks(dogModel, poseStack, buffer, packedLight, wolfArmorPair.getLeft());
    }

    private Optional<Pair<ItemStack, Item>> getWolfArmorItem(Dog dog) {
        var wolf_armor_stack = dog.wolfArmor();
        if (!(ItemUtil_1_21_5.isWolfArmor(wolf_armor_stack)))
            return Optional.empty();
        // if (wolfArmorItem.getBodyType() != AnimalArmorItem.BodyType.CANINE)
        //     return Optional.empty();
        return Optional.of(Pair.of(wolf_armor_stack, wolf_armor_stack.getItem()));
    }

    private void renderWolfArmorLayerMain(DogModel model, PoseStack poseStack, MultiBufferSource buffer, int light, Item item) {
        var vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(WOLF_ARMOR_MAIN_21_3));
        model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }

    private void renderWolfArmorLayerDyed(DogModel model, PoseStack stack, MultiBufferSource buffer, int light, 
        ItemStack itemStack, Item item) {
        if (item != Items.WOLF_ARMOR)
            return;
        int i = DyedItemColor.getOrDefault(itemStack, 0);
        if (ARGB.alpha(i) == 0)
            return;

        var armor_overlay = WOLF_ARMOR_DYE_21_3;
        if (armor_overlay == null)
            return;
            
        float r = (float)ARGB.red(i) / 255.0F;
        float g = (float)ARGB.green(i) / 255.0F;
        float b = (float)ARGB.blue(i) / 255.0F;
        
        model
            .renderToBuffer(
                stack, buffer.getBuffer(RenderType.entityCutoutNoCull(armor_overlay)), light, 
                OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1, r, g, b)
            );
    }

    private void renderWolfArmorLayerCracks(DogModel model, PoseStack stack, MultiBufferSource buffer, int light, ItemStack itemStack) {
        var crack_level = Crackiness.WOLF_ARMOR.byDamage(itemStack);
        if (crack_level == Crackiness.Level.NONE)
            return;

        var crack_rl = ARMOR_CRACK_LOCATIONS.get(crack_level);
        var vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(crack_rl));
        model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
    }
    


    //1.21.3+
    private static final ResourceLocation WOLF_ARMOR_MAIN_21_3 = Util.getVanillaResource("textures/entity/wolf/wolf_armor.png");
    private static final ResourceLocation WOLF_ARMOR_DYE_21_3 = Util.getVanillaResource("textures/entity/wolf/wolf_armor_overlay.png");
}
