package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class HungarianPuliModel extends DogModel {

    public HungarianPuliModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 6.5F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(18, 20).addBox(-1.0F, -4.0363F, -2.7205F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 4.1958F, 0.3324F, -0.3491F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(24, 24).addBox(-1.0F, -1.7744F, 0.5786F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 4.1958F, 0.3324F, 3.098F, 0.0F, 0.0F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(24, 24).addBox(-1.0F, -3.7786F, 2.7354F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(24, 24).addBox(-1.0F, -4.7786F, 2.1354F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.1958F, 0.3324F, -2.138F, 0.0F, 0.0F));

		PartDefinition tail_r4 = real_tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(24, 24).addBox(-1.0F, -3.1786F, 1.2854F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 4.1958F, 0.3324F, -2.5307F, 0.0F, 0.0F));

		PartDefinition tailfluff = real_tail.addOrReplaceChild("tailfluff", CubeListBuilder.create(), PartPose.offset(-3.0F, 7.4458F, -3.1676F));

		PartDefinition mane_rotation_r1 = tailfluff.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(21, 17).addBox(-2.5F, -4.9817F, -4.0967F, 3.0F, 5.0F, 6.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(3.7923F, -3.6641F, 1.2554F, -0.3054F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r2 = tailfluff.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(21, 17).addBox(-2.5F, -4.9817F, -4.0967F, 3.0F, 5.0F, 6.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(3.7923F, -0.6641F, 1.2554F, 0.0F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r3 = tailfluff.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(21, 17).mirror().addBox(-2.5F, -2.2065F, -3.6111F, 3.0F, 5.0F, 6.0F, new CubeDeformation(-0.65F)).mirror(false), PartPose.offsetAndRotation(3.7923F, -0.6641F, 1.2554F, 0.2618F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r4 = tailfluff.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(21, 17).mirror().addBox(-2.5F, 0.7076F, -3.1732F, 3.0F, 4.0F, 6.0F, new CubeDeformation(-0.65F)).mirror(false), PartPose.offsetAndRotation(3.7923F, -0.6641F, 1.2554F, 0.48F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r5 = tailfluff.addOrReplaceChild("mane_rotation_r5", CubeListBuilder.create().texOffs(3, 34).mirror().addBox(-0.5F, -2.2065F, -3.6111F, 1.0F, 5.0F, 6.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.4423F, -0.6641F, 1.2554F, 0.2618F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r6 = tailfluff.addOrReplaceChild("mane_rotation_r6", CubeListBuilder.create().texOffs(3, 34).mirror().addBox(-0.5F, -4.9817F, -4.0967F, 1.0F, 5.0F, 6.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.4423F, -3.6641F, 1.2554F, -0.3054F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r7 = tailfluff.addOrReplaceChild("mane_rotation_r7", CubeListBuilder.create().texOffs(3, 34).addBox(-0.5F, -4.9817F, -4.0967F, 1.0F, 5.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(4.2577F, -3.6641F, 1.2554F, -0.3054F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r8 = tailfluff.addOrReplaceChild("mane_rotation_r8", CubeListBuilder.create().texOffs(3, 34).mirror().addBox(-0.5F, -4.9817F, -4.0967F, 1.0F, 5.0F, 6.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.4423F, -0.6641F, 1.2554F, 0.0F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r9 = tailfluff.addOrReplaceChild("mane_rotation_r9", CubeListBuilder.create().texOffs(3, 34).addBox(-0.5F, -2.2065F, -3.6111F, 1.0F, 5.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(4.2577F, -0.6641F, 1.2554F, 0.2618F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r10 = tailfluff.addOrReplaceChild("mane_rotation_r10", CubeListBuilder.create().texOffs(3, 34).mirror().addBox(-0.5F, 0.7076F, -3.1732F, 1.0F, 4.0F, 6.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.7423F, -0.6641F, 1.2554F, 0.48F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r11 = tailfluff.addOrReplaceChild("mane_rotation_r11", CubeListBuilder.create().texOffs(3, 34).addBox(-0.5F, 0.7076F, -3.1732F, 1.0F, 4.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(4.2577F, -0.6641F, 1.2554F, 0.48F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r12 = tailfluff.addOrReplaceChild("mane_rotation_r12", CubeListBuilder.create().texOffs(4, 35).addBox(1.75F, 0.0183F, -2.8467F, 1.0F, 4.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(5.0077F, -0.6641F, 1.2554F, 3.0804F, -1.2372F, -1.5373F));

		PartDefinition mane_rotation_r13 = tailfluff.addOrReplaceChild("mane_rotation_r13", CubeListBuilder.create().texOffs(3, 34).addBox(0.5F, 0.0183F, 0.9033F, 1.0F, 4.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(5.0077F, -0.6641F, 1.2554F, 0.0633F, -1.2487F, 1.4864F));

		PartDefinition mane_rotation_r14 = tailfluff.addOrReplaceChild("mane_rotation_r14", CubeListBuilder.create().texOffs(3, 34).addBox(-0.5F, -4.9817F, -4.0967F, 1.0F, 5.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(4.2577F, -0.6641F, 1.2554F, 0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.5F, 17.75F, 5.0F));

		PartDefinition legfluff3 = right_hind_leg.addOrReplaceChild("legfluff3", CubeListBuilder.create(), PartPose.offset(-1.4474F, 1.7443F, -9.25F));

		PartDefinition head_r1 = legfluff3.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(6, 34).addBox(-0.75F, -3.25F, -2.1F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.25F, 9.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition head_r2 = legfluff3.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(6, 34).addBox(-2.25F, -3.5F, -0.5F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.25F, 9.0F, 0.0F, 1.5708F, 0.1309F));

		PartDefinition head_r3 = legfluff3.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(6, 34).addBox(-0.75F, -4.25F, -7.15F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.25F, 17.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(1.5F, 17.75F, 5.0F));

		PartDefinition legfluff4 = left_hind_leg.addOrReplaceChild("legfluff4", CubeListBuilder.create(), PartPose.offset(1.4474F, 1.9943F, -9.25F));

		PartDefinition head_r4 = legfluff4.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(6, 34).mirror().addBox(-3.25F, -3.25F, -2.1F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition head_r5 = legfluff4.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(6, 34).mirror().addBox(-1.75F, -3.5F, -0.5F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.0F, -1.5708F, -0.1309F));

		PartDefinition head_r6 = legfluff4.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(6, 34).mirror().addBox(-3.25F, -4.25F, -7.15F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 17.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition legfluff2 = right_front_leg.addOrReplaceChild("legfluff2", CubeListBuilder.create(), PartPose.offset(-0.4474F, 3.4943F, -0.25F));

		PartDefinition head_r7 = legfluff2.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(6, 34).addBox(-0.75F, -3.25F, -2.1F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition head_r8 = legfluff2.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(6, 34).addBox(-2.25F, -3.5F, -0.5F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.1309F));

		PartDefinition head_r9 = legfluff2.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(6, 34).addBox(-0.75F, -4.25F, -7.15F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition legfluff = left_front_leg.addOrReplaceChild("legfluff", CubeListBuilder.create(), PartPose.offset(2.4474F, 3.4943F, -0.25F));

		PartDefinition head_r10 = legfluff.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(6, 34).mirror().addBox(-3.25F, -3.25F, -2.1F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition head_r11 = legfluff.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(6, 34).mirror().addBox(-1.75F, -3.5F, -0.5F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.1309F));

		PartDefinition head_r12 = legfluff.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(6, 34).mirror().addBox(-3.25F, -4.25F, -7.15F, 4.0F, 8.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 8.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.75F, 2.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -9.25F, 0.25F, 6.0F, 7.0F, 6.0F, new CubeDeformation(-0.35F))
		.texOffs(18, 14).addBox(-3.0F, -11.25F, -1.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 2.75F, 8.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone2 = body.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.05F, -0.25F));

		PartDefinition bodyfluff5 = bone2.addOrReplaceChild("bodyfluff5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition manefluff6 = bodyfluff5.addOrReplaceChild("manefluff6", CubeListBuilder.create().texOffs(21, 30).addBox(4.25F, -3.0F, -9.25F, 1.0F, 6.0F, 11.0F, new CubeDeformation(-0.55F)), PartPose.offset(0.0F, 0.0F, -0.5F));

		PartDefinition mane_rotation_r15 = manefluff6.addOrReplaceChild("mane_rotation_r15", CubeListBuilder.create().texOffs(0, 31).addBox(3.3F, -3.0F, -5.75F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition mane_rotation_r16 = manefluff6.addOrReplaceChild("mane_rotation_r16", CubeListBuilder.create().texOffs(0, 31).addBox(5.5F, -3.0F, -6.5F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition mane_rotation_r17 = manefluff6.addOrReplaceChild("mane_rotation_r17", CubeListBuilder.create().texOffs(0, 31).addBox(5.0F, -3.0F, -6.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition mane_rotation_r18 = manefluff6.addOrReplaceChild("mane_rotation_r18", CubeListBuilder.create().texOffs(0, 23).addBox(3.6F, -4.0F, -10.25F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.068F, -0.1656F, 0.2645F));

		PartDefinition manefluff5 = bodyfluff5.addOrReplaceChild("manefluff5", CubeListBuilder.create().texOffs(21, 30).mirror().addBox(-5.25F, -3.0F, -9.25F, 1.0F, 6.0F, 11.0F, new CubeDeformation(-0.55F)).mirror(false), PartPose.offset(2.0F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r19 = manefluff5.addOrReplaceChild("mane_rotation_r19", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(4.0F, -3.0F, -5.75F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)).mirror(false)
		.texOffs(0, 31).mirror().addBox(-4.0F, -3.0F, -5.75F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition mane_rotation_r20 = manefluff5.addOrReplaceChild("mane_rotation_r20", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-6.5F, -3.0F, -6.5F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition mane_rotation_r21 = manefluff5.addOrReplaceChild("mane_rotation_r21", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-6.0F, -3.0F, -6.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1309F, 0.0F));

		PartDefinition mane_rotation_r22 = manefluff5.addOrReplaceChild("mane_rotation_r22", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-4.6F, -4.0F, -10.25F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.55F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.068F, 0.1656F, -0.2645F));

		PartDefinition bodyfluff9 = bone2.addOrReplaceChild("bodyfluff9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition manefluff10 = bodyfluff9.addOrReplaceChild("manefluff10", CubeListBuilder.create().texOffs(21, 30).addBox(4.25F, -3.0F, -9.25F, 1.0F, 6.0F, 11.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 1.75F));

		PartDefinition mane_rotation_r23 = manefluff10.addOrReplaceChild("mane_rotation_r23", CubeListBuilder.create().texOffs(1, 32).addBox(-6.25F, -2.5F, -4.75F, 1.0F, 6.0F, 8.0F, new CubeDeformation(-0.45F))
		.texOffs(1, 32).addBox(1.75F, -2.5F, -4.75F, 1.0F, 6.0F, 8.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition mane_rotation_r24 = manefluff10.addOrReplaceChild("mane_rotation_r24", CubeListBuilder.create().texOffs(0, 31).addBox(4.5756F, -3.6F, -7.0559F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition mane_rotation_r25 = manefluff10.addOrReplaceChild("mane_rotation_r25", CubeListBuilder.create().texOffs(0, 31).addBox(5.0F, -3.0F, -6.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition mane_rotation_r26 = manefluff10.addOrReplaceChild("mane_rotation_r26", CubeListBuilder.create().texOffs(0, 23).addBox(3.6F, -4.0F, -10.25F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.068F, -0.1656F, 0.2645F));

		PartDefinition manefluff11 = bodyfluff9.addOrReplaceChild("manefluff11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.07F, 3.1245F, -3.2447F, 0.0F, -0.2182F, 1.5708F));

		PartDefinition mane_rotation_r27 = manefluff11.addOrReplaceChild("mane_rotation_r27", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(1.5F, -6.5F, -4.75F, 1.0F, 5.0F, 9.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.5437F, 1.3977F, 1.1758F, -0.0913F, 0.1122F, -0.4834F));

		PartDefinition mane_rotation_r28 = manefluff11.addOrReplaceChild("mane_rotation_r28", CubeListBuilder.create().texOffs(0, 31).addBox(-0.5F, -2.5F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.1836F, 1.3977F, 1.1758F, 0.1159F, 0.132F, 0.5175F));

		PartDefinition mane_rotation_r29 = manefluff11.addOrReplaceChild("mane_rotation_r29", CubeListBuilder.create().texOffs(1, 24).addBox(3.6F, -4.5F, -9.25F, 1.0F, 8.0F, 8.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-4.6701F, 0.07F, 5.3831F, 0.0013F, -0.0423F, -0.1259F));

		PartDefinition mane_rotation_r30 = manefluff11.addOrReplaceChild("mane_rotation_r30", CubeListBuilder.create().texOffs(21, 30).addBox(4.5F, -5.0F, -9.25F, 1.0F, 8.0F, 11.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-4.6701F, 0.07F, 5.3831F, 0.0F, 0.1309F, 0.0F));

		PartDefinition manefluff12 = bodyfluff9.addOrReplaceChild("manefluff12", CubeListBuilder.create().texOffs(21, 30).mirror().addBox(-5.25F, -3.0F, -9.25F, 1.0F, 6.0F, 11.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offset(2.0F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r31 = manefluff12.addOrReplaceChild("mane_rotation_r31", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-5.9204F, -3.0F, -6.3447F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition mane_rotation_r32 = manefluff12.addOrReplaceChild("mane_rotation_r32", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-6.0F, -3.0F, -6.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition mane_rotation_r33 = manefluff12.addOrReplaceChild("mane_rotation_r33", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-4.6F, -4.0F, -10.25F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.068F, 0.1656F, -0.2645F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.75F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.0F, 15.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition manefluff4 = upper_body.addOrReplaceChild("manefluff4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition manefluff = manefluff4.addOrReplaceChild("manefluff", CubeListBuilder.create().texOffs(21, 30).addBox(4.25F, -3.0F, -9.25F, 1.0F, 6.0F, 11.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r34 = manefluff.addOrReplaceChild("mane_rotation_r34", CubeListBuilder.create().texOffs(1, 32).addBox(-6.0F, -3.0F, -5.0F, 1.0F, 6.0F, 8.0F, new CubeDeformation(-0.4F))
		.texOffs(1, 32).addBox(3.0F, -3.0F, -5.0F, 1.0F, 6.0F, 8.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition mane_rotation_r35 = manefluff.addOrReplaceChild("mane_rotation_r35", CubeListBuilder.create().texOffs(0, 31).addBox(4.7614F, -3.0F, -6.3698F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition mane_rotation_r36 = manefluff.addOrReplaceChild("mane_rotation_r36", CubeListBuilder.create().texOffs(0, 31).addBox(5.0F, -3.0F, -6.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition mane_rotation_r37 = manefluff.addOrReplaceChild("mane_rotation_r37", CubeListBuilder.create().texOffs(0, 23).addBox(3.6F, -4.0F, -10.25F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.068F, -0.1656F, 0.2645F));

		PartDefinition manefluff3 = manefluff4.addOrReplaceChild("manefluff3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.07F, -2.9745F, -4.2447F, 0.0F, -0.2182F, -1.5708F));

		PartDefinition mane_rotation_r38 = manefluff3.addOrReplaceChild("mane_rotation_r38", CubeListBuilder.create().texOffs(-2, 29).mirror().addBox(1.5F, 1.5F, -4.75F, 1.0F, 5.0F, 11.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.2937F, -1.3977F, -0.8242F, 0.0913F, 0.1122F, 0.4834F));

		PartDefinition mane_rotation_r39 = manefluff3.addOrReplaceChild("mane_rotation_r39", CubeListBuilder.create().texOffs(-2, 29).addBox(-0.5F, -2.5F, -4.5F, 1.0F, 5.0F, 11.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.5664F, -1.3977F, -0.8242F, -0.1159F, 0.132F, -0.5175F));

		PartDefinition mane_rotation_r40 = manefluff3.addOrReplaceChild("mane_rotation_r40", CubeListBuilder.create().texOffs(-1, 22).addBox(3.6F, -3.5F, -9.25F, 1.0F, 8.0F, 10.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-4.6701F, -0.07F, 5.3831F, -0.0013F, -0.0423F, 0.1259F));

		PartDefinition mane_rotation_r41 = manefluff3.addOrReplaceChild("mane_rotation_r41", CubeListBuilder.create().texOffs(19, 28).addBox(4.5F, -3.0F, -9.25F, 1.0F, 8.0F, 13.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-4.6701F, -0.07F, 5.3831F, 0.0F, 0.1309F, 0.0F));

		PartDefinition manefluff2 = manefluff4.addOrReplaceChild("manefluff2", CubeListBuilder.create().texOffs(21, 30).mirror().addBox(-5.25F, -3.0F, -9.25F, 1.0F, 6.0F, 11.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offset(2.0F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r42 = manefluff2.addOrReplaceChild("mane_rotation_r42", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-5.9051F, -3.0F, -6.4217F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1309F, 0.0F));

		PartDefinition mane_rotation_r43 = manefluff2.addOrReplaceChild("mane_rotation_r43", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-6.0F, -3.0F, -6.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition mane_rotation_r44 = manefluff2.addOrReplaceChild("mane_rotation_r44", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-4.6F, -4.0F, -10.25F, 1.0F, 6.0F, 9.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0675F, 0.1221F, -0.2615F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -5.25F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone = real_head.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 7.0F));

		PartDefinition headfluff3 = bone.addOrReplaceChild("headfluff3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r13 = headfluff3.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(7, 36).addBox(0.75F, -4.5F, 0.75F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-6.0F, -10.5F, -8.5F, -0.0293F, 0.5928F, 0.1642F));

		PartDefinition head_r14 = headfluff3.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(7, 35).addBox(-3.75F, -4.25F, 1.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-6.0F, -10.5F, -8.5F, 0.0F, 1.5708F, 0.2618F));

		PartDefinition head_r15 = headfluff3.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(6, 40).mirror().addBox(-1.25F, -4.25F, 1.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(6.0F, -10.5F, -8.5F, 0.0F, -1.5708F, -0.2618F));

		PartDefinition head_r16 = headfluff3.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(7, 36).mirror().addBox(-5.75F, -4.5F, 0.75F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(6.0F, -10.5F, -8.5F, -0.0293F, -0.5928F, -0.1642F));

		PartDefinition head_r17 = headfluff3.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(8, 41).mirror().addBox(-2.75F, -4.0F, 2.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.25F, -16.5F, -8.5F, -1.6144F, 0.0F, 0.0F));

		PartDefinition head_r18 = headfluff3.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(6, 38).addBox(0.5F, -1.0F, 0.75F, 5.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.25F, -15.0F, -11.5F, 0.0F, 0.0873F, 1.1345F));

		PartDefinition head_r19 = headfluff3.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(6, 38).mirror().addBox(-5.5F, -1.0F, 0.75F, 5.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(-0.25F, -15.0F, -11.5F, 0.0F, -0.0873F, -1.1345F));

		PartDefinition head_r20 = headfluff3.addOrReplaceChild("head_r20", CubeListBuilder.create().texOffs(31, 42).mirror().addBox(-2.5F, -3.25F, -2.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.25F, -15.1046F, -6.126F, 1.4399F, 0.0F, 0.0F));

		PartDefinition head_r21 = headfluff3.addOrReplaceChild("head_r21", CubeListBuilder.create().texOffs(31, 42).mirror().addBox(-2.5F, -1.75F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.25F, -13.0F, -9.75F, -0.6109F, 0.0F, 0.0F));

		PartDefinition headfluff2 = bone.addOrReplaceChild("headfluff2", CubeListBuilder.create(), PartPose.offset(6.0F, -10.5F, -8.5F));

		PartDefinition head_r22 = headfluff2.addOrReplaceChild("head_r22", CubeListBuilder.create().texOffs(8, 31).mirror().addBox(-5.75F, -3.75F, 0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0143F, -0.5928F, -0.1642F));

		PartDefinition head_r23 = headfluff2.addOrReplaceChild("head_r23", CubeListBuilder.create().texOffs(5, 34).mirror().addBox(-5.75F, -3.0F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.058F, -0.5928F, -0.1642F));

		PartDefinition head_r24 = headfluff2.addOrReplaceChild("head_r24", CubeListBuilder.create().texOffs(31, 42).mirror().addBox(-5.75F, -4.5F, 0.25F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(31, 43).mirror().addBox(-5.75F, -4.5F, 0.75F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0293F, -0.5928F, -0.1642F));

		PartDefinition headfluff4 = bone.addOrReplaceChild("headfluff4", CubeListBuilder.create(), PartPose.offset(-6.0F, -10.5F, -8.5F));

		PartDefinition head_r25 = headfluff4.addOrReplaceChild("head_r25", CubeListBuilder.create().texOffs(8, 31).addBox(0.75F, -3.75F, 0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0143F, 0.5928F, 0.1642F));

		PartDefinition head_r26 = headfluff4.addOrReplaceChild("head_r26", CubeListBuilder.create().texOffs(5, 34).addBox(0.75F, -3.0F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.058F, 0.5928F, 0.1642F));

		PartDefinition head_r27 = headfluff4.addOrReplaceChild("head_r27", CubeListBuilder.create().texOffs(31, 42).addBox(0.75F, -4.5F, 0.25F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(31, 43).addBox(0.75F, -4.5F, 0.75F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0293F, 0.5928F, 0.1642F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(1.0F, -1.5F, -1.0F));

		PartDefinition head_r28 = right_ear.addOrReplaceChild("head_r28", CubeListBuilder.create().texOffs(7, 36).mirror().addBox(-5.75F, -4.5F, 0.75F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(5.0F, 1.5F, -0.5F, -0.0293F, -0.5928F, -0.1642F));

		PartDefinition head_r29 = right_ear.addOrReplaceChild("head_r29", CubeListBuilder.create().texOffs(6, 40).addBox(-1.25F, -4.25F, 1.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(5.0F, 1.5F, -0.5F, 0.0F, -1.5708F, -0.2618F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.5F, -1.0F));

		PartDefinition head_r30 = left_ear.addOrReplaceChild("head_r30", CubeListBuilder.create().texOffs(7, 36).addBox(0.75F, -4.5F, 0.75F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-5.0F, 1.5F, -0.5F, -0.0293F, 0.5928F, 0.1642F));

		PartDefinition head_r31 = left_ear.addOrReplaceChild("head_r31", CubeListBuilder.create().texOffs(7, 35).addBox(-3.75F, -4.25F, 1.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-5.0F, 1.5F, -0.5F, 0.0F, 1.5708F, 0.2618F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
