package doggytalents.forge_imitate.event;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EntityTravelToDimensionEvent extends Event {
    
    private Entity entity;
    private ResourceKey<Level> dim;

    public EntityTravelToDimensionEvent(Entity entity, ResourceKey<Level> dim) {
        this.entity = entity;
        this.dim = dim;
    }

    public ResourceKey<Level> getDimension() {
        return this.dim;
    }

    public Entity getEntity() {
        return this.entity;
    }

}
