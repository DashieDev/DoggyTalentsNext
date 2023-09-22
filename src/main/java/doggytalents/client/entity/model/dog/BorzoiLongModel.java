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

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 9.75F, -5.2F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, -6.25F, -3.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.55F)), PartPose.offset(0.0F, 2.5F, 0.75F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 55).addBox(-1.5F, -5.5515F, -221.3978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 36).addBox(-1.5F, -4.7015F, -220.6978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -59.3478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -65.4478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -65.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -71.5478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -71.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -77.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -77.4478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -83.5478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -83.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -89.6478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -89.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -95.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -95.5478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -101.6478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -101.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -107.7478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -107.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -59.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -5.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -53.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -53.4978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -47.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -47.3978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -41.2978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -41.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -35.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -35.3978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -29.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -29.2978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -23.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -23.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -17.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -17.2978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -11.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -11.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -5.0978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -167.0478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -173.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -172.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -179.2478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -178.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -184.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -185.1478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -191.2478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -190.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -197.3478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -196.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -202.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -203.2478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -209.3478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -208.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -215.4478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -214.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -166.8478F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -112.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -160.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -161.1978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -154.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -155.0978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -148.9978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -148.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -142.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -143.0978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -136.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -136.9978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -130.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -130.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -124.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -124.9978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(25, 27).addBox(-1.5F, -4.7015F, -118.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -118.8978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 46).addBox(-1.5F, -5.5515F, -112.7978F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 2.155F, -1.35F, -0.0175F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(2, 20).addBox(-2.0F, -3.05F, -1.5F, 5.0F, 4.0F, 4.0F, new CubeDeformation(-0.91F)), PartPose.offsetAndRotation(-0.5F, -2.0F, -1.0F, -0.829F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(18, 13).addBox(-2.4771F, 0.0664F, -1.0083F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(18, 13).mirror().addBox(-1.8771F, -0.7336F, -1.2583F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-0.1271F, -1.7336F, -1.0083F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.0271F, -1.2336F, -1.0083F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.8271F, -1.2336F, -1.0083F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-0.8271F, -1.6336F, -1.0083F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -4.5F, -0.25F, -0.5014F, 0.7667F, -0.6257F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(18, 13).mirror().addBox(0.5825F, 0.0429F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(18, 13).addBox(-0.0175F, -0.7571F, -1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(18, 13).addBox(-1.7675F, -1.7571F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(18, 13).addBox(-0.8675F, -1.2571F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(18, 13).addBox(-0.0675F, -1.2571F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(18, 13).addBox(-1.0675F, -1.6571F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(2.0F, -4.5F, -0.25F, -0.5014F, -0.7667F, 0.6257F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, 7.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition real_tail2 = real_tail.addOrReplaceChild("real_tail2", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 1.8919F, -0.3819F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(3, 30).addBox(-1.0F, 3.8919F, -1.1319F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.15F))
		.texOffs(2, 31).addBox(-1.0F, -0.1081F, -1.3819F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(1.0F, 1.2769F, 0.2848F, -0.48F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail2.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(3, 30).addBox(-1.0F, -0.25F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 7.7919F, 1.3681F, 1.0472F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail2.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(6, 30).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.7919F, 1.3681F, 0.4363F, 0.0F, 0.0F));

		PartDefinition tail_r3 = real_tail2.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(7, 30).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 6.3919F, 0.6181F, 0.2182F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 17.0F, 5.0F));

		PartDefinition righthindleg = right_hind_leg.addOrReplaceChild("righthindleg", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -2.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.0F, 2.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 17.0F, 5.0F));

		PartDefinition lefthindleg = left_hind_leg.addOrReplaceChild("lefthindleg", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -2.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.0F, 2.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 17.25F, -2.75F));

		PartDefinition rightfrontleg = right_front_leg.addOrReplaceChild("rightfrontleg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -1.0F, -0.25F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-0.25F, -1.25F, -0.25F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 17.25F, -2.75F));

		PartDefinition leftfronleg = left_front_leg.addOrReplaceChild("leftfronleg", CubeListBuilder.create().texOffs(0, 28).addBox(0.75F, -9.0F, -3.25F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-1.5F, 6.75F, 2.75F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 3.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 9.0F));

		PartDefinition body_rotation_r1 = body2.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -4.5976F, -5.5719F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -3.7747F, -7.958F, 1.4835F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane = upper_body.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition mane_rotation_r1 = mane.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(23, 50).addBox(-3.5F, -1.9F, -10.5F, 7.0F, 6.0F, 8.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(1.0F, -1.2539F, 7.4657F, 0.0436F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r2 = mane.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(25, 53).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(1.0F, -2.0457F, 5.0768F, 0.7418F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
