package doggytalents.common.forward_imitate;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Suppliers;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

public class CodecUtil_1_20_under {
    
    public static <T extends StringRepresentable> Codec<T> 
        stringRepresentableCodecFromvValues(Supplier<T[]> valueSupplier) {
        
        var values = (T[]) valueSupplier.get();
        final var map = Arrays.stream(values)
            .collect(Collectors.toMap(
                StringRepresentable::getSerializedName,
                Function.identity()
            ));
        return ExtraCodecs.stringResolverCodec(
            StringRepresentable::getSerializedName, 
            map::get);
    }

    public static <A> Codec<A> recursive(final String name, final Function<Codec<A>, Codec<A>> wrapped) {
        return new RecursiveCodec<>(name, wrapped);
    }

    private static class RecursiveCodec<T> implements Codec<T> {
        private final String name;
        private final Supplier<Codec<T>> wrapped;

        private RecursiveCodec(final String name, final Function<Codec<T>, Codec<T>> wrapped) {
            this.name = name;
            this.wrapped = Suppliers.memoize(() -> wrapped.apply(this));
        }

        @Override
        public <S> DataResult<Pair<T, S>> decode(final DynamicOps<S> ops, final S input) {
            return wrapped.get().decode(ops, input);
        }

        @Override
        public <S> DataResult<S> encode(final T input, final DynamicOps<S> ops, final S prefix) {
            return wrapped.get().encode(input, ops, prefix);
        }

        @Override
        public String toString() {
            return "RecursiveCodec[" + name + ']';
        }
    }
    
}
