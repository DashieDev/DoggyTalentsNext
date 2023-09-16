package doggytalents.common.entity.serializers;

import doggytalents.api.feature.DogSize;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class DogSizeSerializer implements EntityDataSerializer<DogSize> {

    @Override
    public void write(FriendlyByteBuf buf, DogSize val) {
        buf.writeByte(val.getId());
    }

    @Override
    public DogSize read(FriendlyByteBuf buf) {
        return DogSize.fromId(buf.readByte());
    }

    @Override
    public DogSize copy(DogSize val) {
        return val;
    }
}
