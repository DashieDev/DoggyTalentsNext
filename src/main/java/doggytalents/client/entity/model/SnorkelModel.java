package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SnorkelModel extends SyncedAccessoryModel{

    public SnorkelModel(ModelPart root) {
        super(root);
    }
    @Override
	protected void populatePart(ModelPart box) {
		this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
	}
    public static LayerDefinition createGlassesLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Snorkel = real_head.addOrReplaceChild("Snorkel", CubeListBuilder.create().texOffs(0, 5).addBox(-3.0F, -0.75F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-2.5F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-2.0F, -0.3F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-1.5F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-1.0F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(0.0F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-1.0F, -2.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-1.5F, -2.1F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-2.0F, -2.1F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-2.5F, -2.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.0F, -2.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.75F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.75F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.75F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.25F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.75F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.25F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.25F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.5F, -1.25F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.0F, -1.25F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.0F, -0.75F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(1.25F, 1.5F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(0, 5).addBox(1.75F, 1.5F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 5).addBox(2.25F, 1.25F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 5).addBox(2.25F, -4.25F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(7, 6).addBox(2.25F, -3.75F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 5).addBox(2.25F, -3.25F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 0).addBox(1.85F, -4.35F, -3.8F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-1.15F))
		.texOffs(7, 6).addBox(2.25F, -2.75F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 5).addBox(2.25F, -2.25F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(7, 6).addBox(2.25F, -1.75F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 5).addBox(2.25F, -1.25F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(7, 6).addBox(2.25F, 0.25F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 5).addBox(2.25F, -0.25F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(4, 5).addBox(2.25F, 0.75F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(7, 6).addBox(2.25F, -0.75F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.25F, 1.25F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.0F, -1.25F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(1.0F, -0.3F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(1.5F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.0F, -1.25F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.0F, -1.25F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.0F, -1.75F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-3.0F, -1.75F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(0.0F, -2.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(-0.5F, -1.8F, -2.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-0.5F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(0.0F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-1.0F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 7).addBox(-1.5F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-2.0F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-2.5F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(1.5F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(1.0F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 7).addBox(0.5F, -1.3F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-2.5F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 7).addBox(-2.0F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-1.5F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-1.0F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-0.5F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 7).addBox(0.0F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(0.5F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(1.0F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(1.5F, -0.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(1.5F, -1.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(1.0F, -1.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(0.5F, -1.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(0.0F, -1.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-0.5F, -1.8F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 7).addBox(-1.0F, -1.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-1.5F, -1.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-2.0F, -1.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 7).addBox(-2.5F, -1.8F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(1.0F, -2.1F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(1.5F, -2.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(0.5F, -2.1F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.0F, -2.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.75F))
		.texOffs(0, 5).addBox(2.5F, -1.75F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.75F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.75F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.75F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.25F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.25F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.25F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.25F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 5).addBox(2.5F, -1.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}
}
