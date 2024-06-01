package doggytalents.client.entity.model.dog;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class AustralianKelpieModel extends DogModel{

    public AustralianKelpieModel(ModelPart box) {
        super(box);
    }
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(1.0F, 6.0825F, -0.5571F, -0.011F, -0.0886F, 0.3776F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, 8.429F, 0.4172F, -0.1188F, -0.0552F, -0.564F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, 6.0825F, -0.5571F, 0.3381F, 0.0886F, -0.3776F));

		PartDefinition tail_r4 = real_tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-1.0F, 4.0261F, -0.7022F, -0.3049F, -0.0114F, -0.2174F));

		PartDefinition tail_r5 = real_tail.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(-1.0F, 1.9696F, -0.8473F, -0.432F, -0.0522F, -0.2975F));

		PartDefinition tail_r6 = real_tail.addOrReplaceChild("tail_r6", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.75F, 0.5F, -0.8F, 0.0F, 0.0F, -0.3054F));

		PartDefinition tail_r7 = real_tail.addOrReplaceChild("tail_r7", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.75F, -1.0F, 0.45F, -0.3054F, 0.0F, 0.3491F));

		PartDefinition tail_r8 = real_tail.addOrReplaceChild("tail_r8", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.9696F, -0.8473F, -0.3054F, 0.0F, 0.1309F));

		PartDefinition tail_r9 = real_tail.addOrReplaceChild("tail_r9", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(1.0F, 4.0261F, -0.7022F, -0.3922F, 0.0114F, 0.2174F));

		PartDefinition tail_r10 = real_tail.addOrReplaceChild("tail_r10", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(1.0F, 8.729F, 0.4172F, -0.1304F, 0.0114F, 0.2174F));

		PartDefinition tail_r11 = real_tail.addOrReplaceChild("tail_r11", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, 6.0825F, -0.5571F, -0.011F, 0.0886F, -0.3776F));

		PartDefinition tail_r12 = real_tail.addOrReplaceChild("tail_r12", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(1.0F, 8.429F, 0.4172F, -0.1188F, 0.0552F, 0.564F));

		PartDefinition tail_r13 = real_tail.addOrReplaceChild("tail_r13", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(1.0F, 6.0825F, -0.5571F, 0.3381F, -0.0886F, 0.3776F));

		PartDefinition tail_r14 = real_tail.addOrReplaceChild("tail_r14", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(1.0F, 4.0261F, -0.7022F, -0.3049F, 0.0114F, 0.2174F));

		PartDefinition tail_r15 = real_tail.addOrReplaceChild("tail_r15", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.9696F, -0.8473F, -0.432F, 0.0522F, 0.2975F));

		PartDefinition tail_r16 = real_tail.addOrReplaceChild("tail_r16", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.75F, 0.5F, -0.8F, 0.0F, 0.0F, 0.3054F));

		PartDefinition tail_r17 = real_tail.addOrReplaceChild("tail_r17", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, 8.729F, 0.4172F, -0.1304F, -0.0114F, -0.2174F));

		PartDefinition tail_r18 = real_tail.addOrReplaceChild("tail_r18", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, 4.0261F, -0.7022F, -0.3922F, -0.0114F, -0.2174F));

		PartDefinition tail_r19 = real_tail.addOrReplaceChild("tail_r19", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(-1.0F, 1.9696F, -0.8473F, -0.3054F, 0.0F, -0.1309F));

		PartDefinition tail_r20 = real_tail.addOrReplaceChild("tail_r20", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.75F, -1.0F, 0.45F, -0.3054F, 0.0F, -0.3491F));

		PartDefinition tail_r21 = real_tail.addOrReplaceChild("tail_r21", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, 1.4696F, -0.3473F, -0.3054F, 0.0F, 0.0F));

		PartDefinition tail_r22 = real_tail.addOrReplaceChild("tail_r22", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, 3.5261F, -0.2022F, -0.1309F, 0.0F, 0.0F));

		PartDefinition tail_r23 = real_tail.addOrReplaceChild("tail_r23", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, 7.729F, 0.9172F, -0.1309F, 0.0F, 0.0F));

		PartDefinition tail_r24 = real_tail.addOrReplaceChild("tail_r24", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, 5.5825F, -0.0571F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 11.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(31, 20).addBox(-3.55F, -1.25F, -1.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(2, 1).addBox(-2.0F, -3.5F, -1.75F, 4.0F, 5.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 3).addBox(-3.0F, 1.5F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(31, 20).mirror().addBox(2.55F, -1.25F, -1.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.5F, -2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.6F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.98F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-2.0F, -3.0F, 0.5F));

		PartDefinition head_r1 = right_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.4567F, 0.0078F, -0.45F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(-0.9572F, 0.0805F, -0.55F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r2 = right_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.5771F, -1.0486F, -0.45F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, -0.7418F));

		PartDefinition head_r3 = right_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0212F, -2.1634F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.102F)).mirror(false), PartPose.offsetAndRotation(-1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(0.7852F, -2.1702F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.101F)).mirror(false), PartPose.offsetAndRotation(-1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, -1.1345F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(38, 14).mirror().addBox(-1.0212F, -2.1634F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.102F)).mirror(false), PartPose.offsetAndRotation(-0.4203F, -0.097F, -0.35F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head_r6 = right_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(38, 14).addBox(-0.9572F, 0.0805F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.5203F, 0.103F, -0.55F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r7 = right_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(38, 14).addBox(0.7852F, -2.1702F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.101F)), PartPose.offsetAndRotation(-0.5203F, 0.003F, -0.35F, 0.0F, 0.0F, -1.1345F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(38, 14).mirror().addBox(-0.5771F, -1.0486F, -0.45F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5203F, 0.003F, -0.35F, 0.0F, 0.0F, -0.7418F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(38, 14).mirror().addBox(-0.4567F, 0.0078F, -0.45F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.6203F, 0.003F, -0.35F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r10 = right_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(16, 14).addBox(0.1054F, -2.0664F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, -0.9163F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.0F, -3.0F, 0.5F));

		PartDefinition head_r11 = left_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.1054F, -2.0664F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, 0.9163F));

		PartDefinition head_r12 = left_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(38, 14).mirror().addBox(-0.0428F, 0.0805F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.5203F, 0.103F, -0.55F, 0.0F, 0.0F, 0.3491F));

		PartDefinition head_r13 = left_ear.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(38, 14).mirror().addBox(-1.7852F, -2.1702F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.101F)).mirror(false), PartPose.offsetAndRotation(0.5203F, 0.003F, -0.35F, 0.0F, 0.0F, 1.1345F));

		PartDefinition head_r14 = left_ear.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(38, 14).addBox(0.0212F, -2.1634F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.102F)), PartPose.offsetAndRotation(0.4203F, -0.097F, -0.35F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r15 = left_ear.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(38, 14).addBox(-1.4229F, -1.0486F, -0.45F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5203F, 0.003F, -0.35F, 0.0F, 0.0F, 0.7418F));

		PartDefinition head_r16 = left_ear.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(38, 14).addBox(-1.5433F, 0.0078F, -0.45F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.6203F, 0.003F, -0.35F, 0.0F, 0.0F, 0.3491F));

		PartDefinition head_r17 = left_ear.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(16, 14).addBox(-0.0428F, 0.0805F, -0.55F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(-1.5433F, 0.0078F, -0.45F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, 0.3491F));

		PartDefinition head_r18 = left_ear.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(16, 14).addBox(0.0212F, -2.1634F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.102F)), PartPose.offsetAndRotation(1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r19 = left_ear.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(16, 14).addBox(-1.7852F, -2.1702F, -0.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.101F)), PartPose.offsetAndRotation(1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, 1.1345F));

		PartDefinition head_r20 = left_ear.addOrReplaceChild("head_r20", CubeListBuilder.create().texOffs(16, 14).addBox(-1.4229F, -1.0486F, -0.45F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2703F, -1.147F, -0.05F, 0.0F, 0.0F, 0.7418F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

    @Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

    @Override
    public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
        return true;
    }
}
