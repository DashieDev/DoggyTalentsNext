package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CollieBorderShortModel extends DogModel{

    public CollieBorderShortModel(ModelPart box) {
        super(box);
    }
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, -6.5F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 0).addBox(-3.0F, -3.75F, -2.25F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.8F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -0.5F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 10).addBox(-1.5F, -1.5F, -2.75F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 1.48F, -2.5F, 0.2182F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(29, 36).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition headfur = real_head.addOrReplaceChild("headfur", CubeListBuilder.create(), PartPose.offset(0.0F, 14.5F, 6.5F));

		PartDefinition bone3 = headfur.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(20, 34).mirror().addBox(-5.25F, -14.05F, -9.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(34, 55).mirror().addBox(-4.5F, -13.3F, -9.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(34, 55).mirror().addBox(-3.25F, -13.8F, -9.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(34, 55).mirror().addBox(-2.25F, -13.8F, -9.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(21, 35).mirror().addBox(-4.9F, -14.8F, -9.35F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(23, 34).mirror().addBox(-4.65F, -15.8F, -9.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(20, 34).mirror().addBox(-4.4F, -16.55F, -9.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(20, 34).mirror().addBox(-4.15F, -17.3F, -9.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offset(1.25F, 1.0F, 1.75F));

		PartDefinition bone2 = headfur.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(20, 34).addBox(2.25F, -14.05F, -9.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(34, 55).addBox(1.25F, -13.8F, -9.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(35, 55).addBox(2.5F, -13.3F, -9.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(21, 35).addBox(3.9F, -14.8F, -9.35F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(23, 34).addBox(2.65F, -15.8F, -9.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(20, 34).addBox(3.4F, -16.55F, -9.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(20, 34).addBox(3.15F, -17.3F, -9.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offset(-1.25F, 1.0F, 1.75F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.75F, -2.05F, 0.0F, -0.0869F, 0.0076F, -0.0876F));

		PartDefinition head_r3 = right_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(45, 0).addBox(-3.0186F, 0.1264F, -1.567F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.5099F, 0.527F, -2.0183F, -0.7854F, 1.4835F, 2.0944F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(46, 1).addBox(-2.3702F, -1.002F, -0.548F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.6599F, -1.823F, -2.0183F, -0.7854F, 1.4835F, 2.0944F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(45, 0).addBox(-2.3702F, -1.002F, -1.548F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.9099F, -0.773F, -2.0183F, -0.7854F, 1.4835F, 2.0944F));

		PartDefinition head_r6 = right_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(45, 0).addBox(-2.3702F, -0.002F, -1.548F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-0.5099F, 0.777F, -2.0183F, -0.7854F, 1.4835F, 2.0944F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(1.75F, -2.05F, 0.0F, -0.0869F, -0.0076F, 0.0876F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(1.3702F, -1.002F, -1.548F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.9099F, -0.773F, -2.0183F, -0.7854F, -1.4835F, -2.0944F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(1.3702F, -0.002F, -1.548F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.5099F, 0.777F, -2.0183F, -0.7854F, -1.4835F, -2.0944F));

		PartDefinition head_r9 = left_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(2.0186F, 0.1264F, -1.567F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.5099F, 0.527F, -2.0183F, -0.7854F, -1.4835F, -2.0944F));

		PartDefinition head_r10 = left_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(46, 1).mirror().addBox(1.3702F, -1.002F, -0.548F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.6599F, -1.823F, -2.0183F, -0.7854F, -1.4835F, -2.0944F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone9 = upper_body.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.9219F, 4.1251F, -1.5708F, 0.0F, 0.0F));

		PartDefinition bone5 = bone9.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(2.25F, 8.5189F, -0.0335F, 0.0F, 1.5708F, -1.5708F));

		PartDefinition bone = bone9.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.2561F, -3.1176F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 4.3898F, 0.1785F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = bone.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(0, 33).addBox(-3.0F, -2.1441F, -0.6384F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.0F, 0.288F, -0.1292F, 0.7418F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, 1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition asvav = body.addOrReplaceChild("asvav", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = asvav.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -1.5029F, -2.8691F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.8909F, -1.2094F, 1.5272F, 0.0F, 0.0F));

		PartDefinition bone7 = asvav.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.77F, -11.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition head_r11 = bone7.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(3, 54).addBox(-2.75F, -2.15F, -3.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(7, 55).addBox(1.0F, -2.15F, -3.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(5, 55).addBox(-3.25F, -2.5F, -1.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(7, 57).addBox(2.0F, -1.6F, 0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, -1.5396F, 14.8582F, 0.3491F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r2 = bone7.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(0, 55).addBox(-2.75F, -1.75F, -2.85F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(0, 55).addBox(0.1F, -0.5F, -2.85F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-0.1F, -1.5396F, 14.8582F, 1.9199F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r3 = bone7.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(3, 54).addBox(-3.0F, -1.5F, -1.75F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.25F, -1.2539F, 16.5585F, 0.3927F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 18.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 18.0F, -4.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.5F, 7.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.5F, 7.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 13.1F, 7.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(31, 39).addBox(-1.0F, 8.15F, 1.8F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(32, 39).addBox(-1.0F, 7.05F, 1.3F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(10, 22).addBox(-1.0F, 2.95F, -0.1F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(32, 39).addBox(-1.0F, 5.85F, 0.7F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 20).addBox(-1.0F, -0.35F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
