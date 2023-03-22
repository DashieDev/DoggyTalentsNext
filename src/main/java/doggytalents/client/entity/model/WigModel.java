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

public class WigModel extends ListModel<Dog> {

    public ModelPart pHead;
    public ModelPart wig;

	public WigModel(ModelPart root) {
        this.pHead = root.getChild("phead");
        this.wig = pHead.getChild("pwig");
	}

	public static LayerDefinition createWigLayerDefinition() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("phead", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));

        var pwig = head.addOrReplaceChild("pwig", CubeListBuilder.create(), PartPose.ZERO);

        pwig.addOrReplaceChild("wi", CubeListBuilder.create().texOffs(0, 0).addBox(2.75F, -12.25F, -9.35F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.75F, -8.75F, -9.1F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(3.0F, -11.3F, -9.1F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(3.0F, -9.3F, -9.1F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(3.0F, -10.3F, -9.1F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -12.25F, -9.35F, 2.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.5F, -11.3F, -9.1F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.5F, -9.3F, -9.1F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.75F, -8.75F, -9.1F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.75F, -9.3F, -9.1F, 0.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.85F, -10.3F, -9.1F, 1.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.5F, -13.0F, -9.6F, 7.0F, -1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.25F, -14.5F, -9.1F, 5.0F, -1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -12.75F, -9.35F, 6.0F, -2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 11.05F, 7.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(pHead);
    }

    @Override
    public void  prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        
    }

    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
         
    }
    
}
