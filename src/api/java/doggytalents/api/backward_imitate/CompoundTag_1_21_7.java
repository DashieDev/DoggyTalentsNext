package doggytalents.api.backward_imitate;

import java.util.Objects;
import java.util.UUID;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.storage.ValueOutput;

public interface CompoundTag_1_21_7 {

    public static CompoundTag_1_21_7 wrap(ValueOutput wrapped) {
        Objects.requireNonNull(wrapped, "Optional Wrapped ValueInput must not be null");
        return new CompoundTag_1_21_7.WrapValueOutput(wrapped);
    }

    public static CompoundTag_1_21_7 wrap(CompoundTag wrapped) {
        Objects.requireNonNull(wrapped, "Optional Wrapped CompoundTag must not be null");
        return new CompoundTag_1_21_7.WrapCompoundTag(wrapped);
    }

    public void putBoolean(String id, boolean val);

    public void putByte(String id, byte val);
    
    public void putInt(String id, int val);

    public void putLong(String id, long val);

    public void putDouble(String id, double val);

    public void putIntArray(String id, int[] val);

    public void putString(String id, String val);

    public void putUUID(String id, UUID val);

    public void put(String id, Tag val);

    //public void remove(String id);

    public static class WrapCompoundTag implements CompoundTag_1_21_7 {

        private final CompoundTag wrapped;

        private WrapCompoundTag(CompoundTag wrapped) {
            this.wrapped = wrapped;
        }

        public void putBoolean(String id, boolean val) {
            this.wrapped.putBoolean(id, val);
        }

        public void putByte(String id, byte val) {
            this.wrapped.putByte(id, val);
        }
        
        public void putInt(String id, int val) {
            this.wrapped.putInt(id, val);
        }

        public void putLong(String id, long val) {
            this.wrapped.putLong(id, val);
        }

        public void putDouble(String id, double val) {
            this.wrapped.putDouble(id, val);
        }

        public void putIntArray(String id, int[] val) {
            this.wrapped.putIntArray(id, val);
        }

        public void putString(String id, String val) {
            this.wrapped.putString(id, val);
        }

        public void putUUID(String id, UUID val) {
            this.wrapped.store(id, UUIDUtil.CODEC, val);
        }

        public void put(String id, Tag val) {
            this.wrapped.put(id, val);
        }
    }

    public static class WrapValueOutput implements CompoundTag_1_21_7 {

        private final ValueOutput wrapped;

        private WrapValueOutput(ValueOutput wrapped) {
            this.wrapped = wrapped;
        }

        public void putBoolean(String id, boolean val) {
            this.wrapped.putBoolean(id, val);
        }

        public void putByte(String id, byte val) {
            this.wrapped.putByte(id, val);
        }
        
        public void putInt(String id, int val) {
            this.wrapped.putInt(id, val);
        }

        public void putLong(String id, long val) {
            this.wrapped.putLong(id, val);
        }

        public void putDouble(String id, double val) {
            this.wrapped.putDouble(id, val);
        }

        public void putIntArray(String id, int[] val) {
            this.wrapped.putIntArray(id, val);
        }

        public void putString(String id, String val) {
            this.wrapped.putString(id, val);
        }

        public void putUUID(String id, UUID val) {
            this.wrapped.store(id, UUIDUtil.CODEC, val);
        }

        public void put(String id, Tag val) {
            ValueOutputUtil_1_21_7.outputTagTo(wrapped, id, val);
        }
    }
}
