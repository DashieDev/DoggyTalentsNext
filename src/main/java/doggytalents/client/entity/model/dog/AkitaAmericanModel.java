package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class AkitaAmericanModel extends DogModel {

    public AkitaAmericanModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.25F, 7.6F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 20).addBox(-1.0F, 0.25F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 21).addBox(-1.0F, -0.8F, -0.7F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 4.75F, 0.4F, 0.0F, 0.0F, -1.4835F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 20).addBox(-1.0F, -0.2F, -1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 1.75F, 0.4F, 0.0F, 0.0F, -0.48F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(9, 21).addBox(-1.25F, 1.9F, -0.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 4.75F, -0.85F, 0.0F, 0.0F, -1.7453F));

		PartDefinition tail_r4 = real_tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(9, 21).addBox(-1.0F, -1.5F, -1.4F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(3.9933F, 2.4471F, 1.05F, 0.0F, 0.0F, 2.5307F));

		PartDefinition tail_r5 = real_tail.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(9, 21).addBox(-0.5F, 0.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(3.9933F, 2.4471F, 1.05F, -0.7418F, 0.0F, 2.5307F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(52, 0).addBox(-0.25F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(52, 0).addBox(-1.5F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offset(2.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -1.8762F, -3.0526F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 14.5F, 1.5F, 1.5272F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.75F, -3.9F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 14.25F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(22, 1).addBox(-3.0F, -4.5F, -2.8F, 8.0F, 5.0F, 6.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.25F, -0.8727F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 10.25F, -6.75F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 3).addBox(-3.0F, -0.25F, -1.5F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.299F))
		.texOffs(0, 0).addBox(-3.0F, -3.25F, -1.5F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, 0.05F, -1.95F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 1.23F, -2.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.5F, -2.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 1.23F, -2.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -1.5F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.301F)), PartPose.offsetAndRotation(0.0F, -0.25F, 2.5F, -0.9163F, 0.0F, 0.0F));

		PartDefinition headfur = real_head.addOrReplaceChild("headfur", CubeListBuilder.create().texOffs(52, 18).mirror().addBox(-3.5F, -11.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(52, 18).mirror().addBox(-4.0F, -12.05F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(52, 18).mirror().addBox(-3.25F, -13.05F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(51, 17).mirror().addBox(-3.2F, -10.55F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(54, 27).mirror().addBox(-3.75F, -14.05F, -8.6F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(54, 25).mirror().addBox(-3.5F, -15.05F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(51, 27).mirror().addBox(-3.75F, -14.55F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(54, 25).addBox(2.5F, -15.05F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(51, 27).addBox(0.75F, -14.55F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(54, 27).addBox(1.75F, -14.05F, -8.6F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(52, 18).addBox(0.25F, -13.05F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(52, 18).addBox(1.0F, -12.05F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(52, 18).addBox(0.5F, -11.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(51, 17).addBox(0.2F, -10.55F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 27).mirror().addBox(-3.0F, -15.8F, -8.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(54, 25).mirror().addBox(-2.0F, -16.05F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(51, 27).mirror().addBox(-2.75F, -16.05F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(51, 27).addBox(-0.25F, -16.05F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F))
		.texOffs(54, 25).addBox(1.0F, -16.05F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 27).addBox(1.0F, -15.8F, -8.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(52, 27).addBox(-3.0F, -11.75F, -6.05F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(52, 27).addBox(-2.25F, -12.75F, -5.8F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(47, 24).addBox(0.25F, -13.8F, -7.2F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.7F)), PartPose.offset(0.0F, 12.25F, 7.55F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.75F, -3.0F, 0.5F, 0.0F, -0.7418F, 0.0F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.45F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 1.25F, 0.0F, 0.1745F, 0.0F, -0.3054F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.45F, -2.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(-0.25F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 1.25F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head_r6 = right_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(23, 14).mirror().addBox(-0.35F, -2.45F, -0.4F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 0.75F, -0.25F, 0.0F, 0.0F, -0.3927F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(2.75F, -3.0F, 0.5F, 0.0F, 0.7418F, 0.0F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(16, 14).addBox(-1.55F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.25F, 1.25F, 0.0F, 0.1745F, 0.0F, 0.3054F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(16, 14).addBox(-1.55F, -2.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(-1.75F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.25F, 1.25F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r9 = left_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(23, 14).addBox(-1.65F, -2.45F, -0.4F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.25F, 0.75F, -0.25F, 0.0F, 0.0F, 0.3927F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
