package doggytalents.common.entity.serializers;

import doggytalents.DoggyRegistries;
import doggytalents.common.variant.DogVariant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class DogVariantSerializer implements EntityDataSerializer<DogVariant> {

    @Override
    public void write(FriendlyByteBuf buf, DogVariant val) {
        buf.writeRegistryIdUnsafe(DoggyRegistries.DOG_VARIANT.get(), val);
    }

    @Override
    public DogVariant read(FriendlyByteBuf buf) {
        return buf.readRegistryIdUnsafe(DoggyRegistries.DOG_VARIANT.get());
    }

    @Override
    public DogVariant copy(DogVariant val) {
        return val;
    }
    
}
