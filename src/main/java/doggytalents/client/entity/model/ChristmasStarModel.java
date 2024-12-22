package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ChristmasStarModel extends SyncedAccessoryModel {

    public ChristmasStarModel(ModelPart root) {
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

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-0.0622F, -5.7034F, 0.0F));

		PartDefinition head_r1 = bone3.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.77F)).mirror(false), PartPose.offsetAndRotation(0.2122F, -0.0466F, 0.0F, -1.5708F, 0.0F, -2.3736F));

		PartDefinition head_r2 = bone3.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0622F, 0.7034F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head_r3 = bone3.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F)).mirror(false), PartPose.offsetAndRotation(0.4122F, -1.0466F, 0.0F, -1.5708F, 0.0F, -1.5882F));

		PartDefinition head_r4 = bone3.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.78F)), PartPose.offsetAndRotation(-0.0878F, -0.0466F, 0.0F, -1.5708F, 0.0F, 2.3736F));

		PartDefinition head_r5 = bone3.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.66F)), PartPose.offsetAndRotation(-0.3878F, -0.7966F, 0.0F, -1.5708F, 0.0F, 0.3578F));

		PartDefinition head_r6 = bone3.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-0.2878F, -1.0466F, 0.0F, -1.5708F, 0.0F, 1.5882F));

		PartDefinition head_r7 = bone3.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.0622F, 0.2034F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head_r8 = bone3.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.65F)).mirror(false), PartPose.offsetAndRotation(0.5122F, -0.7966F, 0.0F, -1.5708F, 0.0F, -0.3578F));

		PartDefinition head_r9 = bone3.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(0.0622F, 1.2034F, 0.0F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}
