package doggytalents.api.enu.forward_imitate.anim;

import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KeyframeAnimations {

   public static Vector3f posVec(float p_232303_, float p_232304_, float p_232305_) {
      return new Vector3f(p_232303_, -p_232304_, p_232305_);
   }

   public static Vector3f degreeVec(float p_232332_, float p_232333_, float p_232334_) {
      return new Vector3f(p_232332_ * ((float)Math.PI / 180F), p_232333_ * ((float)Math.PI / 180F), p_232334_ * ((float)Math.PI / 180F));
   }

   public static Vector3f scaleVec(double p_232299_, double p_232300_, double p_232301_) {
      return new Vector3f((float)(p_232299_ - 1.0D), (float)(p_232300_ - 1.0D), (float)(p_232301_ - 1.0D));
   }
}
