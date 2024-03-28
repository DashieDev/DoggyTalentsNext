package doggytalents.client.entity.model.dog.kusa;

import doggytalents.api.anim.AltDogAnimationSequences;
import doggytalents.api.anim.DogAnimation;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class TeiModel extends DogModel {

    public TeiModel(ModelPart box) {
		super(box);
	}

	public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

		var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), 
            PartPose.offset(0F, 13.5F, -7.0F));
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(1, 1).addBox(-3.0F, -3.7F, -1.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.35F))
		.texOffs(54, 0).addBox(0.75F, 0.48F, -4.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(54, 0).mirror().addBox(-1.75F, 0.48F, -4.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 10).addBox(-1.5F, -0.22F, -5.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.ZERO);
	
		real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(15, 15).addBox(-1.5F, 0.0F, -2.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F))
		.texOffs(16, 12).addBox(-1.7F, 0.6F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.04F))
		.texOffs(16, 12).addBox(-2.3F, 0.85F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-3.05F, 2.0F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(16, 12).addBox(-2.55F, 1.5F, -2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offset(-2.25F, -3.0F, 0.5F));

		real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 12).mirror().addBox(1.8F, 1.5F, -2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(2.3F, 2.0F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(1.55F, 0.85F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(16, 12).mirror().addBox(0.95F, 0.6F, -2.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.04F)).mirror(false)
		.texOffs(15, 15).mirror().addBox(-0.25F, 0.0F, -2.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offset(2.0F, -3.0F, 0.5F));

        var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		CubeListBuilder var4 = CubeListBuilder.create().texOffs(44, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE);
        CubeListBuilder var4_1 = CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, CubeDeformation.NONE);
        partdefinition.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-1.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(1.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("right_front_leg", var4_1, PartPose.offset(-1.5F, 16.0F, -4.0F));
        partdefinition.addOrReplaceChild("left_front_leg", var4_1, PartPose.offset(1.5F, 16.0F, -4.0F));
		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 12.0F, 8.0F, 1.8326F, 0.0F, 0.0F));

		tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        
		return LayerDefinition.create(meshdefinition, 64, 64);

    }

	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

	@Override
	protected AnimationDefinition getAnimationSequence(DogAnimation anim) {
		if (anim == DogAnimation.HOWL) {
			return AltDogAnimationSequences.VICTORY_HOWL_ALT;
		}
		return super.getAnimationSequence(anim);
	}

}
