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

public class KawausoModel extends DogModel {

    public KawausoModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-1.0F, 0.5F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.25F, -1.75F, 0.0873F, 0.0F, 0.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 10).addBox(-1.5F, -0.498F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.201F)), PartPose.offsetAndRotation(0.0F, 0.48F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(17, 14).addBox(-0.1F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 14).mirror().addBox(-0.75F, -1.1F, -0.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(17, 14).addBox(-0.3F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(19, 17).addBox(-0.3F, -0.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(2.0F, -3.0F, 0.5F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(19, 17).mirror().addBox(-0.7F, -0.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(17, 14).addBox(-0.25F, -1.1F, -0.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(17, 14).mirror().addBox(-0.9F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(17, 14).mirror().addBox(-0.7F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(-2.0F, -3.0F, 0.5F));

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