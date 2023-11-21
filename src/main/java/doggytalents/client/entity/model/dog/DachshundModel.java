package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DachshundModel extends DogModel {

    public DachshundModel(ModelPart box) {
        super(box);
    }
    
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -3.25F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 16.25F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(8, 41).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 17.5F, 8.8F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(8, 41).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 17.5F, 8.8F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 16.25F, -6.75F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(26, 43).addBox(-1.5F, -0.02F, -5.25F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 0).addBox(-3.0F, -3.45F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(18, 49).addBox(-3.3F, 0.2F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(18, 49).addBox(-2.8F, -0.3F, -2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(18, 49).addBox(-1.95F, -1.2F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.15F))
		.texOffs(30, 5).addBox(-1.75F, -1.8F, -2.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(18, 49).addBox(-2.55F, -0.95F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offset(-2.0F, -1.0F, 0.5F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(30, 23).addBox(-0.25F, -1.8F, -2.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(18, 49).mirror().addBox(0.95F, -1.2F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(18, 49).addBox(1.55F, -0.95F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(18, 49).mirror().addBox(1.8F, -0.3F, -2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(18, 49).mirror().addBox(2.3F, 0.2F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offset(2.0F, -1.0F, 0.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 16.0F, 1.0F, 1.6581F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -1.7199F, -1.6637F, 6.0F, 11.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.1305F, -1.5086F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(8, 42).addBox(-1.0F, 0.85F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 18.0F, -3.5F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 14.2F, 9.25F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(0, 40).addBox(-1.0F, -0.5997F, -1.3842F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(8, 42).addBox(-1.0F, 0.85F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.0F, -3.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }
}
