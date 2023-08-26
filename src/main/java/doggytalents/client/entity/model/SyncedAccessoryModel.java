package doggytalents.client.entity.model;

import java.util.ArrayList;
import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class SyncedAccessoryModel extends EntityModel<Dog> {

    public final ModelPart root;
    
    public Optional<ModelPart> head = Optional.empty();
    public Optional<ModelPart> realHead = Optional.empty();
    public Optional<ModelPart> body = Optional.empty();
    public Optional<ModelPart> mane = Optional.empty();
    public Optional<ModelPart> legBackRight = Optional.empty();
    public Optional<ModelPart> legBackLeft = Optional.empty();
    public Optional<ModelPart> legFrontRight = Optional.empty();
    public Optional<ModelPart> legFrontLeft = Optional.empty();
    public Optional<ModelPart> tail = Optional.empty();

    public SyncedAccessoryModel(ModelPart root) {
        this.root = root;
        populatePart(root);
    }

    protected abstract void populatePart(ModelPart box);

    public void sync(DogModel<? extends AbstractDog> dogModel) {
        root.copyFrom(dogModel.root);

        syncPart(this.head, dogModel.head);
        syncPart(this.realHead, dogModel.realHead);
        syncPart(this.body, dogModel.body);
        syncPart(this.mane, dogModel.mane);
        syncPart(this.legBackRight, dogModel.legBackRight);
        syncPart(this.legBackLeft, dogModel.legBackLeft);
        syncPart(this.legFrontRight, dogModel.legFrontRight);
        syncPart(this.legFrontLeft, dogModel.legFrontLeft);
        syncPart(this.tail, dogModel.tail);
    }

    private void syncPart(Optional<ModelPart> part, ModelPart dogPart) {
        part.ifPresent(p -> p.copyFrom(dogPart));
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_, float p_103017_, float p_103018_, float p_103019_, float p_103020_) {
        stack.pushPose();
        stack.translate((double)(0 / 16.0F), (double)(15 / 16.0F), (double)(0 / 16.0F));
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
        root.xRot = 0; root.yRot = 0; root.zRot = 0;
        stack.pushPose();
        stack.translate((double)(0 / 16.0F), (double)(-15 / 16.0F), (double)(0 / 16.0F));
            
        this.root.render(stack, p_103014_, p_103015_, p_103016_, p_103017_, p_103018_, p_103019_, p_103020_);
        
        stack.popPose();
        stack.popPose();
        root.xRot = xRot0; root.yRot = yRot0; root.zRot = zRot0;
    }

    @Override
    public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    }

    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
