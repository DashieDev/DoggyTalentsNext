package doggytalents.forge_imitate.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingHurtEvent extends Event {
    
    private LivingEntity living;
    private DamageSource source;
    private float amount;
    
    public LivingHurtEvent(LivingEntity living, DamageSource source, float amount) {
        this.living = living;
        this.source = source;
        this.amount = amount;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public DamageSource getSource() {
        return this.source;
    }

    public LivingEntity getEntity() {
        return this.living;
    }

}
