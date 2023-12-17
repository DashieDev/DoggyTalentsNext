package doggytalents.client.entity.model.dog;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class StBernardModel extends DogModel {

    public StBernardModel(ModelPart box) {
        super(box);

    }   

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, 13.5F, -7.0F));
		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -3.65F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(36, 13).mirror().addBox(-1.75F, 0.48F, -4.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(36, 13).addBox(0.75F, 0.48F, -4.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        PartDefinition bone = real_head.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(15, 15).mirror().addBox(1.75F, -13.75F, -9.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(2.7F, -13.15F, -9.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(3.1F, -13.1F, -9.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(3.4F, -12.7F, -9.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(0F, 10.5F, 7.0F));

		PartDefinition bone2 = real_head.addOrReplaceChild("ear1", CubeListBuilder.create().texOffs(15, 15).addBox(-3.75F, -13.75F, -9.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 12).addBox(-3.7F, -13.15F, -9.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(16, 12).addBox(-4.1F, -13.1F, -9.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-4.4F, -12.7F, -9.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(0F, 10.5F, 7.0F));

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
        partdefinition.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-1.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(1.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-1.5F, 16.0F, -4.0F));
        partdefinition.addOrReplaceChild("left_front_leg", var4, PartPose.offset(1.5F, 16.0F, -4.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 12.0F, 8.0F, 1.8326F, 0.0F, 0.0F));

        PartDefinition real_tail_1 = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(56, 0).addBox(-1.0F, 2.0F, 1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(56, 0).addBox(-1.0F, 5.0F, 2.0F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(56, 0).addBox(-1.0F, 4.0F, 4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        
		return LayerDefinition.create(meshdefinition, 64, 32);
    }
    
}
