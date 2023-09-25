package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BelgianMalinoisModel extends DogModel{

    public BelgianMalinoisModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.25F, 7.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition tail2 = real_tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition tail_r1 = tail2.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(8, 46).addBox(-1.0F, -4.0654F, 0.2782F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(1.0F, 6.8154F, 1.5218F, -1.2654F, 0.0F, 0.0F));

		PartDefinition tail_r2 = tail2.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(5, 46).addBox(-1.0F, -3.2212F, -0.8005F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.0F, 6.8154F, 1.5218F, -1.7453F, 0.0F, 0.0F));

		PartDefinition tail_r3 = tail2.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(0, 47).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(1.0F, 5.3F, 0.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition tail_r4 = tail2.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(6, 47).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, 6.7F, 1.7F, -2.2689F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.5F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.5F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-0.5F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 18.0F, -4.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.5F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.0F, -4.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -5.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-1.0F, 13.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(1, 32).addBox(-3.0F, -2.5905F, -2.5352F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.15F)), PartPose.offsetAndRotation(1.0F, -2.1059F, 0.6384F, -0.9163F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -13.0F, -3.25F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 2.0F, 10.0F, 1.4399F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, -6.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 0).addBox(-3.0F, -4.75F, -2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 10).addBox(-1.5F, -1.02F, -5.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(39, 38).addBox(-1.5F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.98F, -2.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(28, 38).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.5F, -0.9163F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(54, 10).mirror().addBox(-1.1759F, -3.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-0.9259F, -4.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-1.3759F, -2.8F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-1.4759F, -2.2F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(54, 4).mirror().addBox(-1.4759F, -3.45F, -0.5248F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-1.3121F, -3.35F, -0.2268F, 0.0F, 0.3491F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(54, 10).addBox(-0.8241F, -3.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(54, 10).addBox(-1.0741F, -4.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(54, 10).addBox(-0.6241F, -2.8F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(54, 10).addBox(-0.5241F, -2.2F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(54, 4).addBox(-0.5241F, -3.45F, -0.5248F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.3121F, -3.35F, -0.2268F, 0.0F, -0.3491F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
    
}
