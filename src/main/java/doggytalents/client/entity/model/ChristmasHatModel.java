package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ChristmasHatModel extends SyncedAccessoryModel {

    public ChristmasHatModel(ModelPart root) {
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

		PartDefinition bone2 = real_head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 6).addBox(-3.0F, -13.25F, -9.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.4F))
		.texOffs(0, 0).addBox(-3.0F, -15.0F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(14, 11).addBox(-2.0F, -15.75F, -8.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.3F))
		.texOffs(14, 11).addBox(-1.5F, -16.75F, -8.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(14, 11).addBox(-0.75F, -17.5F, -8.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(-0.15F))
		.texOffs(1, 12).addBox(0.25F, -18.25F, -8.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(15, 16).addBox(3.25F, -18.25F, -8.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.75F, 7.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

}
