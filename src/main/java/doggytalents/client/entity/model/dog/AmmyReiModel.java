package doggytalents.client.entity.model.dog;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class AmmyReiModel extends DogModel {

	

    public AmmyReiModel(ModelPart box) {
		super(box, RenderType::entityTranslucent);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(25, 13).addBox(-2.0F, -3.45F, -2.0F, 6.0F, 1.0F, 3.0F, new CubeDeformation(-0.35F))
		.texOffs(23, 0).addBox(-0.5F, -0.22F, -5.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(23, 1).addBox(-0.5F, 0.53F, -5.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(35, 8).addBox(-1.43F, -2.49F, -1.45F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(36, 5).addBox(-1.63F, -1.89F, -1.45F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(36, 5).addBox(-2.23F, -1.64F, -1.45F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(36, 5).addBox(-2.73F, 0.01F, -1.45F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(36, 5).addBox(-2.48F, -0.99F, -1.7F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.07F, -0.51F, -0.3F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(35, 8).mirror().addBox(-0.57F, -2.49F, -1.45F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(36, 5).mirror().addBox(0.63F, -1.89F, -1.45F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(36, 5).mirror().addBox(1.23F, -1.64F, -1.45F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(36, 5).mirror().addBox(1.73F, 0.01F, -1.45F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(36, 5).mirror().addBox(1.48F, -0.99F, -1.7F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(3.07F, -0.51F, -0.3F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -5.25F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, 2.0F));

		PartDefinition mane_rotation = upper_body.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 32).mirror().addBox(-4.1F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 32).addBox(3.1F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, -2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).mirror().addBox(-0.1F, 1.7F, -2.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).addBox(1.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).mirror().addBox(-0.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).addBox(1.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.5F, 7.75F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, 0.0F, -0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 28).addBox(0.0F, 3.5F, 0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(33, 24).mirror().addBox(0.55F, -0.8F, -2.3F, 1.0F, 8.0F, 7.0F, new CubeDeformation(-1.4F)).mirror(false)
		.texOffs(33, 24).addBox(0.45F, -0.8F, -2.3F, 1.0F, 8.0F, 7.0F, new CubeDeformation(-1.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -1.25F, -1.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.0F, 3.75F, 1.25F, 0.1309F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	public static void createAugment() {
		
	}

	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

	@Override
	public boolean tickClient() { return true; }

	@Override
    public void doTickClient(Dog dog) {
	}
    
}
