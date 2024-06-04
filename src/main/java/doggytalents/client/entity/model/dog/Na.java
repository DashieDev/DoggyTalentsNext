package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class Na extends DogModel{

    public Na(ModelPart box) {
        super(box);
    }
    public static LayerDefinition na() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 18).addBox(-1.0F, 2.0F, 0.3F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(9, 18).addBox(-1.0F, 4.5F, 1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 18).addBox(-1.0F, 6.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(9, 16).addBox(-1.0F, 2.75F, 3.25F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -3.5F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(43, 23).mirror().addBox(-3.0F, -1.0F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.25F, 0.0F, 0.0F, 0.0F, 3.1416F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -2.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create(), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition head_r2 = snout_upper.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.6F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.8F, -0.9F, 0.0873F, 0.0F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create(), PartPose.offset(0.0F, 0.98F, 0.0F));

		PartDefinition head_r3 = snout_lower.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(43, 15).addBox(-1.5F, 0.1F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -0.7F, -0.9F, 0.0873F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.0F, -3.0F, 0.5F));

		PartDefinition head_r4 = left_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(56, 14).addBox(-1.5463F, -0.9183F, -0.3483F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 14).addBox(0.4692F, -0.9273F, -0.3483F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(55, 14).addBox(-1.0888F, -0.8938F, -0.0983F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.8491F, 0.1951F, -0.4017F, 0.0F, 0.0F, 0.6981F));

		PartDefinition head_r5 = left_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(55, 14).addBox(-0.5817F, -1.0085F, 0.7239F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.8491F, 0.1951F, -0.4017F, 1.9706F, -0.1796F, 1.098F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(55, 14).addBox(-1.0991F, -1.0164F, 0.5562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.8491F, 0.1951F, -0.4017F, 1.9706F, 0.1796F, 0.2983F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-2.0F, -3.0F, 0.5F));

		PartDefinition head_r7 = right_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(16, 13).mirror().addBox(-0.9009F, -1.0164F, 0.5562F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.8491F, 0.1951F, -0.4017F, 1.9706F, -0.1796F, -0.2983F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(16, 13).mirror().addBox(-0.9112F, -0.8938F, -0.0983F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(17, 13).mirror().addBox(-1.4692F, -0.9273F, -0.3483F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(17, 13).addBox(0.5463F, -0.9183F, -0.3483F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8491F, 0.1951F, -0.4017F, 0.0F, 0.0F, -0.6981F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(16, 13).addBox(-1.4183F, -1.0085F, 0.7239F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.8491F, 0.1951F, -0.4017F, 1.9706F, 0.1796F, -1.098F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

}
