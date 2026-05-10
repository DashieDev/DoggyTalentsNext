package doggytalents.client.entity.model;

import java.util.Optional;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
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
        DogModel.copyPartPose(root(), dogModel.root);

        syncPart(this.head, dogModel.head);
        syncPart(this.realHead, dogModel.realHead);
        pivot = DogModel.DEFAULT_ROOT_PIVOT;
        var custom_pivot = dogModel.getCustomRootPivotPoint();
        if (custom_pivot != null) {
            pivot = custom_pivot;
        }
    }

    private void syncPart(Optional<ModelPart> part, ModelPart dogPart) {
        part.ifPresent(p -> DogModel.copyPartPose(p, dogPart));
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


    public void startRenderFromRoot(PoseStack stack, Renderer actualRendering) {
        DogModel.renderDogModelFromRootWithPivot(stack, createDogRenderContext(actualRendering));
    }

    public DogModel.DogModelRenderContext createDogRenderContext(Renderer actualRendering) {
        DogModel.AddtionalHeadRenderer additional_head_renderer = 
            (stack, part_ctx) -> {
                this.startRenderItemFromHead(stack, actualRendering);
            };
        return new DogModel.DogModelRenderContext(this.root(), this.pivot, SyncedAccessoryModel.getDogModelBabyHead(head, false), Optional.empty(), Optional.of(additional_head_renderer));
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
