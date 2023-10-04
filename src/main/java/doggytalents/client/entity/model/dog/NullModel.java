package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class NullModel extends DogModel {

    public NullModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.0F, 0.5F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -3.0F, 0.5F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -0.3F, 0.25F));

		PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(56, 14).addBox(-1.0F, -3.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(56, 14).addBox(0.75F, -3.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(56, 14).addBox(2.25F, -3.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(56, 14).addBox(2.25F, -3.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(54, 8).addBox(-0.5F, -3.0F, -3.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(54, 8).addBox(1.25F, -3.0F, -3.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(54, 8).mirror().addBox(-2.5F, -3.0F, -3.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false)
		.texOffs(56, 14).mirror().addBox(-2.75F, -3.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(54, 8).mirror().addBox(-4.25F, -3.0F, -3.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false)
		.texOffs(56, 14).mirror().addBox(-4.25F, -3.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(56, 14).mirror().addBox(-4.25F, -3.0F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(54, 8).mirror().addBox(-4.25F, -3.0F, 0.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false)
		.texOffs(56, 14).mirror().addBox(-2.75F, -3.0F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(54, 8).mirror().addBox(-2.5F, -3.0F, 0.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false)
		.texOffs(56, 14).addBox(-1.0F, -3.0F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(54, 8).addBox(-0.5F, -3.0F, 0.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(56, 14).addBox(0.75F, -3.0F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(54, 8).addBox(1.25F, -3.0F, 0.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(56, 14).addBox(2.25F, -3.0F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(56, 14).mirror().addBox(-4.25F, -3.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offset(0.0F, -0.25F, 0.0F));

		PartDefinition cube_r1 = bone3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(54, 8).mirror().addBox(-2.5F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false)
		.texOffs(54, 8).mirror().addBox(-4.5F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offsetAndRotation(-3.75F, -2.0F, -2.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = bone3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(54, 8).addBox(1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(54, 8).addBox(-0.5F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(3.75F, -2.0F, -2.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.9736F, -6.4741F));

		PartDefinition cube_r3 = bone2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(50, 28).addBox(-3.0F, -1.3036F, 0.0741F, 6.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r4 = bone2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(50, 24).addBox(-3.0F, -1.439F, -0.85F, 6.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -0.5346F, 2.1998F, -1.2217F, 0.0F, 0.0F));

		PartDefinition cube_r5 = bone2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(50, 20).addBox(-3.0F, -1.75F, -3.15F, 6.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -1.0736F, 6.0741F, -0.4363F, 0.0F, 0.0F));

		PartDefinition upper_body = root.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 1.0F, -4.0F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 1.0F, -4.0F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 1.0F, 7.0F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 1.0F, 7.0F));

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
