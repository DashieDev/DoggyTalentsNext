package doggytalents.client.entity.model.dog;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class IwankoModel extends DogModel<Dog> {

    public IwankoModel(ModelPart box) {
        super(box);
    }

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F) );

		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F , CubeDeformation.NONE)
		.texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 10).addBox(-0.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.ZERO);
        

        var ear_normal = real_head.addOrReplaceChild("ear_normal", CubeListBuilder.create(),PartPose.ZERO);
        var ear_boni = real_head.addOrReplaceChild("ear_boni", CubeListBuilder.create(),PartPose.ZERO);
        var ear_small = real_head.addOrReplaceChild("ear_small", CubeListBuilder.create(),PartPose.ZERO);

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		//PartDefinition mane = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, 2.0F));

        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		upper_body.addOrReplaceChild("rock_ring", CubeListBuilder.create()
		.texOffs(2, 34).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 2.0F, 7.0F, new CubeDeformation(0.15F))
		.texOffs(0, 35).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 35).addBox(-1.0F, -6.0F, 6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 35).addBox(1.75F, -6.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)) 
		.texOffs(0, 35).addBox(3.25F, -6.0F, -0.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 35).addBox(3.25F, -6.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 35).addBox(3.25F, -6.0F, 4.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 35).addBox(-5.25F, -6.0F, 4.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 35).addBox(-5.25F, -6.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 35).addBox(-5.25F, -6.0F, -0.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 35).addBox(-3.75F, -6.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 35).addBox(-3.75F, -6.0F, 6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 35).addBox(1.75F, -6.0F, 6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offset(1f, 2.5f, -2.5f));

		PartDefinition leg1 = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition leg2 = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition leg3 = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition leg4 = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE), PartPose.offset(0.5F, 16.0F, -4.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 13.5F, 8.5f, 1.8326F, 0.0F, 0.0F));

        PartDefinition real_tail_1 = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(56, 0).addBox(0.0F, 2.0F, 1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(56, 0).addBox(0.0F, 5.0F, 2.0F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(56, 0).addBox(0.0F, 4.0F, 4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        
        tail.addOrReplaceChild("real_tail_2", CubeListBuilder.create(), PartPose.ZERO);
        
        tail.addOrReplaceChild("real_tail_bushy", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    @Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

}
