package doggytalents.api.enu.forward_imitate.anim;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.compress.utils.Lists;

@OnlyIn(Dist.CLIENT)
public class AnimationDefinition {

   public final float lengthInSeconds;
   public final boolean looping;
   public final Map<String, List<AnimationChannel>> boneAnimations;

   public AnimationDefinition(float lengthInSeconds, boolean looping, Map<String, List<AnimationChannel>> boneAnimations) {
      this.lengthInSeconds = lengthInSeconds;
      this.looping = looping;
      this.boneAnimations = boneAnimations;
   }

   public final float lengthInSeconds() { return this.lengthInSeconds; }
   public final boolean looping() {return this.looping; }
   public final Map<String, List<AnimationChannel>> boneAnimations() { 
      return this.boneAnimations;
   }

   
   @OnlyIn(Dist.CLIENT)
   public static class Builder {
      private final float length;
      private final Map<String, List<AnimationChannel>> animationByBone = Maps.newHashMap();
      private boolean looping;

      public static AnimationDefinition.Builder withLength(float p_232276_) {
         return new AnimationDefinition.Builder(p_232276_);
      }

      private Builder(float p_232273_) {
         this.length = p_232273_;
      }

      public AnimationDefinition.Builder looping() {
         this.looping = true;
         return this;
      }

      public AnimationDefinition.Builder addAnimation(String p_232280_, AnimationChannel p_232281_) {
         this.animationByBone.computeIfAbsent(p_232280_, (p_232278_) -> {
            return Lists.newArrayList();
         }).add(p_232281_);
         return this;
      }

      public AnimationDefinition build() {
         return new AnimationDefinition(this.length, this.looping, this.animationByBone);
      }
   }
}