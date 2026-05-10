package doggytalents.client.entity.render.layer;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.core.component.DataComponents;

public class DogWolfArmorRenderer extends RenderLayer<DogRenderState, DogModel> {

    private static final Map<Crackiness.Level, Identifier> ARMOR_CRACK_LOCATIONS = Map.of(
        Crackiness.Level.LOW,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_low.png"),
        Crackiness.Level.MEDIUM,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_medium.png"),
        Crackiness.Level.HIGH,
        Util.getVanillaResource("textures/entity/wolf/wolf_armor_crackiness_high.png")
    );

    private DogModel defaultModel;

    public DogWolfArmorRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.defaultModel = DogModelRegistry.getDogModelHolder("default").getValue();
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, DogRenderState state, float yRot, float xRot) {
        Dog dog = state.dog;
        if (dog == null) return;
        if (!dog.isTame() || dog.isInvisible()) {
            return;
        }

        if (!ConfigHandler.CLIENT.RENDER_ARMOR.get() || dog.hideArmor())
            return;

        var skin = state.activeSkin;
        if (skin == null) return;
        if (skin.useCustomModel()) {
            var model = skin.getCustomModel().getValue();
            if (!model.armorShouldRender(dog))
                return;
        }

        if (dog.wolfArmor().isEmpty())
            return;

        var wolfArmorOptional = getWolfArmorWithEquippable(dog);
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

        renderWolfArmorLayerMain(dogModel, poseStack, submitNodeCollector, lightCoords, state, wolfArmorPair.getRight());
        renderWolfArmorLayerDyed(dogModel, poseStack, submitNodeCollector, lightCoords, state, wolfArmorPair.getLeft(), wolfArmorPair.getRight());
        renderWolfArmorLayerCracks(dogModel, poseStack, submitNodeCollector, lightCoords, state, wolfArmorPair.getLeft());
    }

    private Optional<Pair<ItemStack, Equippable>> getWolfArmorWithEquippable(Dog dog) {
        var wolf_armor_stack = dog.wolfArmor();
        if (wolf_armor_stack.isEmpty()) return Optional.empty();
        Equippable equippable = wolf_armor_stack.get(DataComponents.EQUIPPABLE);
        if (equippable == null) return Optional.empty();
        if (equippable.assetId().isEmpty()) return Optional.empty();
        return Optional.of(Pair.of(wolf_armor_stack, equippable));
    }

    private void renderWolfArmorLayerMain(DogModel model, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, DogRenderState state, Equippable equippable) {
        equippable.assetId().ifPresent(assetId -> {
            // Use model's renderType for the asset - simplified approach
            // Full EquipmentLayerRenderer usage would be needed for proper trim support
            submitNodeCollector.submitModel(
                model, state, poseStack,
                model.renderType(Identifier.withDefaultNamespace("textures/entity/wolf/wolf_armor.png")),
                light, OverlayTexture.NO_OVERLAY, 0xffffffff, null, state.outlineColor, null
            );
        });
    }

    private void renderWolfArmorLayerDyed(DogModel model, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light,
        DogRenderState state, ItemStack itemStack, Equippable equippable) {
        int i = DyedItemColor.getOrDefault(itemStack, 0);
        if (ARGB.alpha(i) == 0)
            return;

        float r = (float)ARGB.red(i) / 255.0F;
        float g = (float)ARGB.green(i) / 255.0F;
        float b = (float)ARGB.blue(i) / 255.0F;

        equippable.assetId().ifPresent(assetId -> {
            var overlay_texture = Identifier.withDefaultNamespace("textures/entity/wolf/wolf_armor_overlay.png");
            submitNodeCollector.submitModel(
                model, state, poseStack,
                model.renderType(overlay_texture),
                light, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(1, r, g, b), null, state.outlineColor, null
            );
        });
    }

    private void renderWolfArmorLayerCracks(DogModel model, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, DogRenderState state, ItemStack itemStack) {
        var crack_level = Crackiness.WOLF_ARMOR.byDamage(itemStack);
        if (crack_level == Crackiness.Level.NONE)
            return;

        var crack_rl = ARMOR_CRACK_LOCATIONS.get(crack_level);
        submitNodeCollector.submitModel(
            model, state, poseStack,
            RenderTypes.armorTranslucent(crack_rl),
            light, OverlayTexture.NO_OVERLAY, 0xffffffff, null, state.outlineColor, null
        );
    }

}
