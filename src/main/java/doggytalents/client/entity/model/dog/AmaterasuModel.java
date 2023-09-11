package doggytalents.client.entity.model.dog;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleTypes;

public class AmaterasuModel extends DogModel {

	

    public AmaterasuModel(ModelPart box) {
		super(box, RenderType::entityTranslucent);
	}

	public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

		var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(32, 24).addBox(-0.5F, -0.53F, -2.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(33, 24).addBox(-0.5F, -0.77F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F))
		.texOffs(23, 0).addBox(-1.5F, -0.07F, -4.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(24, 13).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.ZERO);

		var right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-1.6753F, -3.9F, -0.2839F));

		var head_r1 = right_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.3F, -0.3F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.5F, -1.2F, -0.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-0.6F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-1.2F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-0.9F, -1.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6545F, 0.0F));

		var left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(1.6753F, -3.9F, -0.2839F));

		var head_r2 = left_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.1F, -1.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 13).addBox(0.2F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 13).addBox(-0.4F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 0).addBox(-0.7F, -0.3F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(18, 13).addBox(-1.5F, -1.2F, -0.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6545F, 0.0F));

		var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		var mane_rotation = upper_body.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-4.1F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 32).addBox(3.1F, -5.5F, -4.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -5.5F, -0.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.5F, -2.5F));

		var left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(33, 43).addBox(1.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		var left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(33, 43).addBox(1.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		var right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 43).mirror().addBox(-0.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 16.0F, -4.0F));

		var right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(33, 43).mirror().addBox(-0.1F, 1.7F, -2.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 12.0F, 8.0F));
		tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(33, 24).mirror().addBox(2.1F, -0.3F, -1.4F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(33, 24).mirror().addBox(-0.15F, -0.3F, -1.4F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(24, 24).addBox(0.0F, 4.0F, 3.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(24, 24).addBox(0.0F, 2.0F, 0.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(24, 24).addBox(0.0F, 5.0F, 1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(24, 23).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

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
