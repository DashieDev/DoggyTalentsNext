package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ChefHatModel extends SyncedAccessoryModel{

    public ChefHatModel(ModelPart root) {
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

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -5.75F, 1.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition cube_r1 = bone3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(47, 15).addBox(-1.75F, -4.25F, -1.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.4F))
		.texOffs(47, 15).addBox(-1.75F, -10.5F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.75F, 5.75F, -1.0F, 0.0F, 0.0F, -0.2618F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
