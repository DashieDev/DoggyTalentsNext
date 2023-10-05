// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
package doggytalents.client.entity.model.dog;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleTypes;


public class CHUCKIE_MODEL<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "chuckie_model"), "main");
	private final ModelPart root;

	public CHUCKIE_MODEL(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(1.0F, 13.0F, -1.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, -3.0F, -8.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.2403F, -2.4358F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(25, 17).addBox(-1.0F, 0.4251F, -5.9358F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.7251F, 0.2966F));

		PartDefinition cube_r1 = real_head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -0.4055F, -1.152F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -0.8694F, 1.8554F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r2 = real_head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 19).addBox(-2.5F, -1.151F, -3.1099F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.1762F, 2.4132F, -0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r3 = real_head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(11, 29).addBox(-3.2F, -3.2422F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0019F, -0.2358F, 0.0F, -0.48F, 0.0F));

		PartDefinition cube_r4 = real_head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, -0.5F, -0.4276F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2467F, -0.2436F, -2.4691F, -0.0436F, 0.1745F, 0.0F));

		PartDefinition cube_r5 = real_head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(19, 26).mirror().addBox(1.2F, -3.2422F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.0019F, -0.2358F, 0.0F, 0.48F, 0.0F));

		PartDefinition cube_r6 = real_head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(9, 11).mirror().addBox(0.0F, -0.5F, -0.3276F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2467F, -0.2436F, -2.4691F, -0.0436F, -0.0873F, 0.0F));

		PartDefinition cube_r7 = real_head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, -0.8422F, -0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5019F, -5.2358F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r8 = real_head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(29, 24).addBox(-1.0F, -0.8317F, -1.159F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1914F, -2.5768F, 0.8727F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 1.2597F, -3.2358F, -0.1745F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(17, 11).addBox(-0.9879F, -0.6654F, -0.5F, 2.0F, 1.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 0).addBox(-0.5879F, -3.6497F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4879F, -2.0749F, -0.2358F));

		PartDefinition cube_r9 = right_ear.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(21, 8).addBox(-0.3624F, -0.5292F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1745F, 0.9637F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition cube_r10 = right_ear.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(26, 0).addBox(-1.6F, -2.5602F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2121F, -0.7398F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition cube_r11 = right_ear.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(21, 14).addBox(-1.47F, -2.118F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3121F, -1.6074F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(23, 11).mirror().addBox(-1.0121F, -0.6654F, -0.5F, 2.0F, 1.5F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(31, 0).mirror().addBox(-0.3121F, -3.6497F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.4879F, -2.0749F, -0.2358F));

		PartDefinition cube_r12 = left_ear.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(21, 6).mirror().addBox(-0.6376F, -0.5292F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1745F, 0.9637F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition cube_r13 = left_ear.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(21, 0).mirror().addBox(0.7F, -2.5602F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2121F, -0.7398F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition cube_r14 = left_ear.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(17, 14).mirror().addBox(-0.4626F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2495F, -2.0476F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition upper_body = root.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 37).addBox(-2.95F, -2.9674F, -1.8103F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.09F)), PartPose.offset(-1.0F, 1.0019F, -3.6288F));

		PartDefinition cube_r15 = upper_body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(23, 32).addBox(-3.0F, -1.2783F, -0.3383F, 6.0F, 4.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 1.1263F, -2.7329F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r16 = upper_body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(17, 40).addBox(-2.55F, -1.7768F, -3.3F, 5.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.7252F, -1.8103F, -0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r17 = upper_body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(1, 51).addBox(-3.0F, -4.5455F, -5.0388F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.5435F, -2.3324F, -0.7418F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 24).addBox(-2.9F, -2.3219F, -3.8014F, 6.0F, 5.0F, 4.5F, new CubeDeformation(0.0F))
		.texOffs(40, 52).addBox(-3.0F, -2.2565F, 0.1377F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.4565F, 1.3623F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(22, 52).addBox(-3.0F, -1.5539F, -1.6444F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.0525F, 4.7821F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r18 = body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(40, 42).addBox(-2.95F, -1.2346F, -0.7609F, 5.9F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5781F, -2.2014F, 0.1745F, 0.0F, 0.0F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(43, 35).addBox(-3.0F, -1.1039F, -0.8444F, 6.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -0.0525F, 4.7821F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 12).mirror().addBox(-0.6375F, -0.9654F, -0.7059F, 1.95F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.4875F, 4.0F, -4.7333F));

		PartDefinition cube_r19 = left_front_leg.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(58, 9).mirror().addBox(2.3F, 0.0F, -1.3609F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-2.9875F, 0.0346F, 0.2941F, 0.0873F, 0.0F, 0.0F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 12).addBox(-1.2625F, -0.9654F, -0.7059F, 1.95F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.4875F, 4.0F, -4.7333F));

		PartDefinition cube_r20 = right_front_leg.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(58, 9).addBox(-4.3F, 0.0F, -1.3609F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.9875F, 0.0346F, 0.2941F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(56, 1).addBox(-1.1F, 3.4346F, -0.1525F, 2.0F, 4.6F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 3.0F, 6.8133F));

		PartDefinition cube_r21 = left_hind_leg.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(32, 13).addBox(-0.6F, -3.7663F, -0.9962F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(-0.5F, 2.0009F, -0.3172F, 0.48F, 0.0F, 0.0F));

		PartDefinition cube_r22 = left_hind_leg.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(54, 15).addBox(-0.6F, -2.9663F, -0.3462F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, 2.0009F, -0.3172F, 0.281F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(56, 1).mirror().addBox(-0.9F, 3.4346F, -0.1525F, 2.0F, 4.6F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 3.0F, 6.8133F));

		PartDefinition cube_r23 = right_hind_leg.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(54, 15).mirror().addBox(-1.4F, -2.9663F, -0.3462F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.5F, 2.0009F, -0.3172F, 0.281F, 0.0F, 0.0F));

		PartDefinition cube_r24 = right_hind_leg.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(32, 13).addBox(-4.4F, -3.7663F, -0.9962F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(3.5F, 2.0009F, -0.3172F, 0.48F, 0.0F, 0.0F));

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(47, 2).addBox(-1.0F, 0.0207F, -1.6623F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.2207F, 6.2623F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.2207F, -0.2623F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
