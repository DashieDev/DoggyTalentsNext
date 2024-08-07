package doggytalents.api.inferface;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.world.entity.LivingEntity;

public class InferTypeContext {
    
    private Optional<LivingEntity> ownerOptional;
    private boolean teleport;

    private InferTypeContext(LivingEntity owner, boolean teleport) {
        this.ownerOptional = Optional.ofNullable(owner);
        this.teleport = teleport;
    }

    public Optional<LivingEntity> owner() {
        return this.ownerOptional;
    }

    public boolean teleport() {
        return this.teleport;
    }

    public static InferTypeContext getDefault() {
        return new InferTypeContext(null, false);
    }

    public static InferTypeContext forTeleport() {
        return forTeleport(null);
    }
    
    public static InferTypeContext forTeleport(@Nullable LivingEntity owner) {
        return new InferTypeContext(owner, true);
    }

}
