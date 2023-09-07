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

public class DeathModel extends DogModel<Dog> {
    
    public DeathModel(ModelPart box) {
        super(box);
    }

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F) );

		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(13, 7).addBox(-1.5F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(13, 7).addBox(2.5F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 10).addBox(-0.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(12, 4).addBox(4.0F, -0.75F, -2.0F, 1.0F, 3.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(12, 6).addBox(4.0F, -0.75F, -1.75F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(13, 5).addBox(-3.0F, -0.75F, -2.0F, 1.0F, 3.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(12, 5).addBox(-4.0F, -0.75F, -1.75F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(10, 6).addBox(-4.75F, -1.75F, -1.75F, 3.0F, 1.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(10, 7).addBox(-3.25F, -2.5F, -1.5F, 3.0F, 1.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(10, 7).addBox(2.25F, -2.5F, -1.5F, 3.0F, 1.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(10, 6).addBox(-1.5F, -3.5F, -1.5F, 5.0F, 1.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(28, 8).addBox(-1.5F, 2.5F, -1.5F, 5.0F, 1.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(9, 6).addBox(3.5F, -1.75F, -1.75F, 3.0F, 1.0F, 2.0F, CubeDeformation.NONE), PartPose.ZERO);

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		upper_body.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(44, 24).addBox(-4.75F, -7.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-5.0F, -6.75F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(2.75F, -7.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(3.0F, -6.7F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(2.75F, -6.25F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-5.0F, -6.25F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(2.75F, -6.0F, 2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-5.0F, -6.0F, 2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(2.75F, -6.25F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-5.0F, -6.25F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-3.5F, -7.75F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(1.5F, -7.75F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(1.25F, -6.5F, -2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-2.25F, -7.25F, -2.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).mirror().addBox(0.25F, -7.25F, -2.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(44, 24).addBox(-3.25F, -6.5F, -2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-3.25F, -6.5F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(44, 24).addBox(-1.75F, -6.25F, 5.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(-1.5F, -7.5F, 4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(-0.5F, -7.5F, 4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(-0.25F, -6.25F, 5.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(44, 24).addBox(1.25F, -6.5F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(44, 24).addBox(2.0F, -6.0F, 4.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(-4.25F, -6.0F, 4.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(44, 24).addBox(2.1F, -6.9F, 4.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(-4.4F, -6.9F, 4.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(3.25F, -7.25F, 3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(3.25F, -7.0F, 2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).addBox(3.25F, -6.95F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).mirror().addBox(-5.5F, -6.75F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).mirror().addBox(-5.3F, -6.75F, 0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).addBox(3.45F, -6.95F, 0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).mirror().addBox(-5.5F, -7.25F, 3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).mirror().addBox(-4.75F, -7.25F, 4.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(44, 24).addBox(2.75F, -7.25F, 4.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(44, 24).mirror().addBox(-5.5F, -7.0F, 2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offset(1F, 2.75F, -1.25F));

		CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
        partdefinition.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-2.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(0.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-2.5F, 16.0F, -4.0F));
        partdefinition.addOrReplaceChild("left_front_leg", var4, PartPose.offset(0.5F, 16.0F, -4.0F));
        
		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 1.8326F, 0.0F, 0.0F));

        PartDefinition real_tail_1 = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(56, 0).addBox(0.0F, 2.0F, 1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(56, 0).addBox(0.0F, 5.0F, 2.0F, 2.0F, 3.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(56, 0).addBox(0.0F, 4.0F, 4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

}
