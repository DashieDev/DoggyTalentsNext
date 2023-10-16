package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PlagueDoctorMaskModel extends SyncedAccessoryModel{

    public PlagueDoctorMaskModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
		this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mask = real_head.addOrReplaceChild("mask", CubeListBuilder.create().texOffs(44, 26).addBox(-3.0F, -3.25F, -3.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(58, 48).addBox(-3.25F, -3.25F, -3.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(58, 48).addBox(-3.25F, -0.25F, -3.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(60, 46).addBox(-1.25F, -2.75F, -3.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(60, 46).addBox(-4.0F, -2.5F, -3.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(58, 48).mirror().addBox(1.25F, -0.25F, -3.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(60, 46).mirror().addBox(3.0F, -2.5F, -3.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(58, 48).mirror().addBox(1.25F, -3.25F, -3.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(60, 46).mirror().addBox(0.25F, -2.75F, -3.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(-2, 30).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 1.0F, 9.0F, new CubeDeformation(-0.1F))
		.texOffs(44, 36).addBox(-2.5F, -5.75F, -4.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.15F))
		.texOffs(46, 17).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(48, 18).addBox(-1.5F, -0.02F, -5.1F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition head_r1 = mask.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(49, 19).addBox(-1.5F, -1.5308F, -2.3362F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.0F, 2.968F, -9.4354F, 0.7854F, 0.0F, 0.0F));

		PartDefinition head_r2 = mask.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(48, 18).addBox(-1.5F, -1.6736F, -2.7348F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 2.218F, -7.6854F, 0.48F, 0.0F, 0.0F));

		PartDefinition head_r3 = mask.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(48, 18).addBox(-1.5F, -1.5F, -3.25F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.48F, -4.5F, 0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
