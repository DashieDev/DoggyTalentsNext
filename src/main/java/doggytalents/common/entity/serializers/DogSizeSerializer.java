package doggytalents.common.entity.serializers;

import doggytalents.api.feature.DogSize;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;

public class DogSizeSerializer implements IDataSerializer<DogSize> {

    @Override
    public void write(PacketBuffer buf, DogSize val) {
        buf.writeByte(val.getId());
    }

    @Override
    public DogSize read(PacketBuffer buf) {
        return DogSize.fromId(buf.readByte());
    }

    @Override
    public DogSize copy(DogSize val) {
        return val;
    }
}
