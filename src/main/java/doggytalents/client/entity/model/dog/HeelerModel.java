package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class HeelerModel extends DogModel{

    public HeelerModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 5.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(58, 49).addBox(-1.0F, 0.0207F, -1.6623F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2207F, 0.2623F, 1.789F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(59, 19).mirror().addBox(-0.9F, 2.4346F, 0.8475F, 2.0F, 4.6F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 17.0F, 4.8133F));

		PartDefinition cube_r1 = right_hind_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(80, 20).mirror().addBox(-1.4F, -2.7663F, -1.3353F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.0009F, 0.6828F, 0.6545F, 0.0F, 0.0F));

		PartDefinition cube_r2 = right_hind_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(65, 5).mirror().addBox(-1.4F, -2.9663F, -0.5353F, 2.0F, 5.0F, 2.2F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.0009F, 0.6828F, 0.281F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(59, 19).addBox(-1.1F, 2.4346F, 0.8475F, 2.0F, 4.6F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 17.0F, 4.8133F));

		PartDefinition cube_r3 = left_hind_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(80, 20).addBox(-0.6F, -2.7663F, -1.3353F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, 1.0009F, 0.6828F, 0.6545F, 0.0F, 0.0F));

		PartDefinition cube_r4 = left_hind_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(65, 5).addBox(-0.6F, -2.9663F, -0.5353F, 2.0F, 5.0F, 2.2F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, 1.0009F, 0.6828F, 0.281F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(57, 4).addBox(-1.2625F, -0.9654F, -0.7059F, 1.95F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4875F, 17.0F, -5.7333F));

		PartDefinition cube_r5 = right_front_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(71, 23).addBox(-4.3F, 0.0F, -1.7F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.9875F, 0.0346F, 0.2941F, 0.1745F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(57, 4).mirror().addBox(-0.6375F, -0.9654F, -0.7059F, 1.95F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.4875F, 17.0F, -5.7333F));

		PartDefinition cube_r6 = left_front_leg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(71, 23).mirror().addBox(2.3F, 0.0F, -1.7F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-2.9875F, 0.0346F, 0.2941F, 0.1745F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.4565F, 1.3623F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(2, 110).addBox(-3.0F, -3.0F, -5.5609F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.6781F, 0.4986F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body_r1 = bone.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(4, 122).addBox(-3.0F, -1.5539F, -1.8444F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.6307F, -0.9165F, -0.6109F, 0.0F, 0.0F));

		PartDefinition body_r2 = bone.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(24, 101).addBox(-3.0F, -1.2F, -2.8132F, 6.0F, 5.0F, 4.5F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -1.7F, -6.6868F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r7 = bone.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 102).addBox(-2.95F, -0.77F, 0.0F, 5.9F, 1.8F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -7.9F, 0.2618F, 0.0F, 0.0F));

		PartDefinition body_r3 = bone.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(29, 117).addBox(-3.0F, -1.2006F, -0.9444F, 6.0F, 5.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -0.6307F, -0.9165F, 0.0436F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0019F, -4.6288F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone2 = upper_body.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 56).addBox(-2.95F, -3.0F, -2.0F, 6.0F, 7.0F, 5.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.1326F, 0.9897F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r8 = bone2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(21, 54).addBox(-3.0F, 0.0F, -0.5774F, 6.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 1.0937F, -2.9226F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r9 = bone2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(19, 62).addBox(-2.55F, -1.2422F, -3.3F, 5.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.7578F, -2.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r10 = bone2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 74).addBox(-3.0F, -3.4109F, -4.0388F, 6.0F, 5.0F, 7.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.5109F, -2.5221F, -0.7418F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, -9.5F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.9654F, -2.1391F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 17).addBox(-1.0F, -0.3F, -5.6391F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.5F));

		PartDefinition cube_r11 = real_head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, -0.2055F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.5945F, 2.152F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r12 = real_head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(11, 20).addBox(-2.5F, -0.551F, -3.2099F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.451F, 2.7099F, -0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r13 = real_head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(13, 27).addBox(-3.2F, -3.2422F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.2768F, 0.0609F, 0.0F, -0.48F, 0.0F));

		PartDefinition cube_r14 = real_head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(17, 11).addBox(-2.0F, -0.5F, -0.4276F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2467F, -0.9687F, -2.1724F, -0.0436F, 0.1745F, 0.0F));

		PartDefinition cube_r15 = real_head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(13, 27).mirror().addBox(1.2F, -3.2422F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.2768F, 0.0609F, 0.0F, 0.48F, 0.0F));

		PartDefinition cube_r16 = real_head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(17, 11).mirror().addBox(0.0F, -0.5F, -0.3276F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2467F, -0.9687F, -2.1724F, -0.0436F, -0.0873F, 0.0F));

		PartDefinition cube_r17 = real_head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -0.8422F, -0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.7768F, -4.9391F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r18 = real_head.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(27, 28).addBox(-1.0F, -0.8317F, -1.159F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5337F, -2.2801F, 0.8727F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.5346F, -2.9391F, -0.1745F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(37, 20).addBox(-0.9879F, -1.1967F, -0.5F, 2.0F, 1.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(39, 4).addBox(-0.3879F, -3.1811F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4879F, -2.2687F, 0.0609F));

		PartDefinition cube_r19 = right_ear.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(38, 17).addBox(-0.3624F, -0.5292F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1745F, 0.4324F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition cube_r20 = right_ear.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(39, 9).addBox(-1.3F, -1.5256F, -0.5F, 1.0F, 3.6F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2121F, -1.2711F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r21 = right_ear.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(39, 14).addBox(-1.47F, -1.118F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3121F, -2.1387F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(37, 20).mirror().addBox(-1.0121F, -1.1967F, -0.5F, 2.0F, 1.5F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(39, 4).mirror().addBox(-0.6121F, -3.1811F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.4879F, -2.2687F, 0.0609F));

		PartDefinition cube_r22 = left_ear.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(38, 17).mirror().addBox(-0.6376F, -0.5292F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1745F, 0.4324F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition cube_r23 = left_ear.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(39, 9).mirror().addBox(0.3F, -1.5256F, -0.5F, 1.0F, 3.6F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2121F, -1.2711F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r24 = left_ear.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(39, 14).mirror().addBox(0.47F, -1.118F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.3121F, -2.1387F, 0.0F, 0.0F, 0.0F, 0.1745F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
