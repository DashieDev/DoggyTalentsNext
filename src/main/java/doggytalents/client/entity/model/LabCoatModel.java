package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class LabCoatModel extends SyncedAccessoryModel{

    public LabCoatModel(ModelPart root) {
        super(root);
    }
	
	@Override
	protected void populatePart(ModelPart box) {
		this.mane = Optional.of(box.getChild("upper_body"));
		this.body = Optional.of(box.getChild("body"));
		this.legFrontRight = Optional.of(box.getChild("right_front_leg"));
		this.legFrontLeft = Optional.of(box.getChild("left_front_leg"));
		this.tail = Optional.of(box.getChild("tail"));
        this.realTail = Optional.of(tail.get().getChild("real_tail"));
	}

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(12, 8).addBox(-1F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(11, 5).addBox(-1F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(11, 5).mirror().addBox(-1F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition lab2 = body.addOrReplaceChild("lab2", CubeListBuilder.create().texOffs(40, 3).addBox(-3.4F, -4.0F, -13.3F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.35F))
		.texOffs(40, 3).mirror().addBox(1.4F, -4.0F, -13.3F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(4, 7).addBox(-3.9F, -0.55F, -14.1F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(4, 7).addBox(-3.9F, -1.55F, -14.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(4, 7).mirror().addBox(0.9F, -0.55F, -14.1F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(4, 7).mirror().addBox(0.9F, -1.55F, -14.1F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offset(0.0F, 2.0F, 10.0F));

		PartDefinition body_rotation_r1 = lab2.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(2.0F, 0.5F, -5.6F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.35F)).mirror(false)
		.texOffs(43, 8).mirror().addBox(-2.75F, 2.8F, -0.7F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(43, 8).addBox(0.75F, 2.8F, -0.7F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(38, 3).addBox(-3.0F, 0.5F, -5.6F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 7.7311F, -12.1955F, -1.4835F, 0.0F, 0.0F));

		PartDefinition body_rotation_r2 = lab2.addOrReplaceChild("body_rotation_r2", CubeListBuilder.create().texOffs(37, 0).mirror().addBox(2.0F, -4.5F, -5.5F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)).mirror(false)
		.texOffs(37, 0).addBox(-3.0F, -4.5F, -5.5F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)), PartPose.offsetAndRotation(0.0F, 6.9755F, -11.2386F, -1.0472F, 0.0F, 0.0F));

		PartDefinition body_rotation_r3 = lab2.addOrReplaceChild("body_rotation_r3", CubeListBuilder.create().texOffs(43, 8).mirror().addBox(-1.5F, 0.0F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-0.1669F, 7.2052F, -12.3245F, -1.1725F, 0.3477F, 0.0317F));

		PartDefinition body_rotation_r4 = lab2.addOrReplaceChild("body_rotation_r4", CubeListBuilder.create().texOffs(43, 8).addBox(-0.5F, 0.0F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.1669F, 7.2052F, -12.3245F, -1.1725F, -0.3477F, -0.0317F));

		PartDefinition body_rotation_r5 = lab2.addOrReplaceChild("body_rotation_r5", CubeListBuilder.create().texOffs(43, 6).addBox(-3.0F, -4.5F, -0.5F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 6.9755F, -11.2386F, -1.0908F, 0.0F, 0.0F));

		PartDefinition body_rotation_r6 = lab2.addOrReplaceChild("body_rotation_r6", CubeListBuilder.create().texOffs(38, 2).addBox(-3.5F, -4.0F, -11.0F, 7.0F, 9.0F, 4.0F, new CubeDeformation(0.24F)), PartPose.offsetAndRotation(0.0F, 0.6083F, 0.4319F, -0.0873F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition lab = upper_body.addOrReplaceChild("lab", CubeListBuilder.create().texOffs(0, 3).addBox(-4.5F, -3.5F, -0.5F, 9.0F, 7.0F, 5.0F, new CubeDeformation(-0.4F))
		.texOffs(1, 4).addBox(-4.4F, -3.55F, -3.8F, 4.0F, 7.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(1, 4).mirror().addBox(0.4F, -3.55F, -3.8F, 4.0F, 7.0F, 4.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(4, 7).mirror().addBox(0.9F, -0.95F, -4.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(4, 7).mirror().addBox(0.9F, 0.05F, -4.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(3, 6).addBox(-3.25F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(3, 6).addBox(-4.25F, -4.5F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(10, 2).addBox(-4.75F, -4.5F, -3.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F))
		.texOffs(2, 5).mirror().addBox(2.75F, -4.5F, -3.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(3, 6).addBox(-2.35F, -2.75F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(3, 6).addBox(-1.85F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(3, 6).mirror().addBox(2.25F, -4.5F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(3, 6).mirror().addBox(-0.15F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(3, 6).mirror().addBox(0.35F, -2.75F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(3, 6).mirror().addBox(1.25F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offset(0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
