package doggytalents.client.entity.model.dog;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DeathModel extends GlowingEyeDogModel {
    
    public DeathModel(ModelPart box) {
        super(box);
    }

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.5F, -8.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 7).addBox(-2.5F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 7).addBox(1.5F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 14).addBox(1.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 4).addBox(3.0F, -0.75F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 6).addBox(3.0F, -0.75F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(13, 5).addBox(-4.0F, -0.75F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 5).addBox(-5.0F, -0.75F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 6).addBox(-5.75F, -1.75F, -1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 7).addBox(-4.25F, -2.5F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 7).addBox(1.25F, -2.5F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 6).addBox(-2.5F, -3.5F, -1.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(28, 8).addBox(-2.5F, 2.5F, -1.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 6).addBox(2.5F, -1.75F, -1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hood = real_head.addOrReplaceChild("hood", CubeListBuilder.create(), PartPose.offset(0.0F, 3.25F, 8.75F));

		PartDefinition cube_r1 = hood.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(44, 24).mirror().addBox(-5.5F, -8.0F, 2.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).addBox(2.75F, -8.25F, 4.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).mirror().addBox(-4.75F, -8.25F, 4.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).mirror().addBox(-5.5F, -8.25F, 3.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).addBox(3.45F, -7.95F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).mirror().addBox(-5.3F, -7.75F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).mirror().addBox(-5.5F, -7.75F, 1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).addBox(3.25F, -7.95F, 1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(3.25F, -8.0F, 2.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(3.25F, -8.25F, 3.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(-4.4F, -7.9F, 5.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(2.1F, -7.9F, 5.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(-4.25F, -7.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(44, 24).addBox(2.0F, -7.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(1.25F, -7.5F, 5.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(44, 24).addBox(-0.25F, -7.25F, 5.45F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-0.5F, -8.5F, 4.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(-1.5F, -8.5F, 4.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(-1.75F, -7.25F, 5.45F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-3.25F, -7.5F, 5.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r2 = hood.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(44, 24).addBox(-3.25F, -7.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).mirror().addBox(0.25F, -8.25F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(44, 24).addBox(-2.25F, -8.25F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(1.25F, -7.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(1.5F, -8.75F, -1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-3.5F, -8.75F, -1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-5.0F, -7.25F, 5.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(2.75F, -7.25F, 5.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-5.0F, -7.0F, 3.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(2.75F, -7.0F, 3.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-5.0F, -7.25F, 2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(2.75F, -7.25F, 2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(3.0F, -7.7F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(2.75F, -8.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-5.0F, -7.75F, 0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-4.75F, -8.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition glowing_eyes = partdefinition.addOrReplaceChild("glowing_eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 12.5F, -8.0F));

		PartDefinition real_glowing_eyes = glowing_eyes.addOrReplaceChild("real_glowing_eyes", CubeListBuilder.create().texOffs(3, 4).addBox(-3.0F, -2.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
		.texOffs(4, 5).addBox(-2.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
		.texOffs(3, 4).mirror().addBox(2.0F, -2.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false)
		.texOffs(4, 5).mirror().addBox(1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.3F, -1.25F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(21, 0).addBox(-4.0F, -3.15F, -4.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(21, 0).addBox(-4.0F, -3.15F, -2.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.25F))
		.texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -1.25F, -2.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.25F))
		.texOffs(18, 14).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(44, 16).mirror().addBox(-1.0F, 1.25F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(44, 16).addBox(-1.0F, 1.25F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public boolean warnAccessory(Dog dog, Accessory inst)  {
        return inst.getType() == DoggyAccessoryTypes.HEAD.get();
    }
}
