package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class SparkyModel extends GlowingEyeDogModel {

    public SparkyModel(ModelPart box) {
        super(box, RenderType::entityTranslucent);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 14.75F, 5.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(9, 18).addBox(-1.0F, 3.5605F, -1.0158F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 18).addBox(-0.9F, 3.45F, -1.25F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(-0.1F, 3.1317F, -1.8841F, 0.5236F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 1.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.0F, 4.75F));

		PartDefinition leg2_r1 = right_hind_leg.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.9F, -0.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.5F, -1.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 1.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 18.0F, 4.75F));

		PartDefinition leg2_r2 = left_hind_leg.addOrReplaceChild("leg2_r2", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-1.0F, -0.9F, -0.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.5F, -1.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 1.75F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.75F, 17.25F, -4.0F));

		PartDefinition leg2_r3 = right_front_leg.addOrReplaceChild("leg2_r3", CubeListBuilder.create().texOffs(0, 18).addBox(-1.15F, 1.9F, -7.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.25F, 1.25F, 7.75F, -0.4363F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(2.5F, 1.75F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(2.5F, 1.75F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-1.75F, 17.25F, -4.0F));

		PartDefinition leg2_r4 = left_front_leg.addOrReplaceChild("leg2_r4", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-0.85F, 1.9F, -7.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(3.25F, 1.25F, 7.75F, -0.4363F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 50).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(-0.24F))
		.texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 16.3293F, 0.1239F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.25F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 39).addBox(-4.0F, -3.25F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.49F)), PartPose.offsetAndRotation(0.0F, 16.5F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(23, 0).addBox(-2.0F, -7.25F, -2.75F, 6.0F, 7.0F, 6.0F, new CubeDeformation(-0.95F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.5F, -0.9599F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 10.65F, -7.5F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, 0.5F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.49F))
		.texOffs(32, 31).addBox(0.25F, 1.0F, 0.5F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F))
		.texOffs(35, 31).addBox(3.0F, 1.0F, 0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(32, 31).mirror().addBox(-4.15F, 1.0F, 0.5F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).mirror(false)
		.texOffs(35, 31).mirror().addBox(-3.9F, 1.0F, 0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = real_head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(56, 20).addBox(-0.25F, -0.5F, -1.0F, 1.5F, 1.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(2.3831F, -1.7016F, -0.5F, 0.0F, 0.0F, 0.3491F));

		PartDefinition cube_r2 = real_head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(56, 20).mirror().addBox(-1.25F, -0.5F, -1.0F, 1.5F, 1.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(-2.3831F, -1.7016F, -0.5F, 0.0F, 0.0F, -0.3491F));

		PartDefinition cube_r3 = real_head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(56, 20).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(1.95F, -1.75F, -0.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition cube_r4 = real_head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(56, 20).mirror().addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(-1.95F, -1.75F, -0.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(40, 14).addBox(-3.2F, 0.8F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.7F, 1.03F, -1.5F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(54, 7).mirror().addBox(2.2F, -1.25F, -3.7F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-1.7F, 0.53F, -1.0F, 0.5444F, 0.2635F, 0.1564F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(54, 7).addBox(-4.2F, -1.25F, -3.7F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(1.7F, 0.53F, -1.0F, 0.5444F, -0.2635F, -0.1564F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(55, 15).addBox(-2.25F, -1.75F, -5.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(50, 26).addBox(-2.9F, -1.25F, -4.9F, 2.4F, 2.0F, 3.0F, new CubeDeformation(0.101F)), PartPose.offsetAndRotation(1.7F, 0.53F, -1.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition head_r5 = real_head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(0, 29).addBox(-4.5F, -1.8F, -3.9F, 5.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(1.7F, 0.53F, -1.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition head_r6 = real_head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(1, 11).addBox(-3.7F, -1.25F, -1.95F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(1.7F, 0.78F, -0.5F, 0.3491F, 0.0F, 0.0F));

		PartDefinition head_r7 = real_head.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(1, 0).addBox(-5.3F, -3.1F, -0.75F, 5.0F, 5.0F, 4.0F, new CubeDeformation(-0.69F)), PartPose.offsetAndRotation(2.8F, 0.35F, 2.0F, -1.2654F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 0.1F, 2.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5677F, -0.2314F, -1.5562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-1.0845F, -2.738F, 0.2F, 0.0133F, -0.864F, -0.0067F));

		PartDefinition head_r9 = left_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5177F, -1.8641F, -1.9077F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-0.9845F, -2.738F, 0.7F, 0.0598F, -0.8811F, 0.1414F));

		PartDefinition head_r10 = left_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(45, 1).addBox(-0.3677F, -0.1195F, -1.6579F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.0845F, -2.638F, 0.7F, 0.0873F, -0.9163F, 0.0F));

		PartDefinition head_r11 = left_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(51, 0).addBox(-0.8626F, -1.319F, -1.8596F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.7483F, -1.7755F, 1.0F, 0.0425F, -0.9276F, -0.0004F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 0.1F, 2.5F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head_r12 = right_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.4323F, -0.2314F, -1.5562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.9341F, -2.7611F, 0.2F, 0.0133F, 0.864F, 0.0067F));

		PartDefinition head_r13 = right_ear.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.4823F, -1.8641F, -1.9077F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.8341F, -2.7611F, 0.7F, 0.0598F, 0.8811F, -0.1414F));

		PartDefinition head_r14 = right_ear.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-1.6323F, -0.1195F, -1.6579F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.9341F, -2.6611F, 0.7F, 0.0873F, 0.9163F, 0.0F));

		PartDefinition head_r15 = right_ear.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(51, 0).mirror().addBox(-1.1374F, -1.319F, -1.8596F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.5979F, -1.7986F, 1.0F, 0.0425F, 0.9276F, 0.0004F));

		PartDefinition glowing_eyes = partdefinition.addOrReplaceChild("glowing_eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 10.65F, -7.5F));

		PartDefinition real_glowing_eyes = glowing_eyes.addOrReplaceChild("real_glowing_eyes", CubeListBuilder.create().texOffs(43, 24).addBox(0.95F, -1.5F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(43, 24).mirror().addBox(-2.95F, -1.5F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
