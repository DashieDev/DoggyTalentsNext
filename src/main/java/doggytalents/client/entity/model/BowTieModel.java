package doggytalents.client.entity.model;

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

public class BowTieModel extends ListModel<Dog> {

    public ModelPart bowTie;

    public BowTieModel(ModelPart part) {
        
        this.bowTie = part
            .getChild("bowtie");

    }

    public static LayerDefinition createBowtieLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// PartDefinition mane = partdefinition.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, 2.0F));

		// PartDefinition mane_rotation = mane.addOrReplaceChild("mane_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 2.5F, -2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bowtie = partdefinition.addOrReplaceChild("bowtie", CubeListBuilder.create().texOffs(3, 1).addBox(-0.5F, -7.5F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 1).addBox(-1.5F, -8.25F, -7.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(1, 1).addBox(0.5F, -8.25F, -7.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 12.5F, -1.5F));

		return LayerDefinition.create(meshdefinition, 16, 16);
    }

    
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.bowTie);
    }

    @Override
    public void  prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        if (dogIn.isInSittingPose()) {
            if (dogIn.isLying()) {
                //this.bowTie.setPos(-1F, 20F, -2F);
                this.bowTie.xRot = (float) (Math.PI / 2);
            }
            else  {
                this.bowTie.setPos(-0.0F, 11.5F, 0F);
                this.bowTie.xRot = (float) (Math.PI / 2);
            }
        }
        else {
            this.bowTie.setPos(-0.0F, 11.5F, 1F);
            this.bowTie.xRot = (float) (Math.PI / 2);
        }
    }

    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
