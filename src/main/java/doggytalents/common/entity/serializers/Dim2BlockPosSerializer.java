package doggytalents.common.entity.serializers;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;

public class Dim2BlockPosSerializer extends DogSerializer<Dimension2BlockPosMap> {

    @Override
    public void write(FriendlyByteBuf buf, Dimension2BlockPosMap value) {
        buf.writeInt(value.size());
        for (var entry : value.entrySet()) {
            buf.writeResourceLocation(entry.getKey().location());
            EntityDataSerializers.BLOCK_POS.codec().encode((RegistryFriendlyByteBuf) buf, entry.getValue());
        }
    }

    @Override
    public Dimension2BlockPosMap read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        var value = new Dimension2BlockPosMap();
        for (int i = 0; i < size; i++) {
            var loc = buf.readResourceLocation();
            var pos = EntityDataSerializers.BLOCK_POS.codec().decode((RegistryFriendlyByteBuf)buf);
            
            var res_key = ResourceKey.create(Registries.DIMENSION, loc);
            value.put(res_key, pos);
        }

        return value;
    }

    @Override
    public Dimension2BlockPosMap copy(Dimension2BlockPosMap value) {
        return value.copy();
    }
}
