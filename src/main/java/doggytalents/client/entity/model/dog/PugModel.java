package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PugModel extends DogModel {

    public PugModel(ModelPart box) {
        super(box);
    }

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.5F, 7.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -2.25F, 0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(56, 0).addBox(-1.0F, 0.75F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F))
		.texOffs(56, 0).addBox(-1.0F, -2.75F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 4.2599F, 0.5573F, -0.3491F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -3.5067F, -2.4338F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.2599F, 0.5573F, -0.1745F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.75F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-2.25F, 16.5F, 5.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(2.25F, 16.5F, 5.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.25F, -0.25F, -1.0F, 2.0F, 8.5F, 2.0F, new CubeDeformation(0.45F)), PartPose.offset(-2.25F, 15.75F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 8.5F, 2.0F, new CubeDeformation(0.45F)), PartPose.offset(2.25F, 15.75F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.5F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(1.6F)), PartPose.offsetAndRotation(0.0F, 0.5246F, -0.6252F, -0.2182F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -2.0086F, -3.1195F, 8.0F, 6.0F, 7.0F, new CubeDeformation(1.35F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, -8.5F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -0.5F, 6.0F, 6.0F, 4.0F, new CubeDeformation(1.5F))
		.texOffs(36, 13).mirror().addBox(-2.25F, 0.98F, -2.85F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 10).addBox(-1.5F, 0.48F, -3.1F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(36, 13).addBox(1.5F, 0.98F, -2.85F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(46, 15).addBox(-2.3875F, -0.95F, -1.4375F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(47, 20).addBox(-2.3375F, -0.35F, -1.4375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(47, 20).addBox(-2.7375F, -0.3F, -1.4375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(47, 20).addBox(-3.0375F, 0.1F, -1.6875F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(-3.3625F, -3.8F, -0.0625F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(46, 15).mirror().addBox(0.3875F, -0.95F, -1.4375F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(47, 20).mirror().addBox(1.3375F, -0.35F, -1.4375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(47, 20).mirror().addBox(1.7375F, -0.3F, -1.4375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(47, 20).mirror().addBox(2.0375F, 0.1F, -1.6875F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(3.3625F, -3.8F, -0.0625F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
