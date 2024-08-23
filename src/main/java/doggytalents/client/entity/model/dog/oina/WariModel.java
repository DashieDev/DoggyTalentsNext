package doggytalents.client.entity.model.dog.oina;

import doggytalents.client.entity.model.dog.DogModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class WariModel extends DogModel {

    public WariModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 18).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(9, 18).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(9, 18).addBox(-1.0F, 3.0F, -0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(9, 18).addBox(-1.0F, 4.25F, 0.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(25, 2).addBox(-1.0F, -5.25F, -2.2F, 4.0F, 6.0F, 5.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.5F, -0.5672F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.75F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.5F, -2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 10).addBox(-1.5F, -0.498F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.201F)), PartPose.offsetAndRotation(0.0F, 0.58F, 0.5F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.0F, -3.0F, 0.5F));

		PartDefinition head_r1 = left_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, 0.2382F, 0.0F, 0.4659F, -0.1198F, 0.2333F));

		PartDefinition head_r2 = left_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(0.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.4382F, 0.0F, 0.4765F, 0.0603F, -0.1163F));

		PartDefinition head_r3 = left_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(39, 15).mirror().addBox(0.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-0.15F, 0.6882F, -0.1F, 0.48F, 0.0F, 0.0F));

		PartDefinition head_r4 = left_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(39, 15).addBox(0.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.75F, 0.4882F, -0.1F, 0.4659F, -0.1198F, 0.2333F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-2.0F, -3.0F, 0.5F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(39, 15).mirror().addBox(-1.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.75F, 0.4882F, -0.1F, 0.4659F, 0.1198F, -0.2333F));

		PartDefinition head_r6 = right_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(39, 15).addBox(-1.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.15F, 0.6882F, -0.1F, 0.48F, 0.0F, 0.0F));

		PartDefinition head_r7 = right_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.2382F, 0.0F, 0.4659F, 0.1198F, -0.2333F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4382F, 0.0F, 0.4765F, -0.0603F, 0.1163F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	// @Override
    // public boolean useDefaultModelForAccessories() {
    //     return true;
    // }

	// @Override
	// public boolean warnAccessory(Dog dog, Accessory inst)  {
    //     return inst.getType() == DoggyAccessoryTypes.HEAD.get();
    // }
}

