package doggytalents.client.entity.model.dog.kusa;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
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

public class KoModel extends DogModel {

    public KoModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

		var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), 
            PartPose.offset(0F, 13.5F, -7.0F));
        var real_head = head.addOrReplaceChild("real_head", 
		CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.1F))
			.texOffs(11, 23).addBox(-3.0F, -3.65F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F))
			.texOffs(3, 5).addBox(-3.0F, 1.4F, -2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.6F))
			.texOffs(3, 5).addBox(-3.0F, 1.6F, -3.2F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.9F))
			.texOffs(3, 5).addBox(-3.0F, 1.6F, -3.2F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.9F))
			.texOffs(23, 0).addBox(-1.5F, -0.42F, -4.2F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.3F)), 
		PartPose.ZERO);
		var ear = real_head.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.offset(-1.6753F, -3.45F, -0.2839F));
		ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 13).addBox(0.85F, -1.4F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 0).addBox(0.25F, 0.5F, -0.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 13).addBox(-0.4F, -1.0F, -1.2F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F))
		.texOffs(0, 13).addBox(0.55F, -0.3F, -0.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(1.15F, -0.3F, -0.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(0.75F, -0.8F, -0.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3506F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));
		ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-1.85F, -1.4F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-2.0F, -0.8F, -0.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-2.15F, -0.3F, -0.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-1.55F, -0.3F, -0.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-2.7F, -1.1F, -1.3F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-2.25F, 0.5F, -0.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));
		real_head.addOrReplaceChild("headfur", CubeListBuilder.create().texOffs(8, 7).mirror().addBox(-4.0F, -8.25F, -8.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(6, 6).mirror().addBox(-3.3F, -7.85F, -7.95F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(6, 6).addBox(2.3F, -7.85F, -7.95F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(6, 6).mirror().addBox(-3.2F, -8.15F, -9.15F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(6, 6).addBox(2.2F, -8.15F, -9.15F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(6, 6).mirror().addBox(-5.0F, -9.3F, -8.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(10, 24).mirror().addBox(-5.75F, -10.3F, -8.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(20, 10).mirror().addBox(-5.25F, -10.8F, -8.6F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(23, 8).mirror().addBox(-4.75F, -12.05F, -8.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 8).mirror().addBox(-3.75F, -13.05F, -8.65F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(20, 8).mirror().addBox(-4.5F, -12.8F, -8.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(22, 8).addBox(2.75F, -13.05F, -8.65F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(23, 8).addBox(2.75F, -12.05F, -8.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 24).addBox(2.75F, -10.3F, -8.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 10).addBox(4.25F, -10.8F, -8.6F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(6, 6).addBox(2.0F, -9.3F, -8.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 6).addBox(3.0F, -8.35F, -8.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 8).addBox(1.5F, -12.8F, -8.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 10.5F, 7.0F));

        var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE);
        partdefinition.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-2.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(0.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-2.5F, 16.0F, -4.0F));
        partdefinition.addOrReplaceChild("left_front_leg", var4, PartPose.offset(0.5F, 16.0F, -4.0F));
		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 1.8326F, 0.0F, 0.0F));

		tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(24, 23).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 24).addBox(0.0F, 5.9F, 1.8F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(24, 24).addBox(0.0F, 2.0F, 0.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 24).addBox(0.0F, 4.1F, 0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.ZERO);
		
		return LayerDefinition.create(meshdefinition, 64, 64);

    }

	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

	@Override
	public boolean warnAccessory(Dog dog, Accessory inst)  {
        return inst.getType() == DoggyAccessoryTypes.HEAD.get();
    }
}
