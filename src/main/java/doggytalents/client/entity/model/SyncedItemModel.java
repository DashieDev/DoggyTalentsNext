package doggytalents.client.entity.model;

import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class SyncedItemModel extends SyncedAccessoryModel {

    private Vector3f pivot = DogModel.DEFAULT_ROOT_PIVOT;
    private ItemInHandRenderer itemRenderer;

    public SyncedItemModel(ModelPart root, ItemInHandRenderer renderer) {
        super(root);
        this.itemRenderer = renderer;
    }

    @Override
    public void sync(DogModel dogModel) {
        root.copyFrom(dogModel.root);

        syncPart(this.head, dogModel.head);
        syncPart(this.realHead, dogModel.realHead);
        pivot = DogModel.DEFAULT_ROOT_PIVOT;
        var custom_pivot = dogModel.getCustomRootPivotPoint();
        if (custom_pivot != null) {
            pivot = custom_pivot;
        }
    }

    private void syncPart(Optional<ModelPart> part, ModelPart dogPart) {
        part.ifPresent(p -> p.copyFrom(dogPart));
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
    }

    public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
    	var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, 13.5F, -7F));
		
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.ZERO);
		
		return LayerDefinition.create(meshdefinition, 16, 16);
	}


    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_, float p_103017_,
            float p_103018_, float p_103019_, float p_103020_) {
    }

    public void startRenderFromRoot(PoseStack stack, MultiBufferSource bufferSource, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack itemStack) {
        stack.pushPose();
        stack.translate((double)(root.x / 16.0F), (double)(root.y / 16.0F), (double)(root.z / 16.0F));
        stack.translate((double)(pivot.x() / 16.0F), (double)(pivot.y() / 16.0F), (double)(pivot.z() / 16.0F));
        if (root.zRot != 0.0F) {
            stack.mulPose(Vector3f.ZP.rotation(root.zRot));
        }

        if (root.yRot != 0.0F) {
            stack.mulPose(Vector3f.YP.rotation(root.yRot));
        }

        if (root.xRot != 0.0F) {
            stack.mulPose(Vector3f.XP.rotation(root.xRot));
        }
        float xRot0 = root.xRot, yRot0 = root.yRot, zRot0 = root.zRot;
        float x0 = root.x, y0 = root.y, z0 = root.z;
        root.xRot = 0; root.yRot = 0; root.zRot = 0;
        root.x = 0; root.y = 0; root.z = 0;

        stack.pushPose();
        stack.translate((double)(-pivot.x() / 16.0F), (double)(-pivot.y() / 16.0F), (double)(-pivot.z() / 16.0F));
        
        if (this.young) {
            stack.pushPose();
            stack.scale(2, 2, 2);
            stack.translate(0, -0.5, 0.15);
            startRenderItemFromHead(stack, bufferSource, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, itemStack);
            stack.popPose();  
        } else
        startRenderItemFromHead(stack, bufferSource, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, itemStack);

        stack.popPose();
        stack.popPose();
        root.xRot = xRot0; root.yRot = yRot0; root.zRot = zRot0;
        root.x = x0; root.y = y0; root.z = z0;
    }

    public void startRenderItemFromHead(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        matrixStack.pushPose();
        this.head.get().translateAndRotate(matrixStack);
        matrixStack.pushPose();
        this.realHead.get().translateAndRotate(matrixStack);

        renderItem(matrixStack, bufferSource, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);

        matrixStack.popPose();
        matrixStack.popPose();
    }

    public void renderItem(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        matrixStack.translate(-0.025F, 0.125F, -0.32F);
        var item = stack.getItem();

        if (item instanceof SwordItem || item instanceof DiggerItem) {
            matrixStack.translate(0.25, 0, 0);
        }
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(45.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        this.itemRenderer.renderItem(dog, stack, TransformType.GROUND, false, matrixStack, bufferSource, packedLight);
    }

}
