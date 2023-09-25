package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BullTerrierModel extends DogModel{

    public BullTerrierModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.5F, 5.0F, 2.0071F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, -2.75F, 1.5F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 1.75F, -2.5F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(-1.5F, 19.0F, 4.75F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(1.5F, 19.0F, 4.75F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.25F, -0.25F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(-2.0F, 19.25F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.75F, -0.25F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(2.0F, 19.25F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 17.3293F, 0.1239F, -0.2182F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -4.7452F, -3.1411F, 6.0F, 8.0F, 6.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, -0.7319F, 2.6496F, 1.789F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.25F, -3.25F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.0F, 17.5F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(23, 0).addBox(-2.0F, -5.5F, -2.75F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-1.1F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.5F, -0.9599F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.75F, -6.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.85F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.79F))
		.texOffs(43, 24).addBox(0.95F, -5.35F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(43, 24).mirror().addBox(-2.95F, -5.35F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offset(0.0F, 3.0F, 1.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(40, 14).addBox(-3.2F, 0.8F, -1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(1.7F, -2.82F, -4.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-3.7F, -1.0F, -1.95F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(1.7F, -2.82F, -4.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(1, 0).addBox(-5.3F, -3.1F, -0.75F, 5.0F, 5.0F, 4.0F, new CubeDeformation(-0.99F)), PartPose.offsetAndRotation(2.8F, -3.5F, -0.5F, -1.2654F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -0.75F, 1.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r4 = left_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5677F, -0.2314F, -1.5562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0845F, -2.738F, 0.2F, 0.0133F, -0.864F, -0.0067F));

		PartDefinition head_r5 = left_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5177F, -1.8641F, -1.9077F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.9845F, -2.738F, 0.7F, 0.0598F, -0.8811F, 0.1414F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(45, 1).addBox(-0.3677F, -0.1195F, -1.6579F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0845F, -2.638F, 0.7F, 0.0873F, -0.9163F, 0.0F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(51, 0).addBox(-0.8626F, -1.319F, -1.8596F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.7483F, -1.7755F, 1.0F, 0.0425F, -0.9276F, -0.0004F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -0.75F, 1.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.4323F, -0.2314F, -1.5562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.9341F, -2.7611F, 0.2F, 0.0133F, 0.864F, 0.0067F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.4823F, -1.8641F, -1.9077F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.8341F, -2.7611F, 0.7F, 0.0598F, 0.8811F, -0.1414F));

		PartDefinition head_r10 = right_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.6323F, -0.1195F, -1.6579F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.9341F, -2.6611F, 0.7F, 0.0873F, 0.9163F, 0.0F));

		PartDefinition head_r11 = right_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(51, 0).mirror().addBox(-1.1374F, -1.319F, -1.8596F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.5979F, -1.7986F, 1.0F, 0.0425F, 0.9276F, 0.0004F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
