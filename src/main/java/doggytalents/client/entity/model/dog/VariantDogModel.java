package doggytalents.client.entity.model.dog;

import java.util.Optional;

import doggytalents.api.enu.forward_imitate.anim.DogModelPart;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Deprecated
public class VariantDogModel extends DogModel {

    public DogModelPart realTail2;
    public DogModelPart realTail3;
    public DogModelPart earNormal;
    public DogModelPart earBoni;
    public DogModelPart earSmall;

    public VariantDogModel(ModelPart box) {
        super(box);
        this.realTail2 = (DogModelPart)this.tail.getChild("real_tail_2");
        this.realTail3 = (DogModelPart)this.tail.getChild("real_tail_bushy");
        this.earNormal = (DogModelPart)this.realHead.getChild("ear_normal");
        this.earBoni = (DogModelPart)this.realHead.getChild("ear_boni");
        this.earSmall = (DogModelPart)this.realHead.getChild("ear_small");
    }
    
    public static LayerDefinition createBodyLayer() {
        return createBodyLayerInternal(CubeDeformation.NONE);
    }

    private static LayerDefinition createBodyLayerInternal(CubeDeformation scale) {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        float var2 = 13.5F;
        PartDefinition var3 = var1.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, 13.5F, -7.0F));
        var real_head = var3.addOrReplaceChild("real_head", CubeListBuilder.create()
                // Head
                .texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, scale)
                // Nose
                .texOffs(0, 10).addBox(-1.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F, scale)
                , PartPose.ZERO);
        var ear_normal = real_head.addOrReplaceChild("ear_normal", CubeListBuilder.create()
            .texOffs(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, scale)
            .texOffs(16, 14).addBox(1.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, scale)
        ,PartPose.ZERO);
        var ear_boni = real_head.addOrReplaceChild("ear_boni", CubeListBuilder.create()
            .texOffs(52, 0).addBox(-4.0F, -3.0F, -1.5F, 1, 5, 3, scale)
            .texOffs(52, 0).addBox(3.0F, -3.0F, -1.5F, 1, 5, 3, scale)
        ,PartPose.ZERO);
        var ear_small = real_head.addOrReplaceChild("ear_small", CubeListBuilder.create()
            .texOffs(18, 0).addBox(-3.8F, -3.5F, -1.0F, 2, 1, 2, scale)
            .texOffs(18, 0).addBox(1.8F, -3.5F, -1.0F, 2, 1, 2, scale)
        ,PartPose.ZERO);
        var1.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, scale)
        , PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        var1.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, scale), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale);
        var1.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-2.5F, 16.0F, 7.0F));
        var1.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(0.5F, 16.0F, 7.0F));
        var1.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-2.5F, 16.0F, -4.0F));
        var1.addOrReplaceChild("left_front_leg", var4, PartPose.offset(0.5F, 16.0F, -4.0F));
        PartDefinition var5 = var1.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
        var5.addOrReplaceChild("real_tail", CubeListBuilder.create()
                .texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale)
        , PartPose.ZERO);
        var5.addOrReplaceChild("real_tail_2", CubeListBuilder.create()
                .texOffs(45, 0).addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, scale)
        , PartPose.offset(0.0F, -2.0F, 0.0F));
        var5.addOrReplaceChild("real_tail_bushy", CubeListBuilder.create()
                .texOffs(43, 19).addBox(-1.0F, 0F, -2F, 3, 10, 3, scale)
                , PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 32);
    }

    @Override
    public void prepareMobModel(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.prepareMobModel(dog, limbSwing, limbSwingAmount, partialTickTime);
        var skin = dog.getClientSkin();
        pickTail(skin.getTail());
        pickEar(skin.getEar());
    }

    protected void pickTail(int tail_id) {
        switch (tail_id) {
        default:
            this.realTail.visible = true;
            this.realTail2.visible = false;
            this.realTail3.visible = false;
            break;
        case 1 :
            this.realTail.visible = false;
            this.realTail2.visible = true;
            this.realTail3.visible = false;
            break;
        case 2 :
            this.realTail.visible = false;
            this.realTail2.visible = false;
            this.realTail3.visible = true;
            break;
        }
    }
    
    protected void pickEar(int ear_id) {
        switch(ear_id) {
        default:
            this.earNormal.visible = true;
            this.earBoni.visible = false;
            this.earSmall.visible = false;
            break;
        case 1 :
            this.earNormal.visible = false;
            this.earBoni.visible = true;
            this.earSmall.visible = false;
            break; 
        case 2 :
            this.earNormal.visible = false;
            this.earBoni.visible = false;
            this.earSmall.visible = true;
            break;
        }
    }

    @Override
    public void translateShakingDog(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.translateShakingDog(dog, limbSwing, limbSwingAmount, partialTickTime);
        this.realTail2.zRot = dog.getShakeAngle(partialTickTime, -0.2F);
        this.realTail3.zRot = dog.getShakeAngle(partialTickTime, -0.2F);
    }

    @Override
    public void resetShakingDog(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.resetShakingDog(dog, limbSwing, limbSwingAmount, partialTickTime);
        this.realTail2.zRot = 0;
        this.realTail3.zRot = 0;
    }

    @Override
    public void resetAllPose() {
        super.resetAllPose();
        this.realTail2.resetPose();
        this.realTail3.resetPose();
    }
    
    @Override
    public void copyFrom(DogModel dogModel) {
        super.copyFrom(dogModel);
        this.realTail2.copyFrom(this.realTail);
        this.realTail3.copyFrom(this.realTail);
    }

    @Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }
    
}
