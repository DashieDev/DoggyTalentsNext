package doggytalents.client.entity.model.animation;

import doggytalents.client.entity.model.AnimatedSyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.function.Consumers;
import org.joml.Vector3f;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DogKeyframeAnimations {
   public static void animate(DogModel model, Dog dog,
        AnimationDefinition animation, long elapsed_in_millis, float interpolation_scale, 
        Vector3f temp_buffer) {
        keyframeAnimate(AnimationContext.of(
            model::searchForPartWithName, 
            part -> model.resetPart(part, dog),
            part -> model.adjustAnimatedPart(part, dog)), 
            animation, elapsed_in_millis, interpolation_scale, temp_buffer);
   }

   public static void animate(AnimatedSyncedAccessoryModel model, Dog dog,
        AnimationDefinition animation, long elapsed_in_millis, float interpolation_scale, 
        Vector3f temp_buffer) {
        keyframeAnimate(AnimationContext.of(
            model::searchForPartWithName, 
            part -> model.resetPart(part, dog)), 
            animation, elapsed_in_millis, interpolation_scale, temp_buffer);
   }

   public static void animateSimple(SimpleAnimatedModel model,
        AnimationDefinition animation, long elapsed_in_millis, float interpolation_scale, 
        Vector3f temp_buffer) {
        keyframeAnimate(AnimationContext.of(
            model::getPartFromName, model::resetPart), 
            animation, elapsed_in_millis, interpolation_scale, temp_buffer);
   }

   public static float getCurrentAnimatedYRot(Dog dog,
        AnimationDefinition animation, long elapsed_in_millis, float swing) {
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

        var result = new Vector3f(0, 0, 0);
        getAnimationValueForChannel(result, rotationChannel, elapsed_in_seconds, swing);
        return result.y;
   }

    public static interface AnimationContext {

        public static AnimationContext of(
            Function<String, Optional<ModelPart>> partGetter,
            Consumer<ModelPart> partReset
        ) {
            return of(partGetter, partReset, Consumers.nop());
        }

        public static AnimationContext of(
            Function<String, Optional<ModelPart>> partGetter,
            Consumer<ModelPart> partReset,
            Consumer<ModelPart> partAdjust
        ) {
            return new AnimationContext() {

                @Override
                public Optional<ModelPart> getPart(String name) {
                    return partGetter.apply(name);
                }

                @Override
                public void resetPart(ModelPart part) {
                    partReset.accept(part);
                }

                @Override
                public Vector3f adjustAnimationValue(ModelPart target, Vector3f current) {
                    return current;
                }

                @Override
                public void adjustAnimatedPart(ModelPart part) {
                    partAdjust.accept(part);
                }
                
            };
        }

        public static AnimationContext of(
            Function<String, Optional<ModelPart>> partGetter,
            Consumer<ModelPart> partReset,
            BiFunction<ModelPart, Vector3f, Vector3f> animValueAdjust,
            Consumer<ModelPart> partAdjust
        ) {
            return new AnimationContext() {

                @Override
                public Optional<ModelPart> getPart(String name) {
                    return partGetter.apply(name);
                }

                @Override
                public void resetPart(ModelPart part) {
                    partReset.accept(part);
                }

                @Override
                public Vector3f adjustAnimationValue(ModelPart target, Vector3f current) {
                    return animValueAdjust.apply(target, current);
                }

                @Override
                public void adjustAnimatedPart(ModelPart part) {
                    partAdjust.accept(part);
                }
                
            };
        }
        
        public Optional<ModelPart> getPart(String name);
        public void resetPart(ModelPart part);
        public Vector3f adjustAnimationValue(ModelPart target, Vector3f current);
        public void adjustAnimatedPart(ModelPart part);

    }

    public static void keyframeAnimate(AnimationContext context, 
        AnimationDefinition animation, long elapsed_in_millis, 
        float swing, Vector3f buffer) {
        
        float elapsed_in_seconds = getElapsedSeconds(animation, elapsed_in_millis);
        for(var entry : animation.boneAnimations().entrySet()) {
            var partOptional = context.getPart(entry.getKey());
            if (partOptional.isEmpty()) continue;
            var part = partOptional.get();
            context.resetPart(part);
            var channelList = entry.getValue();
            for (var channel : channelList) {
                getAnimationValueForChannel(buffer, channel, elapsed_in_seconds, swing);
                channel.target().apply(part, buffer);
                context.adjustAnimatedPart(part);
            }
        }

    }

    public static void getAnimationValueForChannel(Vector3f buffer, AnimationChannel channel,
        float elapsed_seconds, float swing) {
        
        var keyframes = channel.keyframes();
        if (keyframes.length <= 0)
            return;

        int kf_indx_now = Mth.binarySearch(0, keyframes.length, (compare_indx) -> {
            return elapsed_seconds <= keyframes[compare_indx].timestamp();
        }) - 1;
        kf_indx_now = Mth.clamp(kf_indx_now, 0, keyframes.length - 1);
        int kf_indx_next = Mth.clamp(kf_indx_now + 1, 0, keyframes.length - 1);
        var kf_now = keyframes[kf_indx_now];
        var kf_next = keyframes[kf_indx_next];
        float time_between = kf_next.timestamp() - kf_now.timestamp();
        float t_since_kf_now = elapsed_seconds - kf_now.timestamp();
        float progress = 0;
        if (time_between > 0) {
            progress = Mth.clamp(t_since_kf_now / time_between, 0.0F, 1.0F);
        }
        kf_next.interpolation()
            .apply(buffer, progress, keyframes, kf_indx_now, kf_indx_next, swing);
    }

    public static float getElapsedSeconds(AnimationDefinition animation, long raw_millis) {
        float f = (float)raw_millis / 1000.0F;
        return animation.looping() ? f % animation.lengthInSeconds() : f;
    }

    public static Optional<ModelPart> searchForPartWithName(ModelPart root, String name) {
        return searchForPartWithName(root, name, true);
    }

    public static Optional<ModelPart> searchForPartWithName(ModelPart root, String name, boolean check_root) {
        if (root.hasChild(name)) 
            return Optional.of(root.getChild(name));
        if (check_root && name.equals("root"))
            return Optional.of(root);
        var partOptional = root.getAllParts()
            .filter(part -> part.hasChild(name))
            .findFirst();
        return partOptional.map(part -> part.getChild(name));
    }
}
