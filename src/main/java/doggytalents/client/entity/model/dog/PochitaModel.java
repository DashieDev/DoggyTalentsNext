package doggytalents.client.entity.model.dog;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PochitaModel extends DogModel {

    public PochitaModel(ModelPart box) {
        super(box);
    }
    
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
        var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));
		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.5F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(1.5F))
		.texOffs(0, 10).addBox(-3.25F, 0.25F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 10).mirror().addBox(0.0F, 0.25F, -4.85F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.ZERO);

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(48, 18).mirror().addBox(-5.25F, -2.25F, -7.75F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(48, 18).addBox(4.25F, -2.25F, -7.75F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -2.52F, -3.0F, -2.1817F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(46, 16).addBox(-7.75F, -2.5F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, -2.52F, -3.0F, -1.5708F, 0.6109F, 1.5708F));

		PartDefinition chainsaw = real_head.addOrReplaceChild("chainsaw", CubeListBuilder.create(), PartPose.offset(0.0F, -3.52F, -4.0F));

		PartDefinition head_r3 = chainsaw.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(56, 5).addBox(-0.5F, 5.5F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 5.5F, 7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 5.5F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 5.5F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 5.5F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 5.5F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 5.5F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 5.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, 7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -5.8481F, -4.1292F, -0.5236F, 0.0F, 0.0F));

		PartDefinition head_r4 = chainsaw.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(56, 5).addBox(-0.5F, -2.5F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -1.5F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, -0.5F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 0.5F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 5).addBox(-0.5F, 1.5F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(46, 16).addBox(-0.5F, -2.5F, -6.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.25F, 8.0F, 6.0F, 7.0F, new CubeDeformation(1.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(1.6F)), PartPose.offsetAndRotation(0.0F, -0.4754F, -1.1252F, -0.2182F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.75F, -0.5F, 0.0F, 2.0F, 8.5F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 8.5F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.5F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-2.5F, 16.5F, 5.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.25F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(2.5F, 16.5F, 5.0F));

		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 12.0F, 8.0F));
        PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(56, 1).mirror().addBox(-0.7993F, -0.6839F, -0.9961F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F)).mirror(false), PartPose.offsetAndRotation(0.7159F, 8.2008F, 1.4173F, 0.1515F, -0.0869F, 0.5606F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(56, 1).addBox(-1.0446F, -0.5938F, -0.9961F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.7159F, 8.2008F, 1.4173F, 0.1515F, 0.0869F, -0.4734F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(56, 1).addBox(-2.3071F, -1.4205F, -0.9961F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.7159F, 8.2008F, 1.4173F, 0.0F, 0.1745F, -1.5272F));

		PartDefinition tail_r4 = real_tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(56, 1).addBox(-0.9099F, -3.8519F, -0.9961F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.7159F, 8.2008F, 1.4173F, 0.1745F, 0.0F, 0.0436F));

		PartDefinition tail_r5 = real_tail.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, -0.25F, -1.25F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

}

