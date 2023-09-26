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

public class AmmyTeiModel extends DogModel {

	

    public AmmyTeiModel(ModelPart box) {
		super(box, RenderType::entityTranslucent);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 8.0F, 1.8762F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(24, 23).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(33, 24).mirror().addBox(-1.15F, 0.45F, -2.15F, 1.0F, 8.0F, 7.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(33, 24).addBox(0.15F, 0.45F, -2.15F, 1.0F, 8.0F, 7.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(24, 23).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 6.0F, 3.0F, -1.3963F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(24, 23).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 6.25F, 2.0F, 0.6981F, 0.0F, 0.0F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(24, 23).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 4.25F, 1.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.3F, -1.35F, -0.8F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).addBox(-0.2F, 0.35F, -1.2F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.8F, 17.35F, -4.2F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-0.7F, -1.35F, -0.8F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).mirror().addBox(-0.8F, 0.35F, -1.2F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.8F, 17.35F, -4.2F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.3F, -1.35F, -1.8F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).addBox(-0.2F, 0.35F, -2.2F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.8F, 17.35F, 7.8F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-0.7F, -1.35F, -1.3F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).mirror().addBox(-0.8F, 0.35F, -2.7F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.8F, 17.35F, 7.3F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, -3.0F));

		PartDefinition mane_rotation = upper_body.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-4.1F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 32).addBox(3.1F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = mane_rotation.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.75F, -2.75F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.5F, 0.5F, 0.0436F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 2.5F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -0.25F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body_rotation.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, -2.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(25, 13).addBox(-2.0F, -3.7F, -1.45F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.45F))
		.texOffs(38, 0).mirror().addBox(-0.75F, 0.43F, -4.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(38, 0).addBox(1.75F, 0.43F, -4.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 0).addBox(-0.5F, -0.22F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(26, 14).addBox(-3.0F, -0.25F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(26, 14).addBox(-3.0F, -1.0F, -0.25F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(1.0F, -2.7F, 1.75F, 0.0F, 3.1416F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(44, 16).mirror().addBox(2.1625F, -0.3F, -1.6875F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(44, 16).mirror().addBox(1.9125F, -1.2F, -1.4375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(42, 8).mirror().addBox(1.3125F, -1.7F, -1.4375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(41, 11).mirror().addBox(0.1125F, -2.3F, -1.4375F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(2.6375F, -0.7F, -0.3125F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(44, 16).addBox(-3.1625F, -0.3F, -1.6875F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(44, 16).addBox(-2.9125F, -1.2F, -1.4375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(42, 8).addBox(-2.3125F, -1.7F, -1.4375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(41, 11).addBox(-2.1125F, -2.3F, -1.4375F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offset(-0.6375F, -0.7F, -0.3125F));

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
