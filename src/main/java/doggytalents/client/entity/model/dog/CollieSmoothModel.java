package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CollieSmoothModel extends DogModel{

    public CollieSmoothModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 9.75F, -6.75F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -0.75F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 0).addBox(-3.0F, -3.75F, -1.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.8F)), PartPose.offset(0.0F, -0.25F, -1.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -0.5F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 10).addBox(-1.5F, -1.5F, -2.75F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 1.48F, -1.25F, 0.2182F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(29, 36).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, 1.0F, 2.25F, 0.4363F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -1.75F, -0.25F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r3 = right_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(45, 3).addBox(-2.1514F, -1.9438F, -1.1081F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-1.1885F, -0.4974F, 0.375F, -1.3552F, 0.0612F, 1.0574F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(45, 0).addBox(-2.7119F, -0.9208F, -1.1081F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.1885F, -0.4974F, 0.375F, -1.0313F, 1.1421F, 1.5427F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(45, 0).addBox(-3.0193F, -0.9674F, -1.5326F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(45, 0).addBox(-2.3702F, -1.002F, -1.548F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-1.1885F, -0.4974F, 0.375F, -0.7854F, 1.4835F, 2.0944F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -1.75F, -0.25F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(45, 3).mirror().addBox(1.1514F, -1.9438F, -1.1081F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(1.1885F, -0.4974F, 0.375F, -1.3552F, -0.0612F, -1.0574F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(1.7119F, -0.9208F, -1.1081F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.1885F, -0.4974F, 0.375F, -1.0313F, -1.1421F, -1.5427F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(2.0193F, -0.9674F, -1.5326F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(45, 0).mirror().addBox(1.3702F, -1.002F, -1.548F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(1.1885F, -0.4974F, 0.375F, -0.7854F, -1.4835F, -2.0944F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0781F, -3.8749F));

		PartDefinition bone = upper_body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.3561F, -3.0176F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 4.3898F, 0.1785F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = bone.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -2.1441F, -0.6384F, 6.0F, 5.0F, 7.0F, new CubeDeformation(-0.8F)), PartPose.offsetAndRotation(0.0F, -0.462F, 0.6208F, 0.48F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -12.75F, -2.5F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 2.0F, 10.0F, 1.5272F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.5F, -2.0F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.0F, -3.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-0.5F, -2.0F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 18.0F, -3.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 16.0F, 7.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 13.1F, 7.0F, 2.0508F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.35F, -3.0F));

		PartDefinition tail2 = real_tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(9, 20).addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.4F))
		.texOffs(10, 23).addBox(0.0F, 6.2F, 0.7F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(10, 22).addBox(0.0F, 3.3F, -0.1F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.15F))
		.texOffs(10, 23).addBox(0.0F, 7.4F, 1.3F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(9, 23).addBox(0.0F, 8.5F, 1.8F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 1.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
