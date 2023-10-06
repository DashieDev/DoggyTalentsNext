package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ChuckleModel extends DogModel {

    public ChuckleModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(47, 2).addBox(-1.0F, -0.2F, -1.4F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.2F, 4.75F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(56, 1).mirror().addBox(-0.9F, 3.4346F, -0.1525F, 2.0F, 4.6F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 16.0F, 5.8133F));

		PartDefinition cube_r1 = right_hind_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(54, 15).mirror().addBox(-1.4F, -2.9663F, -0.3462F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.5F, 2.0009F, -0.3172F, 0.281F, 0.0F, 0.0F));

		PartDefinition cube_r2 = right_hind_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 13).addBox(-4.4F, -3.7663F, -0.9962F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(3.5F, 2.0009F, -0.3172F, 0.48F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(56, 1).addBox(-1.1F, 3.4346F, -0.1525F, 2.0F, 4.6F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 16.0F, 5.8133F));

		PartDefinition cube_r3 = left_hind_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 13).addBox(-0.6F, -3.7663F, -0.9962F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(-0.5F, 2.0009F, -0.3172F, 0.48F, 0.0F, 0.0F));

		PartDefinition cube_r4 = left_hind_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(54, 15).addBox(-0.6F, -2.9663F, -0.3462F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, 2.0009F, -0.3172F, 0.281F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 12).addBox(-1.2625F, -0.9654F, -0.7059F, 1.95F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4875F, 17.0F, -5.7333F));

		PartDefinition cube_r5 = right_front_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(58, 9).addBox(-4.3F, 0.0F, -1.3609F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.9875F, 0.0346F, 0.2941F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 12).mirror().addBox(-0.6375F, -0.9654F, -0.7059F, 1.95F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.4875F, 17.0F, -5.7333F));

		PartDefinition cube_r6 = left_front_leg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(58, 9).mirror().addBox(2.3F, 0.0F, -1.3609F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-2.9875F, 0.0346F, 0.2941F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(40, 24).addBox(-2.9F, -2.8654F, -9.4391F, 6.0F, 5.0F, 4.5F, new CubeDeformation(0.0F))
		.texOffs(40, 52).addBox(-3.0F, -2.8F, -5.5F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body_r1 = bone.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(22, 52).addBox(-3.0F, -1.5539F, -1.6444F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.5961F, -0.8556F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r7 = bone.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(40, 42).addBox(-2.95F, -1.2346F, -0.7609F, 5.9F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0346F, -7.8391F, 0.1745F, 0.0F, 0.0F));

		PartDefinition body_r2 = bone.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(43, 35).addBox(-3.0F, -1.1039F, -0.8444F, 6.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -0.5961F, -0.8556F, 0.0436F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0019F, -4.6288F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone2 = upper_body.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 37).addBox(-2.95F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.2326F, -0.1103F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r8 = bone2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(23, 32).addBox(-3.0F, -1.2783F, -0.3383F, 6.0F, 4.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 1.0937F, -2.9226F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r9 = bone2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(17, 40).addBox(-2.55F, -1.7768F, -3.3F, 5.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.7578F, -2.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r10 = bone2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(1, 51).addBox(-3.0F, -4.5455F, -5.0388F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.5109F, -2.5221F, -0.7418F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 10.25F, -9.5F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.2154F, -1.6391F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(25, 17).addBox(-1.0F, -0.55F, -5.1391F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r11 = real_head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -0.4055F, -1.152F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.8445F, 2.652F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r12 = real_head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(12, 19).addBox(-2.5F, -1.151F, -3.1099F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.201F, 3.2099F, -0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r13 = real_head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(11, 29).addBox(-3.2F, -3.2422F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0268F, 0.5609F, 0.0F, -0.48F, 0.0F));

		PartDefinition cube_r14 = real_head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, -0.5F, -0.4276F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2467F, -1.2187F, -1.6724F, -0.0436F, 0.1745F, 0.0F));

		PartDefinition cube_r15 = real_head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(19, 26).mirror().addBox(1.2F, -3.2422F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0268F, 0.5609F, 0.0F, 0.48F, 0.0F));

		PartDefinition cube_r16 = real_head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(9, 11).mirror().addBox(0.0F, -0.5F, -0.3276F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2467F, -1.2187F, -1.6724F, -0.0436F, -0.0873F, 0.0F));

		PartDefinition cube_r17 = real_head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, -0.8422F, -0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5268F, -4.4391F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r18 = real_head.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(29, 24).addBox(-1.0F, -0.8317F, -1.159F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7837F, -1.7801F, 0.8727F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.2846F, -2.4391F, -0.1745F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(17, 11).addBox(-0.9879F, -0.6654F, -0.5F, 2.0F, 1.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 0).addBox(-0.5879F, -3.6497F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4879F, -3.05F, 0.5609F));

		PartDefinition cube_r19 = right_ear.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(21, 8).addBox(-0.3624F, -0.5292F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1745F, 0.9637F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition cube_r20 = right_ear.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(26, 0).addBox(-1.6F, -2.5602F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2121F, -0.7398F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition cube_r21 = right_ear.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(21, 14).addBox(-1.47F, -2.118F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3121F, -1.6074F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(23, 11).mirror().addBox(-1.0121F, -0.6654F, -0.5F, 2.0F, 1.5F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(31, 0).mirror().addBox(-0.3121F, -3.6497F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.4879F, -3.05F, 0.5609F));

		PartDefinition cube_r22 = left_ear.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(21, 6).mirror().addBox(-0.6376F, -0.5292F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1745F, 0.9637F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition cube_r23 = left_ear.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(21, 0).mirror().addBox(0.7F, -2.5602F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2121F, -0.7398F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition cube_r24 = left_ear.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(17, 14).mirror().addBox(-0.4626F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2495F, -2.0476F, 0.0F, 0.0F, 0.0F, 0.2182F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
