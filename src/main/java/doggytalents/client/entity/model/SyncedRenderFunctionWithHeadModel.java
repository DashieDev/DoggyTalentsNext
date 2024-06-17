package doggytalents.client.entity.model;

import java.util.Optional;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;

public class SyncedRenderFunctionWithHeadModel extends SyncedAccessoryModel {
    
    private Vector3f pivot = DogModel.DEFAULT_ROOT_PIVOT;

    public SyncedRenderFunctionWithHeadModel(ModelPart root) {
        super(root);
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
    public void renderToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_, int unused) {
    }

    public void startRenderFromRoot(PoseStack stack, Renderer actualRendering) {
        stack.pushPose();
        stack.translate((double)(root.x / 16.0F), (double)(root.y / 16.0F), (double)(root.z / 16.0F));
        stack.translate((double)(pivot.x / 16.0F), (double)(pivot.y / 16.0F), (double)(pivot.z / 16.0F));
        if (root.zRot != 0.0F) {
            stack.mulPose(Axis.ZP.rotation(root.zRot));
        }

        if (root.yRot != 0.0F) {
            stack.mulPose(Axis.YP.rotation(root.yRot));
        }

        if (root.xRot != 0.0F) {
            stack.mulPose(Axis.XP.rotation(root.xRot));
        }
        float xRot0 = root.xRot, yRot0 = root.yRot, zRot0 = root.zRot;
        float x0 = root.x, y0 = root.y, z0 = root.z;
        root.xRot = 0; root.yRot = 0; root.zRot = 0;
        root.x = 0; root.y = 0; root.z = 0;

        stack.pushPose();
        stack.translate((double)(-pivot.x / 16.0F), (double)(-pivot.y / 16.0F), (double)(-pivot.z / 16.0F));
        
        if (this.young) {
            stack.pushPose();
            stack.scale(2, 2, 2);
            stack.translate(0, -0.5, 0.15);
            startRenderItemFromHead(stack, actualRendering);
            stack.popPose();  
        } else
        startRenderItemFromHead(stack, actualRendering);

        stack.popPose();
        stack.popPose();
        root.xRot = xRot0; root.yRot = yRot0; root.zRot = zRot0;
        root.x = x0; root.y = y0; root.z = z0;
    }

    public void startRenderItemFromHead(PoseStack matrixStack, Renderer renderer) {
        matrixStack.pushPose();
        this.head.get().translateAndRotate(matrixStack);
        matrixStack.pushPose();
        this.realHead.get().translateAndRotate(matrixStack);

        renderer.doRender(matrixStack);

        matrixStack.popPose();
        matrixStack.popPose();
    }

    public static interface Renderer {
        
        void doRender(PoseStack matrixStack);
        
    }


}
