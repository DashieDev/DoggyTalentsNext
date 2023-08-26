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

public class BeastarsUniformMaleAugmentModel extends SyncedAccessoryModel {

    public BeastarsUniformMaleAugmentModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.mane = Optional.of(box.getChild("upper_body"));
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        
        //1 2.5 -2.5
        upper_body.addOrReplaceChild("tie", CubeListBuilder.create().texOffs(54, 13).mirror().addBox(-0.5F, -1.0F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(54, 13).mirror().addBox(-0.5F, -2.25F, -0.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(54, 13).mirror().addBox(-0.5F, -3.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(54, 13).mirror().addBox(-0.5F, -5.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(1, 2.5f, -2.5f));
		
        upper_body.addOrReplaceChild("uniform_fluff", CubeListBuilder.create()
		.texOffs(54, 11).addBox(-3.0F, -6.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(54, 11).addBox(-4.0F, -5.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(54, 11).addBox(-4.5F, -5.25F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(54, 11).mirror().addBox(2.0F, -6.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(54, 11).mirror().addBox(3.0F, -5.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(54, 11).mirror().addBox(3.5F, -5.25F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(54, 11).addBox(2.0F, -6.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(54, 11).addBox(-3.0F, -6.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(1f, 2.5f, -2.5f));
		

		return LayerDefinition.create(meshdefinition, 64, 32);
    }
    
}
