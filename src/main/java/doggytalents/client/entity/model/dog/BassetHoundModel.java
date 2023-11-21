package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BassetHoundModel extends DogModel {

    public BassetHoundModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 7.5F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(0, 39).addBox(-1.0F, 0.628F, -1.6555F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(8, 41).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(8, 41).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 17.5F, 8.8F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(8, 41).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(8, 41).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 17.5F, 8.8F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(8, 42).addBox(-1.0F, -0.15F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(8, 44).addBox(-1.0F, -0.65F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(8, 44).addBox(-1.0F, 3.85F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(-2.0F, 19.0F, -3.5F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(8, 42).addBox(-1.0F, -0.15F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(8, 44).addBox(-1.0F, -0.65F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(8, 44).addBox(-1.0F, 3.85F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(2.0F, 19.0F, -3.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 18.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -1.4699F, -2.6637F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, -0.6737F, -0.4786F, 0.0436F, 0.0F, 0.0F));

		PartDefinition body_rotation_r2 = body.addOrReplaceChild("body_rotation_r2", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -0.6436F, -1.8923F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -4.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.55F))
		.texOffs(0, 0).addBox(-4.0F, -2.5F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 17.5F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(35, 50).addBox(-3.5F, -6.0F, -3.75F, 7.0F, 8.0F, 6.0F, new CubeDeformation(-1.05F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.25F, -0.9599F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 11.5F, -6.75F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(23, 0).addBox(-3.0F, -3.45F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(24, 13).addBox(-3.0F, -2.7F, -2.5F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(41, 39).mirror().addBox(-1.5F, -1.5F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(41, 39).addBox(0.5F, -1.5F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.23F, -3.5F, 0.3054F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(25, 42).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 1.48F, -3.25F, 0.2618F, 0.0F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(24, 13).addBox(-2.5F, -2.05F, -13.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 10.5F, 7.0F, -0.9599F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(30, 23).addBox(-0.75F, -1.8F, -2.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, 0.5F));

		PartDefinition head_r4 = left_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(18, 49).mirror().addBox(-0.2755F, 1.0F, -1.8335F, 1.0F, 4.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(2.55F, 3.2F, -1.875F, 0.0F, 0.9512F, 0.0F));

		PartDefinition head_r5 = left_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(18, 49).mirror().addBox(0.7245F, 1.0F, -1.8335F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.55F, 3.2F, -1.625F, 0.0F, 0.9512F, 0.0F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(18, 49).mirror().addBox(-0.5255F, -0.75F, -1.5835F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.55F, 3.2F, -1.625F, 0.0F, 0.6894F, 0.0F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(18, 49).mirror().addBox(-1.75F, -2.75F, -1.375F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(2.55F, 3.2F, -1.625F, 0.0F, 0.5149F, 0.0F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(18, 49).addBox(-0.2F, -1.375F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(1.25F, 0.425F, -0.75F, 0.0F, 0.3927F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(30, 23).mirror().addBox(-1.25F, -1.8F, -2.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -1.0F, 0.5F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(18, 49).addBox(-0.7245F, 1.0F, -1.8335F, 1.0F, 4.0F, 3.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-2.55F, 3.2F, -1.875F, 0.0F, -0.9512F, 0.0F));

		PartDefinition head_r10 = right_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(18, 49).addBox(-1.7245F, 1.0F, -1.8335F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.55F, 3.2F, -1.625F, 0.0F, -0.9512F, 0.0F));

		PartDefinition head_r11 = right_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(18, 49).addBox(-0.4745F, -0.75F, -1.5835F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.55F, 3.2F, -1.625F, 0.0F, -0.6894F, 0.0F));

		PartDefinition head_r12 = right_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(18, 49).addBox(-0.25F, -2.75F, -1.375F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-2.55F, 3.2F, -1.625F, 0.0F, -0.5149F, 0.0F));

		PartDefinition head_r13 = right_ear.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(18, 49).mirror().addBox(-0.8F, -1.375F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-1.25F, 0.425F, -0.75F, 0.0F, -0.3927F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

}
