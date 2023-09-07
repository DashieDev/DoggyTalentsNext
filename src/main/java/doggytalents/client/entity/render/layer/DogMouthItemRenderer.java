package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import doggytalents.DoggyTalents;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderer;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.SwordItem;

public class DogMouthItemRenderer extends RenderLayer<Dog, DogModel> {
    
    private ItemInHandRenderer itemInHandRenderer;

    public DogMouthItemRenderer(RenderLayerParent dogRendererIn, EntityRendererProvider.Context ctx) {
        super(dogRendererIn);
        this.itemInHandRenderer = ctx.getItemInHandRenderer();
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dog.getDogLevel(DoggyTalents.DOGGY_TOOLS.get()) <= 0) return;

        var stack = dog.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack == null || stack.isEmpty()) return;

        matrixStack.pushPose();
        DogModel model = this.getParentModel();
        if (model.young) {
            // derived from AgeableModel head offset
            matrixStack.translate(0.0F, 5.0F / 16.0F, 2.0F / 16.0F);
        }

        model.head.translateAndRotate(matrixStack);

        matrixStack.translate(-0.025F, 0.125F, -0.32F);
        var item = stack.getItem();

        if (item instanceof SwordItem) {
            matrixStack.translate(0.25, 0, 0);
        }
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(45.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        this.itemInHandRenderer.renderItem(dog, stack, ItemTransforms.TransformType.GROUND, false, matrixStack, bufferSource, packedLight);
        matrixStack.popPose();
    }
}
