package doggytalents.client.entity.render.layer;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WolfArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;

public class DogWolfArmorRenderer extends RenderLayer<Dog, DogModel> {

    private static final Map<Crackiness.Level, ResourceLocation> ARMOR_CRACK_LOCATIONS = Map.of(
        Crackiness.Level.LOW,
        new ResourceLocation("textures/entity/wolf/wolf_armor_crackiness_low.png"),
        Crackiness.Level.MEDIUM,
        new ResourceLocation("textures/entity/wolf/wolf_armor_crackiness_medium.png"),
        Crackiness.Level.HIGH,
        new ResourceLocation("textures/entity/wolf/wolf_armor_crackiness_high.png")
    );

    public DogWolfArmorRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
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

        renderWolfArmorLayerMain(poseStack, buffer, packedLight, wolfArmorPair.getRight());
        renderWolfArmorLayerDyed(poseStack, buffer, packedLight, wolfArmorPair.getLeft(), wolfArmorPair.getRight());
        renderWolfArmorLayerCracks(poseStack, buffer, packedLight, wolfArmorPair.getLeft());
    }

    private Optional<Pair<ItemStack, AnimalArmorItem>> getWolfArmorItem(Dog dog) {
        var wolf_armor_stack = dog.wolfArmor();
        if (!(wolf_armor_stack.getItem() instanceof AnimalArmorItem wolfArmorItem))
            return Optional.empty();
        if (wolfArmorItem.getBodyType() != AnimalArmorItem.BodyType.CANINE)
            return Optional.empty();
        return Optional.of(Pair.of(wolf_armor_stack, wolfArmorItem));
    }

    private void renderWolfArmorLayerMain(PoseStack poseStack, MultiBufferSource buffer, int light, AnimalArmorItem item) {
        var vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(item.getTexture()));
        var model = this.getParentModel();
        model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderWolfArmorLayerDyed(PoseStack stack, MultiBufferSource buffer, int light, 
        ItemStack itemStack, AnimalArmorItem item) {
        if (item != Items.WOLF_ARMOR)
            return;
        int i = DyedItemColor.getOrDefault(itemStack, 0);
        if (FastColor.ARGB32.alpha(i) == 0)
            return;

        var armor_overlay = item.getOverlayTexture();
        if (armor_overlay == null)
            return;
            
        float r = (float)FastColor.ARGB32.red(i) / 255.0F;
        float g = (float)FastColor.ARGB32.green(i) / 255.0F;
        float b = (float)FastColor.ARGB32.blue(i) / 255.0F;
        
        var model = this.getParentModel();
        model
            .renderToBuffer(
                stack, buffer.getBuffer(RenderType.entityCutoutNoCull(armor_overlay)), light, 
                OverlayTexture.NO_OVERLAY, r, g, b, 1.0F
            );
    }

    private void renderWolfArmorLayerCracks(PoseStack stack, MultiBufferSource buffer, int light, ItemStack itemStack) {
        var crack_level = Crackiness.WOLF_ARMOR.byDamage(itemStack);
        if (crack_level == Crackiness.Level.NONE)
            return;

        var crack_rl = ARMOR_CRACK_LOCATIONS.get(crack_level);
        var model = this.getParentModel();
        var vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(crack_rl));
        model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
    
}
