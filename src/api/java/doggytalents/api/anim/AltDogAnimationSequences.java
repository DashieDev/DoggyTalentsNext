package doggytalents.api.anim;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class AltDogAnimationSequences {
    
    public static final AnimationDefinition VICTORY_HOWL_ALT = AnimationDefinition.Builder.withLength(8.25f)
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -3f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(1f, -4.25f, 2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1f, KeyframeAnimations.posVec(4f, -3f, 4f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.25f, KeyframeAnimations.posVec(-1f, -5.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.4583433f, KeyframeAnimations.posVec(-2.78f, -5.81f, 0.88f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2f, KeyframeAnimations.posVec(-2.5f, -6.25f, -0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.25f, KeyframeAnimations.posVec(-1f, -6.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.4167665f, KeyframeAnimations.posVec(-1f, -7.25f, 0.5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.5834335f, KeyframeAnimations.posVec(-0.33f, 0.92f, -0.17f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, 1.5f, 1.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(4.25f, KeyframeAnimations.posVec(0.02f, 1.28f, 3.24f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(5.5f, KeyframeAnimations.posVec(0f, 2.14f, 3.58f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(12.23f, 22.52f, 11.15f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(24.78f, -84.38f, -73.9f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(58.46f, 43.97f, 26.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2f, KeyframeAnimations.degreeVec(58.46f, 43.97f, 26.73f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(59.66f, 35.12f, 26.06f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(59.66f, 35.12f, 26.06f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-60f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(4.25f, KeyframeAnimations.degreeVec(-70f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(5.5f, KeyframeAnimations.degreeVec(-71.01f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(1f, -1f, -1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1f, KeyframeAnimations.posVec(-1.02f, -1.98f, -0.05f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(-0.08f, -1.56f, -0.03f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.25f, KeyframeAnimations.posVec(0.99f, -0.82f, -0.01f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 0f, 36.94f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, 0f, 33.75f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -2f, -2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.0416767f, KeyframeAnimations.posVec(-0.5f, -3f, 1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.2916767f, KeyframeAnimations.posVec(-0.02f, -3.64f, -0.25f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(0.02f, -2.02f, -0.51f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.1676665f, KeyframeAnimations.posVec(0.68f, -1.19f, -1.52f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(5.89f, 13.31f, -25.79f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-0.98f, 12.43f, -53.69f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(1.27f, 6.34f, -24.03f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.25f, KeyframeAnimations.posVec(-1f, -1f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.2916767f, KeyframeAnimations.posVec(-1.02f, -1.27f, 0.76f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.25f, KeyframeAnimations.posVec(-1f, -2f, 2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.4167665f, KeyframeAnimations.posVec(-1f, -2f, 2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, 0f, 1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(7.708343f, KeyframeAnimations.posVec(0f, 0f, 2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 40f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 30f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5416767f, KeyframeAnimations.degreeVec(0f, 0f, 38.94f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, 0f, 50f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(0f, 0f, 50f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -3f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.625f, KeyframeAnimations.posVec(2f, -3f, 1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.75f, KeyframeAnimations.posVec(2f, -2.83f, 2.17f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.1676667f, KeyframeAnimations.posVec(-0.28f, -2.92f, 3.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.4583433f, KeyframeAnimations.posVec(0.21f, -2.94f, -0.78f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.25f, KeyframeAnimations.posVec(1.5f, -4f, 0.5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.4167665f, KeyframeAnimations.posVec(1.5f, -3f, 0.5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, 0f, 1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(7.708343f, KeyframeAnimations.posVec(0f, 0f, 2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -40f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(15.38f, -7.33f, -98.88f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(0f, 0f, -40f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, 0f, -77.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(0f, 0f, -40f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -2f, -1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1f, KeyframeAnimations.posVec(-1.16f, -4.87f, -1.11f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.25f, KeyframeAnimations.posVec(0.25f, -2.5f, -0.97f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.4167665f, KeyframeAnimations.posVec(1f, -4f, 0.28f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, -2f, -1f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(5.5f, KeyframeAnimations.posVec(0f, -1.94f, -0.2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(125f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(128.29f, 22.22f, 16.62f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(137.45f, -38.87f, -34.36f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.2916767f, KeyframeAnimations.degreeVec(128.96f, 24.18f, 18.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(61.1f, 47.27f, 20.82f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(61.1f, 47.27f, 20.82f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(5.5f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, -3f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1f, KeyframeAnimations.posVec(1f, -4f, 1.5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.1676667f, KeyframeAnimations.posVec(0.25f, -4.32f, 1.12f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.3433333f, KeyframeAnimations.posVec(-0.8f, -5.56f, 0.42f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.0416765f, KeyframeAnimations.posVec(-1.03f, -5.6f, 0.16f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.25f, KeyframeAnimations.posVec(0f, -5.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.4167665f, KeyframeAnimations.posVec(0f, -5.5f, 1.25f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.5834335f, KeyframeAnimations.posVec(0f, -2.33f, 0.67f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, -0.5f, 0.5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(5.5f, KeyframeAnimations.posVec(0f, 0.11f, 1.46f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-14.23f, -31.79f, -6.56f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(-14.23f, -31.79f, -6.56f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(4.27f, 23.78f, -3.34f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.25f, KeyframeAnimations.degreeVec(9.74f, 0.06f, -3.03f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(17.39f, 9.91f, -1.32f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(5.5f, KeyframeAnimations.degreeVec(-42.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.9583435f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(4.541677f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(6f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(7.583433f, KeyframeAnimations.posVec(0f, -0.38f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(-40.92f, -4.21f, 24.67f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(-70.12f, -27.83f, 48.12f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.4583433f, KeyframeAnimations.degreeVec(39.02f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.0834335f, KeyframeAnimations.degreeVec(-28.48f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.2916765f, KeyframeAnimations.degreeVec(-36.72f, 40.7f, -3.75f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5416765f, KeyframeAnimations.degreeVec(39.02f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.9583435f, KeyframeAnimations.degreeVec(-15.98f, 0.43f, -22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(4.541677f, KeyframeAnimations.degreeVec(-39.78f, 11.95f, -14.64f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(6f, KeyframeAnimations.degreeVec(-50.98f, 0.43f, -22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(7.583433f, KeyframeAnimations.degreeVec(4.02f, 0.43f, -22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.9583435f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(4.541677f, KeyframeAnimations.posVec(0f, -0.75f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(6f, KeyframeAnimations.posVec(0f, -0.75f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-40.92f, -4.21f, 24.67f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9583434f, KeyframeAnimations.degreeVec(-70.12f, -27.83f, 48.12f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.4583433f, KeyframeAnimations.degreeVec(39.02f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.0834335f, KeyframeAnimations.degreeVec(-28.48f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5416765f, KeyframeAnimations.degreeVec(39.02f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.9583435f, KeyframeAnimations.degreeVec(-15.98f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(5f, KeyframeAnimations.degreeVec(-15.98f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(5.208343f, KeyframeAnimations.degreeVec(-92.75f, -41.14f, 116.11f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(5.375f, KeyframeAnimations.degreeVec(-15.98f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(6f, KeyframeAnimations.degreeVec(-50.98f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(7.583433f, KeyframeAnimations.degreeVec(4.02f, -0.43f, 22.41f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, -3.65f, -0.95f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.0416767f, KeyframeAnimations.posVec(-0.48f, -4.18f, 0.96f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.25f, KeyframeAnimations.posVec(-0.45f, -5.24f, -0.43f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.5f, KeyframeAnimations.posVec(-0.35f, -4.21f, 0.95f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(-0.22f, -3.87f, 0.64f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.1676665f, KeyframeAnimations.posVec(0f, -3.5f, -0.25f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.4167665f, KeyframeAnimations.posVec(0f, -4.5f, -0.5f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(2.75f, KeyframeAnimations.posVec(0f, -1.5f, -0.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(5.5f, KeyframeAnimations.posVec(0f, -1.06f, 0.61f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(8.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(10f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1f, KeyframeAnimations.degreeVec(10.15f, -9.85f, -1.75f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(10f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.1676665f, KeyframeAnimations.degreeVec(20.15f, 9.85f, 1.75f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(2.65f, 9.85f, 1.75f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(5.5f, KeyframeAnimations.degreeVec(-10f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(8.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM))).build();

}