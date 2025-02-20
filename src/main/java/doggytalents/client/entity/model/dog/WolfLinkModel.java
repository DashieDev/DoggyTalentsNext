package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class WolfLinkModel extends DogModel {

    public WolfLinkModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 20).addBox(-1.0F, 4.75F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 17).addBox(-1.0F, 0.25F, 0.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(38, 16).addBox(-1.0F, 6.2F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition leg12_r1 = left_front_leg.addOrReplaceChild("leg12_r1", CubeListBuilder.create().texOffs(39, 16).addBox(0.2434F, -0.4945F, -0.984F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(39, 16).addBox(-1.2566F, -0.4945F, -0.984F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-0.1F, 7.5712F, 4.9403F, -1.8525F, 0.9114F, -2.061F));

		PartDefinition leg9_r1 = left_front_leg.addOrReplaceChild("leg9_r1", CubeListBuilder.create().texOffs(39, 16).addBox(0.016F, -0.4945F, -1.0434F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(40, 17).addBox(-0.984F, -0.4945F, -1.0434F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.1F, 7.5712F, 4.9403F, -0.64F, 0.1711F, -0.2654F));

		PartDefinition leg8_r1 = left_front_leg.addOrReplaceChild("leg8_r1", CubeListBuilder.create().texOffs(39, 16).addBox(0.0078F, -0.4967F, -1.0485F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(39, 16).addBox(-0.9922F, -0.4967F, -1.0485F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.1F, 7.0809F, 3.9921F, -0.4503F, 0.2182F, 1.124F));

		PartDefinition leg10_r1 = left_front_leg.addOrReplaceChild("leg10_r1", CubeListBuilder.create().texOffs(39, 16).addBox(-1.2515F, -0.4967F, -0.9922F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(39, 16).addBox(0.2485F, -0.4967F, -0.9922F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-0.1F, 7.0809F, 3.9921F, -2.042F, 1.0737F, -0.8678F));

		PartDefinition leg7_r1 = left_front_leg.addOrReplaceChild("leg7_r1", CubeListBuilder.create().texOffs(39, 16).addBox(0.018F, -0.5386F, -1.5322F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(39, 16).addBox(-0.982F, -0.5386F, -1.5322F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 6.8996F, 3.2841F, 0.2382F, 0.1096F, 2.7184F));

		PartDefinition leg9_r2 = left_front_leg.addOrReplaceChild("leg9_r2", CubeListBuilder.create().texOffs(39, 16).addBox(-0.7678F, -0.5386F, -0.982F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(39, 16).addBox(0.7322F, -0.5386F, -0.982F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 6.8996F, 3.2841F, 2.0071F, 1.309F, -1.5708F));

		PartDefinition leg9_r3 = left_front_leg.addOrReplaceChild("leg9_r3", CubeListBuilder.create().texOffs(39, 16).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 6.7F, 1.15F, 0.0F, 1.5708F, 0.0F));

		PartDefinition leg8_r2 = left_front_leg.addOrReplaceChild("leg8_r2", CubeListBuilder.create().texOffs(39, 16).addBox(-0.4F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 6.7F, 2.65F, 0.0F, 1.5708F, 1.309F));

		PartDefinition leg7_r2 = left_front_leg.addOrReplaceChild("leg7_r2", CubeListBuilder.create().texOffs(39, 16).addBox(-1.0F, -0.5F, -1.9F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(39, 16).addBox(0.0F, -0.5F, -1.9F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 6.7F, 2.65F, 0.0F, 0.0F, 1.309F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.5F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone = upper_body.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(2.15F, 2.05F, 1.1F));

		PartDefinition mane_rotation_r1 = bone.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(47, 18).mirror().addBox(-5.0F, -4.0F, 3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3884F, -0.045F, 0.2133F));

		PartDefinition mane_rotation_r2 = bone.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -4.0F, 3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.3F, 0.2F, 0.5F, 0.4081F, -0.0114F, -0.0761F));

		PartDefinition mane_rotation_r3 = bone.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.6F, -0.85F, -0.25F, 0.2023F, 0.0814F, -0.1938F));

		PartDefinition mane_rotation_r4 = bone.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -0.55F, -0.05F, 0.2146F, -0.0379F, -0.0115F));

		PartDefinition mane_rotation_r5 = bone.addOrReplaceChild("mane_rotation_r5", CubeListBuilder.create().texOffs(47, 18).mirror().addBox(-5.0F, -4.0F, 3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.2F, 0.0F, 0.3906F, 0.0147F, 0.068F));

		PartDefinition mane_rotation_r6 = bone.addOrReplaceChild("mane_rotation_r6", CubeListBuilder.create().texOffs(60, 18).addBox(-5.0F, -2.0F, 3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.9F, -1.35F, -3.55F, 0.082F, -0.0298F, 0.4787F));

		PartDefinition mane_rotation_r7 = bone.addOrReplaceChild("mane_rotation_r7", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-2.4F, -0.1F, -2.55F, 0.082F, -0.0298F, 0.4787F));

		PartDefinition mane_rotation_r8 = bone.addOrReplaceChild("mane_rotation_r8", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-2.1F, -0.6F, -1.55F, 0.0865F, -0.0114F, 0.2613F));

		PartDefinition mane_rotation_r9 = bone.addOrReplaceChild("mane_rotation_r9", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 1.65F, -1.25F, 0.0177F, 0.0528F, 0.3449F));

		PartDefinition mane_rotation_r10 = bone.addOrReplaceChild("mane_rotation_r10", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.55F, -1.25F, 0.0058F, 0.0554F, 0.1264F));

		PartDefinition mane_rotation_r11 = bone.addOrReplaceChild("mane_rotation_r11", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.8F, 0.95F, -1.25F, 0.008F, -0.023F, 0.0462F));

		PartDefinition mane_rotation_r12 = bone.addOrReplaceChild("mane_rotation_r12", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 1.25F, -1.25F, 0.089F, 0.0113F, 0.3486F));

		PartDefinition mane_rotation_r13 = bone.addOrReplaceChild("mane_rotation_r13", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-2.1F, -0.1F, -1.05F, 0.2123F, -0.0487F, 0.4319F));

		PartDefinition mane_rotation_r14 = bone.addOrReplaceChild("mane_rotation_r14", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -0.6F, -2.25F, -0.1688F, 0.0486F, 0.2126F));

		PartDefinition mane_rotation_r15 = bone.addOrReplaceChild("mane_rotation_r15", CubeListBuilder.create().texOffs(47, 18).mirror().addBox(-5.0F, -4.0F, 3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.2F, 0.5F, 0.4081F, 0.0114F, 0.0761F));

		PartDefinition mane_rotation_r16 = bone.addOrReplaceChild("mane_rotation_r16", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.3F, 0.0F, 0.4233F, -0.045F, 0.2133F));

		PartDefinition mane_rotation_r17 = bone.addOrReplaceChild("mane_rotation_r17", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -3.0F, 3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-2.2F, -0.1F, -0.05F, 0.2651F, -0.0843F, 0.3657F));

		PartDefinition mane_rotation_r18 = bone.addOrReplaceChild("mane_rotation_r18", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -0.5F, 0.4F, 0.3857F, -0.1046F, 0.3685F));

		PartDefinition mane_rotation_r19 = bone.addOrReplaceChild("mane_rotation_r19", CubeListBuilder.create().texOffs(47, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -0.45F, -1.25F, 0.0059F, 0.0236F, -0.1334F));

		PartDefinition mane_rotation_r20 = bone.addOrReplaceChild("mane_rotation_r20", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.8F, 1.55F, -1.25F, 0.0058F, -0.0554F, -0.1264F));

		PartDefinition mane_rotation_r21 = bone.addOrReplaceChild("mane_rotation_r21", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 1.65F, -1.25F, 0.0177F, -0.0528F, -0.3449F));

		PartDefinition mane_rotation_r22 = bone.addOrReplaceChild("mane_rotation_r22", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, 1.65F, -1.25F, 0.089F, -0.0113F, -0.3486F));

		PartDefinition mane_rotation_r23 = bone.addOrReplaceChild("mane_rotation_r23", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.2F, 1.65F, -2.0F, 0.089F, -0.0113F, -0.2177F));

		PartDefinition mane_rotation_r24 = bone.addOrReplaceChild("mane_rotation_r24", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8F, -0.1F, -0.5F, 0.089F, -0.0113F, -0.2177F));

		PartDefinition mane_rotation_r25 = bone.addOrReplaceChild("mane_rotation_r25", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, -0.6F, -2.25F, -0.1688F, -0.0486F, -0.2126F));

		PartDefinition mane_rotation_r26 = bone.addOrReplaceChild("mane_rotation_r26", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.2F, -0.1F, -1.05F, 0.2123F, 0.0487F, -0.4319F));

		PartDefinition mane_rotation_r27 = bone.addOrReplaceChild("mane_rotation_r27", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -2.0F, 3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.4F, -1.35F, -3.55F, 0.082F, 0.0298F, -0.4787F));

		PartDefinition mane_rotation_r28 = bone.addOrReplaceChild("mane_rotation_r28", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, -0.1F, -0.75F, 0.0017F, -0.0113F, -0.2177F));

		PartDefinition mane_rotation_r29 = bone.addOrReplaceChild("mane_rotation_r29", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.8F, 0.4F, -0.5F, 0.1326F, -0.0113F, -0.2177F));

		PartDefinition mane_rotation_r30 = bone.addOrReplaceChild("mane_rotation_r30", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.8F, -0.1F, -0.5F, 0.1271F, -0.0396F, -0.0013F));

		PartDefinition mane_rotation_r31 = bone.addOrReplaceChild("mane_rotation_r31", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -4.0F, 3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-5.3F, 0.2F, 0.0F, 0.3906F, -0.0147F, -0.068F));

		PartDefinition mane_rotation_r32 = bone.addOrReplaceChild("mane_rotation_r32", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -4.0F, 3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-4.3F, 0.0F, 0.0F, 0.3884F, 0.045F, -0.2133F));

		PartDefinition mane_rotation_r33 = bone.addOrReplaceChild("mane_rotation_r33", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3F, -0.1F, 0.0F, 0.3447F, 0.045F, -0.2133F));

		PartDefinition mane_rotation_r34 = bone.addOrReplaceChild("mane_rotation_r34", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -3.0F, 3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.2F, -0.1F, -0.05F, 0.2553F, 0.0561F, -0.2556F));

		PartDefinition mane_rotation_r35 = bone.addOrReplaceChild("mane_rotation_r35", CubeListBuilder.create().texOffs(47, 18).mirror().addBox(-5.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.7F, -0.55F, -0.25F, 0.2146F, 0.0379F, 0.0115F));

		PartDefinition mane_rotation_r36 = bone.addOrReplaceChild("mane_rotation_r36", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, -0.1F, -0.5F, 0.1271F, 0.0396F, 0.0013F));

		PartDefinition mane_rotation_r37 = bone.addOrReplaceChild("mane_rotation_r37", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.5F, -0.1F, -0.5F, 0.1319F, 0.0182F, 0.1658F));

		PartDefinition mane_rotation_r38 = bone.addOrReplaceChild("mane_rotation_r38", CubeListBuilder.create().texOffs(47, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -0.1F, -0.5F, 0.1929F, 0.0213F, 0.1663F));

		PartDefinition mane_rotation_r39 = bone.addOrReplaceChild("mane_rotation_r39", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -0.1F, -0.75F, 0.0032F, 0.0109F, 0.3486F));

		PartDefinition mane_rotation_r40 = bone.addOrReplaceChild("mane_rotation_r40", CubeListBuilder.create().texOffs(60, 18).addBox(4.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.9F, -0.85F, -0.05F, 0.2459F, -0.0814F, 0.1938F));

		PartDefinition bone2 = upper_body.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(-2.15F, 2.05F, 1.1F));

		PartDefinition mane_rotation_r41 = bone2.addOrReplaceChild("mane_rotation_r41", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -3.0F, 3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(2.3F, -0.1F, -0.05F, 0.2651F, 0.0843F, -0.3657F));

		PartDefinition mane_rotation_r42 = bone2.addOrReplaceChild("mane_rotation_r42", CubeListBuilder.create().texOffs(60, 18).addBox(4.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.5F, -0.1F, -0.75F, 0.0032F, -0.0109F, -0.3486F));

		PartDefinition mane_rotation_r43 = bone2.addOrReplaceChild("mane_rotation_r43", CubeListBuilder.create().texOffs(60, 18).mirror().addBox(-5.0F, -1.0F, 3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(3.2F, 1.65F, -2.0F, 0.089F, 0.0113F, 0.2177F));

		PartDefinition mane_rotation_r44 = bone2.addOrReplaceChild("mane_rotation_r44", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.4F, -0.1F, -2.55F, 0.082F, 0.0298F, -0.4787F));

		PartDefinition mane_rotation_r45 = bone2.addOrReplaceChild("mane_rotation_r45", CubeListBuilder.create().texOffs(47, 18).addBox(4.0F, -3.0F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.1F, -0.6F, -1.55F, 0.0865F, 0.0114F, -0.2613F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-3.0F, 0.95F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.95F, 0.75F, 0.5F, -0.0785F, 0.5672F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(3.35F, 0.0F, 1.1F, -0.0436F, 0.5672F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.75F, 0.25F, -0.5F, -0.0436F, 0.5672F, 0.0F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.75F, 1.45F, 0.2F, -0.3752F, 0.5672F, 0.0F));

		PartDefinition head_r5 = real_head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(45, 1).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.75F, 1.0F, 0.0F, -0.1745F, 0.5672F, 0.0F));

		PartDefinition head_r6 = real_head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(44, 0).mirror().addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, -0.75F, 0.5F, 0.1571F, 0.5672F, 0.0F));

		PartDefinition head_r7 = real_head.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(44, 0).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.55F, 1.75F, -0.75F, -0.2182F, 0.5672F, 0.0F));

		PartDefinition head_r8 = real_head.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.55F, 1.75F, -0.75F, -0.2182F, -0.5672F, 0.0F));

		PartDefinition head_r9 = real_head.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.75F, 1.45F, 0.2F, -0.3752F, -0.5672F, 0.0F));

		PartDefinition head_r10 = real_head.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.95F, 0.75F, 0.5F, -0.0785F, -0.5672F, 0.0F));

		PartDefinition head_r11 = real_head.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.35F, 0.0F, 1.1F, -0.0436F, -0.5672F, 0.0F));

		PartDefinition head_r12 = real_head.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.75F, 0.25F, -0.5F, -0.0436F, -0.5672F, 0.0F));

		PartDefinition head_r13 = real_head.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(45, 1).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.75F, 1.0F, 0.0F, -0.1745F, -0.5672F, 0.0F));

		PartDefinition head_r14 = real_head.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(44, 0).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -0.75F, 0.5F, 0.1571F, -0.5672F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -2.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 10).addBox(-1.5F, -0.498F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.101F)), PartPose.offsetAndRotation(0.0F, 0.68F, 0.2F, 0.0698F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(10, 10).addBox(0.15F, -1.7618F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(10, 10).addBox(-1.15F, -1.4618F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(14, 10).addBox(-0.6F, -2.6118F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(2.45F, -2.75F, 0.5F, 0.1772F, 0.1719F, 0.0306F));

		PartDefinition head_r15 = left_ear.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(10, 10).addBox(0.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.17F)), PartPose.offsetAndRotation(0.2171F, -0.6134F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition leg8_r3 = left_ear.addOrReplaceChild("leg8_r3", CubeListBuilder.create().texOffs(42, 28).mirror().addBox(-0.5003F, 0.0466F, -0.9931F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offsetAndRotation(0.7503F, -1.0966F, 0.0431F, 0.0F, 0.0F, -1.5272F));

		PartDefinition leg9_r4 = left_ear.addOrReplaceChild("leg9_r4", CubeListBuilder.create().texOffs(42, 28).mirror().addBox(-0.5003F, -1.0069F, -0.9534F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.34F)).mirror(false), PartPose.offsetAndRotation(0.7503F, -1.0966F, 0.0431F, -1.5708F, 0.0F, -1.5272F));

		PartDefinition leg9_r5 = left_ear.addOrReplaceChild("leg9_r5", CubeListBuilder.create().texOffs(42, 28).mirror().addBox(-0.4997F, 0.0061F, -0.9574F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.36F)).mirror(false), PartPose.offsetAndRotation(0.7503F, -1.0966F, 0.0431F, -1.5272F, 0.0F, 1.6144F));

		PartDefinition leg10_r2 = left_ear.addOrReplaceChild("leg10_r2", CubeListBuilder.create().texOffs(42, 28).mirror().addBox(-0.4997F, -1.0426F, -0.9939F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.34F)).mirror(false), PartPose.offsetAndRotation(0.7503F, -1.0966F, 0.0431F, -3.098F, 0.0F, 1.6144F));

		PartDefinition head_r16 = left_ear.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(10, 10).addBox(0.0F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.17F)), PartPose.offsetAndRotation(-0.6829F, 1.2866F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition head_r17 = left_ear.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(10, 10).addBox(0.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-1.0829F, -1.3134F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(20, 16).mirror().addBox(-0.4F, -2.6118F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(17, 14).addBox(-1.15F, -1.7618F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(17, 14).addBox(0.15F, -1.4618F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.45F, -2.75F, 0.5F, 0.1772F, -0.1719F, -0.0306F));

		PartDefinition head_r18 = right_ear.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(17, 14).addBox(-1.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(1.0829F, -1.3134F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition leg10_r3 = right_ear.addOrReplaceChild("leg10_r3", CubeListBuilder.create().texOffs(42, 28).addBox(-0.5003F, -1.0426F, -0.9939F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.34F)), PartPose.offsetAndRotation(-0.7503F, -1.0966F, 0.0431F, -3.098F, 0.0F, -1.789F));

		PartDefinition leg9_r6 = right_ear.addOrReplaceChild("leg9_r6", CubeListBuilder.create().texOffs(42, 28).addBox(-0.5003F, 0.0061F, -0.9574F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.36F)), PartPose.offsetAndRotation(-0.7503F, -1.0966F, 0.0431F, -1.5272F, 0.0F, -1.789F));

		PartDefinition leg9_r7 = right_ear.addOrReplaceChild("leg9_r7", CubeListBuilder.create().texOffs(42, 28).addBox(-0.4997F, -1.0069F, -0.9534F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.34F)), PartPose.offsetAndRotation(-0.7503F, -1.0966F, 0.0431F, -1.5708F, 0.0F, 1.3526F));

		PartDefinition leg8_r4 = right_ear.addOrReplaceChild("leg8_r4", CubeListBuilder.create().texOffs(42, 28).addBox(-0.4997F, 0.0466F, -0.9931F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-0.7503F, -1.0966F, 0.0431F, 0.0F, 0.0F, 1.3526F));

		PartDefinition head_r19 = right_ear.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(17, 14).mirror().addBox(-1.0F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.17F)).mirror(false), PartPose.offsetAndRotation(0.6829F, 1.2866F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition head_r20 = right_ear.addOrReplaceChild("head_r20", CubeListBuilder.create().texOffs(17, 14).mirror().addBox(-1.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.17F)).mirror(false), PartPose.offsetAndRotation(-0.2171F, -0.6134F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(1.1F, -3.5F, 2.25F));

		PartDefinition head_r21 = bone3.addOrReplaceChild("head_r21", CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.45F, 1.25F, 0.5F, -0.1595F, -0.6749F, 0.2169F));

		PartDefinition head_r22 = bone3.addOrReplaceChild("head_r22", CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, 1.25F, -0.5F, 0.0154F, -0.3259F, 0.1264F));

		PartDefinition head_r23 = bone3.addOrReplaceChild("head_r23", CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.85F, 0.65F, 1.05F, 0.1378F, -0.4284F, 0.1496F));

		PartDefinition head_r24 = bone3.addOrReplaceChild("head_r24", CubeListBuilder.create().texOffs(49, 23).addBox(-1.0F, -0.5F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.25F, 1.25F, -0.3F, -0.1862F, -0.134F, 0.01F));

		PartDefinition head_r25 = bone3.addOrReplaceChild("head_r25", CubeListBuilder.create().texOffs(50, 10).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.35F, 1.25F, 0.0F, -0.0974F, 0.0376F, -0.0222F));

		PartDefinition head_r26 = bone3.addOrReplaceChild("head_r26", CubeListBuilder.create().texOffs(48, 22).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.35F, 0.65F, 1.05F, 0.2409F, 0.189F, -0.0979F));

		PartDefinition head_r27 = bone3.addOrReplaceChild("head_r27", CubeListBuilder.create().texOffs(48, 22).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.55F, 1.25F, -0.5F, 0.0416F, 0.3259F, -0.1264F));

		PartDefinition head_r28 = bone3.addOrReplaceChild("head_r28", CubeListBuilder.create().texOffs(48, 22).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.25F, 1.25F, 0.5F, -0.153F, 0.6411F, -0.2062F));

		PartDefinition head_r29 = bone3.addOrReplaceChild("head_r29", CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, 0.5F, -0.5F, 0.1666F, -0.3487F, 0.0029F));

		PartDefinition head_r30 = bone3.addOrReplaceChild("head_r30", CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.45F, 0.5F, 0.5F, 0.1764F, -0.4777F, -0.0215F));

		PartDefinition head_r31 = bone3.addOrReplaceChild("head_r31", CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.85F, 0.1F, 1.05F, 0.3811F, -0.2798F, 0.015F));

		PartDefinition head_r32 = bone3.addOrReplaceChild("head_r32", CubeListBuilder.create().texOffs(48, 22).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.25F, 0.5F, 0.5F, 0.1764F, 0.4777F, 0.0215F));

		PartDefinition head_r33 = bone3.addOrReplaceChild("head_r33", CubeListBuilder.create().texOffs(48, 22).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.55F, 0.5F, -0.5F, 0.1666F, 0.3487F, -0.0029F));

		PartDefinition head_r34 = bone3.addOrReplaceChild("head_r34", CubeListBuilder.create().texOffs(48, 22).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.35F, 0.1F, 1.05F, 0.3098F, 0.5035F, 0.0267F));

		PartDefinition head_r35 = bone3.addOrReplaceChild("head_r35", CubeListBuilder.create().texOffs(50, 10).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.35F, 0.5F, 0.0F, 0.2006F, -0.1111F, -0.0392F));

		PartDefinition head_r36 = bone3.addOrReplaceChild("head_r36", CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.25F, 0.5F, -0.3F, 0.1659F, -0.1293F, -0.0367F));

		PartDefinition head_r37 = bone3.addOrReplaceChild("head_r37", CubeListBuilder.create().texOffs(48, 22).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5438F, 0.2737F, 0.1359F));

		PartDefinition head_r38 = bone3.addOrReplaceChild("head_r38", CubeListBuilder.create().texOffs(48, 22).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.7F, 0.0F, -0.9F, 0.529F, 0.1611F, 0.0674F));

		PartDefinition head_r39 = bone3.addOrReplaceChild("head_r39", CubeListBuilder.create().texOffs(49, 23).mirror().addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.6F, -0.6F, 0.25F, 0.6553F, 0.1008F, 0.032F));

		PartDefinition head_r40 = bone3.addOrReplaceChild("head_r40", CubeListBuilder.create().texOffs(50, 10).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 0.0F, -0.5F, 0.5259F, -0.1234F, -0.0452F));

		PartDefinition head_r41 = bone3.addOrReplaceChild("head_r41", CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-2.5F, 0.0F, -0.8F, 0.5438F, -0.2737F, -0.1359F));

		return LayerDefinition.create(meshdefinition, 64, 32);

    }

	public static void createAugment() {
		
	}
}
