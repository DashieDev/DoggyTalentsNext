package doggytalents.common.entity.serializers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;

public abstract class DogSerializer<T> implements EntityDataSerializer<T> {
    
    protected StreamCodec<RegistryFriendlyByteBuf, T> codec =
        StreamCodec.of(this::write, this::read);

    public abstract void write(FriendlyByteBuf buf, T val);

    public abstract T read(FriendlyByteBuf buf);

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, T> codec() {
        return this.codec;
    }

}
