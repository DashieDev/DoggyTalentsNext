package doggytalents.client.entity.model.dog.oina;

import doggytalents.client.entity.model.dog.DogModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class OkikurumiModel extends DogModel {

    public OkikurumiModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.25F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 18).addBox(-1.0F, 2.75F, -0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(9, 18).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.55F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.75F, -0.25F, 0.25F, -0.2921F, -0.0905F, -0.0303F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, -0.1309F, 0.0F, 0.1309F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, -0.0873F, 0.0F, -0.1309F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.75F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head_r1 = upper_body.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(51, 21).addBox(-0.6123F, -0.635F, -2.7552F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.3733F, 0.3826F, 1.8722F, -2.2888F, 1.2787F, -1.3311F));

		PartDefinition head_r2 = upper_body.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(51, 21).addBox(-0.0928F, -0.6351F, -2.6969F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.3733F, 0.3826F, 1.8722F, -2.525F, 1.0243F, -1.5357F));

		PartDefinition head_r3 = upper_body.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(52, 21).addBox(0.353F, -0.7426F, -1.087F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.8733F, 0.3826F, 1.8722F, -2.2592F, 0.908F, -1.636F));

		PartDefinition head_r4 = upper_body.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(51, 21).addBox(-0.6123F, -0.635F, -2.7552F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.3733F, 0.3826F, 2.6222F, -2.2888F, 1.2787F, -1.3311F));

		PartDefinition head_r5 = upper_body.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(51, 21).addBox(-2.1131F, -0.7315F, -2.8958F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6233F, 0.3826F, 1.8722F, -2.5572F, 1.3161F, -1.2474F));

		PartDefinition head_r6 = upper_body.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(51, 21).addBox(1.0112F, -0.6935F, -1.8187F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.3733F, 0.3826F, 1.8722F, -2.7551F, 0.6484F, -1.7163F));

		PartDefinition head_r7 = upper_body.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.75F, 0.25F, 3.45F, -1.4352F, -0.0509F, -0.1051F));

		PartDefinition head_r8 = upper_body.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 1.25F, 1.95F, -1.1784F, -0.219F, 0.0842F));

		PartDefinition head_r9 = upper_body.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.75F, 0.25F, 3.45F, -1.4352F, 0.0509F, 0.1051F));

		PartDefinition head_r10 = upper_body.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(3.0F, 0.25F, 1.95F, -1.1106F, -0.0511F, -0.5882F));

		PartDefinition head_r11 = upper_body.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-2.75F, 0.25F, 3.45F, -1.1216F, 0.0312F, 0.4147F));

		PartDefinition head_r12 = upper_body.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.25F, 0.25F, 3.45F, -1.131F, 0.017F, 0.1441F));

		PartDefinition head_r13 = upper_body.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.25F, 3.45F, -1.132F, 0.0356F, 0.1046F));

		PartDefinition head_r14 = upper_body.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.25F, 0.25F, 3.45F, -1.1232F, 0.0501F, 0.3754F));

		PartDefinition head_r15 = upper_body.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(1.5F, 0.25F, 1.95F, -1.113F, -0.0705F, -0.5491F));

		PartDefinition head_r16 = upper_body.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(52, 21).mirror().addBox(-0.5F, -1.0F, -1.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-3.25F, 0.25F, 3.45F, -1.95F, -0.0109F, 0.5372F));

		PartDefinition head_r17 = upper_body.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 0.25F, 3.45F, -1.5984F, -0.0506F, 0.5375F));

		PartDefinition head_r18 = upper_body.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.25F, 1.95F, -1.1484F, -0.1497F, -0.0787F));

		PartDefinition head_r19 = upper_body.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-2.25F, 0.25F, 3.45F, -1.4331F, 0.045F, 0.1484F));

		PartDefinition head_r20 = upper_body.addOrReplaceChild("head_r20", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.25F, 0.25F, 3.45F, -1.4264F, 0.0109F, -0.5372F));

		PartDefinition head_r21 = upper_body.addOrReplaceChild("head_r21", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 0.25F, 3.45F, -1.4331F, -0.045F, -0.1484F));

		PartDefinition head_r22 = upper_body.addOrReplaceChild("head_r22", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.25F, 0.25F, 2.95F, -1.4729F, 0.0816F, -0.6193F));

		PartDefinition head_r23 = upper_body.addOrReplaceChild("head_r23", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-2.0112F, -0.6935F, -1.8187F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.3733F, 0.3826F, 1.8722F, -2.613F, -0.91F, 1.7005F));

		PartDefinition head_r24 = upper_body.addOrReplaceChild("head_r24", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(1.1131F, -0.7315F, -2.8958F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.8733F, 0.1326F, 1.8722F, -2.4263F, -1.3161F, 1.2474F));

		PartDefinition head_r25 = upper_body.addOrReplaceChild("head_r25", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.3877F, -0.635F, -2.7552F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.3733F, 0.3826F, 2.6222F, -2.0706F, -1.2787F, 1.3311F));

		PartDefinition head_r26 = upper_body.addOrReplaceChild("head_r26", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.3877F, -0.635F, -2.7552F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-4.3733F, 0.3826F, 1.8722F, -2.2888F, -1.2787F, 1.3311F));

		PartDefinition head_r27 = upper_body.addOrReplaceChild("head_r27", CubeListBuilder.create().texOffs(52, 21).mirror().addBox(-1.353F, -0.7426F, -1.087F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-3.8733F, 0.3826F, 1.8722F, -2.3901F, -0.908F, 1.636F));

		PartDefinition head_r28 = upper_body.addOrReplaceChild("head_r28", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.9072F, -0.6351F, -2.6969F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-4.3733F, 0.3826F, 1.8722F, -2.525F, -1.0243F, 1.5357F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(25, 2).addBox(-1.0F, -4.75F, -2.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, 0.25F, 0.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 0).addBox(-3.0F, -3.35F, -1.6F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r29 = real_head.addOrReplaceChild("head_r29", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-0.95F, -2.7F, 3.5F, 0.4498F, -0.2368F, -0.1128F));

		PartDefinition head_r30 = real_head.addOrReplaceChild("head_r30", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-0.75F, -2.95F, 2.5F, 0.5239F, -0.0029F, 0.007F));

		PartDefinition head_r31 = real_head.addOrReplaceChild("head_r31", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.75F, -3.0F, 2.7F, 0.2031F, -0.3049F, -0.1694F));

		PartDefinition head_r32 = real_head.addOrReplaceChild("head_r32", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -2.95F, 1.75F, 0.9596F, -0.7437F, -0.0744F));

		PartDefinition head_r33 = real_head.addOrReplaceChild("head_r33", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(4.0F, -0.5F, 1.7F, 0.4123F, 0.7595F, 0.7278F));

		PartDefinition head_r34 = real_head.addOrReplaceChild("head_r34", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, -3.0F, 2.7F, 0.2031F, 0.3049F, 0.1694F));

		PartDefinition head_r35 = real_head.addOrReplaceChild("head_r35", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -3.0F, 2.7F, 0.8139F, -0.595F, -0.4653F));

		PartDefinition head_r36 = real_head.addOrReplaceChild("head_r36", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -3.0F, 1.7F, 0.7273F, -0.438F, -0.2907F));

		PartDefinition head_r37 = real_head.addOrReplaceChild("head_r37", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -0.25F, 1.2F, 1.1826F, -0.8303F, -1.3806F));

		PartDefinition head_r38 = real_head.addOrReplaceChild("head_r38", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -0.5F, 1.7F, 0.4123F, -0.7595F, -0.7278F));

		PartDefinition head_r39 = real_head.addOrReplaceChild("head_r39", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -1.75F, 1.2F, 0.5537F, -0.7346F, -0.679F));

		PartDefinition head_r40 = real_head.addOrReplaceChild("head_r40", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-2.7F, 0.5F, 2.45F, 0.3737F, -0.7726F, -1.7279F));

		PartDefinition head_r41 = real_head.addOrReplaceChild("head_r41", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2F, -2.7F, 3.5F, 0.5682F, 0.0681F, 0.0569F));

		PartDefinition head_r42 = real_head.addOrReplaceChild("head_r42", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -1.25F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.7F, -3.4F, 3.5F, 0.6243F, -0.2368F, -0.1128F));

		PartDefinition head_r43 = real_head.addOrReplaceChild("head_r43", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -3.0F, 3.5F, 0.4674F, -0.3542F, -0.1733F));

		PartDefinition head_r44 = real_head.addOrReplaceChild("head_r44", CubeListBuilder.create().texOffs(51, 21).mirror().addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-1.25F, -2.95F, 3.0F, 0.4498F, -0.2368F, -0.1128F));

		PartDefinition head_r45 = real_head.addOrReplaceChild("head_r45", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.95F, 1.75F, 0.9596F, 0.7437F, 0.0744F));

		PartDefinition head_r46 = real_head.addOrReplaceChild("head_r46", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -0.75F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.95F, 1.75F, 0.7101F, -0.1821F, -0.1199F));

		PartDefinition head_r47 = real_head.addOrReplaceChild("head_r47", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.75F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, -2.95F, 1.25F, 0.5442F, -0.2605F, -0.1634F));

		PartDefinition head_r48 = real_head.addOrReplaceChild("head_r48", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -2.95F, 1.25F, 0.5308F, 0.1538F, 0.081F));

		PartDefinition head_r49 = real_head.addOrReplaceChild("head_r49", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.75F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.25F, -2.95F, 1.25F, 0.5239F, 0.0029F, -0.007F));

		PartDefinition head_r50 = real_head.addOrReplaceChild("head_r50", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.75F, -2.95F, 2.5F, 0.5239F, 0.0029F, -0.007F));

		PartDefinition head_r51 = real_head.addOrReplaceChild("head_r51", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.25F, -2.95F, 3.0F, 0.4498F, 0.2368F, 0.1128F));

		PartDefinition head_r52 = real_head.addOrReplaceChild("head_r52", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.2F, -2.7F, 3.5F, 0.176F, 0.0791F, 0.037F));

		PartDefinition head_r53 = real_head.addOrReplaceChild("head_r53", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.95F, -2.7F, 4.25F, 0.4498F, 0.2368F, 0.1128F));

		PartDefinition head_r54 = real_head.addOrReplaceChild("head_r54", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.7F, -3.4F, 2.75F, 0.6122F, 0.1239F, 0.0454F));

		PartDefinition head_r55 = real_head.addOrReplaceChild("head_r55", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.0F, 0.5F, 2.45F, 0.6854F, 0.4648F, 2.3218F));

		PartDefinition head_r56 = real_head.addOrReplaceChild("head_r56", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.0F, -0.25F, 1.2F, 1.1826F, 0.8303F, 1.3806F));

		PartDefinition head_r57 = real_head.addOrReplaceChild("head_r57", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(3.5F, -2.5F, 1.7F, 0.4219F, 0.3079F, 0.4214F));

		PartDefinition head_r58 = real_head.addOrReplaceChild("head_r58", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.75F, 1.2F, 0.5537F, 0.7346F, 0.679F));

		PartDefinition head_r59 = real_head.addOrReplaceChild("head_r59", CubeListBuilder.create().texOffs(51, 21).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.0F, 1.7F, 0.7273F, 0.438F, 0.2907F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.5F, -2.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 10).addBox(-1.5F, -0.498F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.301F)), PartPose.offsetAndRotation(0.0F, 0.48F, 0.25F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(49, 19).addBox(-1.05F, -2.55F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(16, 14).addBox(0.1F, -1.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(-1.15F, -1.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(2.0F, -2.5F, 0.5F, 0.3341F, -0.103F, 0.288F));

		PartDefinition head_r60 = left_ear.addOrReplaceChild("head_r60", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.3F, -0.7118F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r61 = left_ear.addOrReplaceChild("head_r61", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.21F)), PartPose.offsetAndRotation(-1.3F, -1.0118F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.1F, -1.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(0.15F, -1.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(49, 19).mirror().addBox(-0.95F, -2.55F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -2.5F, 0.5F, 0.3341F, 0.103F, -0.288F));

		PartDefinition head_r62 = right_ear.addOrReplaceChild("head_r62", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.21F)).mirror(false), PartPose.offsetAndRotation(1.3F, -1.0118F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r63 = right_ear.addOrReplaceChild("head_r63", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -2.1382F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-0.3F, -0.7118F, 0.0F, 0.0F, 0.0F, 0.3491F));

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

