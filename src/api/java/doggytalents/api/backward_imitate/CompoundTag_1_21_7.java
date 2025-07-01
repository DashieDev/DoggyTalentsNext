package doggytalents.api.backward_imitate;

import java.util.Objects;
import java.util.UUID;

import net.minecraft.core.UUIDUtil;
import net.minecraft.world.level.storage.ValueOutput;

public class CompoundTag_1_21_7 {
    
    private final ValueOutput wrapped;

    public static CompoundTag_1_21_7 wrap(ValueOutput wrapped) {
        Objects.requireNonNull(wrapped, "Optional Wrapped ValueInput must not be null");
        return new CompoundTag_1_21_7(wrapped);
    }

    private CompoundTag_1_21_7(ValueOutput wrapped) {
        this.wrapped = wrapped;
    }

    public void putUUID(String id, UUID val) {
        this.wrapped.store(id, UUIDUtil.CODEC, val);
    }
}
