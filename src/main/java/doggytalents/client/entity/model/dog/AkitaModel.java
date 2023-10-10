package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class AkitaModel extends DogModel{

    public AkitaModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 13.75F, 7.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 1.1F, -1.0F));

		PartDefinition real_tail2 = tail2.addOrReplaceChild("real_tail2", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.35F, -3.0F));

		PartDefinition tail3 = real_tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition tail_r1 = tail3.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 21).addBox(-0.5F, 0.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(4.9933F, 4.1971F, 2.05F, -0.7418F, 0.0F, 2.5307F));

		PartDefinition tail_r2 = tail3.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 21).addBox(-1.0F, -1.5F, -1.4F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(4.9933F, 4.1971F, 2.05F, 0.0F, 0.0F, 2.5307F));

		PartDefinition tail_r3 = tail3.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(9, 21).addBox(-1.25F, 1.9F, -0.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(1.0F, 6.5F, 0.15F, 0.0F, 0.0F, -1.7453F));

		PartDefinition tail_r4 = tail3.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(9, 20).addBox(-1.0F, -0.2F, -1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.0F, 3.5F, 1.4F, 0.0F, 0.0F, -0.48F));

		PartDefinition tail_r5 = tail3.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(9, 21).addBox(-1.0F, -0.8F, -0.7F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.0F, 6.5F, 1.4F, 0.0F, 0.0F, -1.4835F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(52, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(52, 0).addBox(1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -1.6262F, -3.0526F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 14.5F, 1.5F, 1.4835F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -2.75F, -4.15F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(22, 1).addBox(-3.0F, -5.5F, -2.75F, 8.0F, 6.0F, 6.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, -7.5F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(36, 14).addBox(-1.5F, -0.15F, -2.45F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 12).addBox(-1.5F, 0.05F, -1.7F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 1.73F, -2.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.75F, -2.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.73F, -2.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition headfur = real_head.addOrReplaceChild("headfur", CubeListBuilder.create().texOffs(52, 18).addBox(1.25F, -11.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(51, 27).addBox(2.0F, -12.3F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(52, 28).addBox(3.5F, -12.8F, -8.6F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(54, 27).addBox(2.25F, -14.05F, -8.6F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(54, 25).addBox(2.25F, -15.05F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(51, 27).addBox(1.0F, -14.8F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(52, 18).mirror().addBox(-4.5F, -11.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(52, 18).mirror().addBox(-4.25F, -12.05F, -9.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(52, 18).mirror().addBox(-3.5F, -13.05F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(52, 18).addBox(0.5F, -13.05F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(52, 18).addBox(1.25F, -12.05F, -9.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(51, 27).mirror().addBox(-5.25F, -12.3F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(52, 18).mirror().addBox(-2.75F, -10.05F, -8.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(51, 17).mirror().addBox(-4.0F, -10.55F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(52, 18).addBox(0.75F, -10.05F, -8.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(52, 18).addBox(-1.0F, -9.55F, -8.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(51, 17).addBox(0.75F, -10.55F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(52, 28).mirror().addBox(-4.75F, -12.8F, -8.35F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(54, 27).mirror().addBox(-4.5F, -14.05F, -8.6F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(54, 25).mirror().addBox(-3.5F, -15.05F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(51, 27).mirror().addBox(-4.25F, -14.8F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(54, 27).mirror().addBox(-3.0F, -15.8F, -8.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(54, 25).mirror().addBox(-2.0F, -16.3F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(51, 27).mirror().addBox(-2.75F, -16.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(51, 27).addBox(-0.25F, -16.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 25).addBox(1.0F, -16.3F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(54, 27).addBox(1.0F, -15.8F, -8.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(53, 27).addBox(1.25F, -13.5F, -5.55F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(47, 24).addBox(-2.0F, -14.75F, -6.8F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(47, 24).addBox(0.25F, -14.75F, -6.8F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(52, 27).addBox(-3.5F, -14.75F, -5.55F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 27).addBox(-4.25F, -13.75F, -5.8F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(43, 27).addBox(0.5F, -14.75F, -5.55F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(47, 24).addBox(-1.0F, -15.8F, -6.95F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(52, 27).addBox(-3.0F, -11.75F, -6.05F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(52, 27).addBox(-2.25F, -12.75F, -5.8F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(47, 24).addBox(-0.75F, -12.75F, -7.05F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.5F))
		.texOffs(47, 24).addBox(0.25F, -13.8F, -7.2F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 12.5F, 7.3F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.25F, -0.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-2.5F, -3.0F, 0.5F));

		PartDefinition head_r3 = right_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.45F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 1.25F, 0.0F, 0.1745F, 0.0F, -0.3054F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.2F, -2.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(0.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 1.25F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(23, 14).mirror().addBox(-0.25F, -2.85F, -0.35F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 0.75F, -0.25F, 0.0F, 0.0F, -0.3927F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-0.75F, -0.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(2.5F, -3.0F, 0.5F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(23, 14).addBox(-1.75F, -2.95F, -0.35F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.25F, 0.75F, -0.25F, 0.0F, 0.0F, 0.3927F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(16, 14).addBox(-1.55F, -3.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.25F, 1.25F, 0.0F, 0.1745F, 0.0F, 0.3054F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(16, 14).addBox(-1.8F, -2.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 14).addBox(-2.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 1.25F, 0.0F, 0.0F, 0.0F, 0.3054F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
