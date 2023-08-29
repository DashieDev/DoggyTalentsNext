package doggytalents.api.enu.forward_imitate.anim;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnimationChannel {

   private final AnimationChannel.Target target;
   private final Keyframe[] keyframes;

   public AnimationChannel(AnimationChannel.Target target, Keyframe... keyframes) {
      this.target = target;
      this.keyframes = keyframes;
   }

   public final AnimationChannel.Target target() { return this.target; }
   public final Keyframe[] keyframes() { return this.keyframes; }

   @OnlyIn(Dist.CLIENT)
   public interface Interpolation {
      Vector3f apply(Vector3f p_232223_, float p_232224_, Keyframe[] p_232225_, int p_232226_, int p_232227_, float p_232228_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Interpolations {
      public static final AnimationChannel.Interpolation LINEAR = (p_232241_, p_232242_, p_232243_, p_232244_, p_232245_, p_232246_) -> {
         Vector3f vector3f = p_232243_[p_232244_].target();
         Vector3f vector3f1 = p_232243_[p_232245_].target();
         p_232241_.set(Mth.lerp(p_232242_, vector3f.x(), vector3f1.x()) * p_232246_, Mth.lerp(p_232242_, vector3f.y(), vector3f1.y()) * p_232246_, Mth.lerp(p_232242_, vector3f.z(), vector3f1.z()) * p_232246_);
         return p_232241_;
      };
      public static final AnimationChannel.Interpolation CATMULLROM = (p_232234_, p_232235_, p_232236_, p_232237_, p_232238_, p_232239_) -> {
         Vector3f vector3f = p_232236_[Math.max(0, p_232237_ - 1)].target();
         Vector3f vector3f1 = p_232236_[p_232237_].target();
         Vector3f vector3f2 = p_232236_[p_232238_].target();
         Vector3f vector3f3 = p_232236_[Math.min(p_232236_.length - 1, p_232238_ + 1)].target();
         p_232234_.set(catmullrom(p_232235_, vector3f.x(), vector3f1.x(), vector3f2.x(), vector3f3.x()) * p_232239_, catmullrom(p_232235_, vector3f.y(), vector3f1.y(), vector3f2.y(), vector3f3.y()) * p_232239_, catmullrom(p_232235_, vector3f.z(), vector3f1.z(), vector3f2.z(), vector3f3.z()) * p_232239_);
         return p_232234_;
      };
   }

   public static float catmullrom(float p_216245_, float p_216246_, float p_216247_, float p_216248_, float p_216249_) {
      return 0.5F * (2.0F * p_216247_ + (p_216248_ - p_216246_) * p_216245_ + (2.0F * p_216246_ - 5.0F * p_216247_ + 4.0F * p_216248_ - p_216249_) * p_216245_ * p_216245_ + (3.0F * p_216247_ - p_216246_ - 3.0F * p_216248_ + p_216249_) * p_216245_ * p_216245_ * p_216245_);
   }


   @OnlyIn(Dist.CLIENT)
   public interface Target {
      void apply(DogModelPart p_232248_, Vector3f p_232249_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Targets {
      public static final AnimationChannel.Target POSITION = DogModelPart::offsetPos;
      public static final AnimationChannel.Target ROTATION = DogModelPart::offsetRotation;
      public static final AnimationChannel.Target SCALE = DogModelPart::offsetScale;
   }
}