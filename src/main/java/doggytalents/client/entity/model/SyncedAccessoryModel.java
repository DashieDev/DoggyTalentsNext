package doggytalents.client.entity.model;

import java.util.ArrayList;
import java.util.Optional;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;

public abstract class SyncedAccessoryModel extends EntityModel<Dog> {

    public final ModelPart root;
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
        this.root = root;
        populatePart(root);
    }

    protected abstract void populatePart(ModelPart box);

    public void sync(DogModel dogModel) {
        root.copyFrom(dogModel.root);

        syncPart(this.head, dogModel.head);
        syncPart(this.body, dogModel.body);
        syncPart(this.mane, dogModel.mane);
        syncPart(this.legBackRight, dogModel.legBackRight);
        syncPart(this.legBackLeft, dogModel.legBackLeft);
        syncPart(this.legFrontRight, dogModel.legFrontRight);
        syncPart(this.legFrontLeft, dogModel.legFrontLeft);
        syncPart(this.tail, dogModel.tail);
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
    public void renderToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_, float p_103017_, float p_103018_, float p_103019_, float p_103020_) {
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
            boolean headSync = this.head.isPresent();
            boolean headVisible0 = false;

            if (headSync) {
                headVisible0 = this.head.get().visible;
                this.head.get().visible = false;
            }
            
            stack.pushPose();
            // float f1 = 1.0F / 2f;
            // stack.scale(f1, f1, f1);
            // stack.translate(0.0D, (double)(24 / 16.0F), 0.0D);
            this.root.render(stack, p_103014_, p_103015_, p_103016_, p_103017_, p_103018_, p_103019_, p_103020_);
            stack.popPose();
            if (headSync) {
                this.head.get().visible = headVisible0;
                stack.pushPose();
                stack.scale(2, 2, 2);
                stack.translate(0, -0.5, 0.15);
                this.head.get().render(stack, p_103014_, p_103015_, p_103016_, p_103017_, p_103018_, p_103019_, p_103020_);
                stack.popPose();  
            }
        } else
        this.root.render(stack, p_103014_, p_103015_, p_103016_, p_103017_, p_103018_, p_103019_, p_103020_);
        
        stack.popPose();
        stack.popPose();
        root.xRot = xRot0; root.yRot = yRot0; root.zRot = zRot0;
        root.x = x0; root.y = y0; root.z = z0;
    }

    @Override
    public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    }

    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
