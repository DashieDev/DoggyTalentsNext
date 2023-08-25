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

public class SmartyGlassesModel extends ListModel<Dog> {

	public ModelPart root;
    public ModelPart pHead;
    public ModelPart glasses;

	public SmartyGlassesModel(ModelPart root) {
		this.root = root;
		this.pHead = root.getChild("glasses");
        this.glasses = pHead.getChild("real_glasses");
	}

	public static LayerDefinition createGlassesLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition glasses = partdefinition.addOrReplaceChild("glasses", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7F));

		glasses.addOrReplaceChild("real_glasses", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -0.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.5F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.0F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-0.5F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -1.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-0.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -2.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -2.0F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -2.0F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -1.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.5F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -0.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.5F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(2.0F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(2.5F, -0.5F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -1.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -1.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.0F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.0F, -1.75F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(2.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(2.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.0F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.75F))
		.texOffs(0, 0).addBox(3.5F, -2.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -2.0F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -2.0F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -2.0F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -1.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(3.5F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.root);
    }

    @Override
    public void  prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        
    }

    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
         
    }
}