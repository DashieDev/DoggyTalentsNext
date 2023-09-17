package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BorzoiLongModel extends DogModel{

    public BorzoiLongModel(ModelPart box) {
        super(box);
    }
    
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 13.75F, 7.25F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(8, 28).addBox(-1.0F, 1.8919F, -0.3819F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(8, 28).addBox(-1.0F, 6.2919F, 0.3681F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(8, 28).addBox(-1.0F, -0.1081F, -1.3819F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -0.4731F, 1.0348F, -0.48F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -2.2F, -1.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-1.5F, 18.2F, 6.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -2.2F, -1.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(1.5F, 18.2F, 6.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.25F, -3.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-1.5F, 18.0F, -3.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-0.75F, -3.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(1.5F, 18.0F, -3.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 2.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -4.3476F, -5.0719F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, -2.7747F, 2.042F, 1.4835F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 15.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -1.9F, -9.0F, 7.0F, 6.0F, 7.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(1.0F, -1.2539F, 6.4657F, 0.0436F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, -4.5F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, -4.25F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.6F))
		.texOffs(23, 0).addBox(-3.0F, -4.75F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.7F)), PartPose.offset(0.0F, 0.5F, -0.25F));

		PartDefinition head_extend_r1 = real_head.addOrReplaceChild("head_extend_r1", CubeListBuilder.create().texOffs(25, 27).addBox(-1.5F, -0.7015F, -5.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -5.7978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -11.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -0.7015F, -11.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -17.9978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -0.7015F, -17.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -0.7015F, -23.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -23.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -29.9978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -0.7015F, -29.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -36.0978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -0.7015F, -35.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -0.7015F, -41.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -41.9978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -48.0978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -0.7015F, -47.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -1.5515F, -54.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -0.7015F, -53.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 36).addBox(-1.5F, -0.7015F, -59.6978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 55).addBox(-1.5F, -1.5515F, -60.3978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -0.095F, -1.05F, -0.0873F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(18, 13).mirror().addBox(-3.3667F, 0.15F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-2.7667F, -0.65F, -1.2083F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.0167F, -1.65F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.9167F, -1.15F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-2.7167F, -1.15F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.7167F, -1.55F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offset(-1.8833F, -3.25F, -0.0417F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(18, 13).addBox(1.3667F, 0.15F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(18, 13).addBox(0.7667F, -0.65F, -1.2083F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(18, 13).addBox(0.7167F, -1.15F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(18, 13).addBox(-0.2833F, -1.55F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(18, 13).addBox(-0.9833F, -1.65F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(18, 13).addBox(-0.0833F, -1.15F, -0.9583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(1.8833F, -3.25F, -0.0417F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
