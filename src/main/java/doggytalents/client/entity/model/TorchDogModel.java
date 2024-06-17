package doggytalents.client.entity.model;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class TorchDogModel extends AnimatedSyncedAccessoryModel {
    
    public ModelPart torchBig;
    public ModelPart torchSmall2;
    public ModelPart torchSmall;

    public TorchDogModel(ModelPart box) {
        super(box);
        this.torchBig = root.getChild("torch_big");
        this.torchSmall = root.getChild("torch_small");
        this.torchSmall2 = root.getChild("torch_small2");
    }

    public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition torch_big = partdefinition.addOrReplaceChild("torch_big", CubeListBuilder.create().texOffs(4, 7).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.25F, 5.5F, -4.0F));

		PartDefinition torch_small2 = partdefinition.addOrReplaceChild("torch_small2", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -4.0F));

		PartDefinition cube_r1 = torch_small2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 7).mirror().addBox(-9.0F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false)
		.texOffs(4, 7).addBox(7.0F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition torch_small = partdefinition.addOrReplaceChild("torch_small", CubeListBuilder.create().texOffs(4, 7).addBox(7.0F, -2.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(4, 7).mirror().addBox(-9.0F, -2.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offset(0.0F, 5.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

    public void prepareModel(Dog dog) {
        this.root.visible = dog.isDoingFine();
    }

    private Vector3f vecObj = new Vector3f(0, 0, 0);
    
    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        var animLenMillis = (long)TORCH_SPINNA.lengthInSeconds() * 1000;
        var offset = (entityIn.getId() % 6) * (20 * 0.5);
        var timeLine = (offset + (entityIn.isDefeated() ? 0.25f : 1f)*ageInTicks) 
            % Util.millisToTickMayWithPartial(animLenMillis);
        var timeLineMillis = Util.tickMayWithPartialToMillis(timeLine);
        if (entityIn.getId() % 2 == 0) {
            timeLineMillis = animLenMillis - timeLineMillis;
        }
        DogKeyframeAnimations.animate(this, entityIn, TORCH_SPINNA, timeLineMillis, 1, this.vecObj);
    }

    @Override
    public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, int color_overlay) {
        super.renderToBuffer(p_102034_, p_102035_, 15728880, p_102037_, color_overlay);
    }

    @Override
    public void resetAllPose() {
        this.root.resetPose();
        this.torchBig.resetPose();
        this.torchSmall.resetPose();
        this.torchSmall2.resetPose();
    }

    @Override
    protected void populatePart(ModelPart box) {
        
    }

    public static final AnimationDefinition TORCH_SPINNA = AnimationDefinition.Builder.withLength(3f).looping()
        .addAnimation("torch_small",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, -5f, 9f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("torch_small",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 180f, -30f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("torch_small2",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.0416767f, KeyframeAnimations.posVec(0f, -1f, 4f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("torch_small2",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 180f, 25f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("torch_big",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 2f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM))).build();

}
