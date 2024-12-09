package doggytalents.forge_imitate.event;

import net.minecraft.world.entity.LivingEntity;

public class LivingChangeTargetEvent extends Event {
    
    private final LivingEntity living;
    private final LivingEntity aboutToBeSetTarget;

    public LivingChangeTargetEvent(LivingEntity entity, LivingEntity aboutToBeSetTarget) {
        this.living = entity;
        this.aboutToBeSetTarget = aboutToBeSetTarget;
    }

    public LivingEntity getEntity() {
        return this.living;
    }

    public LivingEntity getNewAboutToBeSetTarget() {
        return this.aboutToBeSetTarget;
    }

}
