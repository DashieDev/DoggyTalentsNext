package doggytalents.client.entity.model.animation;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.math.Vector3f;

import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class DogAnimationSequences {

    public static AnimationDefinition STRETCH = AnimationDefinition.Builder
    .withLength(3.5f)
    .addAnimation(
        "head", 
        new AnimationChannel(
             AnimationChannel.Targets.ROTATION, 
           new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.375f, KeyframeAnimations.degreeVec(-6.15518f, -4.29357f, -34.76905f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.6667f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.0f, KeyframeAnimations.degreeVec(7.15656f, 2.24944f, -17.35932f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.25f, KeyframeAnimations.degreeVec(-14.33501f, -4.46375f, -16.93839f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, 0f, -35f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.0f, KeyframeAnimations.degreeVec(-7.15656f, -2.24944f, -17.35932f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.3333f, KeyframeAnimations.degreeVec(0f, 0f, -35f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.625f, KeyframeAnimations.degreeVec(-22.90981f, -10.28859f, -22.90981f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "head", 
        new AnimationChannel(
             AnimationChannel.Targets.POSITION, 
           new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "body", 
        new AnimationChannel(
             AnimationChannel.Targets.ROTATION, 
           new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.75f, KeyframeAnimations.degreeVec(19.9299f, -1.70818f, 4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.0f, KeyframeAnimations.degreeVec(19.9299f, 1.70818f, -4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.25f, KeyframeAnimations.degreeVec(19.9299f, -1.70818f, 4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.5f, KeyframeAnimations.degreeVec(19.9299f, 1.70818f, -4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.75f, KeyframeAnimations.degreeVec(19.9299f, -1.70818f, 4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.0f, KeyframeAnimations.degreeVec(19.9299f, 1.70818f, -4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.25f, KeyframeAnimations.degreeVec(19.9299f, -1.70818f, 4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "body", 
        new AnimationChannel(
             AnimationChannel.Targets.POSITION, 
           new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "upper_body", 
        new AnimationChannel(
             AnimationChannel.Targets.ROTATION, 
           new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.6667f, KeyframeAnimations.degreeVec(19.9299f, -1.70818f, 4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.9167f, KeyframeAnimations.degreeVec(19.9299f, 1.70818f, -4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.1667f, KeyframeAnimations.degreeVec(19.9299f, -1.70818f, 4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.4167f, KeyframeAnimations.degreeVec(19.9299f, 1.70818f, -4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.6667f, KeyframeAnimations.degreeVec(19.9299f, -1.70818f, 4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.9167f, KeyframeAnimations.degreeVec(19.9299f, 1.70818f, -4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.1667f, KeyframeAnimations.degreeVec(19.9299f, -1.70818f, 4.69986f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "upper_body", 
        new AnimationChannel(
             AnimationChannel.Targets.POSITION, 
           new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -4f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -4f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "left_hind_leg", 
        new AnimationChannel(
             AnimationChannel.Targets.ROTATION, 
           new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.degreeVec(8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.degreeVec(8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "right_hind_leg", 
        new AnimationChannel(
             AnimationChannel.Targets.ROTATION, 
           new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.degreeVec(8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.degreeVec(8f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "left_front_leg", 
        new AnimationChannel(
             AnimationChannel.Targets.ROTATION, 
           new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.degreeVec(-55f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.0833f, KeyframeAnimations.degreeVec(-55f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.4167f, KeyframeAnimations.degreeVec(-77.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.7917f, KeyframeAnimations.degreeVec(-55f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.degreeVec(-55f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "left_front_leg", 
        new AnimationChannel(
             AnimationChannel.Targets.POSITION, 
           new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -3.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.0833f, KeyframeAnimations.posVec(0f, -3.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.4167f, KeyframeAnimations.posVec(0f, -4.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.7917f, KeyframeAnimations.posVec(0f, -3.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -3.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "right_front_leg", 
        new AnimationChannel(
             AnimationChannel.Targets.ROTATION, 
           new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.degreeVec(-55f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.0833f, KeyframeAnimations.degreeVec(-55f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.4167f, KeyframeAnimations.degreeVec(-65f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.7917f, KeyframeAnimations.degreeVec(-55f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.degreeVec(-55f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "right_front_leg", 
        new AnimationChannel(
             AnimationChannel.Targets.POSITION, 
           new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -3.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.0833f, KeyframeAnimations.posVec(0f, -3.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.4167f, KeyframeAnimations.posVec(0f, -4.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.7917f, KeyframeAnimations.posVec(0f, -3.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -3.5f, 2f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "tail", 
        new AnimationChannel(
             AnimationChannel.Targets.ROTATION, 
           new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.degreeVec(165f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.75f, KeyframeAnimations.degreeVec(166.93569f, 7.43547f, 29.14743f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.0f, KeyframeAnimations.degreeVec(166.93569f, -7.43547f, -29.14743f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.25f, KeyframeAnimations.degreeVec(166.93569f, 7.43547f, 29.14743f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.5f, KeyframeAnimations.degreeVec(166.93569f, -7.43547f, -29.14743f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(1.75f, KeyframeAnimations.degreeVec(166.93569f, 7.43547f, 29.14743f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.0f, KeyframeAnimations.degreeVec(166.93569f, -7.43547f, -29.14743f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.25f, KeyframeAnimations.degreeVec(166.93569f, 7.43547f, 29.14743f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.degreeVec(165f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "tail", 
        new AnimationChannel(
             AnimationChannel.Targets.POSITION, 
           new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1.5f, -3.25f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -1.5f, -3.25f), AnimationChannel.Interpolations.CATMULLROM),
           new Keyframe(3.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .build();

    public static AnimationDefinition TEST_ANIM_1 = AnimationDefinition.Builder
    .withLength(6.5f)
    .addAnimation(
        "head", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
            new Keyframe(0, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(3, KeyframeAnimations.degreeVec(0, 90, 0), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(6, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.CATMULLROM)
        )
    )
    .build();

    public static AnimationDefinition GREET = AnimationDefinition.Builder
    .withLength(2f)
    .addAnimation(
        "head", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
          new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.25f, KeyframeAnimations.degreeVec(-28.83844f, -8.64738f, -15.27269f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.degreeVec(-13.64973f, 6.27967f, 24.24769f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "head", 
        new AnimationChannel(
            AnimationChannel.Targets.POSITION, 
          new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 6f, 2.5f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.posVec(0f, 6f, 2.5f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "body", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
          new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.degreeVec(-42.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0417f, KeyframeAnimations.degreeVec(-42.25489f, 5.05904f, 5.54401f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5417f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "body", 
        new AnimationChannel(
            AnimationChannel.Targets.POSITION, 
          new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 3f, 1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0417f, KeyframeAnimations.posVec(0f, 3f, 1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5417f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "upper_body", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
          new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.25f, KeyframeAnimations.degreeVec(-19.71975f, 3.40487f, 9.40804f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.degreeVec(-14.78217f, -2.57594f, -9.6658f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "upper_body", 
        new AnimationChannel(
            AnimationChannel.Targets.POSITION, 
          new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 4f, 2f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.posVec(0f, 4f, 2f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "left_hind_leg", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
          new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "left_hind_leg", 
        new AnimationChannel(
            AnimationChannel.Targets.POSITION, 
          new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "right_hind_leg", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
          new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.75f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.2917f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.7083f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "right_hind_leg", 
        new AnimationChannel(
            AnimationChannel.Targets.POSITION, 
          new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.posVec(0f, 1f, -1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "left_front_leg", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
          new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.degreeVec(-92.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.degreeVec(-92.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.6667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "left_front_leg", 
        new AnimationChannel(
            AnimationChannel.Targets.POSITION, 
          new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.posVec(0f, 5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "right_front_leg", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
          new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.75f, KeyframeAnimations.degreeVec(-92.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.25f, KeyframeAnimations.degreeVec(-92.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.6667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "right_front_leg", 
        new AnimationChannel(
            AnimationChannel.Targets.POSITION, 
          new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.posVec(0f, 5f, 1f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "tail", 
        new AnimationChannel(
            AnimationChannel.Targets.ROTATION, 
          new Keyframe(0.0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.degreeVec(122.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.75f, KeyframeAnimations.degreeVec(125.68672f, 22.91934f, 15.62633f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.degreeVec(125.68672f, -22.91934f, -15.62633f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.25f, KeyframeAnimations.degreeVec(125.68672f, 22.91934f, 15.62633f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5f, KeyframeAnimations.degreeVec(125.68672f, -22.91934f, -15.62633f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .addAnimation(
        "tail", 
        new AnimationChannel(
            AnimationChannel.Targets.POSITION, 
          new Keyframe(0.0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -2f, -2f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.0f, KeyframeAnimations.posVec(0f, -2f, -2f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.5417f, KeyframeAnimations.posVec(0f, -1f, -2f), AnimationChannel.Interpolations.CATMULLROM),
          new Keyframe(1.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
      )
    )
    .build();

    public static final AnimationDefinition FAINT = AnimationDefinition.Builder.withLength(4f)
    .addAnimation("head",
      new AnimationChannel(AnimationChannel.Targets.POSITION, 
        new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 1f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(1f, KeyframeAnimations.posVec(0f, -2f, -0.25f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(1.4167667f, KeyframeAnimations.posVec(-0.05f, -3.18f, 0.11f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2f, KeyframeAnimations.posVec(-0.12f, -1.5f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -1f, -0.5f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3f, KeyframeAnimations.posVec(2f, -6.5f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3.1676665f, KeyframeAnimations.posVec(2f, -6.5f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3.5f, KeyframeAnimations.posVec(2f, -7f, 0f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("head",
      new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5f, KeyframeAnimations.degreeVec(-6.75f, 6.28f, 13.65f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75f, KeyframeAnimations.degreeVec(-13.72f, -1.11f, -21.98f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1f, KeyframeAnimations.degreeVec(37.74f, -5.74f, 15.16f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(26.09f, -3.13f, 1.84f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2f, KeyframeAnimations.degreeVec(-14.25f, -1.4f, -5.54f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.375f, KeyframeAnimations.degreeVec(-23.1f, -0.12f, -5.34f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.6766665f, KeyframeAnimations.degreeVec(-25f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.1676665f, KeyframeAnimations.degreeVec(0f, 0f, 77.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("body",
      new AnimationChannel(AnimationChannel.Targets.POSITION, 
        new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.25f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(1f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(1.5f, KeyframeAnimations.posVec(-0.08f, -1.53f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2f, KeyframeAnimations.posVec(-0.15f, -0.84f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3f, KeyframeAnimations.posVec(2f, -6.75f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3.5f, KeyframeAnimations.posVec(2f, -6.75f, 0f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("body",
      new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5f, KeyframeAnimations.degreeVec(-5f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.5f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("right_hind_leg",
      new AnimationChannel(AnimationChannel.Targets.POSITION, 
        new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3f, KeyframeAnimations.posVec(2f, -2.25f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3.5f, KeyframeAnimations.posVec(2f, -2.25f, 0f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("right_hind_leg",
      new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5f, KeyframeAnimations.degreeVec(-7.5f, 0f, -0.87f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.75f, KeyframeAnimations.degreeVec(0f, 0f, 82.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 75f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.25f, KeyframeAnimations.degreeVec(0f, 0f, 107.03f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.4583435f, KeyframeAnimations.degreeVec(0f, 0f, 77.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.6766665f, KeyframeAnimations.degreeVec(0f, 0f, 82.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.8343335f, KeyframeAnimations.degreeVec(0f, 0f, 80f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("left_hind_leg",
      new AnimationChannel(AnimationChannel.Targets.POSITION, 
        new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3f, KeyframeAnimations.posVec(-1f, -5.75f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3.5f, KeyframeAnimations.posVec(-1f, -5.75f, 0f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("left_hind_leg",
      new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5f, KeyframeAnimations.degreeVec(15f, 0f, -1.04f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("right_front_leg",
      new AnimationChannel(AnimationChannel.Targets.POSITION, 
        new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, 1f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 1f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(1.75f, KeyframeAnimations.posVec(0.7f, -0.78f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2.5f, KeyframeAnimations.posVec(-1f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3f, KeyframeAnimations.posVec(2f, -2f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3.5f, KeyframeAnimations.posVec(2f, -2f, 0f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("right_front_leg",
      new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(-24.25f, 6.28f, 13.65f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.75f, KeyframeAnimations.degreeVec(1.21f, -0.5f, 31.45f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.75f, KeyframeAnimations.degreeVec(-0.03f, 0.08f, 86.32f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 72.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.25f, KeyframeAnimations.degreeVec(0f, 0f, 114.14f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.4583435f, KeyframeAnimations.degreeVec(0f, 0f, 75f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.6766665f, KeyframeAnimations.degreeVec(0f, 0f, 77.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.8343335f, KeyframeAnimations.degreeVec(0f, 0f, 75f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("left_front_leg",
      new AnimationChannel(AnimationChannel.Targets.POSITION, 
        new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 1f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(1f, KeyframeAnimations.posVec(0f, -0.25f, 1f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2f, KeyframeAnimations.posVec(0.69f, -0.66f, 0.5f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2.5f, KeyframeAnimations.posVec(1f, -0.25f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3f, KeyframeAnimations.posVec(-1f, -6f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3.5f, KeyframeAnimations.posVec(-1f, -6f, 0f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("left_front_leg",
      new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5f, KeyframeAnimations.degreeVec(-4.98f, -0.44f, -4.98f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM)))
      .addAnimation("tail",
          new AnimationChannel(AnimationChannel.Targets.POSITION, 
            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 2f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -2f, 0f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(1.625f, KeyframeAnimations.posVec(0.25f, -2f, -0.75f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(2f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(2.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(2.75f, KeyframeAnimations.posVec(2f, -4f, 0f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(3f, KeyframeAnimations.posVec(3.75f, -7.75f, -0.5f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(3.25f, KeyframeAnimations.posVec(3.75f, -8.75f, -0.5f),
              AnimationChannel.Interpolations.CATMULLROM), 
            new Keyframe(3.5f, KeyframeAnimations.posVec(3.75f, -8.25f, -0.5f),
              AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("tail",
      new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5f, KeyframeAnimations.degreeVec(135f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1f, KeyframeAnimations.degreeVec(152.32f, 16.49f, 39.05f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.625f, KeyframeAnimations.degreeVec(152.32f, -16.49f, -39.05f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2f, KeyframeAnimations.degreeVec(152.32f, 16.49f, 39.05f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.5f, KeyframeAnimations.degreeVec(152.32f, 16.49f, 39.05f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.75f, KeyframeAnimations.degreeVec(127.5f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3f, KeyframeAnimations.degreeVec(110f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.25f, KeyframeAnimations.degreeVec(67.5f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5f, KeyframeAnimations.degreeVec(125f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.75f, KeyframeAnimations.degreeVec(62.5f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("upper_body",
      new AnimationChannel(AnimationChannel.Targets.POSITION, 
        new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 1f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0.75f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(1.5f, KeyframeAnimations.posVec(0.03f, -1.87f, 0.85f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2f, KeyframeAnimations.posVec(0.06f, -1.44f, 0.84f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -1f, 0.75f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3f, KeyframeAnimations.posVec(2.5f, -5.5f, 0f),
          AnimationChannel.Interpolations.CATMULLROM), 
        new Keyframe(3.5f, KeyframeAnimations.posVec(2.5f, -5.5f, 0f),
          AnimationChannel.Interpolations.CATMULLROM)))
    .addAnimation("upper_body",
      new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5f, KeyframeAnimations.degreeVec(0.15f, 1.73f, 9.85f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1f, KeyframeAnimations.degreeVec(10f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.5f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
          AnimationChannel.Interpolations.CATMULLROM))).build();
        
    
}
