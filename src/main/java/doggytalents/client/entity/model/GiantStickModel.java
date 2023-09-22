package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GiantStickModel extends SyncedAccessoryModel{

    public GiantStickModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.body = Optional.of(box.getChild("body"));
    }
    	public static LayerDefinition createGiantStickLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -25.75F, -2.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 14.75F, 2.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

}
