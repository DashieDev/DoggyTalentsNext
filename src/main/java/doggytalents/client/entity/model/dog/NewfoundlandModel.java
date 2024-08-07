package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class NewfoundlandModel extends DogModel{

    public NewfoundlandModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 12.25F, 8.0F, 1.6144F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.55F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.55F)), PartPose.offsetAndRotation(-1.0F, 7.1151F, 2.3489F, 1.2654F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.65F)), PartPose.offsetAndRotation(-1.0F, 3.7378F, -0.0827F, 0.6109F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.25F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.75F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.8F, -0.25F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-0.2F, -0.25F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(48, 25).addBox(-3.0F, 6.0F, -4.9F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.3002F))
		.texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.3F))
		.texOffs(58, 7).addBox(2.01F, -2.0F, -5.3F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.3001F))
		.texOffs(58, 7).mirror().addBox(-3.01F, -2.0F, -5.3F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.3001F)).mirror(false), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(46, 4).addBox(-4.0F, -2.9F, -4.9F, 8.0F, 0.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(50, 16).addBox(4.0F, -3.0F, -6.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
		.texOffs(50, 16).mirror().addBox(-4.0F, -3.0F, -6.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(24, 3).mirror().addBox(-4.0F, -3.0F, 0.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(1.0F, -2.5F, 2.1F, -0.3057F, 0.0002F, -0.0051F));

		PartDefinition mane_rotation_r2 = upper_body.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(24, 3).addBox(-2.0F, -3.0F, 0.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, -2.5F, 2.1F, -0.3057F, -0.0002F, 0.0051F));

		PartDefinition mane_rotation_r3 = upper_body.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(23, 2).addBox(-2.0F, -3.0F, -1.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, -2.45F, 1.7F, -0.6981F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 9.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -3.0F, -2.35F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 0).addBox(-3.0F, -3.75F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-3.0F, -4.75F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-0.7F))
		.texOffs(0, 0).mirror().addBox(0.0F, -3.0F, -2.35F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -2.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(37, 13).addBox(-2.25F, -0.9F, -2.75F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(37, 13).mirror().addBox(0.25F, -0.9F, -2.75F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -0.498F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, 0.98F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.75F, -3.0F, 0.5F));

		PartDefinition head_r1 = left_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -4.1382F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(2.05F, 1.2882F, 0.0F, 2.5744F, -1.4835F, 0.9163F));

		PartDefinition head_r2 = left_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -4.1382F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.55F, 1.7882F, 0.0F, 2.3562F, -1.4835F, 0.9163F));

		PartDefinition head_r3 = left_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -3.1382F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(1.05F, 0.7882F, 0.0F, 2.5744F, -1.4835F, 0.9163F));

		PartDefinition head_r4 = left_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -2.1382F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.5F, -0.1118F, 0.0F, 1.5708F, -1.4835F, 0.9163F));

		PartDefinition head_r5 = left_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(29, 21).addBox(-1.0F, -2.1382F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.25F, 0.6382F, 0.0F, -0.2618F, -1.4835F, 0.9163F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -4.1382F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, 1.7882F, 0.0F, 2.1817F, -1.4835F, 0.9163F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-2.75F, -3.0F, 0.5F));

		PartDefinition head_r7 = right_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -4.1382F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.05F, 1.7882F, 0.0F, 2.1817F, 1.4835F, -0.9163F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -4.1382F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.55F, 1.7882F, 0.0F, 2.3562F, 1.4835F, -0.9163F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -4.1382F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-2.05F, 1.2882F, 0.0F, 2.5744F, 1.4835F, -0.9163F));

		PartDefinition head_r10 = right_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -3.1382F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-1.05F, 0.7882F, 0.0F, 2.5744F, 1.4835F, -0.9163F));

		PartDefinition head_r11 = right_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -2.1382F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -0.1118F, 0.0F, 1.5708F, 1.4835F, -0.9163F));

		PartDefinition head_r12 = right_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(29, 21).mirror().addBox(-1.0F, -2.1382F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.25F, 0.6382F, 0.0F, -0.2618F, 1.4835F, -0.9163F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

}
