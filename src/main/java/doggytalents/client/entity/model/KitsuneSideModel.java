package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class KitsuneSideModel extends SyncedAccessoryModel{

    public KitsuneSideModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
		this.head = Optional.of(box.getChild("head"));
		this.realHead = Optional.of(head.get().getChild("real_head"));
    }
    public static LayerDefinition createKitsuneSideLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone = real_head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(49, 17).addBox(-2.5F, -1.4594F, -0.0437F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.4F))
		.texOffs(54, 29).addBox(-1.5F, 3.5206F, -2.0437F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(3.5F, -2.9509F, -0.7637F, -0.3927F, -1.1345F, 0.0F));

		PartDefinition head_r1 = bone.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(47, 25).addBox(-1.5F, -0.75F, -1.25F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 3.0206F, -1.5437F, 0.2618F, 0.0F, 0.0F));

		PartDefinition head_r2 = bone.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(44, 17).mirror().addBox(-0.85F, -0.8F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.405F)).mirror(false), PartPose.offsetAndRotation(2.0F, -3.0094F, 0.4563F, 0.0F, 0.0F, -0.2182F));

		PartDefinition head_r3 = bone.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(44, 21).mirror().addBox(-1.1F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.401F)).mirror(false), PartPose.offsetAndRotation(2.1F, -3.0094F, 0.4563F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r4 = bone.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(44, 21).addBox(0.1F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.401F)), PartPose.offsetAndRotation(-2.1F, -3.0094F, 0.4563F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head_r5 = bone.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(44, 17).addBox(-0.15F, -0.8F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.405F)), PartPose.offsetAndRotation(-2.0F, -3.0094F, 0.4563F, 0.0F, 0.0F, 0.2182F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
