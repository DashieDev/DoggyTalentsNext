package doggytalents.api.backward_imitate;

import java.util.Optional;

import net.minecraft.world.damagesource.DamageSource;

@FunctionalInterface
public interface HurtSuperCall {
    
    public boolean hurt(DamageSource source, Optional<Float> amount);

}
