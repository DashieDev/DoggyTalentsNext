package doggytalents.common.network.packet.data;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.world.phys.Vec3;

public class DogExplosionData {
    
    public final int dogId;
    private Vec3 knockback;
    
    public DogExplosionData(int dogId, @Nullable Vec3 knockback) {
        this.dogId = dogId;
        this.knockback = knockback;
    }

    public Optional<Vec3> knockback() {
        return Optional.ofNullable(knockback);
    }

}
