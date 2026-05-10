package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.SyncedRenderFunctionWithHeadModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;

public class DogMouthItemRenderer extends RenderLayer<DogRenderState, DogModel> {

    private final ItemModelResolver itemModelResolver;
    private SyncedRenderFunctionWithHeadModel itemSyncer;
    private final ItemStackRenderState itemRenderState = new ItemStackRenderState();

    public DogMouthItemRenderer(RenderLayerParent<DogRenderState, DogModel> dogRendererIn, EntityRendererProvider.Context ctx) {
        super(dogRendererIn);
        this.itemModelResolver = ctx.getItemModelResolver();
        itemSyncer = new SyncedRenderFunctionWithHeadModel(ctx.bakeLayer(ClientSetup.DOG_SYNCED_FUNCTION_WITH_HEAD));
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int lightCoords, DogRenderState state, float yRot, float xRot) {
        Dog dog = state.dog;
        if (dog == null) return;

        if (!ConfigHandler.CLIENT.MOUTH_ITEM_FORCE_RENDER.get()) {
            var skin = state.activeSkin;
            if (skin.useCustomModel()) {
                var model = skin.getCustomModel().getValue();
                if (!model.armorShouldRender(dog))
                    return;
            }
        }

        var stackOptional = dog.getMouthItemForRender();
        if (!stackOptional.isPresent())
            return;
        var itemStack = stackOptional.get();

        var model = this.getParentModel();
        model.copyPropertiesTo(itemSyncer);
        itemSyncer.sync(model);

        itemSyncer.startRenderFromRoot(matrixStack, matrixStack1 -> {
            matrixStack1.pushPose();
            matrixStack1.translate(-0.025F, 0.125F, -0.32F);
            var item = itemStack.getItem();

            if (itemStack.has(DataComponents.WEAPON) || itemStack.has(DataComponents.TOOL)
                || itemStack.is(Items.TRIDENT)) {
                matrixStack1.translate(0.25, 0, 0);
            }
            if (item instanceof BowItem || item instanceof CrossbowItem) {
                matrixStack1.scale(1, -1, -1);
                matrixStack1.translate(0, 0, -0.1);
            }
            if (item instanceof BlockItem) {
                matrixStack1.scale(0.5f, -0.5f, -0.5f);
                matrixStack1.translate(0.2f, -0.31f, 0.07f);
                matrixStack1.mulPose(Axis.YP.rotationDegrees(60.0F));
            } else {
                matrixStack1.mulPose(Axis.YP.rotationDegrees(45.0F));
                matrixStack1.mulPose(Axis.XP.rotationDegrees(90.0F));
            }

            itemModelResolver.updateForNonLiving(itemRenderState, itemStack, ItemDisplayContext.GROUND, dog);
            itemRenderState.submit(matrixStack1, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, -1);
            matrixStack1.popPose();
        });
    }
}
