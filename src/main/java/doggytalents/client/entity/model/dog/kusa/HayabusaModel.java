package doggytalents.client.entity.model.dog.kusa;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class HayabusaModel extends DogModel<Dog> {

    public HayabusaModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

		var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), 
            PartPose.offset(-1.0F, 13.5F, -7.0F));
        var real_head = head.addOrReplaceChild("real_head", 
            CubeListBuilder.create()
            .texOffs(24, 13).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
		    .texOffs(23, 0).addBox(-0.5F, -0.07F, -4.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.3F)),
        PartPose.ZERO);
		var bone = real_head.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(2.6753F, -3.65F, -0.2839F));
		bone.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 3).addBox(-0.1F, -1.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 3).addBox(0.2F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 3).addBox(-0.4F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(18, 13).addBox(-1.5F, -1.2F, -0.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F))
		.texOffs(0, 0).addBox(-0.7F, -0.3F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6545F, 0.0F));
		var bone2 = real_head.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(-0.6753F, -3.65F, -0.2839F));
		bone2.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.3F, -0.3F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.5F, -1.2F, -0.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F)).mirror(false)
		.texOffs(0, 3).mirror().addBox(-0.6F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 3).mirror().addBox(-1.2F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 3).mirror().addBox(-0.9F, -1.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6545F, 0.0F));

        var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		CubeListBuilder var4 = CubeListBuilder.create().texOffs(24, 23).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE);
        partdefinition.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-2.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(0.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-2.5F, 16.0F, -4.0F));
        partdefinition.addOrReplaceChild("left_front_leg", var4, PartPose.offset(0.5F, 16.0F, -4.0F));
		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 1.8326F, 0.0F, 0.0F));

		tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 7).addBox(0.0F, 5.2F, 1.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(8, 28).addBox(0.0F, 3.3F, -0.1F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 28).addBox(0.0F, 4.4F, 2.3F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(16, 28).addBox(0.0F, 3.5F, 2.8F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.65F)), PartPose.ZERO);
		
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
