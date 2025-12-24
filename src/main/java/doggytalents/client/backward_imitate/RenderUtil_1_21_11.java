package doggytalents.client.backward_imitate;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;

public class RenderUtil_1_21_11 {
    
    public static Entity getCameraEntity(EntityRenderDispatcher dispatcher) {
        return dispatcher.camera.entity();
    }

}
