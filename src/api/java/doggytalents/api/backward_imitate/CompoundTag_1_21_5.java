package doggytalents.api.backward_imitate;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;

public class CompoundTag_1_21_5 {
    
    private final CompoundTag wrapped;

    public static CompoundTag_1_21_5 wrap(CompoundTag wrapped) {
        Objects.requireNonNull(wrapped, "Wrapped Compound Tag must not be null");
        return new CompoundTag_1_21_5(wrapped);
    }

    public static CompoundTag_1_21_5 wrap(Optional<CompoundTag> wrappedOptional) {
        Objects.requireNonNull(wrappedOptional, "Optional Wrapped Compound Tag must not be null");
        return new CompoundTag_1_21_5(wrappedOptional.orElse(new CompoundTag()));
    }

    public static CompoundTag_1_21_5 createEmpty() {
        return new CompoundTag_1_21_5(new CompoundTag());
    }

    private CompoundTag_1_21_5(CompoundTag wrapped) {
        this.wrapped = wrapped;
    }

    public CompoundTag wrapped() {
        return this.wrapped;
    }

    public boolean contains(String id, int typeId) {
        var tag = this.wrapped.get(id);
        if (tag == null)
            return false;
        return tag.getId() == typeId;
    }

    public boolean contains(String id) {
        return this.wrapped.get(id) != null;
    }

    public boolean containsAnyNumeric(String id) {
        return this.wrapped.get(id) instanceof NumericTag;
    }

    public boolean getBoolean(String id) {
        return this.wrapped.getBooleanOr(id, false);
    }

    public byte getByte(String id) {
        return this.wrapped.getByteOr(id, (byte) 0);
    }

    public int getInt(String id) {
        return this.wrapped.getIntOr(id, 0);
    }

    public long getLong(String id) {
        return this.wrapped.getLongOr(id, 0L);
    }

    public float getFloat(String id) {
        return this.wrapped.getFloatOr(id, 0f);
    }

    public double getDouble(String id) {
        return this.wrapped.getDoubleOr(id, 0);
    }

    public int[] getIntArray(String id) {
        return this.wrapped.getIntArray(id).orElse(new int[0]);
    }

    public String getString(String id) {
        return this.wrapped.getStringOr(id, "");
    }

    public UUID getUUID(String id) {
        return this.wrapped.read(id, UUIDUtil.CODEC).orElseThrow();
    }
    
    public boolean hasUUID(String id) {
        return this.wrapped.read(id, UUIDUtil.CODEC).isPresent();
    }

    public ListTag_1_21_5 getList(String id, int type) {
        var list_tag = this.wrapped.getListOrEmpty(id);
        return ListTag_1_21_5.wrap(list_tag);
    }

    public CompoundTag_1_21_5 getCompound(String id) {
        return CompoundTag_1_21_5.wrap(this.wrapped.getCompoundOrEmpty(id));
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

    public void remove(String id) {
        this.wrapped.remove(id);
    }

}
