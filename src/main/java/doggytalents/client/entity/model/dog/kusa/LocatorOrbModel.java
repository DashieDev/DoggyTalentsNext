package doggytalents.client.entity.model.dog.kusa;

import java.util.Optional;

import doggytalents.client.entity.model.SyncedAccessoryModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class LocatorOrbModel extends SyncedAccessoryModel {

    public LocatorOrbModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.mane = Optional.of(box.getChild("upper_body"));
    }

    public static LayerDefinition createOrbLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        upper_body.addOrReplaceChild("scarf", CubeListBuilder.create().texOffs(0, 43).addBox(-4.0F, -13.0F, -1.0F, 8.0F, 13.0F, 7.0F, new CubeDeformation(0.5F))
		.texOffs(28, 27).addBox(-4.0F, -13.2F, -7.0F, 8.0F, 6.0F, 10.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 38).addBox(-1.0F, -14.0F, -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), 
        PartPose.offset(1.0F, 10F, -2F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    
}
