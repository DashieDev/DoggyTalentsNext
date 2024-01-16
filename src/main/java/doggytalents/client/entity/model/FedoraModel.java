package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FedoraModel extends SyncedAccessoryModel {

    public FedoraModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
		this.realHead = Optional.of(head.get().getChild("real_head"));
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition fedora = real_head.addOrReplaceChild("fedora", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0272F, -3.1713F, -0.4835F, 0.0701F, 0.0871F, 0.0061F));

		PartDefinition head_r1 = fedora.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(42, 25).addBox(-2.4F, -0.15F, -3.4F, 5.0F, 1.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(2.4728F, 0.7713F, 0.2335F, 0.0F, 0.0F, -0.0349F));

		PartDefinition head_r2 = fedora.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(42, 14).addBox(-2.9F, -0.85F, -3.0F, 7.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.7772F, 1.0213F, 0.2335F, 0.0433F, 0.0438F, -0.0049F));

		PartDefinition head_r3 = fedora.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(37, 26).addBox(-2.5F, -1.45F, -2.75F, 6.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F))
		.texOffs(36, 25).addBox(-4.5F, -0.5F, -3.45F, 8.0F, 1.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.7772F, 1.0213F, 0.2335F, 0.0873F, 0.0435F, 0.0038F));

		PartDefinition head_r4 = fedora.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(37, 25).addBox(-3.25F, -1.25F, -3.0F, 6.0F, 2.0F, 5.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-0.0272F, 0.4713F, 0.4835F, -0.0698F, 0.0F, 0.0611F));

		PartDefinition head_r5 = fedora.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(44, 25).addBox(-1.0F, -1.2F, -1.8F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.9728F, -0.8287F, 0.2335F, 0.0F, 0.0F, 0.1047F));

		PartDefinition head_r6 = fedora.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(49, 26).addBox(-1.7F, -1.15F, -2.1F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0272F, -1.3287F, 0.2335F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r7 = fedora.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(44, 26).addBox(-3.25F, -1.5F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(44, 26).addBox(-3.0F, -1.0F, -2.25F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.0272F, -0.0787F, 0.2335F, 0.0F, 0.0F, 0.0436F));

		return LayerDefinition.create(meshdefinition, 64, 32);
    }
    
}
