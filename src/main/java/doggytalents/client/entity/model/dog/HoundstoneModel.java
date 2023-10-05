package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class HoundstoneModel extends DogModel{

    public HoundstoneModel(ModelPart box) {
        super(box, RenderType::entityTranslucent);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -1.6144F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, -0.9F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.0F, -2.1921F, 5.3741F, 2.4871F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, -2.0F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(1.0F, -3.3393F, 7.0124F, 1.6581F, 0.0F, 0.0F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 2.1817F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-2.0F, 0.0F, -1.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 18).mirror().addBox(-2.0F, 5.5F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 18).addBox(-1.5F, 6.75F, -4.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition leg8_r1 = right_hind_leg.addOrReplaceChild("leg8_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -0.5F, 0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.5F, 8.0F, -5.0F, 0.5672F, -0.3747F, -0.2291F));

		PartDefinition leg7_r1 = right_hind_leg.addOrReplaceChild("leg7_r1", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-2.25F, -0.5F, -0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 8.0F, -5.0F, 0.5672F, 0.3747F, 0.2291F));

		PartDefinition leg9_r1 = right_hind_leg.addOrReplaceChild("leg9_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.5F, 8.0F, -5.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition leg7_r2 = right_hind_leg.addOrReplaceChild("leg7_r2", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(2.5F, 7.25F, -3.25F, 0.0F, -0.4363F, 0.0F));

		PartDefinition leg6_r1 = right_hind_leg.addOrReplaceChild("leg6_r1", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.25F, -0.5F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 7.25F, -3.25F, 0.0F, 0.4363F, 0.0F));

		PartDefinition leg6_r2 = right_hind_leg.addOrReplaceChild("leg6_r2", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -0.75F, 0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 7.0F, -1.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition leg5_r1 = right_hind_leg.addOrReplaceChild("leg5_r1", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 7.0F, -1.5F, 0.2182F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(0.0F, 5.5F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(0, 18).mirror().addBox(0.5F, 6.75F, -4.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition leg7_r3 = left_hind_leg.addOrReplaceChild("leg7_r3", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(0.5F, -0.5F, 0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 8.0F, -5.0F, 0.5672F, 0.3747F, 0.2291F));

		PartDefinition leg6_r3 = left_hind_leg.addOrReplaceChild("leg6_r3", CubeListBuilder.create().texOffs(0, 18).addBox(1.25F, -0.5F, -0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.5F, 8.0F, -5.0F, 0.5672F, -0.3747F, -0.2291F));

		PartDefinition leg8_r2 = left_hind_leg.addOrReplaceChild("leg8_r2", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(3.0F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 8.0F, -5.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition leg6_r4 = left_hind_leg.addOrReplaceChild("leg6_r4", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 7.25F, -3.25F, 0.0F, 0.4363F, 0.0F));

		PartDefinition leg5_r2 = left_hind_leg.addOrReplaceChild("leg5_r2", CubeListBuilder.create().texOffs(0, 18).addBox(0.25F, -0.5F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(1.5F, 7.25F, -3.25F, 0.0F, -0.4363F, 0.0F));

		PartDefinition leg5_r3 = left_hind_leg.addOrReplaceChild("leg5_r3", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.75F, 0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(1.0F, 7.0F, -1.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition leg4_r1 = left_hind_leg.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(1.0F, 7.0F, -1.5F, 0.2182F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.75F, 4.5F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(0, 18).addBox(-1.25F, 5.75F, -4.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offset(-3.25F, 17.0F, -3.0F));

		PartDefinition leg6_r5 = right_front_leg.addOrReplaceChild("leg6_r5", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -0.75F, 0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(-0.75F, 6.0F, -1.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition leg9_r2 = right_front_leg.addOrReplaceChild("leg9_r2", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.75F, 7.0F, -5.35F, 0.5236F, 0.0F, 0.0F));

		PartDefinition leg7_r4 = right_front_leg.addOrReplaceChild("leg7_r4", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-2.25F, -0.5F, -0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.25F, 7.0F, -5.35F, 0.5672F, 0.3747F, 0.2291F));

		PartDefinition leg8_r3 = right_front_leg.addOrReplaceChild("leg8_r3", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -0.5F, 0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.75F, 7.0F, -5.35F, 0.5672F, -0.3747F, -0.2291F));

		PartDefinition leg7_r5 = right_front_leg.addOrReplaceChild("leg7_r5", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(2.75F, 6.25F, -3.25F, 0.0F, -0.4363F, 0.0F));

		PartDefinition leg6_r6 = right_front_leg.addOrReplaceChild("leg6_r6", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.25F, -0.5F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(-1.25F, 6.25F, -3.25F, 0.0F, 0.4363F, 0.0F));

		PartDefinition leg5_r4 = right_front_leg.addOrReplaceChild("leg5_r4", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.75F, 6.0F, -1.5F, 0.2182F, 0.0F, 0.0F));

		PartDefinition leg3_r1 = right_front_leg.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-0.25F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.75F, 2.0F, -0.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.25F, 4.5F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.3F))
		.texOffs(0, 18).mirror().addBox(0.25F, 5.75F, -4.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offset(3.25F, 17.0F, -3.0F));

		PartDefinition leg5_r5 = left_front_leg.addOrReplaceChild("leg5_r5", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.75F, 0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.75F, 6.0F, -1.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition leg8_r4 = left_front_leg.addOrReplaceChild("leg8_r4", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(3.0F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-2.75F, 7.0F, -5.35F, 0.5236F, 0.0F, 0.0F));

		PartDefinition leg6_r7 = left_front_leg.addOrReplaceChild("leg6_r7", CubeListBuilder.create().texOffs(0, 18).addBox(1.25F, -0.5F, -0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.25F, 7.0F, -5.35F, 0.5672F, -0.3747F, -0.2291F));

		PartDefinition leg7_r6 = left_front_leg.addOrReplaceChild("leg7_r6", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(0.5F, -0.5F, 0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-2.75F, 7.0F, -5.35F, 0.5672F, 0.3747F, 0.2291F));

		PartDefinition leg6_r8 = left_front_leg.addOrReplaceChild("leg6_r8", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(1.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(-2.75F, 6.25F, -3.25F, 0.0F, 0.4363F, 0.0F));

		PartDefinition leg5_r6 = left_front_leg.addOrReplaceChild("leg5_r6", CubeListBuilder.create().texOffs(0, 18).addBox(0.25F, -0.5F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(1.25F, 6.25F, -3.25F, 0.0F, -0.4363F, 0.0F));

		PartDefinition leg4_r2 = left_front_leg.addOrReplaceChild("leg4_r2", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.75F, 6.0F, -1.5F, 0.2182F, 0.0F, 0.0F));

		PartDefinition leg2_r1 = left_front_leg.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.75F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.75F, 2.0F, -0.5F, 0.0F, 0.0F, -0.3054F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 16).addBox(-0.5F, 1.0043F, -3.0653F, 1.0F, 3.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -1.25F, 0.4363F, 0.0F, 0.0F));

		PartDefinition body_rotation_r2 = body.addOrReplaceChild("body_rotation_r2", CubeListBuilder.create().texOffs(19, 15).addBox(-3.0F, -2.7457F, -2.3153F, 6.0F, 4.0F, 5.0F, new CubeDeformation(1.35F)), PartPose.offsetAndRotation(0.0F, 5.0F, -1.25F, -0.1309F, 0.0F, 0.0F));

		PartDefinition bone3 = body.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(2.1299F, -2.3866F, 3.1868F));

		PartDefinition head_r1 = bone3.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(60, 26).mirror().addBox(0.0202F, -2.0206F, -0.7947F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(-0.2653F, 2.083F, 0.8446F, -2.1768F, 0.8366F, 2.4123F));

		PartDefinition bone4 = body.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(-2.1299F, -2.3866F, 3.1868F));

		PartDefinition head_r2 = bone4.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(60, 26).addBox(-1.0202F, -2.0206F, -0.7947F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.2653F, 2.083F, 0.8446F, -2.1768F, -0.8366F, -2.4123F));

		PartDefinition bone2 = body.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0116F, 4.1444F, 4.0369F, 0.0F, -0.3054F, 0.0F));

		PartDefinition head_r3 = bone2.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-2.7555F, -5.1383F, -2.3138F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-2.2777F, 0.5604F, -1.6556F, 0.7279F, -0.1091F, 1.2691F));

		PartDefinition head_r4 = bone2.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(60, 0).addBox(1.888F, -4.6598F, -2.5052F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-2.2777F, 0.5604F, -1.6556F, 0.1251F, 0.2616F, -1.3839F));

		PartDefinition head_r5 = bone2.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.25F, -1.75F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.9481F, 1.8885F, 0.3038F, -2.6369F, -0.058F, 2.7183F));

		PartDefinition head_r6 = bone2.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-2.7644F, -0.389F, -3.9294F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-2.7777F, 0.5604F, -1.6556F, -2.4051F, 0.2425F, 2.3993F));

		PartDefinition head_r7 = bone2.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-0.25F, 0.25F, -1.25F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-1.6152F, 1.3476F, 2.5577F, -0.5312F, 0.3048F, -0.0002F));

		PartDefinition head_r8 = bone2.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-1.4676F, -1.2129F, -4.1294F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-2.7777F, 0.5604F, -1.6556F, -2.6031F, -0.1484F, 2.8135F));

		PartDefinition head_r9 = bone2.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-0.9182F, -0.9295F, 1.5095F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(0.1183F, -0.281F, 0.6499F, -1.5892F, 0.9284F, 2.616F));

		PartDefinition head_r10 = bone2.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-0.4715F, -1.7219F, 0.0968F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.1183F, -0.281F, 0.6499F, -1.4588F, 0.8705F, 2.8815F));

		PartDefinition head_r11 = bone2.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-0.3145F, -2.0201F, -1.2046F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.1183F, -0.281F, 0.6499F, -1.8079F, 0.8705F, 2.8815F));

		PartDefinition head_r12 = bone2.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-2.5035F, 0.7736F, 1.1086F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.8817F, -0.281F, 0.1499F, -1.9677F, 0.4045F, 2.4871F));

		PartDefinition bone5 = body.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0116F, 4.1444F, 4.0369F, 0.0F, 0.3054F, 0.0F));

		PartDefinition head_r13 = bone5.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(60, 0).addBox(1.7555F, -5.1383F, -2.3138F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(2.2777F, 0.5604F, -1.6556F, 0.7279F, 0.1091F, -1.2691F));

		PartDefinition head_r14 = bone5.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-2.888F, -4.6598F, -2.5052F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(2.2777F, 0.5604F, -1.6556F, 0.1251F, -0.2616F, 1.3839F));

		PartDefinition head_r15 = bone5.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(60, 0).addBox(-1.25F, -1.75F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.9481F, 1.8885F, 0.3038F, -2.6369F, 0.058F, -2.7183F));

		PartDefinition head_r16 = bone5.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(60, 0).addBox(1.7644F, -0.389F, -3.9294F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(2.7777F, 0.5604F, -1.6556F, -2.4051F, -0.2425F, -2.3993F));

		PartDefinition head_r17 = bone5.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(60, 0).addBox(0.4676F, -1.2129F, -4.1294F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(2.7777F, 0.5604F, -1.6556F, -2.6031F, 0.1484F, -2.8135F));

		PartDefinition head_r18 = bone5.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(60, 0).addBox(-0.0818F, -0.9295F, 1.5095F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-0.1183F, -0.281F, 0.6499F, -1.5892F, -0.9284F, -2.616F));

		PartDefinition head_r19 = bone5.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(60, 0).addBox(-0.5285F, -1.7219F, 0.0968F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-0.1183F, -0.281F, 0.6499F, -1.4588F, -0.8705F, -2.8815F));

		PartDefinition head_r20 = bone5.addOrReplaceChild("head_r20", CubeListBuilder.create().texOffs(60, 0).addBox(-0.6855F, -2.0201F, -1.2046F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-0.1183F, -0.281F, 0.6499F, -1.8079F, -0.8705F, -2.8815F));

		PartDefinition head_r21 = bone5.addOrReplaceChild("head_r21", CubeListBuilder.create().texOffs(60, 0).addBox(1.5035F, 0.7736F, 1.1086F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.8817F, -0.281F, 0.1499F, -1.9677F, -0.4045F, -2.4871F));

		PartDefinition hair4 = body.addOrReplaceChild("hair4", CubeListBuilder.create(), PartPose.offsetAndRotation(6.2691F, 4.3677F, -2.1315F, -1.6013F, 0.2128F, -0.0925F));

		PartDefinition head_r22 = hair4.addOrReplaceChild("head_r22", CubeListBuilder.create().texOffs(60, 27).addBox(-0.9084F, -6.4154F, -3.2046F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.3F))
		.texOffs(60, 27).addBox(-0.9084F, -6.4154F, -2.2046F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(60, 27).addBox(-0.9084F, -6.4154F, -1.2046F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.3F))
		.texOffs(60, 27).addBox(-0.9084F, -6.4154F, -0.2046F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r23 = hair4.addOrReplaceChild("head_r23", CubeListBuilder.create().texOffs(60, 8).addBox(-0.8937F, -1.2862F, -0.932F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 8).addBox(-0.0285F, -1.4719F, -0.4032F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, 0.1309F, 0.0F, -0.6981F));

		PartDefinition head_r24 = hair4.addOrReplaceChild("head_r24", CubeListBuilder.create().texOffs(60, 8).addBox(-1.5506F, -1.1677F, -1.845F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, -0.0436F, 0.0F, -0.6981F));

		PartDefinition head_r25 = hair4.addOrReplaceChild("head_r25", CubeListBuilder.create().texOffs(60, 8).addBox(-0.278F, -2.5862F, -0.3986F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.3593F, 1.427F, -4.0282F, -0.1846F, 0.04F, -0.6891F));

		PartDefinition head_r26 = hair4.addOrReplaceChild("head_r26", CubeListBuilder.create().texOffs(60, 8).addBox(-1.5506F, -1.124F, -2.844F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(60, 8).addBox(-0.6855F, -1.3987F, -2.3555F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, -0.0436F, 0.0F, -0.6981F));

		PartDefinition head_r27 = hair4.addOrReplaceChild("head_r27", CubeListBuilder.create().texOffs(60, 8).addBox(-0.6855F, -1.1838F, -3.4973F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, -0.0873F, 0.0F, -0.6981F));

		PartDefinition head_r28 = hair4.addOrReplaceChild("head_r28", CubeListBuilder.create().texOffs(60, 27).addBox(-0.0452F, -6.1012F, 2.6327F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, -0.0232F, -0.4891F, -0.3979F));

		PartDefinition head_r29 = hair4.addOrReplaceChild("head_r29", CubeListBuilder.create().texOffs(60, 27).addBox(-0.515F, -6.3243F, 1.3728F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, 0.0318F, -0.1377F, -0.5079F));

		PartDefinition head_r30 = hair4.addOrReplaceChild("head_r30", CubeListBuilder.create().texOffs(60, 8).addBox(1.3765F, 2.3986F, 1.9089F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, 0.1636F, -0.3545F, -0.4179F));

		PartDefinition head_r31 = hair4.addOrReplaceChild("head_r31", CubeListBuilder.create().texOffs(60, 8).addBox(-0.0818F, -0.6795F, 1.2595F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, 0.2165F, -0.1359F, -0.8082F));

		PartDefinition head_r32 = hair4.addOrReplaceChild("head_r32", CubeListBuilder.create().texOffs(60, 8).addBox(-0.37F, -1.3789F, 1.3738F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.6817F, 0.2932F, -0.2832F, -0.7893F));

		PartDefinition head_r33 = hair4.addOrReplaceChild("head_r33", CubeListBuilder.create().texOffs(60, 8).addBox(1.5035F, 1.7736F, 1.1086F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.6817F, 0.0042F, -0.4514F, -0.3567F));

		PartDefinition head_r34 = hair4.addOrReplaceChild("head_r34", CubeListBuilder.create().texOffs(60, 8).addBox(1.5035F, 1.8287F, 2.4963F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, 0.2174F, -0.4802F, -0.3152F));

		PartDefinition head_r35 = hair4.addOrReplaceChild("head_r35", CubeListBuilder.create().texOffs(60, 8).addBox(-0.0322F, -0.8072F, 2.0633F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, 0.4024F, -0.3446F, -0.7691F));

		PartDefinition head_r36 = hair4.addOrReplaceChild("head_r36", CubeListBuilder.create().texOffs(60, 8).addBox(-0.6855F, -1.5201F, -1.2046F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.101F, -0.0043F, -0.1817F, 0.0F, 0.0F, -0.6981F));

		PartDefinition hair7 = body.addOrReplaceChild("hair7", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.2691F, 4.3677F, -2.1315F, -1.6013F, -0.2128F, 0.0925F));

		PartDefinition head_r37 = hair7.addOrReplaceChild("head_r37", CubeListBuilder.create().texOffs(60, 27).mirror().addBox(-0.0916F, -6.4154F, -3.2046F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(60, 27).mirror().addBox(-0.0916F, -6.4154F, -2.2046F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(60, 27).mirror().addBox(-0.0916F, -6.4154F, -1.2046F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(60, 27).mirror().addBox(-0.0916F, -6.4154F, -0.2046F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, 0.0F, 0.0F, 0.3491F));

		PartDefinition head_r38 = hair7.addOrReplaceChild("head_r38", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.1063F, -1.2862F, -0.932F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(60, 8).mirror().addBox(-0.9715F, -1.4719F, -0.4032F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, 0.1309F, 0.0F, 0.6981F));

		PartDefinition head_r39 = hair7.addOrReplaceChild("head_r39", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(0.5506F, -1.1677F, -1.845F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, -0.0436F, 0.0F, 0.6981F));

		PartDefinition head_r40 = hair7.addOrReplaceChild("head_r40", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.722F, -2.5862F, -0.3986F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.3593F, 1.427F, -4.0282F, -0.1846F, -0.04F, 0.6891F));

		PartDefinition head_r41 = hair7.addOrReplaceChild("head_r41", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(0.5506F, -1.124F, -2.844F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(60, 8).mirror().addBox(-0.3145F, -1.3987F, -2.3555F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, -0.0436F, 0.0F, 0.6981F));

		PartDefinition head_r42 = hair7.addOrReplaceChild("head_r42", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.3145F, -1.1838F, -3.4973F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, -0.0873F, 0.0F, 0.6981F));

		PartDefinition head_r43 = hair7.addOrReplaceChild("head_r43", CubeListBuilder.create().texOffs(60, 27).mirror().addBox(-0.9548F, -6.1012F, 2.6327F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, -0.0232F, 0.4891F, 0.3979F));

		PartDefinition head_r44 = hair7.addOrReplaceChild("head_r44", CubeListBuilder.create().texOffs(60, 27).mirror().addBox(-0.485F, -6.3243F, 1.3728F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, 0.0318F, 0.1377F, 0.5079F));

		PartDefinition head_r45 = hair7.addOrReplaceChild("head_r45", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-2.3765F, 2.3986F, 1.9089F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, 0.1636F, 0.3545F, 0.4179F));

		PartDefinition head_r46 = hair7.addOrReplaceChild("head_r46", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.9182F, -0.6795F, 1.2595F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, 0.2165F, 0.1359F, 0.8082F));

		PartDefinition head_r47 = hair7.addOrReplaceChild("head_r47", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.63F, -1.3789F, 1.3738F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.6817F, 0.2932F, 0.2832F, 0.7893F));

		PartDefinition head_r48 = hair7.addOrReplaceChild("head_r48", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-2.5035F, 1.7736F, 1.1086F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.6817F, 0.0042F, 0.4514F, 0.3567F));

		PartDefinition head_r49 = hair7.addOrReplaceChild("head_r49", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-2.5035F, 1.8287F, 2.4963F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, 0.2174F, 0.4802F, 0.3152F));

		PartDefinition head_r50 = hair7.addOrReplaceChild("head_r50", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.9678F, -0.8072F, 2.0633F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, 0.4024F, 0.3446F, 0.7691F));

		PartDefinition head_r51 = hair7.addOrReplaceChild("head_r51", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.3145F, -1.5201F, -1.2046F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.101F, -0.0043F, -0.1817F, 0.0F, 0.0F, 0.6981F));

		PartDefinition hair6 = body.addOrReplaceChild("hair6", CubeListBuilder.create(), PartPose.offsetAndRotation(2.6933F, 8.512F, -2.681F, -1.5781F, 0.2371F, 0.9819F));

		PartDefinition head_r52 = hair6.addOrReplaceChild("head_r52", CubeListBuilder.create().texOffs(60, 8).addBox(-0.8937F, -1.2862F, -0.932F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 8).addBox(-0.0285F, -1.4719F, 0.0968F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.1322F, 0.1309F, 0.0F, -0.6981F));

		PartDefinition head_r53 = hair6.addOrReplaceChild("head_r53", CubeListBuilder.create().texOffs(60, 8).addBox(-1.5506F, -1.1677F, -1.845F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.1322F, -0.0436F, 0.0F, -0.6981F));

		PartDefinition head_r54 = hair6.addOrReplaceChild("head_r54", CubeListBuilder.create().texOffs(60, 8).addBox(-1.5506F, -1.124F, -2.844F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(60, 8).addBox(-0.6855F, -1.3987F, -2.3555F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.1322F, -0.1106F, -0.0561F, -0.6963F));

		PartDefinition head_r55 = hair6.addOrReplaceChild("head_r55", CubeListBuilder.create().texOffs(60, 27).addBox(0.0F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-1.693F, -2.7288F, 1.615F, 0.155F, -0.3304F, -0.7454F));

		PartDefinition head_r56 = hair6.addOrReplaceChild("head_r56", CubeListBuilder.create().texOffs(60, 8).addBox(1.3765F, 2.3986F, 1.9089F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.1322F, 0.1636F, -0.3545F, -0.4179F));

		PartDefinition head_r57 = hair6.addOrReplaceChild("head_r57", CubeListBuilder.create().texOffs(60, 8).addBox(0.4182F, -1.6795F, 1.5095F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.1322F, 0.2273F, -0.1167F, -0.894F));

		PartDefinition head_r58 = hair6.addOrReplaceChild("head_r58", CubeListBuilder.create().texOffs(60, 8).addBox(-0.37F, -1.3789F, 1.3738F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.6322F, 0.0314F, -0.2832F, -0.7893F));

		PartDefinition head_r59 = hair6.addOrReplaceChild("head_r59", CubeListBuilder.create().texOffs(60, 8).addBox(1.5035F, 1.7736F, 1.1086F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.6322F, 0.0042F, -0.4514F, -0.3567F));

		PartDefinition head_r60 = hair6.addOrReplaceChild("head_r60", CubeListBuilder.create().texOffs(60, 8).addBox(1.5035F, 1.8287F, 2.4963F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.1322F, 0.2174F, -0.4802F, -0.3152F));

		PartDefinition head_r61 = hair6.addOrReplaceChild("head_r61", CubeListBuilder.create().texOffs(60, 8).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.3751F, -3.1052F, -1.5627F, -0.1693F, -0.0298F, -0.3478F));

		PartDefinition head_r62 = hair6.addOrReplaceChild("head_r62", CubeListBuilder.create().texOffs(60, 27).addBox(0.5F, -3.0F, -0.75F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.3751F, -2.9684F, -0.8368F, -0.0394F, -0.03F, -0.6091F));

		PartDefinition head_r63 = hair6.addOrReplaceChild("head_r63", CubeListBuilder.create().texOffs(60, 8).addBox(-0.6855F, -1.5201F, -1.2046F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.1768F, 0.1014F, -0.1322F, 0.0F, 0.0F, -0.6981F));

		PartDefinition head_r64 = hair6.addOrReplaceChild("head_r64", CubeListBuilder.create().texOffs(60, 27).addBox(0.0F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.3751F, -2.9684F, 0.1632F, 0.0F, 0.0F, -0.5672F));

		PartDefinition hair8 = body.addOrReplaceChild("hair8", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.6933F, 8.512F, -2.681F, -1.5781F, -0.2371F, -0.9819F));

		PartDefinition head_r65 = hair8.addOrReplaceChild("head_r65", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.1063F, -1.2862F, -0.932F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(60, 8).mirror().addBox(-0.9715F, -1.4719F, 0.0968F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.1322F, 0.1309F, 0.0F, 0.6981F));

		PartDefinition head_r66 = hair8.addOrReplaceChild("head_r66", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(0.5506F, -1.1677F, -1.845F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.1322F, -0.0436F, 0.0F, 0.6981F));

		PartDefinition head_r67 = hair8.addOrReplaceChild("head_r67", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(0.5506F, -1.124F, -2.844F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(60, 8).mirror().addBox(-0.3145F, -1.3987F, -2.3555F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.1322F, -0.1106F, 0.0561F, 0.6963F));

		PartDefinition head_r68 = hair8.addOrReplaceChild("head_r68", CubeListBuilder.create().texOffs(60, 27).mirror().addBox(-1.0F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(1.693F, -2.7288F, 1.615F, 0.155F, 0.3304F, 0.7454F));

		PartDefinition head_r69 = hair8.addOrReplaceChild("head_r69", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-2.3765F, 2.3986F, 1.9089F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.1322F, 0.1636F, 0.3545F, 0.4179F));

		PartDefinition head_r70 = hair8.addOrReplaceChild("head_r70", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-1.4182F, -1.6795F, 1.5095F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.1322F, 0.2273F, 0.1167F, 0.894F));

		PartDefinition head_r71 = hair8.addOrReplaceChild("head_r71", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.63F, -1.3789F, 1.3738F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.6322F, 0.0314F, 0.2832F, 0.7893F));

		PartDefinition head_r72 = hair8.addOrReplaceChild("head_r72", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-2.5035F, 1.7736F, 1.1086F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.6322F, 0.0042F, 0.4514F, 0.3567F));

		PartDefinition head_r73 = hair8.addOrReplaceChild("head_r73", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-2.5035F, 1.8287F, 2.4963F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.1322F, 0.2174F, 0.4802F, 0.3152F));

		PartDefinition head_r74 = hair8.addOrReplaceChild("head_r74", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.0151F, -6.4432F, 0.9007F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.1322F, 0.195F, 0.4891F, 0.3979F));

		PartDefinition head_r75 = hair8.addOrReplaceChild("head_r75", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.5F, -3.0F, -1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.3751F, -3.1052F, -1.5627F, -0.1693F, 0.0298F, 0.3478F));

		PartDefinition head_r76 = hair8.addOrReplaceChild("head_r76", CubeListBuilder.create().texOffs(60, 27).mirror().addBox(-1.25F, -2.5F, -0.75F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.3751F, -2.9684F, -0.8368F, -0.0394F, 0.03F, 0.6091F));

		PartDefinition head_r77 = hair8.addOrReplaceChild("head_r77", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.3145F, -1.5201F, -1.2046F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-0.1768F, 0.1014F, -0.1322F, 0.0F, 0.0F, 0.6981F));

		PartDefinition head_r78 = hair8.addOrReplaceChild("head_r78", CubeListBuilder.create().texOffs(60, 27).mirror().addBox(-1.0F, -2.75F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.3751F, -2.9684F, 0.1632F, 0.0F, 0.0F, 0.5672F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(22, 1).addBox(-3.0F, -1.5F, -5.5F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head_r79 = upper_body.addOrReplaceChild("head_r79", CubeListBuilder.create().texOffs(60, 10).mirror().addBox(0.4355F, -2.7701F, -1.2046F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(3.1299F, 2.6134F, 2.9368F, -2.4894F, 1.0026F, 2.0756F));

		PartDefinition head_r80 = upper_body.addOrReplaceChild("head_r80", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.2215F, -1.7219F, 0.0968F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(3.1299F, 2.6134F, 2.9368F, -2.4814F, 0.9752F, 2.1374F));

		PartDefinition head_r81 = upper_body.addOrReplaceChild("head_r81", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-1.0331F, 0.229F, -1.3941F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.6255F, 1.7975F, -2.3458F, 1.0669F, 2.0362F));

		PartDefinition head_r82 = upper_body.addOrReplaceChild("head_r82", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-1.3782F, -1.2105F, 3.5762F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(3.1299F, 2.6134F, 2.9368F, -2.4963F, 0.8657F, 1.9117F));

		PartDefinition head_r83 = upper_body.addOrReplaceChild("head_r83", CubeListBuilder.create().texOffs(60, 8).addBox(0.0331F, 0.229F, -1.3941F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(1.0F, 0.6255F, 1.7975F, -2.3458F, -1.0669F, -2.0362F));

		PartDefinition head_r84 = upper_body.addOrReplaceChild("head_r84", CubeListBuilder.create().texOffs(60, 8).addBox(-0.7785F, -1.7219F, 0.0968F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-1.1299F, 2.6134F, 2.9368F, -2.4814F, -0.9752F, -2.1374F));

		PartDefinition head_r85 = upper_body.addOrReplaceChild("head_r85", CubeListBuilder.create().texOffs(60, 10).addBox(-1.4355F, -2.7701F, -1.2046F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-1.1299F, 2.6134F, 2.9368F, -2.4894F, -1.0026F, -2.0756F));

		PartDefinition head_r86 = upper_body.addOrReplaceChild("head_r86", CubeListBuilder.create().texOffs(60, 8).addBox(0.3782F, -1.4605F, 3.5762F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-1.1299F, 2.6134F, 2.9368F, -2.4963F, -0.8657F, -1.9117F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(22, 1).addBox(-3.0F, 1.5F, -6.5F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition hair5 = upper_body.addOrReplaceChild("hair5", CubeListBuilder.create(), PartPose.offsetAndRotation(4.7691F, -1.1323F, -11.1315F, -1.6013F, 0.2128F, -0.0925F));

		PartDefinition head_r87 = hair5.addOrReplaceChild("head_r87", CubeListBuilder.create().texOffs(60, 8).addBox(-0.9084F, -4.4154F, -3.2046F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F))
		.texOffs(60, 8).addBox(-0.9084F, -4.4154F, -2.2046F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(60, 8).addBox(-0.9084F, -4.4154F, -1.2046F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F))
		.texOffs(60, 8).addBox(-0.9084F, -4.4154F, -0.2046F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.149F, -8.2543F, 3.0683F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r88 = hair5.addOrReplaceChild("head_r88", CubeListBuilder.create().texOffs(60, 8).addBox(-1.5506F, 1.1826F, -3.9068F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.149F, -9.0043F, 3.0683F, -0.2618F, 0.0F, -0.6981F));

		PartDefinition head_r89 = hair5.addOrReplaceChild("head_r89", CubeListBuilder.create().texOffs(60, 8).addBox(-0.6855F, 0.8162F, -3.4973F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-1.149F, -9.0043F, 3.0683F, -0.0873F, 0.0F, -0.6981F));

		PartDefinition head_r90 = hair5.addOrReplaceChild("head_r90", CubeListBuilder.create().texOffs(60, 27).addBox(-0.0452F, -5.1012F, 2.6327F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.149F, -8.5043F, 3.0683F, 0.1947F, -0.3949F, -0.4915F));

		PartDefinition head_r91 = hair5.addOrReplaceChild("head_r91", CubeListBuilder.create().texOffs(60, 8).addBox(-0.515F, -4.3243F, 1.3728F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-0.149F, -8.5043F, 3.0683F, 0.2222F, -0.0298F, -0.5239F));

		PartDefinition head_r92 = hair5.addOrReplaceChild("head_r92", CubeListBuilder.create().texOffs(60, 8).addBox(-0.0818F, 1.3205F, 1.2595F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.149F, -9.2543F, 3.0683F, 0.3666F, 0.0228F, -0.8167F));

		PartDefinition head_r93 = hair5.addOrReplaceChild("head_r93", CubeListBuilder.create().texOffs(60, 8).addBox(-0.37F, 0.6211F, 1.3738F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.149F, -9.0043F, 2.5683F, 0.2932F, -0.2832F, -0.7893F));

		PartDefinition head_r94 = hair5.addOrReplaceChild("head_r94", CubeListBuilder.create().texOffs(60, 8).addBox(-0.7529F, -0.9767F, -0.5501F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.775F, -8.0126F, 6.3747F, 0.7854F, -0.2982F, -0.7861F));

		PartDefinition head_r95 = hair5.addOrReplaceChild("head_r95", CubeListBuilder.create().texOffs(60, 8).addBox(-0.9849F, -4.4432F, 0.9007F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.149F, -8.2543F, 3.0683F, 0.195F, -0.4891F, -0.3979F));

		PartDefinition head_r96 = hair5.addOrReplaceChild("head_r96", CubeListBuilder.create().texOffs(60, 8).addBox(-0.6855F, 0.6013F, -2.3555F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.149F, -9.0043F, 3.0683F, -0.0436F, 0.0F, -0.6981F));

		PartDefinition head_r97 = hair5.addOrReplaceChild("head_r97", CubeListBuilder.create().texOffs(60, 8).addBox(-0.6855F, 0.4799F, -1.2046F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.149F, -9.0043F, 3.0683F, 0.0F, 0.0F, -0.6981F));

		PartDefinition head_r98 = hair5.addOrReplaceChild("head_r98", CubeListBuilder.create().texOffs(60, 8).addBox(-0.0285F, 0.5281F, -0.4032F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.149F, -9.0043F, 3.0683F, 0.1309F, 0.0F, -0.6981F));

		PartDefinition hair9 = upper_body.addOrReplaceChild("hair9", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.7691F, -1.1323F, -11.1315F, -1.6013F, -0.2128F, 0.0925F));

		PartDefinition head_r99 = hair9.addOrReplaceChild("head_r99", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.0916F, -4.4154F, -3.2046F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(60, 8).mirror().addBox(-0.0916F, -4.4154F, -2.2046F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(60, 8).mirror().addBox(-0.0916F, -4.4154F, -1.2046F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(60, 8).mirror().addBox(-0.0916F, -4.4154F, -0.2046F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.149F, -8.2543F, 3.0683F, 0.0F, 0.0F, 0.3491F));

		PartDefinition head_r100 = hair9.addOrReplaceChild("head_r100", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(0.5506F, 1.1826F, -3.9068F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.149F, -9.0043F, 3.0683F, -0.2618F, 0.0F, 0.6981F));

		PartDefinition head_r101 = hair9.addOrReplaceChild("head_r101", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.3145F, 0.8162F, -3.4973F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(1.149F, -9.0043F, 3.0683F, -0.0873F, 0.0F, 0.6981F));

		PartDefinition head_r102 = hair9.addOrReplaceChild("head_r102", CubeListBuilder.create().texOffs(60, 27).mirror().addBox(-0.9548F, -5.1012F, 2.6327F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.149F, -8.5043F, 3.0683F, 0.1947F, 0.3949F, 0.4915F));

		PartDefinition head_r103 = hair9.addOrReplaceChild("head_r103", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.485F, -4.3243F, 1.3728F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.149F, -8.5043F, 3.0683F, 0.2222F, 0.0298F, 0.5239F));

		PartDefinition head_r104 = hair9.addOrReplaceChild("head_r104", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.9182F, 1.3205F, 1.2595F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.149F, -9.2543F, 3.0683F, 0.3666F, -0.0228F, 0.8167F));

		PartDefinition head_r105 = hair9.addOrReplaceChild("head_r105", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.63F, 0.6211F, 1.3738F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(1.149F, -9.0043F, 2.5683F, 0.2932F, 0.2832F, 0.7893F));

		PartDefinition head_r106 = hair9.addOrReplaceChild("head_r106", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.2471F, -0.9767F, -0.5501F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.775F, -8.0126F, 6.3747F, 0.7854F, 0.2982F, 0.7861F));

		PartDefinition head_r107 = hair9.addOrReplaceChild("head_r107", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.0151F, -4.4432F, 0.9007F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.149F, -8.2543F, 3.0683F, 0.195F, 0.4891F, 0.3979F));

		PartDefinition head_r108 = hair9.addOrReplaceChild("head_r108", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.3145F, 0.6013F, -2.3555F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.149F, -9.0043F, 3.0683F, -0.0436F, 0.0F, 0.6981F));

		PartDefinition head_r109 = hair9.addOrReplaceChild("head_r109", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.3145F, 0.4799F, -1.2046F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.149F, -9.0043F, 3.0683F, 0.0F, 0.0F, 0.6981F));

		PartDefinition head_r110 = hair9.addOrReplaceChild("head_r110", CubeListBuilder.create().texOffs(60, 8).mirror().addBox(-0.9715F, 0.5281F, -0.4032F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.149F, -9.0043F, 3.0683F, 0.1309F, 0.0F, 0.6981F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(1, 1).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.4F))
		.texOffs(18, 38).addBox(-1.0F, -6.0F, -0.75F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.5F))
		.texOffs(26, 52).addBox(-2.0F, -9.5F, -1.25F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.5F))
		.texOffs(26, 48).addBox(-2.0F, -8.5F, -1.25F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.7F))
		.texOffs(26, 40).addBox(-2.0F, -10.75F, -1.25F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(41, 40).addBox(-2.0F, -8.25F, -1.25F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(45, 20).addBox(-3.0F, -3.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(46, 20).mirror().addBox(2.0F, -3.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(40, 49).addBox(-5.5F, -14.0F, -0.25F, 11.0F, 12.0F, 1.0F, new CubeDeformation(-2.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r111 = real_head.addOrReplaceChild("head_r111", CubeListBuilder.create().texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.8062F, -0.3716F, -1.6325F, -0.195F, -0.4891F, 0.3979F));

		PartDefinition head_r112 = real_head.addOrReplaceChild("head_r112", CubeListBuilder.create().texOffs(60, 26).addBox(0.0F, -3.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8062F, -0.3716F, -1.6325F, -0.195F, 0.4891F, -0.3979F));

		PartDefinition head_r113 = real_head.addOrReplaceChild("head_r113", CubeListBuilder.create().texOffs(45, 20).mirror().addBox(-0.3F, -1.1F, -2.8F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(1.0F, -2.3F, 0.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r114 = real_head.addOrReplaceChild("head_r114", CubeListBuilder.create().texOffs(45, 20).addBox(-0.7F, -1.1F, -2.8F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.offsetAndRotation(-1.0F, -2.3F, 0.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition hair1 = real_head.addOrReplaceChild("hair1", CubeListBuilder.create(), PartPose.offset(3.5F, -0.25F, 3.5F));

		PartDefinition head_r115 = hair1.addOrReplaceChild("head_r115", CubeListBuilder.create().texOffs(60, 26).addBox(0.0F, -3.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(60, 26).addBox(0.0F, -3.0F, -2.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(60, 26).addBox(0.0F, -3.0F, -3.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(60, 26).addBox(0.0F, -3.0F, -4.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r116 = hair1.addOrReplaceChild("head_r116", CubeListBuilder.create().texOffs(60, 18).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(2.8486F, 4.4966F, -3.5F, -0.1309F, 0.0F, -0.6981F));

		PartDefinition head_r117 = hair1.addOrReplaceChild("head_r117", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.5F, 0.75F, -1.5F, 0.0436F, 0.0F, -0.6981F));

		PartDefinition head_r118 = hair1.addOrReplaceChild("head_r118", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 1.25F, -2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.5F, 0.75F, 0.5F, 0.2618F, 0.0F, -0.6981F));

		PartDefinition head_r119 = hair1.addOrReplaceChild("head_r119", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.5F, 0.75F, -0.5F, 0.0436F, 0.0F, -0.6981F));

		PartDefinition head_r120 = hair1.addOrReplaceChild("head_r120", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, -0.6981F));

		PartDefinition head_r121 = hair1.addOrReplaceChild("head_r121", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, -0.2279F, 0.3446F, -0.7691F));

		PartDefinition head_r122 = hair1.addOrReplaceChild("head_r122", CubeListBuilder.create().texOffs(60, 18).addBox(-1.4F, -1.0F, 1.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.677F, 5.0978F, -8.2776F, 0.088F, 0.4802F, -0.3152F));

		PartDefinition head_r123 = hair1.addOrReplaceChild("head_r123", CubeListBuilder.create().texOffs(60, 18).addBox(-1.9F, -1.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.677F, 5.0978F, -8.5276F, -0.2174F, 0.4802F, -0.3152F));

		PartDefinition head_r124 = hair1.addOrReplaceChild("head_r124", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, -0.4024F, 0.3446F, -0.7691F));

		PartDefinition head_r125 = hair1.addOrReplaceChild("head_r125", CubeListBuilder.create().texOffs(60, 18).addBox(0.0F, -3.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, -0.195F, 0.4891F, -0.3979F));

		PartDefinition head_r126 = hair1.addOrReplaceChild("head_r126", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0436F, 0.0F, -0.6981F));

		PartDefinition head_r127 = hair1.addOrReplaceChild("head_r127", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition head_r128 = hair1.addOrReplaceChild("head_r128", CubeListBuilder.create().texOffs(60, 18).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3486F, 3.7466F, -4.0F, -0.1309F, 0.0F, -0.6981F));

		PartDefinition hair3 = real_head.addOrReplaceChild("hair3", CubeListBuilder.create(), PartPose.offset(-3.5F, -0.25F, 3.5F));

		PartDefinition head_r129 = hair3.addOrReplaceChild("head_r129", CubeListBuilder.create().texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, -2.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, -3.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, -4.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition head_r130 = hair3.addOrReplaceChild("head_r130", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-2.8486F, 4.4966F, -3.5F, -0.1309F, 0.0F, 0.6981F));

		PartDefinition head_r131 = hair3.addOrReplaceChild("head_r131", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.75F, -1.5F, 0.0436F, 0.0F, 0.6981F));

		PartDefinition head_r132 = hair3.addOrReplaceChild("head_r132", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 1.25F, -2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.75F, 0.5F, 0.2618F, 0.0F, 0.6981F));

		PartDefinition head_r133 = hair3.addOrReplaceChild("head_r133", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.75F, -0.5F, 0.0436F, 0.0F, 0.6981F));

		PartDefinition head_r134 = hair3.addOrReplaceChild("head_r134", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.6981F));

		PartDefinition head_r135 = hair3.addOrReplaceChild("head_r135", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, -0.2279F, -0.3446F, 0.7691F));

		PartDefinition head_r136 = hair3.addOrReplaceChild("head_r136", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.4F, -1.0F, 1.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-3.677F, 5.0978F, -8.2776F, 0.088F, -0.4802F, 0.3152F));

		PartDefinition head_r137 = hair3.addOrReplaceChild("head_r137", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.9F, -1.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-3.677F, 5.0978F, -8.5276F, -0.2174F, -0.4802F, 0.3152F));

		PartDefinition head_r138 = hair3.addOrReplaceChild("head_r138", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, -0.4024F, -0.3446F, 0.7691F));

		PartDefinition head_r139 = hair3.addOrReplaceChild("head_r139", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-1.0F, -3.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, -0.195F, -0.4891F, 0.3979F));

		PartDefinition head_r140 = hair3.addOrReplaceChild("head_r140", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0436F, 0.0F, 0.6981F));

		PartDefinition head_r141 = hair3.addOrReplaceChild("head_r141", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition head_r142 = hair3.addOrReplaceChild("head_r142", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.3486F, 3.7466F, -4.0F, -0.1309F, 0.0F, 0.6981F));

		PartDefinition hair2 = real_head.addOrReplaceChild("hair2", CubeListBuilder.create(), PartPose.offset(-3.5F, -0.25F, 3.5F));

		PartDefinition head_r143 = hair2.addOrReplaceChild("head_r143", CubeListBuilder.create().texOffs(60, 26).addBox(-0.5F, -1.25F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -2.0F, -6.0F, -0.2182F, 0.0F, -0.3927F));

		PartDefinition head_r144 = hair2.addOrReplaceChild("head_r144", CubeListBuilder.create().texOffs(60, 26).mirror().addBox(-0.5F, -1.25F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.5F, -2.0F, -6.0F, -0.2182F, 0.0F, 0.3927F));

		PartDefinition upperjaw = real_head.addOrReplaceChild("upperjaw", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, 1.25F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.7F))
		.texOffs(0, 30).addBox(-1.5F, -1.75F, 0.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
		.texOffs(3, 13).addBox(-1.5F, -1.5F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.5F))
		.texOffs(3, 13).addBox(-1.5F, -1.25F, 4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
		.texOffs(10, 45).addBox(1.1F, 0.32F, 3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(10, 45).addBox(1.1F, 0.32F, 0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(10, 45).mirror().addBox(-2.1F, 0.32F, 0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(10, 45).mirror().addBox(-2.1F, 0.32F, 3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offset(0.0F, -0.52F, -6.75F));

		PartDefinition head_r145 = upperjaw.addOrReplaceChild("head_r145", CubeListBuilder.create().texOffs(0, 32).addBox(-1.5F, -0.5F, -0.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition lowerjaw = real_head.addOrReplaceChild("lowerjaw", CubeListBuilder.create().texOffs(0, 39).addBox(-2.0F, -0.25F, -1.5F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 5.98F, -5.5F));

		PartDefinition cube_r1 = lowerjaw.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 45).addBox(-0.4F, -0.95F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(10, 45).addBox(-0.4F, -0.45F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(10, 45).mirror().addBox(-4.6F, -0.45F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(10, 45).mirror().addBox(-4.6F, -0.95F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(2.0F, -2.73F, -0.25F, -0.1309F, 0.0F, 0.0F));

		PartDefinition head_r146 = lowerjaw.addOrReplaceChild("head_r146", CubeListBuilder.create().texOffs(3, 14).addBox(-2.0F, -2.95F, -3.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.5F))
		.texOffs(14, 30).addBox(-2.0F, 0.0F, -2.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F))
		.texOffs(3, 14).addBox(-2.0F, -2.0F, -4.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.4F))
		.texOffs(0, 50).addBox(-2.0F, -2.0F, 2.5F, 4.0F, 2.0F, 2.0F, new CubeDeformation(1.2F))
		.texOffs(2, 41).addBox(-2.0F, -2.0F, -3.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -1.0F, 0.5F, -0.0352F, 0.0829F, -0.147F));

		PartDefinition head_r147 = left_ear.addOrReplaceChild("head_r147", CubeListBuilder.create().texOffs(60, 18).addBox(-1.5F, -2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.1548F, 4.375F, -1.1325F, 0.0078F, 0.0447F, -0.3518F));

		PartDefinition head_r148 = left_ear.addOrReplaceChild("head_r148", CubeListBuilder.create().texOffs(60, 18).addBox(-0.109F, 0.3521F, -0.4486F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.9198F, 4.8309F, -4.0993F, -0.1636F, 0.3545F, -0.4179F));

		PartDefinition head_r149 = left_ear.addOrReplaceChild("head_r149", CubeListBuilder.create().texOffs(60, 26).addBox(0.5F, -3.0F, -1.25F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.8062F, 0.6284F, -1.1325F, -0.1513F, 0.4891F, -0.3979F));

		PartDefinition head_r150 = left_ear.addOrReplaceChild("head_r150", CubeListBuilder.create().texOffs(60, 18).addBox(-0.3033F, -3.3893F, -0.6208F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(3.9198F, 4.8309F, -3.8493F, -0.391F, 0.1359F, -0.8082F));

		PartDefinition head_r151 = left_ear.addOrReplaceChild("head_r151", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.8062F, 0.6284F, 2.8675F, 0.0873F, 0.0F, -0.6981F));

		PartDefinition head_r152 = left_ear.addOrReplaceChild("head_r152", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.8062F, 0.6284F, 1.8675F, 0.0436F, 0.0F, -0.6981F));

		PartDefinition head_r153 = left_ear.addOrReplaceChild("head_r153", CubeListBuilder.create().texOffs(60, 18).addBox(-1.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.8062F, 0.6284F, 0.8675F, 0.0F, 0.0F, -0.6981F));

		PartDefinition head_r154 = left_ear.addOrReplaceChild("head_r154", CubeListBuilder.create().texOffs(60, 18).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.1548F, 4.375F, -1.1325F, -0.1309F, 0.0F, -0.6981F));

		PartDefinition head_r155 = left_ear.addOrReplaceChild("head_r155", CubeListBuilder.create().texOffs(60, 26).addBox(0.0F, -3.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 26).addBox(0.0F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(60, 26).addBox(0.0F, -3.0F, 0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(60, 26).addBox(0.0F, -3.0F, 1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.8062F, 0.6284F, -0.1325F, 0.0F, 0.0F, -0.3491F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -1.0F, 0.5F, -0.0352F, -0.0829F, 0.147F));

		PartDefinition head_r156 = right_ear.addOrReplaceChild("head_r156", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.5F, -2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.1548F, 4.375F, -1.1325F, 0.0078F, -0.0447F, 0.3518F));

		PartDefinition head_r157 = right_ear.addOrReplaceChild("head_r157", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-0.891F, 0.3521F, -0.4486F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-3.9198F, 4.8309F, -4.0993F, -0.1636F, -0.3545F, 0.4179F));

		PartDefinition head_r158 = right_ear.addOrReplaceChild("head_r158", CubeListBuilder.create().texOffs(60, 26).mirror().addBox(-1.5F, -3.0F, -1.25F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.8062F, 0.6284F, -1.1325F, -0.1513F, -0.4891F, 0.3979F));

		PartDefinition head_r159 = right_ear.addOrReplaceChild("head_r159", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-0.6967F, -3.3893F, -0.6208F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-3.9198F, 4.8309F, -3.8493F, -0.391F, -0.1359F, 0.8082F));

		PartDefinition head_r160 = right_ear.addOrReplaceChild("head_r160", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.8062F, 0.6284F, 2.8675F, 0.0873F, 0.0F, 0.6981F));

		PartDefinition head_r161 = right_ear.addOrReplaceChild("head_r161", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.8062F, 0.6284F, 1.8675F, 0.0436F, 0.0F, 0.6981F));

		PartDefinition head_r162 = right_ear.addOrReplaceChild("head_r162", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(0.0F, 2.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.8062F, 0.6284F, 0.8675F, 0.0F, 0.0F, 0.6981F));

		PartDefinition head_r163 = right_ear.addOrReplaceChild("head_r163", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.1548F, 4.375F, -1.1325F, -0.1309F, 0.0F, 0.6981F));

		PartDefinition head_r164 = right_ear.addOrReplaceChild("head_r164", CubeListBuilder.create().texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, -1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, 0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(60, 26).mirror().addBox(-1.0F, -3.0F, 1.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.8062F, 0.6284F, -0.1325F, 0.0F, 0.0F, 0.3491F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
