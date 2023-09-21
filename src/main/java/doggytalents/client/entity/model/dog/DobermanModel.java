package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DobermanModel extends DogModel {

    public DobermanModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 13.0F, 7.0F, 2.0944F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 1.0F, -2.25F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(0.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 18.0F, -4.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-2.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -13.0F, -3.25F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 10.0F, 1.4399F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -4.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.3405F, -3.7852F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.95F)), PartPose.offsetAndRotation(1.0F, -2.1059F, 1.6384F, -0.7418F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 10.25F, -5.75F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(54, 10).mirror().addBox(-1.1759F, -4.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-0.9259F, -5.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-1.3759F, -3.8F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(54, 10).mirror().addBox(-1.4759F, -3.2F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(54, 4).mirror().addBox(-1.4759F, -4.45F, -0.5248F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.8121F, -2.35F, -0.2268F, 0.0F, 0.3491F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(54, 10).addBox(-0.8241F, -4.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(54, 10).addBox(-1.0741F, -5.5F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.45F))
		.texOffs(54, 10).addBox(-0.6241F, -3.8F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 10).addBox(-0.5241F, -3.2F, -0.4248F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 4).addBox(-0.5241F, -4.45F, -0.5248F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8121F, -2.35F, -0.2268F, 0.0F, -0.3491F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
