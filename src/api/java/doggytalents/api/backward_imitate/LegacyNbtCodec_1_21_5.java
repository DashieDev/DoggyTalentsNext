package doggytalents.api.backward_imitate;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Lifecycle;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class LegacyNbtCodec_1_21_5 {

    public static <T> Codec<T> create(BiFunction<T, CompoundTag_1_21_5, CompoundTag_1_21_5> encoder, 
        Function<CompoundTag_1_21_5, T> decoder) {
        
        return Codec.of(encoder(encoder), decoder(decoder));
    }

    private static <A> Encoder<A> encoder(BiFunction<A, CompoundTag_1_21_5, CompoundTag_1_21_5> serializer) {
        return new Encoder<A>() {
            @Override
            public <T> DataResult<T> encode(final A input, final DynamicOps<T> ops, final T prefix) {
                if (prefix instanceof CompoundTag tag) {
                    serializer.apply(input, CompoundTag_1_21_5.wrap(tag));
                    return DataResult.success(prefix, Lifecycle.stable());
                }
                var serialized_tag = serializer.apply(input, CompoundTag_1_21_5.createEmpty());
                var serialized_map = NbtOps.INSTANCE.getMap(serialized_tag.wrapped());

                var prefix_tag = ops.convertTo(NbtOps.INSTANCE, prefix);
                var ret_tag = serialized_map.flatMap(x -> NbtOps.INSTANCE.mergeToMap(prefix_tag, x));
                
                var ret_data = ret_tag.map(x -> NbtOps.INSTANCE.convertTo(ops, x));
                
                return ret_data.setLifecycle(Lifecycle.stable());
            }
        };
    }

    private static <A> Decoder<A> decoder(Function<CompoundTag_1_21_5, A> deserialzier) {
        return new Decoder<A>() {
            @Override
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
                if (input instanceof CompoundTag input_compound) {
                    var ret = deserialzier.apply(CompoundTag_1_21_5.wrap(input_compound));
                    return DataResult.success(Pair.of(ret, input), Lifecycle.stable());
                }
                
                var input_tag = ops.convertTo(NbtOps.INSTANCE, input);
                if (!(input_tag instanceof CompoundTag input_compound))
                    return DataResult.error(() -> "Bad Input!");
                
                var ret = deserialzier.apply(CompoundTag_1_21_5.wrap(input_compound));
                return DataResult.success(Pair.of(ret, input), Lifecycle.stable());
            }
        };
    }

    public static <T extends SavedData> SavedDataType<T> createSavedDataType(String name, 
        Supplier<T> creator, BiFunction<T, CompoundTag_1_21_5, CompoundTag_1_21_5> encoder, 
        Function<CompoundTag_1_21_5, T> decoder) {
        
        var codec = create(encoder, decoder);
        return new SavedDataType<>(name, creator, codec);
    }
}
