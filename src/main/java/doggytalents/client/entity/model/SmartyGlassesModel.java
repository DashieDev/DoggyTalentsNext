package doggytalents.client.entity.model;

import java.util.Optional;

import com.google.common.collect.ImmutableList;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SmartyGlassesModel extends SyncedAccessoryModel {

	public SmartyGlassesModel(ModelPart root) {
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
    	var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, 13.5F, -7F));
		
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.ZERO);
		real_head.addOrReplaceChild("glasses", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -0.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.5F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.0F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-0.5F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -1.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-0.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -2.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -2.0F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -2.0F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -1.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -0.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.5F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(2.0F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(2.5F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -1.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.0F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(2.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(2.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.75F))
		.texOffs(0, 0).addBox(3.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -2.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -2.0F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -2.0F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -1.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(-1, 0, 0));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}
}