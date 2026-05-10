package doggytalents.client.entity.model;

import java.util.Optional;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class SyncedAccessoryModel extends EntityModel<DogRenderState> {

    // root is inherited from Model as protected final ModelPart root
    private Vector3f pivot = DogModel.DEFAULT_ROOT_PIVOT;
    
    public Optional<ModelPart> head = Optional.empty();
    public Optional<ModelPart> realHead = Optional.empty();
    public Optional<ModelPart> body = Optional.empty();
    public Optional<ModelPart> mane = Optional.empty();
    public Optional<ModelPart> legBackRight = Optional.empty();
    public Optional<ModelPart> legBackLeft = Optional.empty();
    public Optional<ModelPart> legFrontRight = Optional.empty();
    public Optional<ModelPart> legFrontLeft = Optional.empty();
    public Optional<ModelPart> tail = Optional.empty();
    public Optional<ModelPart> realTail = Optional.empty();

    public SyncedAccessoryModel(ModelPart root) {
        super(root);
        populatePart(root);
    }

    protected abstract void populatePart(ModelPart box);

    public void sync(DogModel dogModel) {
        syncPart(this.root(), dogModel.root);
        syncPart(this.head, dogModel.head);
        syncPart(this.realHead, dogModel.realHead);
        syncPart(this.body, dogModel.body);
        syncPart(this.mane, dogModel.mane);
        syncPart(this.legBackRight, dogModel.legBackRight);
        syncPart(this.legBackLeft, dogModel.legBackLeft);
        syncPart(this.legFrontRight, dogModel.legFrontRight);
        syncPart(this.legFrontLeft, dogModel.legFrontLeft);
        syncPart(this.tail, dogModel.tail);
        syncPart(this.realTail, dogModel.realTail);
        pivot = DogModel.DEFAULT_ROOT_PIVOT;
        var custom_pivot = dogModel.getCustomRootPivotPoint();
        if (custom_pivot != null) {
            pivot = custom_pivot;
        }
    }

    private void syncPart(Optional<ModelPart> part, ModelPart dogPart) {
        part.ifPresent(p -> syncPart(p, dogPart));
    }

    private void syncPart(ModelPart part, ModelPart dogPart) {
        DogModel.copyPartPose(part, dogPart);
    }

    public void renderAccessoryToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_, int color_overlay) {
        DogModel.renderDogModelFromRootWithPivot(stack, createDogRenderContext(p_103014_, p_103015_, p_103016_, color_overlay));
    }

    public DogModel.DogModelRenderContext createDogRenderContext(VertexConsumer p_103014_, int p_103015_, int p_103016_, int color_overlay) {
        var part_ctx = new DogModel.DogRenderPartContext(p_103014_, p_103015_, p_103016_, color_overlay);
        // 'young' field removed in 26.1.2 - baby head scaling disabled in accessory models
        return new DogModel.DogModelRenderContext(this.root(), this.pivot, getDogModelBabyHead(head, false), Optional.of(part_ctx), Optional.empty());
    }
    
    protected static Optional<ModelPart> getDogModelBabyHead(Optional<ModelPart> head, boolean young) {
        return young ? head : Optional.empty();
    }

    public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    }

    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float relativeHeadYRot, float headPitch) {

    }
}
