package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DemonHornsModel extends SyncedAccessoryModel{

    public DemonHornsModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
		this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
		this.tail = Optional.of(box.getChild("tail"));
        this.realTail = Optional.of(tail.get().getChild("real_tail"));
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 12.0F, 8.0F, 2.0944F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(56, 7).addBox(-1F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(60, 23).addBox(-1.5F, 8.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F))
		.texOffs(60, 23).addBox(-1.5F, 9.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(60, 23).addBox(-1.5F, 10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(46, 24).addBox(-3.0F, -13.5F, -8.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 10.5F, 7.0F));

		PartDefinition head_r1 = bone3.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(60, 25).mirror().addBox(-0.6236F, -1.1192F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-3.531F, -14.1516F, -7.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r2 = bone3.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(60, 25).mirror().addBox(-1.5F, -2.0F, -1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.16F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -13.25F, -6.5F, 0.0F, 0.0F, -0.9599F));

		PartDefinition head_r3 = bone3.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(60, 25).mirror().addBox(-1.1236F, -2.3692F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-3.531F, -14.1516F, -7.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition head_r4 = bone3.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(60, 25).addBox(0.1236F, -2.3692F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(3.531F, -14.1516F, -7.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition head_r5 = bone3.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(60, 25).addBox(-0.3764F, -1.1192F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(3.531F, -14.1516F, -7.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r6 = bone3.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(60, 25).addBox(0.5F, -2.0F, -1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.16F)), PartPose.offsetAndRotation(1.5F, -13.25F, -6.5F, 0.0F, 0.0F, 0.9599F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
