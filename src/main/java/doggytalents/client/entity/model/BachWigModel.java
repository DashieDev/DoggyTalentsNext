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

public class BachWigModel extends SyncedAccessoryModel {

	public BachWigModel(ModelPart root) {
		super(root);
	}

	@Override
	protected void populatePart(ModelPart box) {
		this.head = Optional.of(box.getChild("phead"));
		this.realHead = Optional.of(head.get().getChild("pwig"));
	}

	public static LayerDefinition createWigLayerDefinition() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("phead", CubeListBuilder.create(), PartPose.offset(0F, 13.5F, -7.0F));

        var pwig = head.addOrReplaceChild("pwig", CubeListBuilder.create(), PartPose.ZERO);

        pwig.addOrReplaceChild("wi", CubeListBuilder.create().texOffs(0, 36).addBox(0.0F, -4.9F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(0, 36).addBox(-2.0F, -4.8F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-4.1F, -2.8F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-4.0F, -0.8F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(4.0F, -2.8F, -1.65F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(2.0F, -4.8F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(0.0F, -4.9F, -2.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 41).addBox(2.0F, -4.8F, -2.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 41).addBox(-2.0F, -4.8F, -2.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 41).addBox(3.1F, -4.3F, -1.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(9, 41).addBox(4.1F, -2.8F, -2.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 41).addBox(4.3F, -2.8F, -1.6F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 41).addBox(4.4F, -0.8F, -1.8F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 41).addBox(4.1F, -0.8F, -2.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 41).addBox(4.2F, 1.2F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 41).addBox(3.8F, 1.2F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 41).mirror().addBox(-4.2F, 1.2F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(9, 41).mirror().addBox(-3.8F, 1.2F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(9, 41).mirror().addBox(-4.1F, -0.8F, -2.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(9, 41).mirror().addBox(-4.4F, -0.8F, -1.8F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(9, 41).mirror().addBox(-4.5F, -2.8F, -1.6F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(9, 41).mirror().addBox(-4.1F, -2.8F, -2.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(0, 41).addBox(3.6F, -4.3F, -0.6F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 41).mirror().addBox(-3.6F, -4.3F, -0.6F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 41).mirror().addBox(-3.1F, -4.3F, -1.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 36).addBox(4.0F, -0.8F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(3.75F, -3.3F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(3.75F, -1.3F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(2.0F, -5.6F, -0.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 41).addBox(0.0F, -5.8F, -0.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.3F))
		.texOffs(9, 34).addBox(3.25F, -5.05F, -0.9F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(9, 34).mirror().addBox(-3.25F, -5.05F, -0.9F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 41).addBox(-2.0F, -5.6F, -0.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 41).addBox(0.0F, -5.8F, -0.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 41).addBox(2.0F, -5.6F, -0.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 41).addBox(-2.0F, -5.6F, -0.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 36).addBox(-3.75F, -3.3F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-3.55F, -3.3F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.3F))
		.texOffs(0, 36).mirror().addBox(3.55F, -3.3F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(0, 41).addBox(-3.75F, -1.3F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(3.9F, 0.2F, -1.55F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(3.65F, -0.3F, 0.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(-4.1F, 0.6F, -0.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).mirror().addBox(4.1F, 0.6F, -0.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 36).addBox(-3.8F, 0.2F, -1.55F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-3.55F, -0.3F, 0.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-3.85F, 1.9F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).mirror().addBox(3.85F, 1.9F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
