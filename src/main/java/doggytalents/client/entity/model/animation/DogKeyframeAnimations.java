package doggytalents.client.entity.model.animation;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.entity.model.AnimatedSyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joml.Vector3f;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DogKeyframeAnimations {
   public static void animate(DogModel model, Dog dog,
        AnimationDefinition animation, long elapsed_in_millis, float interpolation_scale, 
        Vector3f temp_buffer) {
        float elapsed_in_seconds = getElapsedSeconds(animation, elapsed_in_millis);

        for(var entry : animation.boneAnimations().entrySet()) {
            var partOptional = model.searchForPartWithName(entry.getKey());
            if (partOptional.isEmpty()) continue;
            var part = partOptional.get();
            model.resetPart(part, dog);
            var channelList = entry.getValue();
            for (var channel : channelList) {
                var keyframes = channel.keyframes();
                int currentKeyframeIndx = Math.max(0, Mth.binarySearch(0, keyframes.length, (compare_indx) -> {
                    return elapsed_in_seconds <= keyframes[compare_indx].timestamp();
                }) - 1);
                int nextKeyframeIndx = Math.min(keyframes.length - 1, currentKeyframeIndx + 1);
                Keyframe currentKeyframe = keyframes[currentKeyframeIndx];
                Keyframe nextKeyframe = keyframes[nextKeyframeIndx];
                float passed_time_since_current = elapsed_in_seconds - currentKeyframe.timestamp();
                float duration_between = nextKeyframe.timestamp() - currentKeyframe.timestamp();
                float passed_progress = 0;
                if (duration_between > 0) {
                    passed_progress = Mth.clamp(
                        passed_time_since_current / duration_between, 
                        0.0F, 1.0F);
                }
                nextKeyframe.interpolation()
                    .apply(temp_buffer, passed_progress, keyframes, currentKeyframeIndx, nextKeyframeIndx, interpolation_scale);
                channel.target().apply(part, temp_buffer);
                model.adjustAnimatedPart(part, dog);
                //ChopinLogger.l("Anim : " + current_pos);
            }
        }

   }

   public static void animate(AnimatedSyncedAccessoryModel model, Dog dog,
        AnimationDefinition animation, long elapsed_in_millis, float interpolation_scale, 
        Vector3f temp_buffer) {
        float elapsed_in_seconds = getElapsedSeconds(animation, elapsed_in_millis);

        for(var entry : animation.boneAnimations().entrySet()) {
            var partOptional = model.searchForPartWithName(entry.getKey());
            if (partOptional.isEmpty()) continue;
            var part = partOptional.get();
            model.resetPart(part, dog);
            var channelList = entry.getValue();
            for (var channel : channelList) {
                var keyframes = channel.keyframes();
                int currentKeyframeIndx = Math.max(0, Mth.binarySearch(0, keyframes.length, (compare_indx) -> {
                    return elapsed_in_seconds <= keyframes[compare_indx].timestamp();
                }) - 1);
                int nextKeyframeIndx = Math.min(keyframes.length - 1, currentKeyframeIndx + 1);
                Keyframe currentKeyframe = keyframes[currentKeyframeIndx];
                Keyframe nextKeyframe = keyframes[nextKeyframeIndx];
                float passed_time_since_current = elapsed_in_seconds - currentKeyframe.timestamp();
                float duration_between = nextKeyframe.timestamp() - currentKeyframe.timestamp();
                float passed_progress = 0;
                if (duration_between > 0) {
                    passed_progress = Mth.clamp(
                        passed_time_since_current / duration_between, 
                        0.0F, 1.0F);
                }
                nextKeyframe.interpolation()
                    .apply(temp_buffer, passed_progress, keyframes, currentKeyframeIndx, nextKeyframeIndx, interpolation_scale);
                channel.target().apply(part, temp_buffer);
                //ChopinLogger.l("Anim : " + current_pos);
            }
        }

   }

   public static void animateSimple(SimpleAnimatedModel model,
        AnimationDefinition animation, long elapsed_in_millis, float interpolation_scale, 
        Vector3f temp_buffer) {
        float elapsed_in_seconds = getElapsedSeconds(animation, elapsed_in_millis);

        for(var entry : animation.boneAnimations().entrySet()) {
            var partOptional = model.getPartFromName(entry.getKey());
            if (partOptional.isEmpty()) continue;
            var part = partOptional.get();
            model.resetPart(part);
            var channelList = entry.getValue();
            for (var channel : channelList) {
                var keyframes = channel.keyframes();
                int currentKeyframeIndx = Math.max(0, Mth.binarySearch(0, keyframes.length, (compare_indx) -> {
                    return elapsed_in_seconds <= keyframes[compare_indx].timestamp();
                }) - 1);
                int nextKeyframeIndx = Math.min(keyframes.length - 1, currentKeyframeIndx + 1);
                Keyframe currentKeyframe = keyframes[currentKeyframeIndx];
                Keyframe nextKeyframe = keyframes[nextKeyframeIndx];
                float passed_time_since_current = elapsed_in_seconds - currentKeyframe.timestamp();
                float duration_between = nextKeyframe.timestamp() - currentKeyframe.timestamp();
                float passed_progress = 0;
                if (duration_between > 0) {
                    passed_progress = Mth.clamp(
                        passed_time_since_current / duration_between, 
                        0.0F, 1.0F);
                }
                nextKeyframe.interpolation()
                    .apply(temp_buffer, passed_progress, keyframes, currentKeyframeIndx, nextKeyframeIndx, interpolation_scale);
                channel.target().apply(part, temp_buffer);
                //ChopinLogger.l("Anim : " + current_pos);
            }
        }

   }

   public static float getCurrentAnimatedYRot(Dog dog,
        AnimationDefinition animation, long elapsed_in_millis, float interpolation_scale) {
        float elapsed_in_seconds = getElapsedSeconds(animation, elapsed_in_millis);

        var rootChannelList = animation.boneAnimations().get("root");
        if (rootChannelList == null || rootChannelList.isEmpty())
            return 0f;
        
        AnimationChannel rotationChannel = null;
        for (var channel : rootChannelList) {
            if (channel.target() == Targets.ROTATION)
                rotationChannel = channel;
        }
        if (rotationChannel == null)
            return 0f;

        var keyframes = rotationChannel.keyframes();
        int currentKeyframeIndx = Math.max(0, Mth.binarySearch(0, keyframes.length, (compare_indx) -> {
            return elapsed_in_seconds <= keyframes[compare_indx].timestamp();
        }) - 1);
        int nextKeyframeIndx = Math.min(keyframes.length - 1, currentKeyframeIndx + 1);
        Keyframe currentKeyframe = keyframes[currentKeyframeIndx];
        Keyframe nextKeyframe = keyframes[nextKeyframeIndx];
        float passed_time_since_current = elapsed_in_seconds - currentKeyframe.timestamp();
        float duration_between = nextKeyframe.timestamp() - currentKeyframe.timestamp();
        float passed_progress = 0;
        if (duration_between > 0) {
            passed_progress = Mth.clamp(
                passed_time_since_current / duration_between, 
                0.0F, 1.0F);
        }
        var result = new Vector3f(0, 0, 0);
        nextKeyframe.interpolation()
            .apply(result, passed_progress, keyframes, currentKeyframeIndx, nextKeyframeIndx, interpolation_scale);
        return result.y;
   }

   private static float getElapsedSeconds(AnimationDefinition animation, long raw_seconds) {
      float f = (float)raw_seconds / 1000.0F;
      return animation.looping() ? f % animation.lengthInSeconds() : f;
   }
}
