package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GermanShepherdModel extends DogModel {

    public GermanShepherdModel(ModelPart box) {
        super(box);
    }
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, 7.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(0, 53).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(6, 47).addBox(-1.0F, 0.3301F, -0.3682F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 7.5169F, 2.9029F, -2.3562F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, -1.0F, -1.45F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(0.0F, 4.3F, 0.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(8, 54).addBox(-1.0F, -1.0826F, -0.6379F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 7.5169F, 2.9029F, -1.8326F, 0.0F, 0.0F));

		PartDefinition tail_r4 = real_tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(8, 46).addBox(-1.0F, -2.2487F, -0.2212F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 7.5169F, 2.9029F, -1.3526F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.75F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-1.75F, 16.0F, 6.5F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.25F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(1.75F, 16.0F, 6.5F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(2.0F, 17.0F, -4.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.0F, 17.0F, -4.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.2561F, -3.5482F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 14.4518F, -3.7439F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(1, 32).addBox(-3.0F, -2.5905F, -2.5352F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.1F)), PartPose.offsetAndRotation(0.0F, -1.862F, 2.8402F, -0.9163F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, 1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -1.5257F, -2.6084F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -0.4599F, -0.1384F, -0.1309F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, -6.75F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -1.75F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 0).addBox(-3.0F, -3.75F, -1.75F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(39, 38).addBox(-1.5F, 0.0858F, -1.8406F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 1.73F, -3.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.5F, -2.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.73F, -3.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(54, 10).mirror().addBox(-1.1759F, -3.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-0.9259F, -4.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-1.3759F, -2.8F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-1.4759F, -2.2F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(54, 4).mirror().addBox(-1.4759F, -3.45F, -0.5248F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-1.3121F, -2.35F, 0.0232F, 0.0F, 0.3491F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(54, 10).addBox(-0.8241F, -3.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(54, 10).addBox(-1.0741F, -4.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.35F))
		.texOffs(54, 10).addBox(-0.6241F, -2.8F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(54, 10).addBox(-0.5241F, -2.2F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(54, 4).addBox(-0.5241F, -3.45F, -0.5248F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.3121F, -2.35F, 0.0232F, 0.0F, -0.3491F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
