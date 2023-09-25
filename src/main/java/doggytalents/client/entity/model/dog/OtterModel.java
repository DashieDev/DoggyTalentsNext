package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class OtterModel extends DogModel{

    public OtterModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 15.75F, 8.25F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.1176F, -2.9695F));

		PartDefinition tail2 = real_tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 2.6324F, 2.4695F));

		PartDefinition tail_r1 = tail2.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(8, 46).addBox(10.0F, 3.0963F, 2.4013F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-10.0F, -0.0937F, 2.3637F, 0.3054F, 0.0F, 0.0F));

		PartDefinition tail_r2 = tail2.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(8, 46).addBox(10.0F, 0.1176F, 5.0163F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(-10.0F, -0.0937F, 2.3637F, -0.5672F, 0.0F, 0.0F));

		PartDefinition tail_r3 = tail2.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(8, 46).addBox(10.0F, -1.1151F, 5.3474F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-10.0F, -0.0937F, 2.3637F, -1.0472F, 0.0F, 0.0F));

		PartDefinition tail_r4 = tail2.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(5, 46).addBox(10.0F, -2.1947F, 5.1901F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-10.0F, -0.0937F, 2.3637F, -1.5272F, 0.0F, 0.0F));

		PartDefinition tail_r5 = tail2.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(0, 47).addBox(10.0F, 1.9108F, -4.1942F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-10.0F, -0.0937F, 2.3637F, 0.5672F, 0.0F, 0.0F));

		PartDefinition tail_r6 = tail2.addOrReplaceChild("tail_r6", CubeListBuilder.create().texOffs(6, 47).addBox(10.0F, -3.9445F, 3.992F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-10.0F, -0.0937F, 2.3637F, -2.0508F, 0.0F, 0.0F));

		PartDefinition tail_r7 = tail2.addOrReplaceChild("tail_r7", CubeListBuilder.create().texOffs(0, 47).addBox(-1.0F, 0.0437F, -1.2637F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.2063F, 0.1137F, 0.3054F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(8, 41).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 19.0F, 7.05F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(8, 41).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 19.0F, 7.05F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(8, 42).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 19.35F, -7.5F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(8, 42).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 19.35F, -7.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, -0.5F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 1.7017F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body_rotation.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -3.2304F, 1.4048F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -0.3914F, -3.5899F, 0.0873F, 0.0F, 0.0F));

		PartDefinition body_rotation_r2 = body_rotation.addOrReplaceChild("body_rotation_r2", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -0.301F, 1.1618F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -0.3914F, -3.3399F, -0.1745F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 17.75F, -5.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head_r1 = upper_body.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(24, 23).addBox(-2.0F, -6.5F, -7.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.5F, -1.8428F, -4.809F, -1.9199F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.55F, -6.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.8F)), PartPose.offsetAndRotation(1.0F, 2.0F, 2.5F, -0.0436F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 16.9F, -10.8F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, -10.75F, -13.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(26, 43).addBox(-1.5F, -8.02F, -16.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(28, 34).addBox(-2.3F, -7.67F, -15.3F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F))
		.texOffs(28, 34).mirror().addBox(0.3F, -7.67F, -15.3F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(23, 0).addBox(-3.0F, -11.45F, -13.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.45F)), PartPose.offset(0.0F, 6.75F, 9.75F));

		PartDefinition cube_r1 = real_head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(51, 0).mirror().addBox(0.0F, -2.0F, -1.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(1.0F, -7.0F, -15.5F, 0.0F, -1.1345F, 0.0F));

		PartDefinition cube_r2 = real_head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(51, 0).addBox(-4.0F, -2.0F, -1.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, -7.0F, -15.5F, 0.0F, 1.1345F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(27, 39).addBox(-1.5F, -0.5F, -2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -5.77F, -13.25F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 35).addBox(-0.2122F, -1.8317F, -1.3292F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.9254F, -9.1185F, -10.608F, -1.0092F, 0.3215F, -1.4129F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 35).mirror().addBox(-1.7878F, -1.8317F, -1.3292F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(1.9254F, -9.1185F, -10.608F, -1.0092F, -0.3215F, 1.4129F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
