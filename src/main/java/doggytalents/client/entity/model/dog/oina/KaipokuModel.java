package doggytalents.client.entity.model.dog.oina;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class KaipokuModel extends DogModel {

    public KaipokuModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.4995F, 8.0218F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 18).addBox(-1.0F, 2.25F, -0.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(9, 18).addBox(-1.0F, 1.75F, 0.25F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.65F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.5F, 2.0F, -0.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 1.75F, -1.25F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(9, 18).addBox(0.5F, 2.0F, -0.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.3F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(25, 2).addBox(-1.0F, -6.0F, -1.25F, 4.0F, 6.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 11.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.3F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(56, 9).mirror().addBox(-3.5658F, -1.5176F, -1.3988F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.644F, 0.0234F, -0.3283F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(56, 9).addBox(0.8342F, -2.3567F, -1.3988F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.8F)), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.5867F, 0.2837F, 0.7579F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(52, 16).mirror().addBox(-3.149F, -0.8248F, -1.3988F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.614F, -0.2096F, -0.6465F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(56, 9).addBox(1.5658F, -1.5176F, -1.3988F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.644F, -0.0234F, 0.3283F));

		PartDefinition head_r5 = real_head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(56, 9).mirror().addBox(-2.8342F, -2.3567F, -1.3988F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.8F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.5867F, -0.2837F, -0.7579F));

		PartDefinition head_r6 = real_head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(52, 16).addBox(1.149F, -0.8248F, -1.3988F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.614F, 0.2096F, 0.6465F));

		PartDefinition head_r7 = real_head.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(56, 9).mirror().addBox(-2.7095F, -3.5109F, -0.9163F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.6504F, 0.0795F, 0.1041F));

		PartDefinition head_r8 = real_head.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(52, 16).mirror().addBox(-1.5952F, -2.3386F, -0.9163F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.6378F, -0.1582F, -0.2095F));

		PartDefinition head_r9 = real_head.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(56, 9).mirror().addBox(-0.2369F, -3.7343F, -0.9163F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.5468F, -0.3796F, -0.5468F));

		PartDefinition head_r10 = real_head.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(56, 9).addBox(-1.7631F, -3.7343F, -0.9163F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.5468F, 0.3796F, 0.5468F));

		PartDefinition head_r11 = real_head.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(56, 9).addBox(0.7095F, -3.5109F, -0.9163F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.6504F, -0.0795F, -0.1041F));

		PartDefinition head_r12 = real_head.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(52, 16).addBox(-0.4048F, -2.3386F, -0.9163F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.6378F, 0.1582F, 0.2095F));

		PartDefinition head_r13 = real_head.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(52, 16).addBox(-1.0F, -0.78F, -0.9163F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.9064F, 0.787F, -0.6545F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.8709F, -2.4183F, 0.2618F, 0.0F, 0.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 10).addBox(-1.5F, -0.498F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.251F)), PartPose.offset(0.0F, 0.58F, 0.25F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(45, 16).addBox(0.2F, 2.0F, -1.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(45, 16).addBox(1.5F, 0.75F, -1.25F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(45, 15).addBox(0.8F, 2.1F, -1.85F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(45, 16).addBox(1.25F, 2.0F, -1.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(45, 16).addBox(0.75F, -1.5F, -1.25F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(2.0F, -1.0F, 0.5F));

		PartDefinition head_r14 = left_ear.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(42, 16).addBox(-0.45F, 0.7118F, -1.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(42, 16).addBox(0.0F, 1.1118F, -1.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.8618F, -0.75F, -0.0385F, -0.2148F, -0.0395F));

		PartDefinition head_r15 = left_ear.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(42, 16).addBox(0.75F, 0.3618F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 16).addBox(0.0F, 0.8618F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(42, 16).addBox(-0.5F, -0.1382F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.8618F, -0.75F, 0.0F, -0.829F, -0.2182F));

		PartDefinition head_r16 = left_ear.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(42, 16).addBox(0.0F, 1.1118F, -1.25F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(42, 16).addBox(-0.45F, 0.7118F, -1.25F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -1.8618F, -0.75F, -0.1341F, -0.1726F, 0.448F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(45, 16).mirror().addBox(-1.75F, -1.5F, -1.25F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(45, 16).mirror().addBox(-2.5F, 0.75F, -1.25F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(45, 15).mirror().addBox(-1.8F, 2.1F, -1.85F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(45, 16).mirror().addBox(-2.25F, 2.0F, -1.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(45, 16).mirror().addBox(-1.2F, 2.0F, -1.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(-2.0F, -1.0F, 0.5F));

		PartDefinition head_r17 = right_ear.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(42, 16).mirror().addBox(-4.0F, 1.1118F, -1.25F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(42, 16).mirror().addBox(-3.55F, 0.7118F, -1.25F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.8618F, -0.75F, -0.1341F, 0.1726F, -0.448F));

		PartDefinition head_r18 = right_ear.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(42, 16).mirror().addBox(-4.0F, 1.1118F, -1.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(42, 16).mirror().addBox(-3.55F, 0.7118F, -1.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.8618F, -0.75F, -0.0385F, 0.2148F, 0.0395F));

		PartDefinition head_r19 = right_ear.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(42, 16).mirror().addBox(-4.0F, 0.8618F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(42, 16).mirror().addBox(-3.5F, -0.1382F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(42, 16).mirror().addBox(-4.75F, 0.3618F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.8618F, -0.75F, 0.0F, 0.829F, 0.2182F));

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
