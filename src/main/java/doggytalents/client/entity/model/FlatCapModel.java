package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FlatCapModel extends SyncedAccessoryModel {

    public FlatCapModel(ModelPart root) {
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

		PartDefinition flat_cap = real_head.addOrReplaceChild("flat_cap", CubeListBuilder.create().texOffs(43, 26).addBox(-3.25F, -0.6479F, -2.0033F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F))
		.texOffs(48, 27).addBox(-3.45F, -0.3479F, -0.3033F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(48, 27).addBox(-3.45F, -0.6479F, -1.6033F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F))
		.texOffs(48, 27).mirror().addBox(0.45F, -0.4479F, -0.4033F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(48, 27).mirror().addBox(2.45F, -0.6479F, -1.7033F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(43, 26).mirror().addBox(0.25F, -0.6479F, -2.0033F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(44, 27).addBox(-3.0F, -1.2979F, -1.4033F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(44, 27).addBox(-3.0F, -0.3479F, -2.2533F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -2.1521F, -1.0467F, 0.0436F, 0.0F, 0.0F));

		PartDefinition head_r1 = flat_cap.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(44, 27).addBox(-3.0F, -0.5F, -2.3F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4979F, -1.0033F, 0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
    }

}
