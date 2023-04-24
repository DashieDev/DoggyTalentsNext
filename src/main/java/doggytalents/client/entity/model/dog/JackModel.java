package doggytalents.client.entity.model.dog;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class JackModel extends DogModel<Dog> {

    public JackModel(ModelPart box) {
        super(box);
        //TODO Auto-generated constructor stub
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -3.65F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 10).addBox(-0.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		real_head.addOrReplaceChild("cheak_fluff", CubeListBuilder.create().texOffs(15, 15).addBox(-3.75F, -13.5F, -8.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 12).addBox(-3.95F, -12.9F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(16, 12).addBox(-4.55F, -12.65F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-5.3F, -11.5F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-4.8F, -12.0F, -9.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(15, 15).mirror().addBox(1.75F, -13.5F, -8.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(2.95F, -12.9F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(3.55F, -12.65F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(4.3F, -11.5F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(3.8F, -12.0F, -9.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(1.0F, 10.5F, 7.0F));
        
        var ear_normal = real_head.addOrReplaceChild("ear_normal", CubeListBuilder.create(),PartPose.ZERO);
        var ear_boni = real_head.addOrReplaceChild("ear_boni", CubeListBuilder.create(),PartPose.ZERO);
        var ear_small = real_head.addOrReplaceChild("ear_small", CubeListBuilder.create(),PartPose.ZERO);

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		PartDefinition leg1 = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition leg2 = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition leg3 = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition leg4 = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(0.5F, 16.0F, -4.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 13.5F, 8.5f, 1.8326F, 0.0F, 0.0F));

        PartDefinition real_tail_1 = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(56, 0).addBox(0.0F, 2.0F, 1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(56, 0).addBox(0.0F, 5.0F, 2.0F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(56, 0).addBox(0.0F, 4.0F, 4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        
        tail.addOrReplaceChild("real_tail_2", CubeListBuilder.create(), PartPose.ZERO);
        
        tail.addOrReplaceChild("real_tail_bushy", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void prepareMobModel(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.tail.yRot = dog.getWagAngle(limbSwing, limbSwingAmount, partialTickTime);

        if (dog.isInSittingPose()) {
            if (dog.isLying()) {
                this.head.setPos(-1, 19.5F, -7);
                this.body.setPos(0, 20, 2);
                this.body.xRot = (float)Math.PI / 2F;
                this.mane.setPos(
                    -1f + MANE_LYING_OFF[0],
                    14f + MANE_LYING_OFF[1], 
                    -3f + MANE_LYING_OFF[2]
                );
                this.mane.xRot = this.body.xRot;
                this.tail.setPos(-1, 18, 8);
                this.legBackRight.setPos(-4.5F, 23, 7);
                this.legBackRight.xRot = -(float)Math.PI / 2F;
                this.legBackLeft.setPos(2.5F, 23, 7);
                this.legBackLeft.xRot = -(float)Math.PI / 2F;
                this.legFrontRight.setPos(-4.5F, 23, -4);
                this.legFrontRight.xRot = -(float)Math.PI / 2F;
                this.legFrontLeft.setPos(2.5F, 23, -4);
                this.legFrontLeft.xRot = -(float)Math.PI / 2F;
            }  else {
                this.head.setPos(-1.0F, 13.5F, -7.0F);
                this.mane.setPos(
                    -1f + MANE_SITTING_OFF[0],
                    14f + MANE_SITTING_OFF[1], 
                    -3f + MANE_SITTING_OFF[2]
                );
                this.mane.xRot = ((float)Math.PI * 2F / 5F);
                this.mane.yRot = 0.0F;
                this.body.setPos(0.0F, 18.0F, 0.0F);
                this.body.xRot = ((float)Math.PI / 4F);
                this.tail.setPos(-1F, 21.0F, 6.0F);
                this.legBackRight.setPos(-2.5F, 22.0F, 2.0F);
                this.legBackRight.xRot = ((float)Math.PI * 3F / 2F);
                this.legBackLeft.setPos(0.5F, 22.0F, 2.0F);
                this.legBackLeft.xRot = ((float)Math.PI * 3F / 2F);
                this.legFrontRight.xRot = 5.811947F;
                this.legFrontRight.setPos(-2.49F, 17.0F, -4.0F);
                this.legFrontLeft.xRot = 5.811947F;
                this.legFrontLeft.setPos(0.51F, 17.0F, -4.0F);


                this.head.setPos(-1.0F, 13.5F, -7.0F);
                this.legFrontRight.yRot = 0;
                this.legFrontLeft.yRot = 0;
            }
        } else {
            this.body.setPos(0.0F, 14.0F, 2.0F);
            this.body.xRot = ((float)Math.PI / 2F);
            this.mane.setPos(-1.0F, 14.0F, -3.0F);
            this.mane.xRot = this.body.xRot;
            this.tail.setPos(-1.0F, 12.0F, 8.0F);
            this.legBackRight.setPos(-2.5F, 16.0F, 7.0F);
            this.legBackLeft.setPos(0.5F, 16.0F, 7.0F);
            this.legFrontRight.setPos(-2.5F, 16.0F, -4.0F);
            this.legFrontLeft.setPos(0.5F, 16.0F, -4.0F);
            this.legBackRight.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legBackLeft.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.legFrontRight.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.legFrontLeft.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

            this.head.setPos(-1.0F, 13.5F, -7.0F);
            this.legFrontRight.yRot = 0.0F;
            this.legFrontLeft.yRot = 0.0F;
        }

        this.realHead.zRot = dog.getInterestedAngle(partialTickTime) + dog.getShakeAngle(partialTickTime, 0.0F);
        this.mane.zRot = dog.getShakeAngle(partialTickTime, -0.08F);
        this.body.zRot = dog.getShakeAngle(partialTickTime, -0.16F);
        this.realTail.zRot = dog.getShakeAngle(partialTickTime, -0.2F);
        this.realTail2.zRot = dog.getShakeAngle(partialTickTime, -0.2F);
        this.realTail3.zRot = dog.getShakeAngle(partialTickTime, -0.2F);

        /*
         * else if (dog.isLying() && false) {
                this.body.setPos(0.0F, 19.0F, 2.0F);
                this.body.xRot = ((float)Math.PI / 2F);
                this.mane.setPos(-1.0F, 19.0F, -3.0F);
                this.mane.xRot = this.body.xRot;
                this.head.setPos(-1.0F, 17.0F, -7.0F);

                this.tail.setPos(-0.5F, 17.0F, 8.0F); // +4.0D
                this.legBackRight.setPos(-4.5F, 20.0F, 7.0F);
                this.legBackLeft.setPos(2.5F, 20.0F, 7.0F);
                this.legFrontRight.setPos(-3.0F, 22.0F, -3.0F);
                this.legFrontLeft.setPos(1.0F, 22.0F, -3.0F);

                this.legBackRight.xRot = -(float)Math.PI / 2.6F;
                this.legBackLeft.xRot = -(float)Math.PI / 2.6F;

                this.legFrontRight.xRot = -(float)Math.PI / 2;
                this.legFrontRight.yRot = (float)Math.PI / 10;
                this.legFrontLeft.xRot = -(float)Math.PI / 2;
                this.legFrontLeft.yRot = -(float)Math.PI / 10;
            }
         */

    }
    
}
