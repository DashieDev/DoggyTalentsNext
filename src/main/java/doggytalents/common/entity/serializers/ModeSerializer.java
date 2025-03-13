package doggytalents.common.entity.serializers;

import doggytalents.api.feature.DogMode;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ModeSerializer extends DogSerializer<DogMode> {

    @Override
    public void write(FriendlyByteBuf buf, DogMode value) {
        buf.writeByte(value.getIndex());
    }

    @Override
    public DogMode read(FriendlyByteBuf buf) {
        return DogMode.byIndex(buf.readByte());
    }

    @Override
    public DogMode copy(DogMode value) {
        return value;
    }

}
