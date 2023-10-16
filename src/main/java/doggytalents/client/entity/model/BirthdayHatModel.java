package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BirthdayHatModel extends SyncedAccessoryModel{

    public BirthdayHatModel(ModelPart root) {
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

		PartDefinition bone = real_head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(36, 59).addBox(0.0F, 0.58F, -7.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(37, 55).addBox(-0.5F, 0.73F, -8.7F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.65F)), PartPose.offset(0.25F, 1.0F, 0.0F));

		PartDefinition head_r1 = bone.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(38, 56).addBox(-1.0F, -0.0393F, -1.0001F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.5F, 0.547F, -8.3429F, 2.9671F, 0.0F, 0.0F));

		PartDefinition head_r2 = bone.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(38, 56).addBox(-1.0F, -0.29F, -0.8255F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.5F, 0.547F, -8.3429F, 0.8727F, 0.0F, 0.0F));

		PartDefinition head_r3 = bone.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(38, 56).addBox(-1.0F, -0.1934F, -1.0639F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.5F, 0.547F, -8.3429F, -1.5272F, 0.0F, 0.0F));

		PartDefinition head_r4 = bone.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(38, 56).addBox(-1.0F, -0.067F, -0.7071F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.5F, 0.547F, -8.3429F, -0.5236F, 0.0F, 0.0F));

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.25F, 0.5F, 0.0F));

		PartDefinition cube_r1 = bone3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(54, 5).addBox(-0.75F, -9.75F, 0.4F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F))
		.texOffs(54, 5).addBox(-0.75F, -9.75F, -0.95F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F))
		.texOffs(53, 4).addBox(-0.75F, -10.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(53, 4).addBox(-1.0F, -9.75F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(53, 4).addBox(-0.5F, -9.75F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(53, 4).addBox(-0.75F, -9.75F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(53, 10).addBox(-0.75F, -9.0F, -0.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(48, 17).addBox(-1.75F, -9.0F, -1.75F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-1.35F))
		.texOffs(48, 26).addBox(-1.75F, -8.0F, -1.75F, 4.0F, 3.0F, 4.0F, new CubeDeformation(-1.0F))
		.texOffs(48, 33).addBox(-1.75F, -7.25F, -1.75F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.75F))
		.texOffs(48, 39).addBox(-1.75F, -6.5F, -1.75F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F))
		.texOffs(48, 49).addBox(-1.75F, -5.25F, -1.75F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(48, 58).addBox(-1.75F, -4.0F, -1.75F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
    
}
