package doggytalents.api.backward_imitate;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.world.entity.TamableAnimal;

public class EntityUtil_1_21_5 {
    
    public static @Nullable UUID getOwnerUUID(TamableAnimal entity) {
        var ref = entity.getOwnerReference();
        if (ref == null)
            return null;
        return ref.getUUID();
    }

}
