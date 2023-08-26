package doggytalents.client.entity.model;

import java.util.Optional;

import com.google.common.collect.ImmutableList;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BowTieModel extends SyncedAccessoryModel {

    public BowTieModel(ModelPart part) {
        super(part);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.mane = Optional.of(box.getChild("upper_body"));
    }

    public static LayerDefinition createBowtieLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        
        //1 2.5 -2.5
		upper_body.addOrReplaceChild("bowtie", CubeListBuilder.create().texOffs(3, 1).addBox(-0.5F, -7.5F, -6.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(1, 1).addBox(-1.5F, -8.25F, -7.0F, 1.0F, 2.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(1, 1).addBox(0.5F, -8.25F, -7.0F, 1.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(1.0F, 4F, 3F));

		return LayerDefinition.create(meshdefinition, 16, 16);
    }
}
