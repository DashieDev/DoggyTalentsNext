package doggytalents.client.entity.model.dog.kusa;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class ShinModel extends DogModel<Dog> {

    public ShinModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

		var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), 
            PartPose.offset(-1.0F, 13.5F, -7.0F));
        var real_head = head.addOrReplaceChild("real_head", 
        CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
            .texOffs(23, 0).addBox(-2.0F, -2.95F, -2.5F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.35F))
            .texOffs(23, 0).addBox(-2.0F, -3.35F, -2.4F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.45F))
            .texOffs(23, 0).addBox(-2.0F, -3.55F, -2.0F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.45F))
            .texOffs(0, 0).addBox(1.8F, -4.55F, -0.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F))
            .texOffs(0, 0).addBox(1.6F, -5.15F, -0.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
            .texOffs(0, 0).addBox(1.4F, -5.85F, -0.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F))
            .texOffs(0, 0).mirror().addBox(-1.5F, -5.85F, -0.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
            .texOffs(0, 0).mirror().addBox(-1.7F, -5.15F, -0.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
            .texOffs(0, 0).mirror().addBox(-1.8F, -4.55F, -0.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F)).mirror(false)
            .texOffs(30, 5).addBox(-0.75F, 0.08F, -4.25F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
            .texOffs(52, 3).addBox(1.75F, 0.08F, -4.25F, 1.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
            .texOffs(42, 0).addBox(-0.5F, -0.22F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F)),
        PartPose.ZERO);
		
        var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
		upper_body.addOrReplaceChild("scarf", CubeListBuilder.create().texOffs(0, 44).addBox(-4.0F, -13.0F, -1.0F, 8.0F, 13.0F, 7.0F, new CubeDeformation(0.1F))
		.texOffs(28, 28).addBox(-4.0F, -13.2F, -7.0F, 8.0F, 6.0F, 10.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 39).addBox(-1.0F, -14.0F, -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), 
        PartPose.offset(1.0F, 10F, -2F));

		CubeListBuilder var4 = CubeListBuilder.create().texOffs(8, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE);
        CubeListBuilder var4_1 = CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE);
        partdefinition.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-2.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(0.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("right_front_leg", var4_1, PartPose.offset(-2.5F, 16.0F, -4.0F));
        partdefinition.addOrReplaceChild("left_front_leg", var4_1, PartPose.offset(0.5F, 16.0F, -4.0F));
		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 13.5F, 8.5f, 1.8326F, 0.0F, 0.0F));

		tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(16, 31).addBox(0.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        
		tail.addOrReplaceChild("real_tail_2", CubeListBuilder.create(), PartPose.ZERO);
        
        tail.addOrReplaceChild("real_tail_bushy", CubeListBuilder.create(), PartPose.ZERO);

		real_head.addOrReplaceChild("ear_normal", CubeListBuilder.create(),PartPose.ZERO);
        real_head.addOrReplaceChild("ear_boni", CubeListBuilder.create(),PartPose.ZERO);
    	real_head.addOrReplaceChild("ear_small", CubeListBuilder.create(),PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);

    }

	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }
}
