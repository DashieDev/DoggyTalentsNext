package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.Camera;

public class ComputeCameraAngles extends Event {
    
    private Camera camera;
    private float yRot, xRot;

    public ComputeCameraAngles(Camera camera, float yRot, float xRot) {
        this.xRot =xRot;
        this.yRot = yRot;
        this.camera = camera;
    }

    public void setYaw(float val) {
        this.yRot = val;
    }

    public void setPitch(float val) {
        this.xRot = val;
    }

    public float getYaw() {
        return this.yRot;
    }

    public float getPitch() {
        return this.xRot;
    }

    public Camera getCamera() {
        return this.camera;
    }
}
