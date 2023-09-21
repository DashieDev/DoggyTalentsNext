package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FrenchBulldogModel extends DogModel{

    public FrenchBulldogModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.75F, 5.5F, 1.9635F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, -0.9048F, -0.7716F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(9, 18).addBox(-0.25F, -0.4048F, -0.7716F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(9, 18).addBox(-0.95F, 0.9452F, -0.7716F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.55F))
		.texOffs(9, 18).addBox(-0.15F, 0.5452F, -0.7716F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.65F))
		.texOffs(9, 18).addBox(-1.65F, 0.4452F, -0.7716F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.65F)), PartPose.offset(1.0F, -0.25F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(-1.5F, 19.0F, 4.75F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(1.5F, 19.0F, 4.75F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.75F, -0.25F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(-2.0F, 19.25F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.25F, -0.25F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(2.0F, 19.25F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 16.65F, 2.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -3.7452F, -2.8911F, 6.0F, 7.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.3373F, 0.9651F, 1.789F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -4.0F, -3.25F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.0F, 17.5F, -2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, -5.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(1, 1).addBox(-3.0F, -5.85F, -2.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.65F))
		.texOffs(1, 1).addBox(-3.0F, -5.4F, -3.15F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.55F))
		.texOffs(1, 0).addBox(-3.0F, -5.0F, -3.35F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.45F))
		.texOffs(12, 11).addBox(1.0F, -1.92F, -4.25F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(12, 11).addBox(-2.0F, -1.92F, -4.25F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 11).addBox(-1.5F, -2.47F, -4.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.7214F, -4.1064F, -1.429F, 0.0F, 0.0F, -0.3927F));

		PartDefinition head_r1 = right_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-0.7819F, -0.8774F, -0.5386F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(45, 1).mirror().addBox(-1.3319F, -1.41F, -0.6401F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-0.1641F, -2.1577F, 0.129F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head_r2 = right_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.4819F, -0.5074F, -0.6201F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.1641F, -2.1577F, 0.129F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head_r3 = right_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-1.0648F, -1.7583F, -0.5891F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(-0.2504F, -1.2952F, 0.029F, 0.0421F, 0.0113F, 0.1311F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.0647F, -0.5085F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(-0.2504F, -1.2952F, 0.029F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(1.7214F, -4.1064F, -1.429F, 0.0F, 0.0F, 0.3927F));

		PartDefinition head_r5 = left_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(45, 1).addBox(-1.2181F, -0.8774F, -0.5386F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(45, 1).addBox(-0.6681F, -1.41F, -0.6401F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.1641F, -2.1577F, 0.129F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5181F, -0.5074F, -0.6201F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.1641F, -2.1577F, 0.129F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(52, 0).addBox(-0.9352F, -1.7583F, -0.5891F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.2504F, -1.2952F, 0.029F, 0.0421F, -0.0113F, -0.1311F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 1).addBox(-0.9353F, -0.5085F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.2504F, -1.2952F, 0.029F, 0.0F, 0.0F, -0.1309F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
    
}
