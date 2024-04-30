package doggytalents.common.entity.serializers;

import java.util.ArrayList;
import java.util.List;

import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.util.NetworkUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class DoggyArtifactsSerializer extends DogSerializer<List<DoggyArtifactItem>> {

    @Override
    public void write(FriendlyByteBuf buf, List<DoggyArtifactItem> value) {
        buf.writeInt(value.size());
        for (var x : value) {
            NetworkUtil.writeRegistryId(buf, Registries.ITEM, x);
        }
    }

    @Override
    public List<DoggyArtifactItem> read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        var list = new ArrayList<DoggyArtifactItem>(size);
        for (int i = 0; i < size; ++i) {
            var item = NetworkUtil.readRegistryId(buf, Registries.ITEM);
            if (item instanceof DoggyArtifactItem artifactItem) {
                list.add(artifactItem);
            }
        }
        return list;
    }

    @Override
    public List<DoggyArtifactItem> copy(List<DoggyArtifactItem> value) {
        List<DoggyArtifactItem> newInst = new ArrayList<>(value.size());

        for (var inst : value) {
            newInst.add(inst);
        }

        return newInst;
    }
}
