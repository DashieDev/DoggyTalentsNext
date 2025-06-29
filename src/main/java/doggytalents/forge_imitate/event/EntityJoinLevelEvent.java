package doggytalents.forge_imitate.event;

import net.minecraft.world.entity.LivingEntity;

public class EntityJoinLevelEvent extends Event {
    
    private final LivingEntity entity;
    private final boolean loadedFromDisk;

    public EntityJoinLevelEvent(LivingEntity entity, boolean loadedFromDisk) {
        this.entity = entity;
        this.loadedFromDisk = loadedFromDisk;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public boolean loadedFromDisk() {
        return loadedFromDisk;
    }
}
