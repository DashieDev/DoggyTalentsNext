package doggytalents.client.backward_imitate;

import org.joml.Vector3f;

import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;

public class RenderUtil_1_21_11 {
    
    public static Entity getCameraEntity(EntityRenderDispatcher dispatcher) {
        return dispatcher.camera.entity();
    }

    public static class KeyFrameUtil_1_21_11 {
        public static Vector3f keyframeValue(Keyframe keyframe) {
            return new Vector3f(keyframe.postTarget());
        }
    }
}
