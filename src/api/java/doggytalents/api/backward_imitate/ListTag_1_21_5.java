package doggytalents.api.backward_imitate;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class ListTag_1_21_5 implements Iterable<Tag> {
    
    private final ListTag wrapped;

    public static ListTag_1_21_5 wrap(ListTag wrapped) {
        Objects.requireNonNull(wrapped, "Wrapped List Tag must not be null");
        return new ListTag_1_21_5(wrapped);
    }

    public static ListTag_1_21_5 wrap(Optional<ListTag> wrappedOptional) {
        Objects.requireNonNull(wrappedOptional, "Optional Wrapped List Tag must not be null");
        return new ListTag_1_21_5(wrappedOptional.orElse(new ListTag()));
    }

    public static ListTag_1_21_5 createEmpty() {
        return new ListTag_1_21_5(new ListTag());
    }

    private ListTag_1_21_5(ListTag wrapped) {
        this.wrapped = wrapped;
    }

    public ListTag wrapped() {
        return this.wrapped;
    }

    public int size() {
        return this.wrapped.size();
    }

    public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    public CompoundTag_1_21_5 getCompound(int i) {
        return CompoundTag_1_21_5.wrap(this.wrapped.getCompoundOrEmpty(i));
    }

    @Override
    public Iterator<Tag> iterator() {
        return this.wrapped.iterator();
    }

}
