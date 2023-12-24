package doggytalents.common.entity.serializers;

import doggytalents.common.entity.texture.DogSkinData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;

public class DogSkinDataSerializer implements IDataSerializer<DogSkinData> {

    @Override
    public void write(PacketBuffer buf, DogSkinData data) {
        buf.writeInt(data.getVersion().getId());
        buf.writeUtf(data.getHash(), 128);
    }

    @Override
    public DogSkinData read(PacketBuffer buf) {
        int version = buf.readInt();
        String hash = buf.readUtf(128);
        return new DogSkinData(hash, DogSkinData.Version.fromId(version));
    }

    @Override
    public DogSkinData copy(DogSkinData oldData) {
        return oldData.copy();
    }
    
}
