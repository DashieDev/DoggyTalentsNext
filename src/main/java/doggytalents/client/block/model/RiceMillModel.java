package doggytalents.client.block.model;

import java.util.Optional;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.client.entity.model.AnimatedSyncedAccessoryModel;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.client.entity.model.animation.SimpleAnimatedModel;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class RiceMillModel extends SimpleAnimatedModel {

    private ModelPart root;
	private ModelPart spin;
	private ModelPart hammer;

    public RiceMillModel(ModelPart box) {
		super(RenderType::entityCutoutNoCull);
		this.root = box;
		this.spin = box.getChild("spin");
		this.hammer = box.getChild("hammer2");
    }

	@Override
	public Optional<ModelPart> getPartFromName(String name) {
		if ("spin".equals(name))
			return Optional.of(this.spin);
		if (this.hammer.hasChild(name))
			return Optional.of(this.hammer.getChild(name));
		return Optional.empty();
	}

	@Override
	public void resetPart(ModelPart part) {
		part.resetPose();
	}

	@Override
	public void resetAllPose() {
		this.spin.resetPose();
		this.hammer.getAllParts().forEach(x -> x.resetPose());
	}

	private Vector3f tempVec = new Vector3f();

	public void setUpMillAnim(RiceMillBlockEntity mill, float pTicks) {
		if (!mill.isSpinning())
			pTicks = 0;
		var timeLine = (mill.getAnimationTick() + pTicks)
			% (double) RiceMillBlockEntity.GRIND_ANIM_TICK_LEN;
		var timeLineMillis = Util.tickMayWithPartialToMillis(timeLine);
		DogKeyframeAnimations.animateSimple(this, GRIND_ANIM, timeLineMillis, 1, tempVec);
	}

    public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition stand = partdefinition.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(38, 55).addBox(-9.0F, -6.25F, -0.5F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(68, 72).addBox(-9.75F, -4.25F, -0.5F, 9.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(41, 72).addBox(-9.75F, -6.4F, -0.5F, 9.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(41, 72).addBox(-9.0F, -6.4F, 4.25F, 8.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(38, 55).addBox(-9.0F, -6.25F, 4.25F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(68, 72).addBox(-9.0F, -4.25F, 4.25F, 8.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(0, 55).addBox(-3.75F, -6.25F, -0.5F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(30, 51).addBox(-3.75F, -7.0F, 4.25F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(59, 96).addBox(-3.75F, -6.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(-0.25F)), PartPose.offset(9.0F, 22.0F, 1.75F));

		PartDefinition hammer2 = partdefinition.addOrReplaceChild("hammer2", CubeListBuilder.create(), PartPose.offset(3.0F, 16.0F, 4.0F));

		PartDefinition hammer_lever2 = hammer2.addOrReplaceChild("hammer_lever2", CubeListBuilder.create(), PartPose.offset(3.25F, 2.5F, 0.0F));

		PartDefinition cube_r1 = hammer_lever2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(58, 51).addBox(-7.0F, -4.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(-6.0F, -3.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition hammer_head2 = hammer2.addOrReplaceChild("hammer_head2", CubeListBuilder.create(), PartPose.offset(3.0F, 0.5F, 0.0F));

		PartDefinition cube_r2 = hammer_head2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(65, 92).addBox(-7.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-5.75F, 3.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r3 = hammer_head2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(58, 56).addBox(-7.0F, -4.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-5.75F, 0.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition bowl2 = partdefinition.addOrReplaceChild("bowl2", CubeListBuilder.create().texOffs(22, 99).addBox(4.25F, -1.9F, -5.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(19, 86).addBox(4.25F, -0.7F, -5.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.6F))
		.texOffs(22, 99).addBox(4.25F, -1.9F, -2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 23.0F, 7.0F));

		PartDefinition cube_r4 = bowl2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(20, 100).addBox(6.5F, 1.75F, -1.65F, 4.0F, 1.0F, 3.0F, new CubeDeformation(-0.101F)), PartPose.offsetAndRotation(6.25F, -1.4F, -11.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r5 = bowl2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(22, 99).addBox(-0.5F, -0.5F, -2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.101F))
		.texOffs(22, 99).addBox(-0.5F, -0.5F, 1.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.101F)), PartPose.offsetAndRotation(6.25F, -1.4F, -4.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition spin = partdefinition.addOrReplaceChild("spin", CubeListBuilder.create(), PartPose.offset(2.0F, 18.0F, 10.25F));

		PartDefinition axis = spin.addOrReplaceChild("axis", CubeListBuilder.create().texOffs(53, 99).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(-0.2F))
		.texOffs(53, 31).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, -6.25F));

		PartDefinition cube_r6 = axis.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(49, 64).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r7 = axis.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(38, 41).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r8 = axis.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(77, 63).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition wheel = spin.addOrReplaceChild("wheel", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, 1.0378F, -1.3695F, 2.0F, 12.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -0.0378F, -0.1305F));

		PartDefinition cube_r9 = wheel.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(49, 0).addBox(-3.3521F, 5.9475F, -1.1132F, 7.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(50, 82).addBox(-0.6021F, 6.9975F, -2.1132F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, -1.9635F));

		PartDefinition cube_r10 = wheel.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(27, 0).addBox(-0.9733F, 0.0267F, -2.1195F, 2.0F, 14.0F, 4.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r11 = wheel.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(18, 45).addBox(-3.3317F, -9.9969F, -1.1123F, 7.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, -2.7489F));

		PartDefinition cube_r12 = wheel.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 45).addBox(-3.3317F, 5.9969F, -1.1123F, 7.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, -0.3927F));

		PartDefinition cube_r13 = wheel.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(44, 31).addBox(-3.6972F, -9.927F, -1.111F, 7.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, -0.3927F));

		PartDefinition cube_r14 = wheel.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(44, 25).addBox(-3.6972F, 5.927F, -1.111F, 7.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, -2.7489F));

		PartDefinition cube_r15 = wheel.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(47, 37).addBox(-3.7177F, -9.9764F, -1.1102F, 7.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(53, 96).addBox(-1.2369F, -8.9307F, -2.1202F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, -1.9635F));

		PartDefinition cube_r16 = wheel.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(47, 13).addBox(-3.2823F, -9.9764F, -1.1102F, 7.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(80, 103).addBox(-0.7631F, -8.9307F, -2.1202F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, 1.9635F));

		PartDefinition cube_r17 = wheel.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(75, 86).addBox(-0.6493F, 6.8835F, -2.1153F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, -0.3927F));

		PartDefinition cube_r18 = wheel.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(73, 95).addBox(-1.2841F, 7.0446F, -2.1182F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, -2.7489F));

		PartDefinition cube_r19 = wheel.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(73, 83).addBox(-1.2841F, -9.0446F, -2.1182F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, -0.3927F));

		PartDefinition cube_r20 = wheel.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(80, 86).addBox(-0.6493F, -8.8835F, -2.1153F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, -2.7489F));

		PartDefinition cube_r21 = wheel.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(41, 98).addBox(-1.3979F, 6.9975F, -2.1132F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F))
		.texOffs(49, 6).addBox(-3.6479F, 5.9475F, -1.1132F, 7.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0436F, 1.9635F));

		PartDefinition cube_r22 = wheel.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(22, 25).addBox(-0.9733F, -0.0267F, -2.1195F, 2.0F, 14.0F, 4.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.3562F));

		PartDefinition cube_r23 = wheel.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0267F, -0.0267F, -2.1195F, 2.0F, 14.0F, 4.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.3562F));

		PartDefinition cube_r24 = wheel.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0267F, 0.0267F, -2.1195F, 2.0F, 14.0F, 4.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r25 = wheel.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 1.0F, -1.5F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0378F, 0.1305F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r26 = wheel.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(34, 25).addBox(-1.0F, 1.0F, -1.5F, 2.0F, 12.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0378F, 0.1305F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r27 = wheel.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(43, 85).addBox(-3.75F, -4.2878F, -1.1195F, 7.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r28 = wheel.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(59, 114).addBox(-3.2878F, 2.25F, -1.1195F, 7.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r29 = wheel.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(43, 81).addBox(-3.7122F, 2.25F, -1.1195F, 7.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r30 = wheel.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(43, 105).addBox(-3.75F, 2.2122F, -1.1195F, 7.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r31 = wheel.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(40, 40).addBox(-1.0F, 1.0F, -1.5F, 2.0F, 12.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0378F, 0.1305F, 0.0F, 0.0F, -3.1416F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, int color_overlay) {
        this.root.render(p_103111_, p_103112_, p_103113_, p_103114_, 0xffffffff);
    }

	private static final AnimationDefinition GRIND_ANIM = AnimationDefinition.Builder.withLength(10f).looping()
		.addAnimation("spin",
			new AnimationChannel(AnimationChannel.Targets.ROTATION,
				new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.LINEAR),
				new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 360f),
					AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("hammer_lever2",
			new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(7.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("hammer_lever2",
			new AnimationChannel(AnimationChannel.Targets.ROTATION,
				new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.9f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.2f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.4f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.7f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(5.9f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(6.2f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(7.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(8.4f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(8.7f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f),
					AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("hammer_head2",
			new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(0.9f, KeyframeAnimations.posVec(0f, 1f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(1.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(3.4f, KeyframeAnimations.posVec(0f, 1f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(3.7f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(5f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(5.9f, KeyframeAnimations.posVec(0f, 1f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(6.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(7.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(8.4f, KeyframeAnimations.posVec(0f, 1f, 0f),
					AnimationChannel.Interpolations.CATMULLROM), 
				new Keyframe(8.7f, KeyframeAnimations.posVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("hammer_head2",
			new AnimationChannel(AnimationChannel.Targets.ROTATION,
				new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.9f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.4f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.7f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(5.9f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(6.2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(7.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(8.4f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(8.7f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
					AnimationChannel.Interpolations.CATMULLROM))).build();
}
