package doggytalents.forge_imitate.event;

import net.minecraft.world.entity.LivingEntity;

public class EntityJoinLevelEvent extends Event {
    
    private LivingEntity entity;

    public EntityJoinLevelEvent(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

}
