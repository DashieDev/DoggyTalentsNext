package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class EnglishBulldogModel extends DogModel {

    public EnglishBulldogModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 15.0F, 7.25F, 1.9635F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(9, 18).addBox(0.75F, 0.75F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(9, 18).addBox(0.05F, 2.1F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(9, 18).addBox(0.85F, 1.7F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.55F))
		.texOffs(9, 18).addBox(-0.65F, 1.6F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.55F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.5F, 18.0F, 5.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(1.5F, 18.0F, 5.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.5F, 18.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(2.5F, 18.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 16.0F, 1.75F, -0.2182F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -12.0F, -1.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.0F, 9.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(21, 0).addBox(-3.0F, -3.0F, -3.75F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(21, 0).addBox(-3.0F, -3.0F, -2.25F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(21, 0).addBox(-3.0F, -3.0F, -5.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(-1.0F, 16.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(1, 2).addBox(-3.0F, -4.2F, -1.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.45F))
		.texOffs(1, 2).addBox(-3.0F, -3.75F, -2.15F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.35F))
		.texOffs(1, 1).addBox(-3.0F, -3.25F, -2.6F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.35F))
		.texOffs(12, 11).addBox(0.75F, 0.08F, -3.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(12, 11).addBox(-1.75F, 0.08F, -3.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(12, 11).addBox(-2.15F, 0.33F, -3.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 11).addBox(1.15F, 0.33F, -3.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-1.5F, -0.47F, -4.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(3.1125F, -2.55F, -0.5625F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r1 = left_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(45, 3).mirror().addBox(1.075F, -0.725F, -1.375F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-0.0375F, -0.175F, -0.0625F, -1.3552F, -0.0612F, -1.0574F));

		PartDefinition head_r2 = left_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(0.575F, -0.475F, -1.375F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-0.0375F, -0.175F, -0.0625F, -1.0313F, -1.1421F, -1.5427F));

		PartDefinition head_r3 = left_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(0.774F, -0.8904F, -1.6096F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(45, 0).mirror().addBox(0.125F, -0.925F, -1.625F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-0.0375F, -0.175F, -0.0625F, -0.7854F, -1.4835F, -2.0944F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.1125F, -2.55F, -0.5625F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(45, 3).addBox(-2.075F, -0.725F, -1.375F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0375F, -0.175F, -0.0625F, -1.3552F, 0.0612F, 1.0574F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(45, 0).addBox(-1.575F, -0.475F, -1.375F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0375F, -0.175F, -0.0625F, -1.0313F, 1.1421F, 1.5427F));

		PartDefinition head_r6 = right_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(45, 0).addBox(-1.774F, -0.8904F, -1.6096F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(45, 0).addBox(-1.125F, -0.925F, -1.625F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0375F, -0.175F, -0.0625F, -0.7854F, 1.4835F, 2.0944F));

		return LayerDefinition.create(meshdefinition, 64, 32);
    }
}
