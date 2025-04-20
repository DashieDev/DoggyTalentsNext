package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CorgiModel extends DogModel {

    public CorgiModel(ModelPart box) {
        super(box);
    }
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 15.45F, 7.75F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, 0.0163F, -0.1345F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 1.2F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 23).addBox(-0.95F, 1.7F, -0.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.5F, 18.75F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 1.2F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 23).mirror().addBox(-1.05F, 1.7F, -0.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(1.5F, 18.75F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 1.2F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.75F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 1.2F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 18.75F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, 3.5F, -3.2F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.55F))
		.texOffs(48, 1).addBox(1.025F, 4.35F, -2.65F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.6F))
		.texOffs(48, 1).mirror().addBox(-3.025F, 4.35F, -2.65F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(0.0F, 17.75F, 1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -4.5F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.5F, -0.2F, -0.0524F, 0.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 20.25F));

		PartDefinition bone2 = body2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(54, 24).addBox(-6.5298F, 29.8046F, -8.5173F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(54, 29).addBox(-6.0298F, 30.3046F, -7.6173F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(54, 25).addBox(-6.0298F, 30.3046F, -8.4673F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(53, 24).addBox(-6.0298F, 30.3046F, -9.2173F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.9783F, -15.552F, -5.8749F, 0.0F, -0.5847F, 0.0F));

		PartDefinition bone7 = body2.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(54, 24).addBox(2.8468F, 29.8046F, -9.1088F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(54, 29).addBox(3.3468F, 30.3046F, -8.2587F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(54, 25).addBox(3.3468F, 30.3046F, -8.9087F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(53, 24).addBox(3.3468F, 30.3046F, -9.4587F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.9217F, -15.552F, -5.6249F, 0.0F, 0.48F, 0.0F));

		PartDefinition dollop_of_cream4 = body2.addOrReplaceChild("dollop_of_cream4", CubeListBuilder.create().texOffs(56, 10).addBox(-1.0F, 32.0F, -19.15F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(41, 26).addBox(-1.5F, 31.5F, -20.4F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(-0.5F, -17.8F, 2.7F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(23, 1).addBox(-3.0F, -2.0F, -2.8F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 17.75F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(27, 2).mirror().addBox(-4.0F, -2.0F, -5.2F, 3.0F, 5.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.5F, 0.0F, 3.65F, -0.6981F, 0.0F, 0.0F));

		PartDefinition body4 = upper_body.addOrReplaceChild("body4", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 25.75F));

		PartDefinition bone5 = body4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(54, 24).addBox(-11.3894F, 2.3045F, -11.6787F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F))
		.texOffs(54, 29).addBox(-10.8894F, 2.8045F, -10.7787F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F))
		.texOffs(54, 25).addBox(-10.8894F, 2.8045F, -11.6287F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F))
		.texOffs(53, 24).addBox(-10.8894F, 2.8045F, -12.3787F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-0.7783F, -15.552F, -6.1749F, 0.0F, -0.7418F, 0.0F));

		PartDefinition bone6 = body4.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(54, 24).addBox(7.5548F, 2.3045F, -12.6059F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.8F))
		.texOffs(54, 29).addBox(8.0548F, 2.8045F, -11.7559F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F))
		.texOffs(54, 25).addBox(8.0548F, 2.8045F, -12.4059F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F))
		.texOffs(53, 24).addBox(8.0548F, 2.8045F, -12.9559F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.7217F, -15.552F, -5.9249F, 0.0F, 0.6545F, 0.0F));

		PartDefinition dollop_of_cream3 = body4.addOrReplaceChild("dollop_of_cream3", CubeListBuilder.create().texOffs(56, 10).addBox(-1.0F, 4.5F, -24.65F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(41, 26).addBox(-1.5F, 4.0F, -25.9F, 4.0F, 4.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(-0.5F, -17.8F, 2.7F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 15.75F, -6.75F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.4F, -2.7F, -1.35F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 2).addBox(-3.0F, -1.3F, -1.65F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-0.35F))
		.texOffs(0, 0).addBox(-3.0F, -3.05F, -1.65F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(11, 24).mirror().addBox(-0.5F, -0.27F, -4.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(2.4F, -2.7F, -1.35F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(13, 20).mirror().addBox(-0.05F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.45F, 1.8597F, -2.8067F, -0.8988F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(0.0F, -2.75F, -1.75F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(1, 11).addBox(-1.0F, 3.5F, -2.4F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition head_r2 = snout_upper.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(2, 12).addBox(-1.5F, -0.82F, -0.3F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.5F, 4.12F, -1.1F, 0.3927F, 0.0F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.98F, 0.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition head_r3 = snout_lower.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(9, 11).addBox(-0.501F, -0.609F, -0.001F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-0.499F, 4.411F, -0.799F, 0.5411F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-1.05F, -2.65F, -0.625F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(16, 14).addBox(-0.05F, -3.1F, -0.625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(39, 15).addBox(-0.9F, -2.8F, -0.975F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.199F))
		.texOffs(16, 14).addBox(-0.35F, -3.6F, -0.525F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(16, 14).addBox(-0.15F, -1.35F, -0.525F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).mirror().addBox(-0.85F, -2.05F, -0.225F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.6F, -2.4F, 0.875F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(0.05F, -2.65F, -0.625F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(-0.95F, -3.1F, -0.625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(39, 15).mirror().addBox(-1.1F, -2.8F, -0.975F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.199F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(-0.65F, -3.6F, -0.525F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(-0.85F, -1.35F, -0.525F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(16, 14).addBox(-1.15F, -2.05F, -0.225F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.6F, -2.4F, 0.875F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

}
