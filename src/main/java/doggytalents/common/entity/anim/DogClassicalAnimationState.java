package doggytalents.common.entity.anim;

import net.minecraft.util.Mth;

public class DogClassicalAnimationState {
    
    public static float shakeAngle(float animValue, float offset) {
        float anim_time_modified = (animValue + offset) * 0.56f;

        anim_time_modified = Mth.clamp(anim_time_modified, 0, 1);

        return Mth.sin(anim_time_modified * Mth.PI) 
            * Mth.sin(anim_time_modified * 11f * Mth.PI) 
            * 0.15f * Mth.PI;
    }

    public static float begAngle(float animValue) {
        return animValue * 0.15f * Mth.PI;
    }

    public static float wagAngle(float limbSwing, float limbSwingAmount, float partialTickTime) {
        return Mth.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
    }

}
