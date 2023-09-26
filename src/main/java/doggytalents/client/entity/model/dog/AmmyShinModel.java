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

public class AmmyShinModel extends DogModel {

	

    public AmmyShinModel(ModelPart box) {
		super(box, RenderType::entityTranslucent);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 12.75F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(25, 14).addBox(-2.0F, -3.35F, -2.4F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.45F))
		.texOffs(26, 14).addBox(-2.0F, -4.05F, -2.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(23, 1).addBox(-0.75F, 0.08F, -4.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(26, 1).addBox(1.75F, 0.08F, -4.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 0).addBox(-0.5F, -0.22F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(26, 14).addBox(-3.0F, -0.35F, -2.35F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.24F))
		.texOffs(26, 14).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(1.0F, -3.05F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.9667F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.2F, -2.6667F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 0).addBox(-0.8F, -1.3667F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offset(2.6F, -3.1833F, -0.4F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.8333F, -2.6667F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-1.0333F, -1.9667F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-1.1333F, -1.3667F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offset(-0.6667F, -3.1833F, -0.4F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, -3.0F));

		PartDefinition mane_rotation = upper_body.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.5F, -1.25F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.15F))
		.texOffs(0, 32).addBox(3.35F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).mirror().addBox(-4.35F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.5F, 2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).mirror().addBox(-1.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).addBox(0.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).mirror().addBox(-1.1F, 0.2F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 17.5F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).addBox(0.1F, 0.2F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 17.5F, -4.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 12.5F, 7.75F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 28).addBox(0.0F, 3.5F, 0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(33, 24).mirror().addBox(0.3F, -0.8F, -2.3F, 1.0F, 8.0F, 7.0F, new CubeDeformation(-1.5F)).mirror(false)
		.texOffs(33, 24).addBox(0.7F, -0.8F, -2.3F, 1.0F, 8.0F, 7.0F, new CubeDeformation(-1.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -1.25F, -1.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(1.0F, 3.75F, 1.25F, 0.1309F, 0.0F, 0.0F));

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
