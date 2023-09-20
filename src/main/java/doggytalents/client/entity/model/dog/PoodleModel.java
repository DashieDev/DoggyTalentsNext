package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PoodleModel extends DogModel{

    public PoodleModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 5.5F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(24, 24).addBox(-1.0F, 2.4595F, 2.4619F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(24, 24).addBox(-1.0F, 0.0595F, 1.3619F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(24, 24).addBox(-1.0F, 0.7095F, 2.4619F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(18, 20).addBox(-1.0F, -1.8405F, -1.6381F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(24, 24).addBox(-1.0F, -1.75F, -1.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.1595F, 2.1619F, -1.0472F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(24, 23).addBox(-1.0F, -2.25F, -1.25F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 4.1595F, 0.8619F, 0.829F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.5F, 17.75F, 5.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(1.5F, 17.75F, 5.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.75F, 2.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -11.25F, -1.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 2.75F, 8.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.75F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0F, 15.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -3.75F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(2, 2).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 0).addBox(-3.0F, -4.5F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.8F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -3.75F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 11).addBox(-1.5F, 0.98F, -3.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 10).mirror().addBox(-0.25F, -0.75F, -1.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.83F, -2.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-1.75F, -0.75F, -1.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.5F, 0.83F, -2.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(44, 14).addBox(-0.75F, -1.5F, -1.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(44, 14).addBox(-0.95F, -0.9F, -1.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(44, 14).addBox(-1.55F, -0.4F, -1.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(44, 14).addBox(-2.3F, 0.5F, -1.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(44, 14).addBox(-1.8F, 0.0F, -2.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(44, 14).addBox(-1.55F, 2.25F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F)), PartPose.offset(-3.0F, -1.5F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(44, 15).mirror().addBox(-1.25F, -1.5F, -1.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(44, 14).mirror().addBox(-0.05F, -0.9F, -1.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(44, 14).mirror().addBox(0.55F, -0.4F, -1.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(44, 14).mirror().addBox(1.3F, 0.5F, -1.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(44, 14).mirror().addBox(0.8F, 0.0F, -2.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(44, 14).mirror().addBox(-0.45F, 2.25F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offset(3.0F, -1.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
