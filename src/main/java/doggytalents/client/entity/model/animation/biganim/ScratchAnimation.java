package doggytalents.client.entity.model.animation.biganim;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class ScratchAnimation {
    public static AnimationDefinition build() {
        var animDef = AnimationDefinition.Builder.withLength(3.5f);
        build1(animDef);
        build2(animDef);
        build3(animDef);
        return animDef.build();
    }

    public static void build1(AnimationDefinition.Builder builder) {
        builder.addAnimation("right_ear",
	new AnimationChannel(AnimationChannel.Targets.POSITION, 
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
			AnimationChannel.Interpolations.CATMULLROM), 
		new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
			AnimationChannel.Interpolations.CATMULLROM), 
		new Keyframe(3.0416765f, KeyframeAnimations.posVec(0f, -0.33f, 0f),
			AnimationChannel.Interpolations.CATMULLROM), 
		new Keyframe(3.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
			AnimationChannel.Interpolations.CATMULLROM)))
.addAnimation("right_ear",
	new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(0f, 0f, -37.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 20f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 37.5f, -2.66f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 0f, -32.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.3433335f, KeyframeAnimations.degreeVec(0f, 0f, -32.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(0f, 82.5f, -32.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, -32.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.9167665f, KeyframeAnimations.degreeVec(0f, 0f, 30f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3.0416765f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3.125f, KeyframeAnimations.degreeVec(0f, 0f, -41.71f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3.4167665f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
			AnimationChannel.Interpolations.CATMULLROM)))
.addAnimation("left_ear",
	new AnimationChannel(AnimationChannel.Targets.POSITION, 
		new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
			AnimationChannel.Interpolations.CATMULLROM), 
		new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
			AnimationChannel.Interpolations.CATMULLROM), 
		new Keyframe(2.9167665f, KeyframeAnimations.posVec(0f, -0.58f, 0f),
			AnimationChannel.Interpolations.CATMULLROM), 
		new Keyframe(3.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
			AnimationChannel.Interpolations.CATMULLROM)))
.addAnimation("left_ear",
	new AnimationChannel(AnimationChannel.Targets.ROTATION,
		new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.125f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(30f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.375f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.7083433f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.7916767f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.9167667f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.0834335f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.1676665f, KeyframeAnimations.degreeVec(30f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.25f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.3433335f, KeyframeAnimations.degreeVec(30f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.4583435f, KeyframeAnimations.degreeVec(30f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.5416765f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.625f, KeyframeAnimations.degreeVec(55f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.7083435f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.7916765f, KeyframeAnimations.degreeVec(30f, 0f, 12.5f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.875f, KeyframeAnimations.degreeVec(20f, 0f, -40f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.9167665f, KeyframeAnimations.degreeVec(15.34f, -15.17f, 20.17f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3f, KeyframeAnimations.degreeVec(21.38f, -19.68f, 60.63f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3.125f, KeyframeAnimations.degreeVec(15.31f, 24.46f, -33.43f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3.2083435f, KeyframeAnimations.degreeVec(28.12f, 6.23f, 8.44f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3.4167665f, KeyframeAnimations.degreeVec(-8.46f, -11.48f, 41.66f),
			AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
			AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -9f, -2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.875f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.125f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.375f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.625f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.875f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.125f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.25f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.375f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.625f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, -7f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.875f, KeyframeAnimations.posVec(-0.25f, -7.25f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -9f, -2f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.875f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.125f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.375f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.625f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.875f, KeyframeAnimations.degreeVec(0f, -15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.1676665f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.375f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)));
    }
    public static void build2(AnimationDefinition.Builder builder) {
        builder.addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(-1f, -4f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(-1f, -3f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-27f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-48.05f, -22.06f, -20.83f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-40.55f, -22.06f, -20.83f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.5f, KeyframeAnimations.degreeVec(-27f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(-1f, -3f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(-1f, -3f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-27f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-47f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-47f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.5f, KeyframeAnimations.degreeVec(-27f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -6f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0.45f, -5.5f, -5.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0.05f, -5.5f, -5.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0.05f, -5.5f, -5.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3f, KeyframeAnimations.posVec(0.02f, -5.65f, -5.53f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -6f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.875f, KeyframeAnimations.degreeVec(-81.78f, -14f, 14.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(-81.78f, -14f, 14.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(-81.78f, -14f, 14.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(-81.78f, -14f, 14.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(-81.78f, -14f, 14.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.125f, KeyframeAnimations.degreeVec(-81.78f, -14f, 14.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.375f, KeyframeAnimations.degreeVec(-81.78f, -14f, 14.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.625f, KeyframeAnimations.degreeVec(-81.78f, -14f, 14.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-137.27f, -15.7f, 16.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3f, KeyframeAnimations.degreeVec(-129.05f, -19.04f, 18.03f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.5f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -6f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -6f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, -6f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -6f, -5f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-90f, 22.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-90f, 22.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.5f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -2f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(-0.5f, -3.25f, -0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(-0.5f, -3.25f, -0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -2f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-18f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(3.75f, -21.73f, 5.24f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-11.01f, -29.22f, 4.68f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.875f, KeyframeAnimations.degreeVec(-5.32f, -29.1f, 4.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-11.01f, -29.22f, 4.68f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(-5.32f, -29.1f, 4.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(-11.01f, -29.22f, 4.68f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(-5.32f, -29.1f, 4.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-11.01f, -29.22f, 4.68f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(-5.32f, -29.1f, 4.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-11.01f, -29.22f, 4.68f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(-5.32f, -29.1f, 4.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2f, KeyframeAnimations.degreeVec(-11.01f, -29.22f, 4.68f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.125f, KeyframeAnimations.degreeVec(-5.32f, -29.1f, 4.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(-11.01f, -29.22f, 4.68f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.375f, KeyframeAnimations.degreeVec(-5.32f, -29.1f, 4.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5f, KeyframeAnimations.degreeVec(-11.01f, -29.22f, 4.68f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.625f, KeyframeAnimations.degreeVec(-5.32f, -29.1f, 4.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(3.75f, -21.73f, 5.24f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.9583435f, KeyframeAnimations.degreeVec(5.94f, -16.45f, -13.91f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.3433335f, KeyframeAnimations.degreeVec(-13.02f, 5.78f, 31.36f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.5f, KeyframeAnimations.degreeVec(-18f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -4f, -2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(-0.5f, -4f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(-0.54f, -3.5f, -3.08f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(-0.5f, -4f, -3f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -4f, -2f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-45f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.875f, KeyframeAnimations.degreeVec(-31.28f, 4.05f, -3.71f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(-31.28f, 4.05f, -3.71f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(-31.28f, 4.05f, -3.71f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(-31.28f, 4.05f, -3.71f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(-31.28f, 4.05f, -3.71f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.125f, KeyframeAnimations.degreeVec(-31.28f, 4.05f, -3.71f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.375f, KeyframeAnimations.degreeVec(-31.28f, 4.05f, -3.71f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.625f, KeyframeAnimations.degreeVec(-31.28f, 4.05f, -3.71f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-27.67f, 3.75f, -3.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.9583435f, KeyframeAnimations.degreeVec(-29.5f, 11.78f, 12.58f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.3433335f, KeyframeAnimations.degreeVec(-40.94f, -3.15f, -3.67f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.5f, KeyframeAnimations.degreeVec(-45f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(1.16f, -3.19f, 0.02f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(1.41f, -2.94f, 0.27f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(1.41f, -2.69f, -0.48f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.2083435f, KeyframeAnimations.posVec(0.51f, -1.71f, -0.21f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(3.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-7.68f, -12.39f, 26.66f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-37.18f, -27.97f, 53.85f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.875f, KeyframeAnimations.degreeVec(-43.87f, -32.19f, 29.75f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-37.18f, -27.97f, 53.85f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(-32.61f, -37.43f, 36.29f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(-37.18f, -27.97f, 53.85f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(-47.61f, -37.43f, 36.29f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-29.26f, -36.24f, 39.04f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(-47.61f, -37.43f, 36.29f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-29.26f, -36.24f, 39.04f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(-47.61f, -37.43f, 36.29f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2f, KeyframeAnimations.degreeVec(-29.26f, -36.24f, 39.04f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.125f, KeyframeAnimations.degreeVec(-47.61f, -37.43f, 36.29f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(-29.26f, -36.24f, 39.04f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.375f, KeyframeAnimations.degreeVec(-47.61f, -37.43f, 36.29f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5f, KeyframeAnimations.degreeVec(-29.26f, -36.24f, 39.04f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.625f, KeyframeAnimations.degreeVec(-47.61f, -37.43f, 36.29f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-37.18f, -27.97f, 53.85f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3f, KeyframeAnimations.degreeVec(19.78f, -24.79f, -57.01f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.2083435f, KeyframeAnimations.degreeVec(-5.41f, -9.3f, 57.45f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)));
    }
    public static void build3(AnimationDefinition.Builder builder) {}
}
