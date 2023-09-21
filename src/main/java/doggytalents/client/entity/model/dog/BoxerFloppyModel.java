package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BoxerFloppyModel extends DogModel {

    public BoxerFloppyModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.25F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -12.0F, -1.5F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 10.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 11.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -3.6F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-3.0F, -3.15F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.45F))
		.texOffs(53, 7).mirror().addBox(-1.75F, 0.73F, -4.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(53, 7).addBox(0.75F, 0.73F, -4.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 16).addBox(-1.372F, -2.15F, 0.2463F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 12).addBox(-1.572F, -1.9F, -0.7537F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(16, 12).addBox(-2.172F, -1.65F, -0.7537F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-2.922F, -0.5F, -0.7537F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-2.422F, -1.0F, -1.0037F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-2.0F, -1.5F, -1.0F, 0.0F, -0.3054F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-0.6165F, -2.15F, 0.1711F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(0.5835F, -1.9F, -0.8289F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(1.1835F, -1.65F, -0.8289F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(1.9335F, -0.5F, -0.8289F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(1.4335F, -1.0F, -1.0789F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(2.0F, -1.5F, -1.0F, 0.0F, 0.3054F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
