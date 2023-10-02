package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ShibaModel extends DogModel {

    public ShibaModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 15.75F, 5.5F, 1.1345F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(1.0F, 1.1F, -1.0F));

		PartDefinition real_tail2 = tail2.addOrReplaceChild("real_tail2", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.35F, -3.0F));

		PartDefinition tail3 = real_tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition tail_r1 = tail3.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 21).addBox(0.25F, 0.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(4.9933F, 4.1971F, 2.05F, -0.7418F, 0.0F, 2.5307F));

		PartDefinition tail_r2 = tail3.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 21).addBox(-0.5F, -1.5F, -1.4F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(4.9933F, 4.1971F, 2.05F, 0.0F, 0.0F, 2.5307F));

		PartDefinition tail_r3 = tail3.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(9, 21).addBox(-1.25F, 1.4F, -0.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, 6.5F, 0.15F, 0.0F, 0.0F, -1.7453F));

		PartDefinition tail_r4 = tail3.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(9, 20).addBox(-1.0F, -0.2F, -1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.5F, 1.4F, 0.0F, 0.0F, -0.48F));

		PartDefinition tail_r5 = tail3.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(9, 21).addBox(-1.0F, -0.8F, -1.95F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(1.0F, 6.5F, 1.4F, 0.0F, 0.0F, -1.4835F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(52, 0).addBox(-0.5F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(52, 0).addBox(0.5F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -4.1F, -3.25F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 3.5F, 1.6144F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -2.5F, -4.15F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, 15.5F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -5.25F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(37, 14).addBox(-1.5F, -0.2F, -1.7F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 1.73F, -3.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.75F, -2.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 1.73F, -3.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 3.0F, -1.25F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 5.0F, -0.8727F, 0.0F, 0.0F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(54, 23).mirror().addBox(-0.7F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(3.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition head_r5 = real_head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(52, 24).mirror().addBox(-0.35F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(-0.09F)).mirror(false), PartPose.offsetAndRotation(3.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.2182F));

		PartDefinition head_r6 = real_head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(53, 25).mirror().addBox(0.0F, 0.4F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(3.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r7 = real_head.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(53, 25).addBox(-1.0F, 0.4F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition head_r8 = real_head.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(52, 24).addBox(-0.65F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(-3.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition head_r9 = real_head.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(53, 22).addBox(-0.3F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition headfur = real_head.addOrReplaceChild("headfur", CubeListBuilder.create(), PartPose.offset(0.0F, 12.5F, 7.3F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -2.0F, 0.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r10 = right_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(23, 14).mirror().addBox(-0.25F, -2.95F, -0.35F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 0.75F, -0.25F, 0.0F, 0.0F, -0.3927F));

		PartDefinition head_r11 = right_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.45F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 1.25F, 0.0F, 0.1745F, 0.0F, -0.3054F));

		PartDefinition head_r12 = right_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.2F, -2.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(0.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 1.25F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -2.0F, 0.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r13 = left_ear.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(23, 14).addBox(-1.75F, -2.95F, -0.35F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.25F, 0.75F, -0.25F, 0.0F, 0.0F, 0.3927F));

		PartDefinition head_r14 = left_ear.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(16, 14).addBox(-1.55F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.25F, 1.25F, 0.0F, 0.1745F, 0.0F, 0.3054F));

		PartDefinition head_r15 = left_ear.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(16, 14).addBox(-1.8F, -2.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 14).addBox(-2.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 1.25F, 0.0F, 0.0F, 0.0F, 0.3054F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
