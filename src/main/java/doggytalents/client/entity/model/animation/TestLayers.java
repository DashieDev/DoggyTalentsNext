package doggytalents.client.entity.model.animation;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class TestLayers {
    
    public static LayerDefinition test1() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -3.65F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.35F))
		.texOffs(0, 10).addBox(-0.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 13.5F, -7.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(15, 15).addBox(-1.5F, 0.0F, -2.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F))
		.texOffs(16, 12).addBox(-1.7F, 0.6F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.04F))
		.texOffs(16, 12).addBox(-2.3F, 0.85F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-3.05F, 2.0F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-2.55F, 1.5F, -2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.25F, -3.0F, 0.5F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 12).mirror().addBox(1.8F, 1.5F, -2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(2.3F, 2.0F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(1.55F, 0.85F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(0.95F, 0.6F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.04F)).mirror(false)
		.texOffs(15, 15).mirror().addBox(-0.25F, 0.0F, -2.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offset(3.0F, -3.0F, 0.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, -3.0F));

		PartDefinition mane_rotation = upper_body.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -5.5F, -0.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, 2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition collarntie = mane_rotation.addOrReplaceChild("collarntie", CubeListBuilder.create().texOffs(54, 2).addBox(-3.0F, -6.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(54, 2).addBox(-4.0F, -5.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(54, 2).addBox(-4.5F, -5.25F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(54, 2).mirror().addBox(2.0F, -6.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(54, 4).mirror().addBox(-0.5F, -5.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(54, 4).mirror().addBox(-0.5F, -4.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(54, 4).mirror().addBox(-0.5F, -3.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(54, 4).mirror().addBox(-0.5F, -2.25F, -0.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(54, 4).mirror().addBox(-0.5F, -1.0F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(54, 2).mirror().addBox(3.0F, -5.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(54, 2).mirror().addBox(3.5F, -5.25F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(54, 2).addBox(2.0F, -6.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(54, 2).addBox(-3.0F, -6.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(44, 19).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(44, 19).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 1.9199F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

    public static LayerDefinition test2() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 1.6144F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition body_no_use = partdefinition.addOrReplaceChild("body_no_use", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body = body_no_use.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.0F, 0.5F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -3.0F, 0.5F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

    public static LayerDefinition test3() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 31).addBox(-1.0F, -4.3F, -1.65F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(0.5F, -4.3F, -1.4F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(1.25F, -3.85F, 0.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 40).addBox(-3.25F, -3.85F, 0.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 40).addBox(-1.45F, 1.15F, 0.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 47).addBox(-1.0F, -5.2F, -1.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(-2.75F, -4.3F, -1.4F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(-2.75F, -3.5F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(1.0F, -3.5F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 30).addBox(-4.0F, -2.25F, -0.25F, 8.0F, 6.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(42, 36).addBox(3.0F, 2.15F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(23, 34).addBox(3.05F, -1.55F, -1.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(47, 44).addBox(2.75F, 0.2F, -1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(43, 34).addBox(2.0F, 1.2F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 31).addBox(2.0F, -2.95F, 2.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(51, 34).addBox(0.75F, -4.2F, 0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(42, 36).addBox(0.0F, 0.0F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(47, 44).addBox(1.25F, -2.25F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(43, 34).addBox(0.5F, -3.5F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(51, 34).addBox(-1.5F, -3.95F, 2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(42, 36).addBox(-3.0F, 0.0F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(47, 44).addBox(-4.25F, -2.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(43, 34).addBox(-3.5F, -3.5F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 36).addBox(-4.0F, 2.15F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(42, 37).addBox(-4.5F, -2.3F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(40, 41).addBox(2.75F, -1.55F, -1.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-4.75F, -1.55F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(41, 41).addBox(-4.75F, -1.8F, -0.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(47, 35).addBox(4.0F, -0.3F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(47, 35).addBox(-5.0F, -0.3F, -1.4F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(47, 44).addBox(-3.25F, 2.7F, -0.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(47, 44).addBox(-5.75F, 0.2F, -1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(43, 34).addBox(-5.0F, 1.2F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -4.25F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.5F, -7.0F));

		PartDefinition mane_rotation_r1 = head.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create()
        .texOffs(43, 30).addBox(-2.75F, -6.25F, 12.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.45F))
		.texOffs(47, 40).addBox(-2.5F, -6.75F, 11.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.55F))
		.texOffs(47, 40).addBox(-0.5F, -5.75F, 10.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(47, 40).addBox(0.25F, -5.75F, 12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 13.5F, 7.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 14).addBox(0.3F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(1.8F, -0.75F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(0.8F, -1.25F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(2.3F, -1.5F, -0.2F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(52, 13).mirror().addBox(0.8F, -1.75F, -0.1F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.0F, -2.0F, -1.5F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-2.3F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(16, 14).addBox(-3.8F, -0.75F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(52, 13).addBox(-4.8F, -1.75F, -0.1F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(16, 14).addBox(-2.8F, -1.25F, -0.2F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(-4.3F, -1.5F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(-2.0F, -2.0F, -1.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition bone8 = right_hind_leg.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(1, 48).mirror().addBox(-2.6F, -3.5F, 5.6F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 49).mirror().addBox(-2.85F, -2.75F, 4.6F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.5F, 6.85F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.0F, 8.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.4F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.75F, 9.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 50).mirror().addBox(-3.35F, -5.5F, 8.85F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.35F, -7.0F, 8.95F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(1, 50).mirror().addBox(-3.35F, -5.75F, 10.7F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(1, 50).mirror().addBox(-3.35F, -6.25F, 9.95F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-3.1F, -5.25F, 7.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-3.1F, -3.75F, 7.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.0F, 8.0F, -6.25F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition bone7 = left_hind_leg.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(1, 48).addBox(0.6F, -3.5F, 5.6F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(0, 49).addBox(-0.15F, -2.75F, 4.6F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(1, 48).addBox(1.1F, -4.5F, 6.85F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.3F))
		.texOffs(1, 48).addBox(1.1F, -4.0F, 8.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.4F))
		.texOffs(1, 48).addBox(1.1F, -4.75F, 9.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(0, 50).addBox(1.35F, -5.5F, 8.85F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(1, 48).addBox(1.35F, -7.0F, 8.95F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(1, 50).addBox(1.35F, -5.75F, 10.7F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(1, 50).addBox(1.35F, -6.25F, 9.95F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 55).addBox(2.1F, -5.25F, 7.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 55).addBox(2.1F, -3.75F, 7.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-1.0F, 8.0F, -6.25F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition bone4 = right_front_leg.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(1, 48).mirror().addBox(-3.1F, -4.0F, -3.9F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.75F, -3.4F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.0F, -2.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.75F, -1.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.6F, -5.25F, -1.9F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-3.1F, -5.75F, -2.65F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-3.1F, -3.75F, -2.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offset(2.0F, 8.0F, 3.5F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition bone3 = left_front_leg.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(1, 48).addBox(1.1F, -4.0F, -3.9F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(1, 48).addBox(1.1F, -4.75F, -3.4F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F))
		.texOffs(1, 48).addBox(1.1F, -4.0F, -2.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(1, 48).addBox(1.1F, -4.75F, -1.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(1, 48).addBox(1.6F, -5.25F, -1.9F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 55).addBox(2.1F, -5.75F, -2.65F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 55).addBox(2.1F, -3.75F, -2.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offset(-1.0F, 8.0F, 3.5F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 12.0F, 8.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(54, 56).addBox(-0.625F, 3.525F, 14.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 56).addBox(-0.625F, 4.225F, 14.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(54, 56).addBox(-0.625F, 5.525F, 16.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(54, 56).addBox(-0.625F, 3.775F, 15.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 56).addBox(-0.625F, 0.775F, 9.625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 56).addBox(-0.625F, 0.525F, 8.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 56).addBox(-0.625F, 6.225F, 14.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.55F))
		.texOffs(54, 56).addBox(-0.375F, 5.475F, 15.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.45F))
		.texOffs(54, 56).addBox(-0.625F, 1.225F, 8.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(53, 55).addBox(-0.625F, 1.475F, 7.125F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(53, 55).addBox(-0.625F, 5.725F, 11.875F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.45F))
		.texOffs(53, 55).addBox(-0.625F, 6.475F, 13.125F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(52, 55).addBox(-1.125F, 4.475F, 11.125F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(41, 53).addBox(-1.125F, 3.475F, 8.125F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.25F))
		.texOffs(24, 52).addBox(-1.625F, 1.725F, 6.125F, 3.0F, 4.0F, 4.0F, new CubeDeformation(-0.35F))
		.texOffs(24, 52).addBox(-1.625F, 2.475F, 6.125F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 40).addBox(-2.125F, 3.475F, 5.125F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(23, 30).addBox(-2.375F, 7.975F, 3.575F, 5.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(23, 30).addBox(-2.375F, 7.975F, 1.575F, 5.0F, 3.0F, 3.0F, new CubeDeformation(-0.4F))
		.texOffs(23, 30).addBox(-2.625F, 6.475F, 2.575F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.35F))
		.texOffs(23, 30).addBox(-2.625F, 5.475F, 3.875F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.125F, -3.725F, 7.875F, -1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_fur = upper_body.addOrReplaceChild("mane_fur", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -4.75F, -4.5F, 8.0F, 5.0F, 7.0F, new CubeDeformation(-0.35F))
		.texOffs(43, 30).addBox(-2.75F, -6.0F, -5.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(32, 36).addBox(-4.75F, -6.5F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.35F))
		.texOffs(32, 36).addBox(-5.25F, -7.0F, -3.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.35F))
		.texOffs(32, 36).addBox(-2.75F, -7.5F, -2.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.35F))
		.texOffs(32, 36).addBox(0.5F, -7.5F, -2.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.35F))
		.texOffs(31, 35).addBox(-1.25F, -8.5F, -3.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(32, 36).mirror().addBox(3.0F, -7.0F, -3.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.35F)).mirror(false)
		.texOffs(32, 36).mirror().addBox(2.75F, -6.5F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.35F)).mirror(false)
		.texOffs(0, 40).addBox(1.0F, -6.35F, -5.9F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(0, 40).addBox(0.25F, -7.3F, -6.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(51, 34).addBox(0.5F, -7.2F, -4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(0, 31).addBox(-1.25F, -6.8F, -7.65F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.25F))
		.texOffs(0, 47).addBox(-1.25F, -8.2F, -6.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(51, 34).addBox(-3.75F, -7.2F, -4.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(0, 40).addBox(-3.0F, -7.05F, -6.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(-3.5F, -6.35F, -5.4F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(0, 40).addBox(-3.5F, -6.35F, -7.15F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 40).mirror().addBox(-2.75F, -7.05F, -7.9F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(0, 31).addBox(-1.25F, -6.55F, -8.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(0.25F, -7.05F, -7.9F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(1.0F, -6.35F, -7.15F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offset(1.0F, 2.5F, 2.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    public static LayerDefinition test4() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 0).addBox(-1.5F, -0.07F, -4.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(33, 24).addBox(-0.5F, -0.77F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F))
		.texOffs(32, 24).addBox(-0.5F, -0.53F, -2.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = left_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.3F, -0.3F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.5F, -1.2F, -0.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-0.6F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-1.2F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-0.9F, -1.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.6753F, -3.9F, -0.2839F, 0.0F, 0.6545F, 0.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r2 = right_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.1F, -1.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 13).addBox(0.2F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 13).addBox(-0.4F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(18, 13).addBox(-1.5F, -1.2F, -0.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F))
		.texOffs(0, 0).addBox(-0.7F, -0.3F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.6753F, -3.9F, -0.2839F, 0.0F, -0.6545F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane = partdefinition.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, 2.0F));

		PartDefinition mane_rotation = mane.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.5F, -0.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(3.1F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).mirror().addBox(-4.1F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 2.5F, -2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).mirror().addBox(-0.1F, 1.7F, -2.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).addBox(1.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).addBox(1.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 23).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 24).addBox(0.0F, 5.0F, 1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(24, 24).addBox(0.0F, 2.0F, 0.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(24, 24).addBox(0.0F, 4.0F, 3.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(33, 24).mirror().addBox(-0.15F, -0.3F, -1.4F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(33, 24).mirror().addBox(2.1F, -0.3F, -1.4F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 12.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    public static LayerDefinition test5() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(1, 1).addBox(-3.0F, -3.7F, -1.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.35F))
		.texOffs(54, 0).addBox(0.75F, 0.48F, -4.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(54, 0).mirror().addBox(-1.75F, 0.48F, -4.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 10).addBox(-1.5F, -0.22F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(15, 15).addBox(-3.75F, -13.5F, -8.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 12).addBox(-3.95F, -12.9F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(16, 12).addBox(-4.55F, -12.4F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-4.8F, -11.5F, -9.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(0.0F, 10.5F, 7.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(15, 15).mirror().addBox(1.75F, -13.5F, -8.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(2.95F, -12.9F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(3.55F, -12.15F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(3.8F, -11.5F, -9.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(0.0F, 10.5F, 7.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane = partdefinition.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, 2.0F));

		PartDefinition mane_rotation = mane.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -5.5F, -0.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, -2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition scarf = mane_rotation.addOrReplaceChild("scarf", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -13.0F, -1.0F, 8.0F, 13.0F, 7.0F, new CubeDeformation(0.1F))
		.texOffs(28, 47).addBox(-4.0F, -13.7F, -7.0F, 8.0F, 6.0F, 10.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 28).addBox(-1.0F, -15.0F, -3.6F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 7.5F, 0.5F));

		PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(44, 19).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(44, 19).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 12.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    public static LayerDefinition test6() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 12.0F, 8.0F, 1.6144F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(9, 18).addBox(-1.0F, 4.0F, 0.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(9, 18).addBox(-1.0F, 6.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -2.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 10).addBox(-1.5F, -0.498F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.101F)), PartPose.offset(0.0F, 0.63F, 0.15F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.0F, -3.0F, 0.5F));

		PartDefinition head_r1 = left_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.2778F, 0.4249F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.4388F, -1.8909F, 0.45F, -0.3541F, -0.9128F, 0.8297F));

		PartDefinition head_r2 = left_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.2778F, -0.5751F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.4888F, -0.5409F, -0.1F, -0.3541F, -0.9128F, 0.8297F));

		PartDefinition head_r3 = left_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(11, 29).addBox(-1.2778F, 0.4249F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.0676F, -2.4108F, 1.4146F, -0.3541F, -0.9128F, 0.8297F));

		PartDefinition head_r4 = left_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(16, 14).addBox(0.3612F, -0.4591F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3888F, -0.7909F, 0.0F, 0.0F, -0.9599F, 0.3927F));

		PartDefinition head_r5 = left_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(16, 14).addBox(-0.3611F, -1.5972F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.2888F, -0.4409F, 0.0F, -0.3541F, -0.9128F, 0.8297F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(16, 14).addBox(-1.3888F, -0.7091F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.3888F, -0.5409F, 0.0F, 0.0F, -0.9599F, 0.3927F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-2.0F, -3.0F, 0.5F));

		PartDefinition head_r7 = right_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.6112F, -0.7091F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-0.3888F, -0.5409F, 0.0F, 0.0F, 0.9599F, -0.3927F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(11, 29).mirror().addBox(-0.6389F, -1.5972F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.2888F, -0.4409F, 0.0F, -0.3541F, 0.9128F, -0.8297F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(16, 14).addBox(0.2778F, 0.4249F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.0676F, -2.4108F, 1.4146F, -0.3541F, 0.9128F, -0.8297F));

		PartDefinition head_r10 = right_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(16, 14).addBox(0.2778F, 0.4249F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.4388F, -1.8909F, 0.45F, -0.3541F, 0.9128F, -0.8297F));

		PartDefinition head_r11 = right_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(16, 14).addBox(0.2778F, -0.5751F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4888F, -0.5409F, -0.1F, -0.3541F, 0.9128F, -0.8297F));

		PartDefinition head_r12 = right_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.3612F, -0.4591F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.3888F, -0.7909F, 0.0F, 0.0F, 0.9599F, -0.3927F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

}
