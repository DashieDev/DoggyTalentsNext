package doggytalents.client.backward_imitate;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;

public class WrappedEntityRenderState_21_3<T extends Entity> extends EntityRenderState {
        
    public T entity;
    public WrappedEntityRenderState_21_3(T entity) {
        this.entity = entity;
    }

}