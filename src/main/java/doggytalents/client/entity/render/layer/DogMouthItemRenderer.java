package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import doggytalents.DoggyTalents;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.SyncedRenderFunctionWithHeadModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderer;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;

public class DogMouthItemRenderer extends RenderLayer<Dog, DogModel> {
    
    private ItemInHandRenderer itemInHandRenderer;
    private SyncedRenderFunctionWithHeadModel itemSyncer;

    public DogMouthItemRenderer(RenderLayerParent dogRendererIn, EntityRendererProvider.Context ctx) {
        super(dogRendererIn);

        this.itemInHandRenderer = Minecraft.getInstance().getItemInHandRenderer();
        itemSyncer = new SyncedRenderFunctionWithHeadModel(ctx.bakeLayer(ClientSetup.DOG_SYNCED_FUNCTION_WITH_HEAD));
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!ConfigHandler.CLIENT.MOUTH_ITEM_FORCE_RENDER.get()) {
            var skin = dog.getClientSkin();
            if (skin.useCustomModel()) {
                var model = skin.getCustomModel().getValue();
                if (!model.armorShouldRender(dog))
                    return;
            }
        }
        
        var stackOptional = dog.getMouthItemForRender();
        if (!stackOptional.isPresent())
            return;
        var stack = stackOptional.get();

        var model = this.getParentModel();

        model.copyPropertiesTo(itemSyncer);
        itemSyncer.sync(model);
        itemSyncer.startRenderFromRoot(matrixStack, matrixStack1 -> {
            renderItem(matrixStack1, bufferSource, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
        });
    }

    public void renderItem(PoseStack stack, MultiBufferSource bufferSource, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack itemStack) {
        stack.pushPose();
        stack.translate(-0.025F, 0.125F, -0.32F);
        var item = itemStack.getItem();

        if (item instanceof SwordItem || item instanceof DiggerItem) {
            stack.translate(0.25, 0, 0);
        }
        if (item instanceof BowItem || item instanceof CrossbowItem) {
            stack.scale(1, 1, -1);
            stack.translate(0, 0, -0.1);
        }
        stack.mulPose(Vector3f.YP.rotationDegrees(45.0F));
        stack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        this.itemInHandRenderer.renderItem(dog, itemStack, TransformType.GROUND, false, stack, bufferSource, packedLight);
        stack.popPose();
    }
}
