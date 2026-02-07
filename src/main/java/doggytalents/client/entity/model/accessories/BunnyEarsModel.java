package doggytalents.client.entity.model.accessories;

import java.util.Optional;

import doggytalents.client.entity.model.SyncedAccessoryModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BunnyEarsModel extends SyncedAccessoryModel {

    public BunnyEarsModel(ModelPart root) {
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

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(6, 13).addBox(-7.15F, -10.45F, -4.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 0).addBox(-3.15F, -10.0F, -4.75F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(5.65F, 7.0F, 3.0F));

		PartDefinition head_r1 = bone3.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(6, 6).addBox(-0.5F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.95F, -9.9F, -3.75F, 0.0F, 0.0F, 0.1745F));

		PartDefinition bone = bone3.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(-7.4794F, -9.9277F, -3.8333F));

		PartDefinition leg5_r1 = bone.addOrReplaceChild("leg5_r1", CubeListBuilder.create().texOffs(4, 9).addBox(-2.5F, -8.9F, -5.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0794F, 2.4277F, 4.6833F, 0.0F, 0.0F, -0.192F));

		PartDefinition leg5_r2 = bone.addOrReplaceChild("leg5_r2", CubeListBuilder.create().texOffs(0, 7).addBox(-1.5F, -9.0F, -4.9F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2794F, 3.4277F, 4.4333F, 0.0F, 0.0F, -0.192F));

		PartDefinition leg4_r1 = bone.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -9.2F, -5.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.7794F, 3.4277F, 4.7333F, 0.0F, 0.0F, -0.192F));

		PartDefinition bone2 = bone3.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(-3.8206F, -9.9277F, -3.8333F));

		PartDefinition leg6_r1 = bone2.addOrReplaceChild("leg6_r1", CubeListBuilder.create().texOffs(4, 9).mirror().addBox(1.5F, -8.9F, -5.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0794F, 2.4277F, 4.6833F, 0.0F, 0.0F, 0.192F));

		PartDefinition leg6_r2 = bone2.addOrReplaceChild("leg6_r2", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.5F, -9.0F, -4.9F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.2794F, 3.4277F, 4.4333F, 0.0F, 0.0F, 0.192F));

		PartDefinition leg5_r3 = bone2.addOrReplaceChild("leg5_r3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.5F, -9.2F, -5.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.7794F, 3.4277F, 4.7333F, 0.0F, 0.0F, 0.192F));

		PartDefinition bone4 = real_head.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(6, 0).addBox(2.15F, -10.0F, -4.75F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(-5.65F, 7.0F, 3.0F));

		PartDefinition head_r2 = bone4.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(6, 6).addBox(-1.5F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.95F, -9.9F, -3.75F, 0.0F, 0.0F, -0.1745F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

}
