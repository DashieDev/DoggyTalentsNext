package doggytalents.common.entity.serializers;

import doggytalents.common.entity.texture.DogSkinData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class DogSkinDataSerializer implements EntityDataSerializer<DogSkinData> {

    @Override
    public void write(FriendlyByteBuf buf, DogSkinData data) {
        buf.writeInt(data.getVersion().getId());
        buf.writeUtf(data.getHash(), 128);
    }

    @Override
    public DogSkinData read(FriendlyByteBuf buf) {
        int version = buf.readInt();
        String hash = buf.readUtf(128);
        return new DogSkinData(hash, DogSkinData.Version.fromId(version));
    }

    @Override
    public DogSkinData copy(DogSkinData oldData) {
        return oldData.copy();
    }
    
}
