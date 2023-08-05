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


        
    
}
