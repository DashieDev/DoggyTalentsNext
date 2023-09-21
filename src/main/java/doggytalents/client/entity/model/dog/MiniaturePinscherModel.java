package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class MiniaturePinscherModel extends DogModel{

    public MiniaturePinscherModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 16.25F, -4.25F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.7F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.5F, -1.75F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.0F, 0.98F, -2.5F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5199F, -1.2977F, 0.9699F, 0.0F, -0.3927F, 0.3054F));

		PartDefinition head_r2 = left_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(45, 1).addBox(-1.0677F, -0.5814F, -1.3562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.12F, -3.0639F, 1.1037F, 0.0079F, 0.0522F, -0.0044F));

		PartDefinition head_r3 = left_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(45, 1).addBox(0.1823F, -0.5814F, -1.1062F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(45, 1).addBox(-1.0677F, -0.5814F, -1.3562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-0.12F, -3.0639F, 0.5037F, 0.0079F, 0.0522F, -0.0044F));

		PartDefinition head_r4 = left_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(45, 1).addBox(-0.7058F, -2.1379F, -1.76F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.02F, -3.0639F, 1.0037F, 0.3435F, -0.057F, -0.1601F));

		PartDefinition head_r5 = left_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(45, 1).addBox(-0.3677F, -0.1195F, -1.7579F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.12F, -2.9639F, 1.0037F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(52, 0).addBox(-0.8626F, -1.319F, -1.9596F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2162F, -2.1014F, 1.1037F, -0.0456F, -0.0093F, 0.0434F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(52, 0).addBox(-0.8626F, -1.319F, -1.8596F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2162F, -2.1014F, 1.3037F, 0.0421F, -0.0113F, -0.0002F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 1).addBox(-0.3485F, -0.2661F, -1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(45, 1).addBox(-0.8485F, -0.0161F, -1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0662F, -2.1014F, 0.9037F, 0.0F, 0.0F, 0.1309F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5199F, -1.2977F, 0.9699F, 0.0F, 0.3927F, -0.3054F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-0.9323F, -0.5814F, -1.3562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.12F, -3.0639F, 1.1037F, 0.0079F, -0.0522F, 0.0044F));

		PartDefinition head_r10 = right_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-2.1823F, -0.5814F, -1.1062F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(45, 1).mirror().addBox(-0.9323F, -0.5814F, -1.3562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(0.12F, -3.0639F, 0.5037F, 0.0079F, -0.0522F, 0.0044F));

		PartDefinition head_r11 = right_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.2942F, -2.1379F, -1.76F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.02F, -3.0639F, 1.0037F, 0.3435F, 0.057F, 0.1601F));

		PartDefinition head_r12 = right_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.6323F, -0.1195F, -1.7579F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.12F, -2.9639F, 1.0037F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head_r13 = right_ear.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-1.1374F, -1.319F, -1.9596F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2162F, -2.1014F, 1.1037F, -0.0456F, 0.0093F, -0.0434F));

		PartDefinition head_r14 = right_ear.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-1.1374F, -1.319F, -1.8596F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2162F, -2.1014F, 1.3037F, 0.0421F, 0.0113F, 0.0002F));

		PartDefinition head_r15 = right_ear.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.6515F, -0.2661F, -1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(45, 1).mirror().addBox(-1.1515F, -0.0161F, -1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.0662F, -2.1014F, 0.9037F, 0.0F, 0.0F, -0.1309F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.5F, -4.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-1.1F)), PartPose.offsetAndRotation(0.0F, 17.0F, -2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 17.5F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-6.0F, -8.3372F, -0.9962F, 6.0F, 8.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(3.0F, 5.0F, -2.25F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(1.5F, 19.0F, -4.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(-1.5F, 19.0F, -4.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(1.25F, 18.25F, 5.2F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(-1.25F, 18.25F, 5.2F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 17.0F, 3.0F, 2.2253F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-2.0F, -2.0934F, -0.7873F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offset(1.0F, 3.0715F, -0.3673F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
