package doggytalents.api.registry;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class TalentOption<T> {
    
    private final EntityDataSerializer<T> serializer;
    private final Supplier<T> defaultSup;

    public TalentOption(EntityDataSerializer<T> ser, Supplier<T> defaultSup) {
        this.serializer = ser;
        this.defaultSup = defaultSup;
    }

    public EntityDataSerializer<T> getSerializer() {
        return this.serializer;
    }

    public T getDefault() {
        return this.defaultSup.get();
    }

    public void encode(FriendlyByteBuf buf, T val) {
        this.serializer.codec().encode((RegistryFriendlyByteBuf) buf, val);
    }

    public T decode(FriendlyByteBuf buf) {
        return this.serializer.codec().decode((RegistryFriendlyByteBuf) buf);
    }

    public static class BooleanOption extends TalentOption<Boolean> {

        public BooleanOption() {
            super(EntityDataSerializers.BOOLEAN, () -> false);
        }

    }

}
