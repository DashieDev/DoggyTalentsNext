package doggytalents.common.entity.serializers;

import doggytalents.api.feature.DogGender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class GenderSerializer extends DogSerializer<DogGender> {

    @Override
    public void write(FriendlyByteBuf buf, DogGender value) {
        buf.writeByte(value.getIndex());
    }

    @Override
    public DogGender read(FriendlyByteBuf buf) {
        return DogGender.byIndex(buf.readByte());
    }

    @Override
    public DogGender copy(DogGender value) {
        return value;
    }

}
