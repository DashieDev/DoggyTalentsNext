package doggytalents.forge_imitate.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingDropsEvent extends Event {
    
    private final LivingEntity killed;
    private final DamageSource source;
    
    public LivingDropsEvent(LivingEntity killed, DamageSource source) {
        this.killed = killed;
        this.source = source;
    }

    public LivingEntity getEntity() {
        return this.killed;
    }

    public DamageSource getSource() {
        return this.source;
    }
}
