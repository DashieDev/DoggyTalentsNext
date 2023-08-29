package doggytalents.api.enu.forward_imitate.anim;

import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Keyframe {

    public final float timestamp;
    public final Vector3f target;
    public final AnimationChannel.Interpolation interpolation;

    public Keyframe(float timestamp, Vector3f target, AnimationChannel.Interpolation interpolation) {
        this.timestamp = timestamp;
        this.target = target;
        this.interpolation = interpolation;
    }

    public final float timestamp() { return this.timestamp; }
    public final Vector3f target() { return this.target; }
    public final AnimationChannel.Interpolation interpolation() {
        return this.interpolation;
    }
}