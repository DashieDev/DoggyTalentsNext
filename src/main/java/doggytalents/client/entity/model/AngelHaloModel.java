package doggytalents.client.entity.model;

import java.util.Optional;

import com.ibm.icu.text.Normalizer.Mode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import doggytalents.api.enu.forward_imitate.anim.AnimationChannel;
import doggytalents.api.enu.forward_imitate.anim.AnimationDefinition;
import doggytalents.api.enu.forward_imitate.anim.DogModelPart;
import doggytalents.api.enu.forward_imitate.anim.AnimationDefinition.Builder;
import doggytalents.api.enu.forward_imitate.anim.Keyframe;
import doggytalents.api.enu.forward_imitate.anim.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class AngelHaloModel extends AnimatedSyncedAccessoryModel {

	public DogModelPart halo;

    public AngelHaloModel(ModelPart root) {
        super(DogModelPart.recreateFromModelPart(root));
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
		this.halo = (DogModelPart) realHead.get().getChild("halo");
	}

	@Override
	public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		this.root.visible = !dogIn.isDefeated();
	}

	private Vector3f vecObj = new Vector3f(0, 0, 0);
    
	public void prepareMobModel(Dog dog) {
        this.root.visible = dog.isDoingFine();
    }

    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        var animLenMillis = (long)HALO_SPINNA.lengthInSeconds() * 1000;
        var offset = (entityIn.getId() % 6) * (20 * 0.5);
        var timeLine = (offset + (entityIn.isDefeated() ? 0.25f : 1f)*ageInTicks) 
            % Util.millisToTickMayWithPartial(animLenMillis);
        var timeLineMillis = Util.tickMayWithPartialToMillis(timeLine);
        if (entityIn.getId() % 2 == 0) {
            timeLineMillis = animLenMillis - timeLineMillis;
        }
        DogKeyframeAnimations.animate(this, entityIn, HALO_SPINNA, timeLineMillis, 1, this.vecObj);
    }
    
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition halo = real_head.addOrReplaceChild("halo", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -13.5F, -10.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(4, 4).addBox(-3.0F, -13.5F, -10.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(3.0F, -13.5F, -10.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(4, 4).addBox(-3.0F, -13.5F, -6.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.75F, 7.0F));

		return LayerDefinition.create(meshdefinition, 32, 16);
	}

	public static final AnimationDefinition HALO_SPINNA = AnimationDefinition.Builder.withLength(2.0F).looping()
		.addAnimation("halo", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	@Override
	public void resetAllPose() {
		((DogModelPart)this.root).resetPose();
		this.halo.resetPose();
	}

	@Override
    public void renderToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_, float r, float g, float b, float a) {
        super.renderToBuffer(stack, p_103014_, 15728880, p_103016_, r, g, b, a);
    }

}
