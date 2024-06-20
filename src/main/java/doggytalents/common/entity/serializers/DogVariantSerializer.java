package doggytalents.common.entity.serializers;

import doggytalents.common.util.NetworkUtil;
import doggytalents.common.variant.DogVariant;
import net.minecraft.network.FriendlyByteBuf;

public class DogVariantSerializer extends DogSerializer<DogVariant> {

    @Override
    public void write(FriendlyByteBuf buf, DogVariant val) {
        NetworkUtil.writeDogVariantToBuf(buf, val);
    }

    @Override
    public DogVariant read(FriendlyByteBuf buf) {
        return NetworkUtil.readDogVariantFromBuf(buf);
    }

    @Override
    public DogVariant copy(DogVariant val) {
        return val;
    }
    
}
