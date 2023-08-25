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

public class BeastarsUniformFemaleAugmentModel extends ListModel<Dog> {
    
	public ModelPart root;
    public ModelPart pMane;
    public ModelPart pBody;
    public ModelPart pLFLeg;
    public ModelPart pRFLeg;

    public BeastarsUniformFemaleAugmentModel(ModelPart part) {
		this.root = part;
        this.pBody = part.getChild("body");
        this.pMane = part.getChild("upper_body");
        this.pLFLeg = part.getChild("pLFLeg");
        this.pRFLeg = part.getChild("pRFLeg");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

        var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(39, 15).addBox(-3.0F, -3.0F, 2.75F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(39, 14).addBox(-3.0F, -2.5F, 1.75F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(39, 14).addBox(-3.1F, -3.5F, 1.75F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(39, 14).addBox(-3.1F, -4.0F, -0.25F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-3.3F, -3.7F, -4.25F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(39, 14).addBox(-3.2F, -2.3F, -0.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-3.0F, -1.8F, -4.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-3.3F, -6.7F, -4.25F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-3.0F, -4.8F, -4.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 28).addBox(-2.9F, -4.95F, -2.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.3F))
		.texOffs(0, 28).addBox(-3.1F, -4.0F, -2.25F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-3.0F, -2.2F, -2.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-3.1F, -7.0F, -2.25F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -2.0F));

        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		upper_body.addOrReplaceChild("collarscarf", CubeListBuilder.create().texOffs(55, 11).addBox(-3.0F, -6.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(55, 11).addBox(-4.0F, -5.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(55, 11).addBox(-4.25F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(55, 11).mirror().addBox(3.25F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(55, 11).addBox(-4.5F, -5.25F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(55, 11).mirror().addBox(2.0F, -6.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(55, 11).mirror().addBox(-0.5F, -5.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(55, 11).mirror().addBox(-0.5F, -4.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(55, 13).mirror().addBox(-1.25F, -3.5F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(55, 13).mirror().addBox(-1.45F, -2.25F, -0.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(55, 13).mirror().addBox(-1.35F, -1.2F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(55, 13).mirror().addBox(-1.1F, -0.3F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(55, 11).mirror().addBox(-1.25F, -3.5F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(55, 11).mirror().addBox(-1.45F, -2.25F, -0.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(55, 11).mirror().addBox(-1.35F, -1.2F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(55, 11).mirror().addBox(-1.1F, -0.3F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(55, 11).addBox(0.1F, -0.3F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(55, 11).addBox(0.35F, -1.2F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(55, 11).addBox(0.45F, -2.25F, -0.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(55, 11).addBox(0.25F, -3.5F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(55, 11).mirror().addBox(3.0F, -5.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(55, 11).mirror().addBox(3.5F, -5.25F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(55, 11).addBox(2.0F, -6.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(55, 11).addBox(-3.0F, -6.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(1f, 2.5f, -2.5f));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("pLFLeg", CubeListBuilder.create().texOffs(0, 18), PartPose.offset(-2.5F, 16.0F, -4.0F));
		PartDefinition leg4 = partdefinition.addOrReplaceChild("pRFLeg", CubeListBuilder.create().texOffs(0, 18), PartPose.offset(0.5F, 16.0F, -4.0F));
        var blouse = CubeListBuilder.create()
            .texOffs(56, 2).addBox(0.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.35F));

        leg3.addOrReplaceChild("blouse", blouse, PartPose.ZERO);
        leg4.addOrReplaceChild("blouse", blouse, PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 32);
    }

    
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.root);
    }

    @Override
    public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
    }

    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

}
