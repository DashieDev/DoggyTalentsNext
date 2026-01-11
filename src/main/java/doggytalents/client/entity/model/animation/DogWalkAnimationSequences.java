package doggytalents.client.entity.model.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class DogWalkAnimationSequences {
    
    public static final AnimationDefinition RUNNING = AnimationDefinition.Builder.withLength(0.5f).looping()
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, -1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 1f, 0.07f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(60f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-71.59f, -12.61f, -8.19f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-6.73f, -6.02f, 5.4f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(60f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.5f, 0.5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 1.43f, 0.13f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1.5f, 0.5f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-62.14f, 8.86f, 4.65f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-62.14f, 8.86f, 4.65f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(87.48f, 7.49f, -0.33f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(-37.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(87.48f, 7.49f, -0.33f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(67.42f, -4.62f, 1.92f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-30f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(67.42f, -4.62f, 1.92f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.25f, 0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -0.62f, 0.9f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.85f, -0.14f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1.25f, 0.75f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(-20.28f, 9.39f, -3.45f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-3.65f, -10.65f, -1.95f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.75f, -1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(6.35f, -3.5f, -0.36f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(10.01f, 2.46f, 0.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 1.75f, 0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(11.69f, -4.27f, -0.01f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-18.49f, 2.45f, -0.48f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(10f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -1.72f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-45f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-11.76f, 1.65f, -24.02f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(35.79f, 12.02f, -16.11f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-45f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-45f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-0.74f, -6.73f, 19.13f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(35.79f, -12.02f, 16.11f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-45f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM))).build();
    public static final AnimationDefinition WALKING = AnimationDefinition.Builder.withLength(0.75f).looping()
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, -0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(-0.5f, -0.5f, -0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.5f, -0.75f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(24.92f, 2.11f, -4.53f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-29.17f, 2.52f, 4.02f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(24.92f, 2.11f, -4.53f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-29.98f, -1.25f, -2.17f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(24.98f, 1.06f, -2.27f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-29.98f, -1.25f, -2.17f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-25.44f, -2.46f, 0.44f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(27.06f, -2.46f, 0.44f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(19.25f, -2.46f, 0.44f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-25.44f, -2.46f, 0.44f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-25f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(35f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, -0.75f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.11f, -5f, 0.18f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-1.39f, 4.21f, -0.06f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.25f, -0.25f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.56f, -4.96f, -0.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(4.94f, 5f, 0.43f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, -0.5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0.49f, 7.23f, 0.05f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(5.22f, 0.04f, 0.01f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 2.5f, 0.11f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(1.75f, -2.5f, 0.05f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 2.5f, 0.11f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-42.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-14.88f, 0.28f, -7.49f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-3.28f, 3.4f, -9.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-42.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.375f, KeyframeAnimations.posVec(-0.2f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-42.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-10f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(1.98f, -0.82f, 22.49f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-42.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM))).build();

    public static final AnimationDefinition DOG_RUNNING = AnimationDefinition.Builder.withLength(0.5f)
        .looping()
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, -0.4f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -1.65f, -0.65f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, -2.2f, -0.9f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.5f, -0.4f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, -0.5f, 0.6f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-21f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(21.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(-19f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-52.56f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-21f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, -0.6f, 0.1f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-68f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(18f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.3333333333333333f, KeyframeAnimations.degreeVec(-5.11f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-26f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-68f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.8f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -1.25f, -0.6f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20833333333333334f, KeyframeAnimations.posVec(0f, -1.07f, -0.43f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, -1.5f, -1.2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.8f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(71f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-3.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(44.87f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(71f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -1.3f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20833333333333334f, KeyframeAnimations.posVec(0f, -0.81f, -0.42f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, -1.6f, -1.4f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.6f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(53f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(49f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(-8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-14.44f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(53f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.16666666666666666f, KeyframeAnimations.posVec(0f, -0.81f, 0.8f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, -1.1f, 0.9f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0.5f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-14.99f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20833333333333334f, KeyframeAnimations.degreeVec(-6.69f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(11f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -0.95f, -0.3f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20833333333333334f, KeyframeAnimations.posVec(0f, -1.08f, -0.9f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, -0.3f, -1.2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(9f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.20833333333333334f, KeyframeAnimations.degreeVec(-1.81f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(-17f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(9f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -0.15f, 0.8f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, -1.2f, 1.2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-9f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0.14f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(12f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.4f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.4f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-44f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(42.0361813007f, 14.4149906967f, -15.4363556762f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-44f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.3f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-23f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(25.5778775385f, -16.2361211711f, 30.2909739309f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-23f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .build();

    public static final AnimationDefinition DOG_WALKING = AnimationDefinition.Builder.withLength(1f)
    .looping()
    .addAnimation("root",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("root",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("head",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.08333333333333333f, KeyframeAnimations.posVec(0f, 0.06f, -0.01f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -0.43f, 0.49f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0.06f, -0.01f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.7916666666666666f, KeyframeAnimations.posVec(0f, -0.43f, 0.49f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("head",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.08333333333333333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("right_ear",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("right_ear",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("left_ear",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("left_ear",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("upper_body",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.12f, 0.12f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.08333333333333333f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -0.29f, 0.39f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.4166666666666667f, KeyframeAnimations.posVec(0f, -0.39f, 0.39f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.12f, 0.12f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.7916666666666666f, KeyframeAnimations.posVec(0f, -0.29f, 0.39f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.875f, KeyframeAnimations.posVec(0f, -0.39f, 0.39f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, -0.12f, 0.12f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("upper_body",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(1.11f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.08333333333333333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.25f, KeyframeAnimations.degreeVec(2.62f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.4166666666666667f, KeyframeAnimations.degreeVec(3.62f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.degreeVec(1.11f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.7916666666666666f, KeyframeAnimations.degreeVec(2.62f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.875f, KeyframeAnimations.degreeVec(3.62f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(1.11f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("body",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.08333333333333333f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.05f, 0.05f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.7916666666666666f, KeyframeAnimations.posVec(0f, 0.05f, 0.05f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("body",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.08333333333333333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.88f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.7916666666666666f, KeyframeAnimations.degreeVec(-2.88f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("left_front_leg",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("left_front_leg",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(-18.69f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.degreeVec(-26.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.6666666666666666f, KeyframeAnimations.degreeVec(14.63f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("right_front_leg",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("right_front_leg",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(-28f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.16666666666666666f, KeyframeAnimations.degreeVec(22.14f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.degreeVec(21f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.75f, KeyframeAnimations.degreeVec(-25.02f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(-28f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("left_hind_leg",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("left_hind_leg",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.degreeVec(-21f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.6666666666666666f, KeyframeAnimations.degreeVec(14.63f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("right_hind_leg",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("right_hind_leg",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.16666666666666666f, KeyframeAnimations.degreeVec(14.63f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("tail",
        new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.08333333333333333f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -0.42f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.7916666666666666f, KeyframeAnimations.posVec(0f, -0.42f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .addAnimation("tail",
        new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.08333333333333333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
        ))
    .build();

}
